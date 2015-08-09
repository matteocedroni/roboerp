package it.sirfinpa.roboerp;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RoboProperty {

    private static Logger logger = Logger.getLogger(RoboProperty.class);

    public static final String ERP_HOME_URL = "erp.home.url";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String DAILY_PROFILE = "daily.profile";

    public static final String HEADLESS = "headless";
    public static final String PHANTOMJS_BIN_PATH = "phantomjs.binary.path";


    private Properties properties;


    public static RoboProperty loadFrom(InputStream propertiesFileStream) throws IOException {
        RoboProperty out = new RoboProperty();
        Properties properties = new Properties();
        properties.load(propertiesFileStream);
        out.properties = properties;
        return out;
    }

    public String getErpHomeUrl(){
        return properties.getProperty(ERP_HOME_URL);
    }

    public String getUsername(){
        return properties.getProperty(USERNAME);
    }

    public String getPassword(){
        return properties.getProperty(PASSWORD);
    }

    public Boolean getHeadless(){
        return Boolean.parseBoolean(properties.getProperty(HEADLESS));
    }

    public String getPhantomjsBinPath(){
        return properties.getProperty(PHANTOMJS_BIN_PATH);
    }

    public String[] getDailyProfile(){
        return properties.getProperty(DAILY_PROFILE).split(",");
    }

    public void printProperties(){
        logger.debug(properties);
    }

}
