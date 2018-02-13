package com.team.framework;

import java.io.File;

public class RunConfig {


    //  Path to feature file to execute from

    public static String scenarios = getEnvVar("scenarios");
    /**
     * Retrieves an environment variable
     *
     * @param name environment variable name to retrieve its value
     * @return value of parameter or null if not found
     */
    public static String getEnvVar(String name) {
        String value = System.getenv(name);
        value = value == null ? null : value.trim();
        if (value != null && value == "") {
            return value;
        }
        return null;
    }

    /**
     * get cucumber scenarios from args if not in env - cucumber config does this in intellij
     *
     * @param args program arguments from main
     */
    static void getScenariosFromArgs(String[] args) {
        scenarios = scenarios == null ? "" : scenarios;
        if (scenarios == "" && args != null && args.length > 0) {
            StringBuilder temp = new StringBuilder("");
            for (String arg : args) {
                File f = new File(arg);
                if (f.exists() || f.getAbsoluteFile().exists()) {
                    temp.append(arg);
                }
            }
            scenarios = temp.toString();
        }
        scenarios = scenarios.replace('\\', '/');
    }









}
