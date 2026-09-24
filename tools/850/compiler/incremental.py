#!/usr/bin/env python3
from __future__ import annotations

import hashlib
import importlib.util
import json
import os
import shutil
import subprocess
import tempfile
from pathlib import Path
from typing import Iterable


HERE = Path(__file__).resolve().parent


def _load_local(filename: str, module_name: str):
    path = HERE / filename
    spec = importlib.util.spec_from_file_location(module_name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


_ABI = _load_local("abi.py", "fast_dev_abi")
_DEPS = _load_local("dependencies.py", "fast_dev_dependencies")


class CompileError(RuntimeError):
    def __init__(self, command: list[str], stdout: str, stderr: str):
        super().__init__(stderr.strip() or stdout.strip() or "javac failed")
        self.command = command
        self.stdout = stdout
        self.stderr = stderr


def _hash_bytes(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def _atomic_write_json(path: Path, data: object) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    fd, name = tempfile.mkstemp(prefix=path.name + ".", suffix=".tmp", dir=path.parent)
    try:
        with os.fdopen(fd, "w", encoding="utf-8", newline="\n") as stream:
            json.dump(data, stream, ensure_ascii=False, indent=2, sort_keys=True)
            stream.write("\n")
        os.replace(name, path)
    except Exception:
        try:
            os.unlink(name)
        except FileNotFoundError:
            pass
        raise


def _read_json(path: Path) -> dict:
    return json.loads(path.read_text(encoding="utf-8"))


class IncrementalCompiler:
    def __init__(
        self,
        *,
        source_root: Path,
        class_dir: Path,
        state_path: Path,
        dependency_index_path: Path,
        classpath: Iterable[Path | str],
        javac: str = "javac",
    ):
        self.source_root = Path(source_root)
        self.class_dir = Path(class_dir)
        self.state_path = Path(state_path)
        self.dependency_index_path = Path(dependency_index_path)
        self.classpath = [str(Path(p)) for p in classpath]
        self.javac = javac

    def _scan_sources(self) -> dict[str, dict[str, object]]:
        found: dict[str, dict[str, object]] = {}
        for path in sorted(self.source_root.rglob("*.java")):
            text = path.read_text(encoding="utf-8")
            rel = path.relative_to(self.source_root).as_posix()
            identity = _DEPS.source_identity(path, text)
            if identity in {row["identity"] for row in found.values()}:
                raise ValueError(f"duplicate source identity: {identity}")
            found[rel] = {
                "path": path,
                "identity": identity,
                "text": text,
                "hash": _hash_bytes(text.encode("utf-8")),
                "abi": _ABI.public_abi_fingerprint(text),
            }
        return found

    @staticmethod
    def _identity_map(sources: dict[str, dict[str, object]]) -> dict[str, dict[str, object]]:
        return {str(row["identity"]): row for row in sources.values()}

    @staticmethod
    def _family_paths(root: Path, identity: str) -> list[str]:
        package, _, simple = identity.rpartition(".")
        package_path = Path(*package.split(".")) if package else Path()
        directory = root / package_path
        if not directory.exists():
            return []
        result = []
        for path in directory.glob(simple + "*.class"):
            name = path.name
            if name == simple + ".class" or name.startswith(simple + "$"):
                result.append(path.relative_to(root).as_posix())
        return sorted(result)

    def _javac(self, source_paths: list[Path], output_dir: Path, *, include_overlay: bool) -> None:
        output_dir.mkdir(parents=True, exist_ok=True)
        cp = list(self.classpath)
        if include_overlay and self.class_dir.exists():
            cp.insert(0, str(self.class_dir))
        command = [
            self.javac,
            "-encoding", "UTF-8",
            "-source", "8",
            "-target", "8",
            "-d", str(output_dir),
        ]
        if cp:
            command += ["-classpath", os.pathsep.join(cp)]
        command += [str(path) for path in source_paths]
        proc = subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        if proc.returncode != 0:
            raise CompileError(command, proc.stdout, proc.stderr)

    def _build_state(self, sources: dict[str, dict[str, object]], class_root: Path) -> dict[str, object]:
        rows: dict[str, object] = {}
        for rel, row in sources.items():
            identity = str(row["identity"])
            family = self._family_paths(class_root, identity)
            if not family:
                raise RuntimeError(f"javac produced no class family for {identity}")
            rows[rel] = {
                "identity": identity,
                "hash": row["hash"],
                "abi": row["abi"],
                "class_family": family,
            }
        return {"schema": 1, "sources": rows}

    def _dependency_index(self, sources: dict[str, dict[str, object]]) -> dict[str, object]:
        by_identity = {str(row["identity"]): str(row["text"]) for row in sources.values()}
        return _DEPS.build_dependency_index(by_identity)

    def _publish_full(self, staging: Path) -> None:
        self.class_dir.parent.mkdir(parents=True, exist_ok=True)
        candidate = Path(tempfile.mkdtemp(prefix="classes.publish.", dir=self.class_dir.parent))
        shutil.rmtree(candidate)
        shutil.copytree(staging, candidate)
        self._swap_class_dir(candidate)

    def _swap_class_dir(self, candidate: Path) -> None:
        backup = self.class_dir.with_name(self.class_dir.name + ".previous")
        if backup.exists():
            shutil.rmtree(backup)
        had_old = self.class_dir.exists()
        try:
            if had_old:
                os.replace(self.class_dir, backup)
            os.replace(candidate, self.class_dir)
        except Exception:
            if not self.class_dir.exists() and backup.exists():
                os.replace(backup, self.class_dir)
            raise
        finally:
            if backup.exists():
                shutil.rmtree(backup)
            if candidate.exists():
                shutil.rmtree(candidate)

    def full_compile(self) -> dict[str, object]:
        sources = self._scan_sources()
        if not sources:
            raise ValueError("no Java sources under source_root")
        self.class_dir.parent.mkdir(parents=True, exist_ok=True)
        with tempfile.TemporaryDirectory(prefix="javac.full.", dir=self.class_dir.parent) as td:
            staging = Path(td)
            self._javac([Path(row["path"]) for row in sources.values()], staging, include_overlay=False)
            state = self._build_state(sources, staging)
            deps = self._dependency_index(sources)
            self._publish_full(staging)
        _atomic_write_json(self.state_path, state)
        _atomic_write_json(self.dependency_index_path, deps)
        identities = sorted(str(row["identity"]) for row in sources.values())
        return {"mode": "full", "compiled_identities": identities}

    def _load_previous(self) -> tuple[dict, dict]:
        if not self.state_path.is_file() or not self.dependency_index_path.is_file():
            raise FileNotFoundError("Fast Dev incremental state is missing")
        return _read_json(self.state_path), _read_json(self.dependency_index_path)

    def compile_changed(self) -> dict[str, object]:
        if not self.state_path.is_file() or not self.dependency_index_path.is_file():
            return self.full_compile()

        old_state, dependency_index = self._load_previous()
        old_sources = old_state.get("sources", {})
        current = self._scan_sources()

        deleted = set(old_sources) - set(current)
        if deleted:
            return self.full_compile()

        changed_rel = [
            rel for rel, row in current.items()
            if rel not in old_sources or old_sources[rel].get("hash") != row["hash"]
        ]
        if not changed_rel:
            return {"mode": "noop", "compiled_identities": []}

        current_by_id = self._identity_map(current)
        changed_ids = {str(current[rel]["identity"]) for rel in changed_rel}
        abi_changed: set[str] = set()
        for rel in changed_rel:
            previous = old_sources.get(rel)
            if previous is None or previous.get("abi") != current[rel]["abi"]:
                abi_changed.add(str(current[rel]["identity"]))

        affected = set(changed_ids)
        if abi_changed:
            if dependency_index.get("complete") is not True:
                return self.full_compile()
            try:
                affected |= _DEPS.reverse_closure(abi_changed, dependency_index)
            except (KeyError, TypeError, ValueError):
                return self.full_compile()

        if any(identity not in current_by_id for identity in affected):
            return self.full_compile()

        self.class_dir.parent.mkdir(parents=True, exist_ok=True)
        selected = [current_by_id[identity] for identity in sorted(affected)]
        with tempfile.TemporaryDirectory(prefix="javac.incremental.", dir=self.class_dir.parent) as td:
            staging = Path(td)
            self._javac([Path(row["path"]) for row in selected], staging, include_overlay=True)

            candidate = Path(tempfile.mkdtemp(prefix="classes.publish.", dir=self.class_dir.parent))
            shutil.rmtree(candidate)
            if self.class_dir.exists():
                shutil.copytree(self.class_dir, candidate)
            else:
                candidate.mkdir(parents=True)

            old_by_identity = {
                str(row.get("identity")): row
                for row in old_sources.values()
                if isinstance(row, dict)
            }
            for identity in affected:
                previous = old_by_identity.get(identity, {})
                for rel_path in previous.get("class_family", []):
                    target = candidate / rel_path
                    if target.exists():
                        target.unlink()
                new_family = self._family_paths(staging, identity)
                if not new_family:
                    shutil.rmtree(candidate)
                    raise RuntimeError(f"incremental javac produced no class family for {identity}")
                for rel_path in new_family:
                    source = staging / rel_path
                    target = candidate / rel_path
                    target.parent.mkdir(parents=True, exist_ok=True)
                    shutil.copy2(source, target)

            self._swap_class_dir(candidate)

        # Publish state only after javac and class overlay publication succeed.
        state_rows: dict[str, object] = {}
        for rel, row in current.items():
            identity = str(row["identity"])
            family = self._family_paths(self.class_dir, identity)
            if not family:
                raise RuntimeError(f"published class family missing for {identity}")
            state_rows[rel] = {
                "identity": identity,
                "hash": row["hash"],
                "abi": row["abi"],
                "class_family": family,
            }
        new_state = {"schema": 1, "sources": state_rows}
        new_dependencies = self._dependency_index(current)
        _atomic_write_json(self.state_path, new_state)
        _atomic_write_json(self.dependency_index_path, new_dependencies)
        return {"mode": "incremental", "compiled_identities": sorted(affected)}
