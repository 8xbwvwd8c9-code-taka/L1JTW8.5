import fs from "node:fs/promises";
import path from "node:path";
import { fileURLToPath } from "node:url";

function uniqueSorted(values) {
  return [...new Set(values)].sort((a, b) => a.localeCompare(b, "zh-Hant"));
}

function uniqueInOrder(values) {
  return [...new Set(values)];
}

function stripSqlComments(sql) {
  return sql
    .replace(/\/\*[\s\S]*?\*\//g, "")
    .replace(/^\s*--.*$/gm, "")
    .replace(/^\s*#.*$/gm, "");
}

export function extract381TableNames(sql) {
  const clean = stripSqlComments(sql);
  const names = [];
  const statement = /\b(?:INSERT\s+INTO|REPLACE\s+INTO|CREATE\s+TABLE(?:\s+IF\s+NOT\s+EXISTS)?|ALTER\s+TABLE|UPDATE)\s+(?:(?:`?[^`.\s]+`?)\s*\.\s*)?`?([^`\s(;.]+)`?/giu;
  for (const match of clean.matchAll(statement)) names.push(match[1]);
  return uniqueInOrder(names);
}

export function extract850TableNames(sql) {
  const clean = stripSqlComments(sql);
  const names = [];
  const create = /\bCREATE\s+TABLE(?:\s+IF\s+NOT\s+EXISTS)?\s+`?([^`\s(;.]+)`?/giu;
  for (const match of clean.matchAll(create)) names.push(match[1]);
  return uniqueSorted(names);
}

export function classifyExactOverlap(sourceTables, targetTables) {
  if (sourceTables.length === 0) return "SOURCE_TABLE_NOT_PROVEN";
  return sourceTables.some((name) => targetTables.has(name.toLowerCase()))
    ? "EXACT_TABLE_MATCH"
    : "NO_EXACT_TABLE_MATCH";
}

export function summarizeSql(sql) {
  const clean = stripSqlComments(sql).trim();
  if (clean.length === 0) {
    return {
      dataState: "NO_ACTIVE_DATA",
      sqlShape: "EMPTY",
      insertStatements: 0,
      createStatements: 0,
    };
  }
  const insertStatements = (clean.match(/\b(?:INSERT|REPLACE)\s+INTO\b/giu) ?? []).length;
  const createStatements = (clean.match(/\bCREATE\s+TABLE\b/giu) ?? []).length;
  let sqlShape = "OTHER_SQL";
  if (insertStatements > 0 && createStatements === 0) sqlShape = "INSERT_ONLY";
  else if (insertStatements === 0 && createStatements > 0) sqlShape = "DDL_ONLY";
  else if (insertStatements > 0 && createStatements > 0) sqlShape = "DDL_AND_DATA";
  return { dataState: "HAS_SQL_CONTENT", sqlShape, insertStatements, createStatements };
}

function csvCell(value) {
  const text = String(value ?? "");
  return /[",\r\n]/.test(text) ? `"${text.replaceAll('"', '""')}"` : text;
}

function toCsv(rows) {
  const headers = Object.keys(rows[0]);
  return [headers, ...rows.map((row) => headers.map((header) => row[header]))]
    .map((row) => row.map(csvCell).join(","))
    .join("\r\n") + "\r\n";
}

function deriveFileTable(filename) {
  return filename.replace(/_?\d{12}\.sql$/iu, "").replace(/\.sql$/iu, "").replace(/^_+|_+$/gu, "");
}

function markdownCell(value) {
  return String(value ?? "").replaceAll("|", "\\|").replaceAll("`", "\\`");
}

export function renderFramework(rows) {
  const header = [
    "# 381 → 850 SQL 模組框架",
    "",
    "每份 381 SQL 保留獨立項目。`overlap_850_exact` 只表示同名 table；語意重複、部分重疊與 850 native replacement 必須在核心追蹤後判定。",
    "",
    "| ID | 381 table | 資料狀態 | 850 同名 | 850 語意對照 | 稽核狀態 | 難易度 | 決策 |",
    "|---:|---|---|---|---|---|---|---|",
  ];
  const body = rows.map((row) =>
    `| ${row.id} | \`${markdownCell(row.source_table)}\` | ${markdownCell(row.data_state)} | ${markdownCell(row.overlap_850_exact)} | ${markdownCell(row.overlap_850_semantic)} | ${markdownCell(row.audit_status)} | ${markdownCell(row.difficulty)} | ${markdownCell(row.decision)} |`
  );
  return [...header, ...body, ""].join("\n");
}

export async function buildInventory({ sourceDir, targetSql, outputDir }) {
  const targetText = await fs.readFile(targetSql, "utf8");
  const targetNames = extract850TableNames(targetText);
  const targetSet = new Set(targetNames.map((name) => name.toLowerCase()));
  const dirEntries = await fs.readdir(sourceDir, { withFileTypes: true });
  const filenames = dirEntries
    .filter((entry) => entry.isFile() && entry.name.toLowerCase().endsWith(".sql"))
    .map((entry) => entry.name)
    .sort((a, b) => a.localeCompare(b, "zh-Hant"));

  const rows = [];
  for (let index = 0; index < filenames.length; index += 1) {
    const filename = filenames[index];
    const fullPath = path.join(sourceDir, filename);
    const [sql, stat] = await Promise.all([fs.readFile(fullPath, "utf8"), fs.stat(fullPath)]);
    const summary = summarizeSql(sql);
    const parsedTables = extract381TableNames(sql);
    const sourceTables = parsedTables.length > 0 ? parsedTables : [deriveFileTable(filename)];
    const exactMatches = sourceTables.filter((name) => targetSet.has(name.toLowerCase()));
    rows.push({
      id: index + 1,
      source_file: filename,
      source_table: sourceTables.join(" | "),
      bytes: stat.size,
      data_state: summary.dataState,
      sql_shape: summary.sqlShape,
      insert_statements: summary.insertStatements,
      create_statements: summary.createStatements,
      overlap_850_exact: classifyExactOverlap(sourceTables, targetSet),
      overlap_850_tables: exactMatches.join(" | "),
      overlap_850_semantic: "PENDING_CORE_REVIEW",
      module_family: "UNCLASSIFIED",
      audit_status: "SQL_SCANNED",
      core_381_status: "NOT_PROVEN",
      core_850_status: "NOT_PROVEN",
      client_status: "NOT_CHECKED",
      difficulty: "NOT_FINAL",
      decision: "HOLD",
      evidence_note: summary.dataState === "NO_ACTIVE_DATA" ? "Empty SQL is not a SKIP reason" : "SQL content indexed; runtime semantics pending",
    });
  }

  await fs.mkdir(outputDir, { recursive: true });
  await fs.writeFile(path.join(outputDir, "SQL_FULL_INVENTORY.csv"), toCsv(rows), "utf8");
  await fs.writeFile(path.join(outputDir, "SQL_MODULE_FRAMEWORK.md"), renderFramework(rows), "utf8");
  const exactCount = rows.filter((row) => row.overlap_850_exact === "EXACT_TABLE_MATCH").length;
  const emptyCount = rows.filter((row) => row.data_state === "NO_ACTIVE_DATA").length;
  const markdown = `# 381 → 850 SQL 全量掃描進度\n\n` +
    `- 381 SQL：${rows.length}\n` +
    `- 非空 SQL：${rows.length - emptyCount}\n` +
    `- 空 SQL：${emptyCount}\n` +
    `- 850 CREATE TABLE：${targetNames.length}\n` +
    `- 850 同名 table：${exactCount}\n` +
    `- SQL_SCANNED：${rows.length}/${rows.length}\n` +
    `- CORE_TRACED：0/${rows.length}\n` +
    `- 850_COMPARED_SEMANTIC：0/${rows.length}\n` +
    `- CLIENT_CHECKED：0/${rows.length}\n` +
    `- AUDIT_COMPLETE：0/${rows.length}\n\n` +
    `## 判定規則\n\n` +
    `同名 table 僅代表 DB 名稱重複，不代表語意完全相同。語意重複、部分重疊及 native replacement 必須完成 381 runtime 與 850 core 對照後才能判定。空 SQL 保持 HOLD，不得因零資料直接 SKIP。\n`;
  await fs.writeFile(path.join(outputDir, "SQL_SCAN_PROGRESS.md"), markdown, "utf8");
  return { rows, targetNames };
}

async function main() {
  const [sourceDir, targetSql, outputDir] = process.argv.slice(2);
  if (!sourceDir || !targetSql || !outputDir) {
    throw new Error("Usage: node scan-381-sql.mjs <381-sql-dir> <850-sql-file> <output-dir>");
  }
  const { rows, targetNames } = await buildInventory({ sourceDir, targetSql, outputDir });
  process.stdout.write(JSON.stringify({ sourceSql: rows.length, targetTables: targetNames.length }) + "\n");
}

if (process.argv[1] && path.resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
  await main();
}
