import assert from "node:assert/strict";
import test from "node:test";

import {
  classifyExactOverlap,
  extract381TableNames,
  extract850TableNames,
  renderFramework,
  summarizeSql,
} from "./scan-381-sql.mjs";

test("extracts backtick and qualified 381 INSERT table names", () => {
  const sql = [
    "INSERT INTO atu381.`w_測試` (`id`) VALUES (1);",
    "INSERT INTO `另一表` (`id`) VALUES (2);",
  ].join("\n");
  assert.deepEqual(extract381TableNames(sql), ["w_測試", "另一表"]);
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
    overlap_850_semantic: "PENDING_CORE_REVIEW",
    audit_status: "SQL_SCANNED",
    difficulty: "NOT_FINAL",
    decision: "HOLD",
  }]);
  assert.match(markdown, /\| 1 \| `w_測試` \| HAS_SQL_CONTENT \| NO_EXACT_TABLE_MATCH \| PENDING_CORE_REVIEW \| SQL_SCANNED \| NOT_FINAL \| HOLD \|/u);
});
