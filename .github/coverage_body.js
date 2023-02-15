const fs = require("fs");
const { execSync } = require("child_process");

function changedFile(filePath, headRef) {
    var command = "git diff -U0 main" + " -- " + filePath + " | tail -n +5";
    return execSync(command, {encoding: "utf-8"});
}

function getStart(hunk) {
    var lines = hunk.split("@@")[1];
    var start = lines.match(/\+([0-9]*)/).slice(1,);
    return Number(start);
}

function newLines(path, headRef) {
    var file = changedFile(path, headRef);
    var lines = file.split("\n");
    var added = [];
    var currentLine = null;

    lines.forEach(line => {
        if (line.startsWith("@@")) {
            currentLine = getStart(line);
        }
        else if (line.startsWith("-")) { return; }
        else if (line.startsWith("+")) {
            added.push(currentLine);
            currentLine += 1;
        }
        else {
            currentLine += 1;
        }
    })
    return added;
}

function analyzeChange(fileNode, toCheck) {
    var covered = 0;
    var total = 0;
    var lastMissed = null;
    var chain = false;
    var missed = "";

    toCheck.forEach(lineNum => {
        let lineNode = fileNode.line ? fileNode.line.find(l => l.nr == lineNum)
            : false;	    
        if (lineNode) {
            total += 1;
            if (lineNode.ci != 0) { covered += 1; }
            else {
                if (lastMissed == null) {
                    missed += "#L" + lineNum;
                }
                else if (lastMissed != lineNum - 1) {
                    if (!chain) {
                        missed += ", #L" + lineNum;
                    } else {
                        missed += "-L" + lastMissed + ", #L" + lineNum;
                        chain = false;
                    }
                }
                else {
                    if (!chain) { chain = true; }
                }
                lastMissed = lineNum;
            }
        }
    })
    if (chain) { missed += "-L" + lastMissed; }

    return [covered, total, missed];
}

function lineReport(path, headRef, xmlObj) {
    var simplePath = path.substring(path.indexOf("com"));
    var package = simplePath.substring(0, simplePath.lastIndexOf("/"));
    var filename = simplePath.substring(simplePath.lastIndexOf("/")+1);

    var packageNode = xmlObj.report.package.find(p => p.name === package);
    var fileNode = packageNode.sourcefile.length ? packageNode.sourcefile.find(s => s.name === filename)
        : packageNode.sourcefile;
    var toCheck = newLines(path, headRef);

    var [covered, total, missed] = analyzeChange(fileNode, toCheck);
    var body = "#### " + simplePath;

    if (covered == total) {
        body += " :heavy_check_mark:\n"
    } else {
        body += " :warning:\n`Added lines " + missed + " were not covered by tests.`\n"
    }

    return [covered, total, body];
}

module.exports = function(moduleName, fileNames, parser, headRef) {
    var reportLocation = moduleName + "/build/reports/jacoco/test/jacocoTestReport.xml";
    var xmlObj = parser.toJson(fs.readFileSync(reportLocation), { object: true });
    var lineCounter = xmlObj.report.counter.find(c => c.type === "LINE");
    var hit = Number(lineCounter.covered);
    var lines = hit + Number(lineCounter.missed);
    var coverage = "`" + (Math.round(hit / lines * 1000) / 10).toFixed(1) + "%` (" + hit + "/" + lines + "lines).";

    var newHit = 0;
    var newTotal = 0;
    var dropdown = "";
    var changedFiles = fileNames.split(" ");
    changedFiles.forEach(path => {
        if (path.includes("src/main")) {
            var [fileHit, fileLines, dropBody] = lineReport(path, headRef, xmlObj);
            newHit += fileHit;
            newTotal += fileLines;
            dropdown += dropBody;
        }
    })

    var details = newTotal != 0 ? "\n`" + (Math.round(newHit / newTotal * 1000) / 10).toFixed(1)
        + "%` of added lines are covered.\n<details>\n<summary>Details by file</summary>\n\n" + dropdown + "</details>"
        : "";

    var body = "## Coverage Report for " + moduleName + "\nThe current coverage rate is " + coverage + details;
    return body;
}
