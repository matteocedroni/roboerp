package it.sirfinpa.roboerp.context;


import java.util.Arrays;

public class ExecutionContext {

    protected String username;
    protected String password;

    protected boolean headless;
    protected String phantomjsBinPath;

    protected String erpHomeUrl;
    protected String[] dailyProfile;

    protected int nowDay;
    protected int nowMonth;

    protected Integer month;
    protected int[] dayList;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isHeadless() {
        return headless;
    }

    public String getErpHomeUrl() {
        return erpHomeUrl;
    }

    public String[] getDailyProfile() {
        return dailyProfile;
    }

    public int getNowDay() {
        return nowDay;
    }

    public int getNowMonth() {
        return nowMonth;
    }

    public Integer getMonth() {
        return month;
    }

    public int[] getDayList() {
        return dayList;
    }

    public String getPhantomjsBinPath() {
        return phantomjsBinPath;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExecutionContext{");
        sb.append("username='").append(username).append('\'');
        sb.append(", headless=").append(headless);
        sb.append(", phantomjsBinPath='").append(phantomjsBinPath).append('\'');
        sb.append(", erpHomeUrl='").append(erpHomeUrl).append('\'');
        sb.append(", dailyProfile=").append(Arrays.toString(dailyProfile));
        sb.append(", nowDay=").append(nowDay);
        sb.append(", nowMonth=").append(nowMonth);
        sb.append(", month=").append(month);
        sb.append(", dayList=").append(Arrays.toString(dayList));
        sb.append('}');
        return sb.toString();
    }
}
