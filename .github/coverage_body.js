var fs = require("fs");

module.exports = function(parser) {
    var modules = ["account-role", "account-service", "data-query-service", "platform", "trino-rule-update-service"];
    var totalHit = 0;
    var totalLines = 0;
    var dropdown = "| Module | Coverage | Line count |\n| --- | --- | --- |\n";

    modules.forEach(m => {
        var reportLocation = m + "/build/reports/jacoco/test/jacocoTestReport.xml";
        var xmlObj = parser.toJson(fs.readFileSync(reportLocation), { object: true });
        var lineCounter = xmlObj.report.counter.find(c => c.type === "LINE");

        var hit = Number(lineCounter.covered);
        var lines = hit + Number(lineCounter.missed);
        var coverage = "`" + (Math.round(hit / lines * 1000) / 10).toFixed(1) + "%`";
        var ratio = hit + "/" + lines;
        var moduleCov = " | " + m + " | " + coverage + " | " + ratio + " |\n";
        totalHit += hit;
        totalLines += lines;
        dropdown += moduleCov;
    })

    var totalCoverage = "`" + (Math.round(totalHit / totalLines * 1000) / 10).toFixed(1) + "%` ("
        + totalHit + "/" + totalLines + " lines).";

    var body = "## Coverage Report\nThe current coverage rate is " + totalCoverage
        + "\n<details>\n<summary>Coverage by module</summary>\n\n" + dropdown + "</details>"

    return body;
}
