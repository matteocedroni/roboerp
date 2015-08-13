package it.sirfinpa.roboerp.context;


import it.sirfinpa.roboerp.RoboProperty;
import it.sirfinpa.roboerp.cli.CliOptions;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import java.util.*;

public class ExecutionContextHolder {

    private static Logger logger = Logger.getLogger(ExecutionContextHolder.class);

    private static ExecutionContext executionContext;


    public static void init(CliOptions commandLineOptions, RoboProperty properties){
        executionContext = new ExecutionContext();

        executionContext.nowDay = determineDayOfMonth();
        executionContext.nowMonth = determineMonthOfYear();

        //confgurazione
        executionContext.erpHomeUrl = properties.getErpHomeUrl();
        executionContext.username = properties.getUsername();
        executionContext.password = properties.getPassword();
        executionContext.dailyProfile = properties.getDailyProfile();
        executionContext.headless = Boolean.TRUE.equals(properties.getHeadless());
        executionContext.phantomjsBinPath = properties.getPhantomjsBinPath();

        //cli
        executionContext.month = commandLineOptions.getMonth();
        executionContext.dayList = toDayList(commandLineOptions.getDayList());

        if (logger.isDebugEnabled()) { logger.debug(executionContext.toString()); }
    }

    private static int[] toDayList(String dayStringList){
        int[] dayList = null;
        if (dayStringList!=null && !dayStringList.isEmpty()){
            Set<Integer> daySet = new HashSet<Integer>();
            for (String dayString:dayStringList.split(",")){
                daySet.add(Integer.valueOf(dayString));
            }
            dayList = ArrayUtils.toPrimitive(daySet.toArray(new Integer[]{}));
            Arrays.sort(dayList);
        }

        return dayList;
    }

    public static ExecutionContext getExecutionContext() {
        return executionContext;
    }

    private static int determineDayOfMonth(){
        return new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH);
    }

    private static int determineMonthOfYear(){
        return new GregorianCalendar().get(GregorianCalendar.MONTH)+1;
    }


}
