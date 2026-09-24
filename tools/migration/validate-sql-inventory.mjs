import fs from "node:fs/promises";
import { Workbook } from "@oai/artifact-tool";
import { validateIdentifierColumn } from "./scan-381-sql.mjs";

const [csvPath, inspectRange = "A1:V321"] = process.argv.slice(2);
if (!csvPath) throw new Error("Usage: node validate-sql-inventory.mjs <inventory.csv>");

const csvText = await fs.readFile(csvPath, "utf8");
const workbook = await Workbook.fromCSV(csvText, { sheetName: "SQL inventory" });
const inspected = await workbook.inspect({
  kind: "table",
  range: `'SQL inventory'!${inspectRange}`,
  include: "values",
  tableMaxRows: 4,
  tableMaxCols: 22,
  maxChars: 6000,
});
const dataLines = csvText.trimEnd().split(/\r?\n/).length - 1;
if (dataLines !== 320) throw new Error(`Expected 320 data rows, found ${dataLines}`);
const lines = csvText.trimEnd().split(/\r?\n/);
const firstHeader = lines[0].split(",", 1)[0];
const firstColumn = lines.slice(1).map((line) => line.split(",", 1)[0]);
validateIdentifierColumn(firstHeader, firstColumn);
process.stdout.write(JSON.stringify({ rows: dataLines, firstColumn: firstHeader, distinctFirstColumnValues: new Set(firstColumn).size }) + "\n");
process.stdout.write(inspected.ndjson + "\n");
