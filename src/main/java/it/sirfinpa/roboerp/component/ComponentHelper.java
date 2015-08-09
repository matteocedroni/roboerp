package it.sirfinpa.roboerp.component;


import org.openqa.selenium.WebElement;

import java.util.Collection;

public class ComponentHelper {

    public static String printElement(WebElement element){
        return (element!=null ? element.toString() : null);
    }

    public static String printElements(Collection<WebElement> elements){
        return printElements(elements.toArray(new WebElement[]{}));
    }

    public static String printElements(WebElement[] elements){
        StringBuilder stringBuilder = new StringBuilder();

        if (elements!=null && elements.length>0){
            for (WebElement element:elements){
                stringBuilder.append("\n").append(printElement(element));
            }
        }

        return stringBuilder.toString();
    }

}
