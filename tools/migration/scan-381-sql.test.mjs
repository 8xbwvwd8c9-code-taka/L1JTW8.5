import assert from "node:assert/strict";
import test from "node:test";

import {
  classifyExactOverlap,
  classifySchemaOverlap,
  classifyProvisionalLevel,
  extractCreateColumnsByTable,
  extract381TableNames,
  extract850TableNames,
  extractInsertColumnsByTable,
  renderFramework,
  summarizeSql,
  validateIdentifierColumn,
} from "./scan-381-sql.mjs";

test("extracts backtick and qualified 381 INSERT table names", () => {
  const sql = [
    "INSERT INTO atu381.`w_測試` (`id`) VALUES (1);",
    "INSERT INTO `另一表` (`id`) VALUES (2);",
  ].join("\n");
  assert.deepEqual(extract381TableNames(sql), ["w_測試", "另一表"]);
});

test("requires unique values only when the first CSV column is id", () => {
  assert.doesNotThrow(() => validateIdentifierColumn("level", ["L1", "L1", "L2"]));
  assert.throws(() => validateIdentifierColumn("id", ["1", "1"]), /not unique/u);
  assert.doesNotThrow(() => validateIdentifierColumn("id", ["1", "2"]));
});

test("assigns provisional L1-L4 from DB overlap, core lifecycle, and client dependency", () => {
  assert.equal(classifyProvisionalLevel({ source_table: "accounts", overlap_850_exact: "EXACT_TABLE_MATCH", overlap_850_schema: "EXACT_COLUMN_SET" }), "L1");
  assert.equal(classifyProvisionalLevel({ source_table: "w_火神裝備製作", overlap_850_exact: "NO_EXACT_TABLE_MATCH", overlap_850_schema: "NOT_APPLICABLE_NO_EXACT_TABLE" }), "L2");
  assert.equal(classifyProvisionalLevel({ source_table: "w_血盟技能", overlap_850_exact: "NO_EXACT_TABLE_MATCH", overlap_850_schema: "NOT_APPLICABLE_NO_EXACT_TABLE" }), "L3");
  assert.equal(classifyProvisionalLevel({ source_table: "w_變身卡片能力登入", overlap_850_exact: "NO_EXACT_TABLE_MATCH", overlap_850_schema: "NOT_APPLICABLE_NO_EXACT_TABLE" }), "L4");
});

test("extracts insert and create columns for same-name schema comparison", () => {
  const source = "INSERT INTO atu381.`accounts` (`login`,`password`) VALUES ('a','b');";
  const target = "CREATE TABLE `accounts` (\n  `login` varchar(50),\n  `password` varchar(50),\n  PRIMARY KEY (`login`)\n) ENGINE=MyISAM;";
  assert.deepEqual(extractInsertColumnsByTable(source).get("accounts"), ["login", "password"]);
  assert.deepEqual(extractCreateColumnsByTable(target).get("accounts"), ["login", "password"]);
});

test("classifies equal, subset, different, and unproven column sets", () => {
  assert.equal(classifySchemaOverlap(["a", "b"], ["a", "b"]), "EXACT_COLUMN_SET");
  assert.equal(classifySchemaOverlap(["a"], ["a", "b"]), "381_COLUMNS_SUBSET_OF_850");
  assert.equal(classifySchemaOverlap(["a", "c"], ["a", "b"]), "COLUMN_DIFFERENCE");
  assert.equal(classifySchemaOverlap([], ["a", "b"]), "SOURCE_COLUMNS_NOT_PROVEN");
});

test("extracts 850 CREATE TABLE names without treating comments as tables", () => {
  const sql = [
    "-- CREATE TABLE `假的`;",
    "CREATE TABLE `accounts` (`id` int);",
    "CREATE TABLE craft (`id` int);",
  ].join("\n");
  assert.deepEqual(extract850TableNames(sql), ["accounts", "craft"]);
});

test("classifies exact table overlap independently from semantic review", () => {
  const tables850 = new Set(["accounts", "craft"]);
  assert.equal(classifyExactOverlap(["craft"], tables850), "EXACT_TABLE_MATCH");
  assert.equal(classifyExactOverlap(["w_自訂"], tables850), "NO_EXACT_TABLE_MATCH");
  assert.equal(classifyExactOverlap([], tables850), "SOURCE_TABLE_NOT_PROVEN");
});

test("summarizes empty and insert-only SQL without inventing row authority", () => {
  assert.deepEqual(summarizeSql(""), {
    dataState: "NO_ACTIVE_DATA",
    sqlShape: "EMPTY",
    insertStatements: 0,
    createStatements: 0,
  });
  assert.deepEqual(summarizeSql("INSERT INTO `x` (`id`) VALUES (1);"), {
    dataState: "HAS_SQL_CONTENT",
    sqlShape: "INSERT_ONLY",
    insertStatements: 1,
    createStatements: 0,
  });
});

test("renders one framework row per SQL item with 850 overlap state", () => {
  const markdown = renderFramework([{
    id: 1,
    source_table: "w_測試",
    data_state: "HAS_SQL_CONTENT",
    overlap_850_exact: "NO_EXACT_TABLE_MATCH",
    overlap_850_schema: "NOT_APPLICABLE_NO_EXACT_TABLE",
    overlap_850_semantic: "PENDING_CORE_REVIEW",
    audit_status: "SQL_SCANNED",
    difficulty: "NOT_FINAL",
    decision: "HOLD",
  }]);
  assert.match(markdown, /\| 1 \| `w_測試` \| HAS_SQL_CONTENT \| NO_EXACT_TABLE_MATCH \| NOT_APPLICABLE_NO_EXACT_TABLE \| PENDING_CORE_REVIEW \| SQL_SCANNED \| NOT_FINAL \| HOLD \|/u);
});
