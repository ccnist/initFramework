package com.team.framework;

import cucumber.api.CucumberOptions;
import cucumber.api.cli.Main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cchandragiri on 4/12/2017.
 */

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;
import org.openqa.selenium.WebDriver;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"html:target/cucumber-html-report"}
)

public class MainRunner {

    public static final WebDriver driver = null;
    static Logger log = Logger.getLogger(MainRunner.class);


    public static void main(String Args[]) throws InterruptedException, IOException {
        MainRunner mn = new MainRunner();
        long startTime =  System.nanoTime();
        BasicConfigurator.configure();
        ArrayList<String> ae = mn.getScenarios();
        ae.add("--glue");
        ae.add("com.team.stepdefinitions");
        log.info("Added Glues to identify step definitions");
        Main.run(ae.toArray(new String[mn.getScenarios().size()]),Thread.currentThread().getContextClassLoader());
        log.info("Started Executing scenarios");
//       mn.getDriverInstance("firefox");
//       mn.navigate();
    }

    public static ArrayList getProperties()
    {
        ArrayList properties =  new ArrayList();
        properties.add(System.getenv("browser"));
        properties.add(System.getenv("website"));
        properties.add(System.getenv("tags"));
        properties.add(System.getenv("scenarios"));
        return properties;
    }

    private ArrayList<String> getScenarios()
    {
        ArrayList<String> ae = new ArrayList();
        String scenarios = getProperties().get(3).toString();
        ae.add(scenarios);
        log.info("scenarios are :" + scenarios);
        String tags = getProperties().get(3).toString();
        log.info("Tags are :" + tags);
        return ae;
    }

}
