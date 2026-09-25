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

export function extractInsertColumnsByTable(sql) {
  const clean = stripSqlComments(sql);
  const result = new Map();
  const insert = /\b(?:INSERT|REPLACE)\s+INTO\s+(?:(?:`?[^`.\s]+`?)\s*\.\s*)?`?([^`\s(;.]+)`?\s*\(([^)]*)\)\s*VALUES/giu;
  for (const match of clean.matchAll(insert)) {
    const columns = match[2].split(",").map((value) => value.trim().replace(/^`|`$/gu, "")).filter(Boolean);
    const previous = result.get(match[1]) ?? [];
    result.set(match[1], uniqueInOrder([...previous, ...columns]));
  }
  return result;
}

export function extractCreateColumnsByTable(sql) {
  const clean = stripSqlComments(sql);
  const result = new Map();
  const create = /\bCREATE\s+TABLE(?:\s+IF\s+NOT\s+EXISTS)?\s+`?([^`\s(;.]+)`?\s*\(([\s\S]*?)\)\s*(?:ENGINE\b|;)/giu;
  for (const match of clean.matchAll(create)) {
    const columns = [];
    for (const line of match[2].split(/\r?\n/u)) {
      const column = line.match(/^\s*`([^`]+)`\s+/u);
      if (column) columns.push(column[1]);
    }
    result.set(match[1], uniqueInOrder(columns));
  }
  return result;
}

export function classifySchemaOverlap(sourceColumns, targetColumns) {
  if (sourceColumns.length === 0) return "SOURCE_COLUMNS_NOT_PROVEN";
  if (targetColumns.length === 0) return "850_COLUMNS_NOT_PROVEN";
  const source = new Set(sourceColumns.map((name) => name.toLowerCase()));
  const target = new Set(targetColumns.map((name) => name.toLowerCase()));
  if (source.size === target.size && [...source].every((name) => target.has(name))) return "EXACT_COLUMN_SET";
  if ([...source].every((name) => target.has(name))) return "381_COLUMNS_SUBSET_OF_850";
  return "COLUMN_DIFFERENCE";
}

export function classifyProvisionalLevel(row) {
  const name = String(row.source_table ?? "").toLowerCase();
  const clientPattern = /變身|卡片|炫色|外觀|造型|特效|sprite|gfx|紋樣|稱號|圖示|圖檔/u;
  const deepCorePattern = /characters?|character_|clan|血盟|合成|升級|附魔|道具狀態|威望|時間限制|掉落限制|爆氣|屬性強化|祝福系統|achievement|成就|quest|任務|城堡|攻城|warehouse|倉庫|交易限制|限制物品|鐘點/u;
  const adapterPattern = /製作|craft|技能|skill|怪物|monster|獎勵|reward|廣播|broadcast|掉落|drop|召喚|spawn|回收|分解|地圖|map|回血|回魔/u;
  if (clientPattern.test(name)) return "L4";
  if (deepCorePattern.test(name)) return "L3";
  if (adapterPattern.test(name)) return "L2";
  if (row.overlap_850_exact === "EXACT_TABLE_MATCH") {
    return row.overlap_850_schema === "COLUMN_DIFFERENCE" ? "L2" : "L1";
  }
  return "L2";
}

export function validateIdentifierColumn(header, values) {
  if (String(header).toLowerCase() !== "id") return;
  if (new Set(values).size !== values.length) throw new Error("Inventory IDs are not unique");
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
    "| ID | 381 table | 資料狀態 | 850 同名 | 欄位對照 | 850 語意對照 | 稽核狀態 | 難易度 | 決策 |",
    "|---:|---|---|---|---|---|---|---|---|",
  ];
  const body = rows.map((row) =>
    `| ${row.id} | \`${markdownCell(row.source_table)}\` | ${markdownCell(row.data_state)} | ${markdownCell(row.overlap_850_exact)} | ${markdownCell(row.overlap_850_schema ?? "NOT_SCANNED")} | ${markdownCell(row.overlap_850_semantic)} | ${markdownCell(row.audit_status)} | ${markdownCell(row.difficulty)} | ${markdownCell(row.decision)} |`
  );
  return [...header, ...body, ""].join("\n");
}

export async function buildInventory({ sourceDir, targetSql, outputDir }) {
  const targetText = await fs.readFile(targetSql, "utf8");
  const targetNames = extract850TableNames(targetText);
  const targetSet = new Set(targetNames.map((name) => name.toLowerCase()));
  const targetColumnsByTable = extractCreateColumnsByTable(targetText);
  const targetColumnsByLowerName = new Map([...targetColumnsByTable].map(([name, columns]) => [name.toLowerCase(), columns]));
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
    const sourceColumnsByTable = extractInsertColumnsByTable(sql);
    const sourceColumns = uniqueInOrder(sourceTables.flatMap((name) => sourceColumnsByTable.get(name) ?? []));
    const targetColumns = uniqueInOrder(exactMatches.flatMap((name) => targetColumnsByLowerName.get(name.toLowerCase()) ?? []));
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
      columns_381: sourceColumns.join(" | "),
      columns_850: targetColumns.join(" | "),
      overlap_850_schema: exactMatches.length > 0 ? classifySchemaOverlap(sourceColumns, targetColumns) : "NOT_APPLICABLE_NO_EXACT_TABLE",
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
  const levelOrder = new Map([["L1", 1], ["L2", 2], ["L3", 3], ["L4", 4]]);
  const provisionalRows = rows
    .map((row) => ({
      level: classifyProvisionalLevel(row),
      item_381: row.source_table,
      item_850: row.overlap_850_tables || "N/A",
    }))
    .sort((a, b) => levelOrder.get(a.level) - levelOrder.get(b.level) || a.item_381.localeCompare(b.item_381, "zh-Hant"));
  await fs.writeFile(path.join(outputDir, "SQL_PROVISIONAL_L1_L4.csv"), toCsv(provisionalRows), "utf8");
  const exactCount = rows.filter((row) => row.overlap_850_exact === "EXACT_TABLE_MATCH").length;
  const schemaComparedCount = rows.filter((row) => row.overlap_850_exact === "EXACT_TABLE_MATCH").length;
  const emptyCount = rows.filter((row) => row.data_state === "NO_ACTIVE_DATA").length;
  const markdown = `# 381 → 850 SQL 全量掃描進度\n\n` +
    `- 381 SQL：${rows.length}\n` +
    `- 非空 SQL：${rows.length - emptyCount}\n` +
    `- 空 SQL：${emptyCount}\n` +
    `- 850 CREATE TABLE：${targetNames.length}\n` +
    `- 850 同名 table：${exactCount}\n` +
    `- 850 同名欄位已比較：${schemaComparedCount}/${exactCount}\n` +
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
