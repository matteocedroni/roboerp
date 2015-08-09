package it.sirfinpa.roboerp.cli;


import org.kohsuke.args4j.Option;

public class CliOptions {

    @Option(name = "-d", aliases = { "--dayList" }, required = false, usage = "Lista giorni mese, separati da virgola. Se assente viene considerato il gionro corrente")
    private String dayList = null;

    @Option(name = "-m", aliases = { "--month" }, required = false, usage = "Mese di riferimento da 1 a 12. Se assente viene considerato il mese corrente", depends = "-d")
    private Integer month = null;

    @Option(name = "-h", aliases = { "--help" }, help = true, hidden = true)
    private boolean help = false;


    public String getDayList() {
        return dayList;
    }

    public Integer getMonth() {
        return month;
    }

    public boolean isHelp() {
        return help;
    }
}
