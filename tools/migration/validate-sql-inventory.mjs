import fs from "node:fs/promises";
import { Workbook } from "@oai/artifact-tool";

const [csvPath] = process.argv.slice(2);
if (!csvPath) throw new Error("Usage: node validate-sql-inventory.mjs <inventory.csv>");

const csvText = await fs.readFile(csvPath, "utf8");
const workbook = await Workbook.fromCSV(csvText, { sheetName: "SQL inventory" });
const inspected = await workbook.inspect({
  kind: "table",
  range: "'SQL inventory'!A1:S321",
  include: "values",
  tableMaxRows: 4,
  tableMaxCols: 19,
  maxChars: 6000,
});
const dataLines = csvText.trimEnd().split(/\r?\n/).length - 1;
if (dataLines !== 320) throw new Error(`Expected 320 data rows, found ${dataLines}`);
const ids = csvText.trimEnd().split(/\r?\n/).slice(1).map((line) => line.split(",", 1)[0]);
if (new Set(ids).size !== 320) throw new Error("Inventory IDs are not unique");
process.stdout.write(JSON.stringify({ rows: dataLines, uniqueIds: new Set(ids).size }) + "\n");
process.stdout.write(inspected.ndjson + "\n");
