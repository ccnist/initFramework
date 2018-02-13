package com.team.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverConfig{

    public static WebDriver driver = null;

    public static WebDriver getDriver(String type) {
        if (driver == null)
        {
            if(type.equals("browser")) {
                System.setProperty("webdriver.chrome.driver", "D://Softwares//chromedriver.exe");
                driver = new ChromeDriver();
            }else
            {

            }
        }
        return driver;
    }









}
