package it.sirfinpa.roboerp.component;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class WebComponent {

    protected Logger logger = Logger.getLogger(getClass());

    protected WebDriver webDriver;
    protected boolean dirty;

    public WebComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Inizializzazione componente, in base allo stato attuale del WebDriver
     */
    protected abstract void fromDriver();

    protected boolean isDirty() {
        return dirty;
    }

    protected void toggleDirty() {
        dirty = !dirty;
    }

    protected void fixedWait(int seconds) {
        if (logger.isDebugEnabled()){
            logger.debug(String.format("Sleep for [%1$s]s...", seconds));
        }
        try { Thread.sleep((seconds * 1000)); }
        catch (InterruptedException e) { logger.error(e); }
    }

    protected void init(){
        logger.info("Init component");
        fromDriver();
        if (logger.isDebugEnabled()){ logger.debug(toString()); }
    }


    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .setExcludeFieldNames("webDriver", "dirty")
                .toString();
    }
}
