//package com.team.runner;
//
//import com.macys.sdt.framework.utils.PageElement;
//import com.macys.sdt.framework.utils.PageUtils;
//import com.macys.sdt.framework.utils.StepUtils;
//import com.macys.sdt.framework.utils.Utils;
//import com.macys.sdt.framework.utils.analytics.AdobeAnalytics;
//import com.macys.sdt.framework.utils.analytics.Analytics;
//import com.macys.sdt.framework.utils.analytics.DigitalAnalytics;
//import org.apache.log4j.ConsoleAppender;
//import org.apache.log4j.FileAppender;
//import org.apache.log4j.Level;
//import org.json.JSONObject;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.parser.Parser;
//import org.junit.Assert;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URLDecoder;
//import java.util.*;
//
///**
// * This class holds all run config data statically
// */
//public class RunConfig {
//
//    private static final Logger log = LoggerFactory.getLogger(RunConfig.class);
//    private static final String MISSING_PROJECT = "Unable to determine project by given environment variables. Please" +
//            "add an environment variable \"sdt_project\" " +
//            "with project name in format \"<domain>.<project>\"";
//
//    /**
//     * Value to set the "EFCKEY" url param to
//     */
//    public static String efcKey = getEnvOrExParam("efckey");
//    /**
//     * Path (or page) on which to set efckey value
//     */
//    public static String exPath = getEnvOrExParam("ex_path");
//    /**
//     * True if executing through sauce labs. Checks for valid sauce labs info in "sauce_user" and "sauce_key" env
//     * variables
//     */
//    public static boolean useSauceLabs;
//    /**
//     * Name of Sauce Connect tunnel to use
//     */
//    public static String tunnelIdentifier = getEnvOrExParam("tunnel_identifier");
//
//    /**
//     * True if executing through TestObject. Checks for valid TestObject info in "testobject_api_key" and
//     * "testobject_device" env variables
//     */
//    public static boolean useTestObject;
//    /**
//     * True if using chrome device emulation
//     */
//    public static boolean useChromeEmulation;
//    /**
//     * True if using appium to connect to a mobile device
//     */
//    public static boolean useAppium = booleanParam("use_appium");
//    /**
//     * To set desired / compatible appium version for running appium tests
//     */
//    public static String appiumVersion = getEnvOrExParam("appium_version");
//    /**
//     * True if testing a mobile application
//     */
//    public static boolean appTest;
//    /**
//     * True if using header file
//     */
//    public static String headerFile = getEnvOrExParam("header_file");
//    /**
//     * Value to set "userAgent" header to. Only works in firefox. If needed otherwise, use a header file.
//     */
//    public static String userAgent = getEnvOrExParam("user_agent");
//    /**
//     * Map of all headers and their values
//     */
//    public static HashMap<String, String> headers = new HashMap<>();
//    /**
//     * Contains OS to use when executing on sauce labs or os version for app as given in "remote_os" env variable
//     * <p>
//     * Options: windows 7|8|8.1|10, OSX 10.10|10.11,
//     * for iOS and android, use the version of OS you want to run against - 9.2, 9.3 for iOS, 5.1, 6.0 for Android.
//     * You can use any version number that you want as long as you have an emulator or device with that OS version.
//     * </p>
//     */
//    public static String remoteOS = getEnvOrExParam("remote_os");
//    /**
//     * OS type to use when executing on TestObject and device is set to null or not specified
//     */
//    public static String osType = getEnvOrExParam("os_type");
//    /**
//     * Browser to use as given in "browser" env variable. Default chrome.
//     */
//    public static String browser = getEnvVar("browser");
//    /**
//     * Version of browser to use as given in "browser_version" env variable
//     */
//    public static String browserVersion = getEnvOrExParam("browser_version");
//    /**
//     * Wait timeout as given in "timeout" env variable. Default 95 seconds (125 seconds for safari)
//     */
//    public static int timeout = Utils.parseInt(getEnvOrExParam("timeout"), -1);
//    /**
//     * Device in use as given by "device" env variable
//     */
//    public static String device = getEnvOrExParam("device");
//    /**
//     * Whether the proxy is disabled
//     */
//    public static boolean useProxy = false;
//    /**
//     * Whether the test uses staging config
//     */
//    public static boolean akamai = false;
//    /**
//     * Whether the test uses staging config
//     */
//    public static boolean useStaging = false;
//    /**
//     * The Sauce Labs username to use
//     */
//    public static String sauceUser = getEnvOrExParam("sauce_user");
//    /**
//     * The Sauce Labs API key for the user
//     */
//    public static String sauceKey = getEnvOrExParam("sauce_key");
//
//    /**
//     * The TestObject API key for the device
//     */
//    public static String testObjectAPIKey = getEnvOrExParam("testobject_api_key");
//
//    /**
//     * The TestObject User
//     */
//    public static String testObjectUser = getEnvOrExParam("testobject_user");
//
//    /**
//     * Workspace path as given in "WORKSPACE" env variable
//     */
//    public static String workspace = getEnvOrExParam("WORKSPACE");
//    /**
//     * Path to logging folder
//     */
//    public static String logs;
//    /**
//     * Path to "temp" directory
//     */
//    public static String temp;
//    /**
//     * Map of current cucumber features
//     */
//    public static Map<String, Map> features = new HashMap<>();
//    /**
//     * Path to feature file to execute from
//     */
//    public static String scenarios = getEnvVar("scenarios");
//    /**
//     * Analytics object
//     */
//    public static Analytics analytics;
//    /**
//     * Whether to close browser or not after testing is complete. False if "DEBUG" env variable is true
//     */
//    public static Boolean closeBrowserAtExit = true;
//    /**
//     * Whether to collect coremetrics tags or not as given in "tag_collection" env variable
//     */
//    public static Boolean tagCollection = false;
//    /**
//     * Whether to run on QA env in batch mode as given in "batch_mode" env variable
//     */
//    public static Boolean batchMode = booleanParam("batch_mode");
//    /**
//     * URL to start at and use as a base as given in "website" env variable
//     */
//    public static String url = getEnvVar("website");
//    /**
//     * Domain - MCOM or BCOM, only needed when resolving website with IP
//     */
//    public static String brand = getEnvOrExParam("brand");
//    /**
//     * Path to project currently being run optionally given by "sdt_project" env variable
//     */
//    public static String project = getEnvVar("sdt_project");
//    /**
//     * Path to active project files on file system
//     */
//    public static List<String> projectResourceDirs = new ArrayList<>();
//    /**
//     * Whether we're running in debug mode
//     */
//    public static boolean debugMode = booleanParam("debug");
//    /**
//     * True if current run is a dry run
//     */
//    public static boolean dryRun = booleanParam("dry-run");
//    /**
//     * Name of jar being run if running from jar
//     */
//    public static String repoJar = getEnvOrExParam("repo_jar");
//    /**
//     * Download bandwidth in Kbps
//     */
//    public static int bandwidth = Utils.parseInt(getEnvOrExParam("bandwidth"), -1);
//    /**
//     * Path to shared resources
//     */
//    public static String sharedResourceDir = repoJar != null ?
//            "com/macys/sdt/shared/resources" : "shared/resources/src/main/resources";
//    /**
//     * List of all projects who's steps this test relies on
//     */
//    public static List<String> includedProjects;
//
//    public static boolean mst;
//    /**
//     * Location of app for app testing (appium)
//     */
//    protected static String appLocation = getEnvOrExParam("app_location");
//
//    // don't allow objects of this type to be initialized, static access only
//    private RunConfig() {}
//
//    public static void init(String[] args) {
//        getScenariosFromArgs(args);
//        setupWorkspace();
//        setupProjectDirs();
//        setupAnalytics();
//        inferTestSettings();
//
//        if (url == null && !appTest) {
//            Assert.fail("\"website\" variable required to test a website");
//        }
//
//        // check if given exPath is a page
//        if (exPath != null && exPath.split("_").length == 2) {
//            String pageUrl = PageUtils.getElementJSONValue(new PageElement(exPath + ".url"));
//            if (pageUrl != null) {
//                exPath = pageUrl;
//            }
//        }
//        sanitizeEfcKey();
//
//        // check for headers file
//        // needs to be after project is set in order to check project resources
//        getHeaders();
//
//        // add pragma header for Akamai testing
//        if (RunConfig.akamai) {
//            headers.put("pragma", "akamai-x-cache-on, akamai-x-cache-remote-on, akamai-x-check-cacheable, " +
//                    "akamai-x-get-cache-key, akamai-x-get-true-cache-key, akamai-x-get-client-ip");
//        }
//    }
//
//    /**
//     * Retrieves project info either from "sdt_project" or "scenarios" env val if possible
//     */
//    static void setupProjectDirs() {
//        if (repoJar != null) {
//            if (!(new File(repoJar).exists())) {
//                repoJar = workspace + repoJar;
//                if (!(new File(repoJar).exists())) {
//                    Assert.fail("Could not find given repo jar " + repoJar +
//                                        "\nPlease check your \"repo_jar\" and/or \"workspace\" variable(s)");
//                } else {
//                    repoJar = workspace + repoJar;
//                }
//            }
//        }
//
//        if (project == null) {
//            project = getProjectFromFilePath();
//        }
//
//        String[] check = project.split("\\.");
//        if (check.length != 2) {
//            Assert.fail("Project info is malformed. Please make sure it is in the format \"<domain>.<project>\"");
//        }
//
//        log.info("Using project: " + project + "\nIf this does not match your project," +
//                         " add an env variable \"sdt_project\" with value \"<domain>.<project>\"");
//        try {
//            if (repoJar != null) {
//                Utils.extractResources(new File(repoJar), workspace, project.replace(".", "/"));
//            }
//        } catch (IOException e) {
//            log.error("Failed to extract resources from jar: " + e);
//        }
//        includedProjects = getDependencies(project);
//        // old, proprietary resource location
//        projectResourceDirs.add(getProjectResourceDir(project));
//        for (String prj : includedProjects) {
//            if (repoJar != null) {
//                try {
//                    Utils.extractResources(new File(repoJar), workspace, prj.replace(".", "/"), true);
//                } catch (IOException e) {
//                    log.error("Failed to extract resources for " + prj + " from jar: " + e);
//                }
//            }
//            projectResourceDirs.add(getProjectResourceDir(prj));
//        }
//
//        // check if project is using MST project resources directory
//        mst = usingMSTResources();
//    }
//
//    /**
//     * Gets the resources directory for the given project
//     * <p>
//     * Can use the following dirs:<br>
//     * <code>Maven standard: <br>
//     * [domain]/[project]/src/main/resources</code><br>
//     * <code>SDT Proprietary: <br>
//     * [domain]/[project]/src/main/java/com/macys/sdt/projects/[domain]/[project]/resources</code><br>
//     * <code>EE resource dir: <br>
//     * [domain]/[project]/resources</code><br>
//     * </p>
//     */
//    private static String getProjectResourceDir(String prj) {
//        String prjResDir = prj.replace(".", "/") + "/src/main/java/com/macys/sdt/projects/" + prj
//                .replace(".", "/") + "/resources/";
//        if (!new File(prjResDir).exists()) {
//            // maven standard resource location
//            prjResDir = prj.replace(".", "/") + "/src/main/resources";
//            if (!new File(prjResDir).exists()) {
//                // location for EE runs
//                prjResDir = prj.replace(".", "/") + "/resources";
//            }
//        }
//
//        return prjResDir;
//}
//
//    /**
//     * Attempts to get the project name from the path to the feature file
//     * <p>
//     * <p>
//     * This usually works as our standard file path includes the project name.
//     * </p>
//     *
//     * @return project name in format: &ltdomain.projectName
//     */
//    private static String getProjectFromFilePath() {
//        String project;
//        String projectPath;
//        if (!workspace.equals(".")) {
//            // when scenarios path is an absolute path, eg. '/', '\\', 'X:'
//            if ((scenarios.charAt(0) == '/') || (scenarios.charAt(0) == '\\') || (scenarios.charAt(1) == ':')) {
//                if (!scenarios.contains(workspace))
//                    log.error("WORKSPACE = (" + workspace + ") is not a part of scenarios path = (" + scenarios + "). Please check the value and try again.");
//            }
//            projectPath = scenarios.replace(workspace, "").replace("/", ".").replace("\\", ".");
//            ArrayList<String> parts = new ArrayList<>(Arrays.asList(projectPath.split("\\.")));
//            if (parts.size() >= 2) {
//                project = parts.get(0) + "." + parts.get(1);
//            } else {
//                project = "";
//            }
//        } else {
//            projectPath = scenarios.replace("/", ".").replace("\\", ".");
//            ArrayList<String> parts = new ArrayList<>(Arrays.asList(projectPath.split("\\.")));
//            int index = parts.lastIndexOf("features");
//            if (index == -1) {
//                index = parts.indexOf("SDT");
//                if (index < 2) {
//                    Assert.fail(MISSING_PROJECT);
//                }
//                project = parts.get(index + 1) + "." + parts.get(index + 2);  // domain.project
//            } else {
//                project = parts.get(index - 2) + "." + parts.get(index - 1);  // domain.project
//            }
//        }
//        return project;
//    }
//
//    /**
//     * Retrieves a parameter value from "ex_params" environment variable
//     *
//     * @param name name of the parameter to retrieve
//     * @return value of parameter or null if not found
//     */
//    public static String getExParam(String name) {
//        try {
//            String exParams = URLDecoder.decode(System.getenv("ex_params"), "utf-8");
//            if (exParams != null && !exParams.isEmpty()) {
//                StringBuilder sb = new StringBuilder(exParams);
//                for (int i = 0, quoteIndex = -1; i < sb.length(); i++) {
//                    char c = sb.charAt(i);
//                    if (c == '"') {
//                        quoteIndex = i;
//                    }
//                    if (quoteIndex > -1) {
//                        for (i = i + 1; i < sb.length(); i++) {
//                            c = sb.charAt(i);
//                            if (c == '"') {
//                                quoteIndex = -1;
//                                break;
//                            }
//                            if (c == ' ') {
//                                sb.setCharAt(i, '~');
//                            }
//                        }
//                    }
//                }
//                exParams = sb.toString();
//                String[] paramList = exParams.split(" ");
//                for (String param : paramList) {
//                    if (param.startsWith(name)) {
//                        return param.split("=")[1].trim().replace('~', ' ').replace("\"", "");
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            // variable not found or malformed
//        }
//        return null;
//    }
//
//    /**
//     * Retrieves an environment variable
//     *
//     * @param name environment variable name to retrieve its value
//     * @return value of parameter or null if not found
//     */
//    public static String getEnvVar(String name) {
//        String value = System.getenv(name);
//        value = value == null ? null : value.trim();
//        if (value != null && !value.isEmpty()) {
//            return value;
//        }
//        return null;
//    }
//
//    /**
//     * Retrieves an environment variable OR ex_param
//     *
//     * @param name name of parameter to retrieve
//     * @return value of parameter or null if not found
//     */
//    public static String getEnvOrExParam(String name) {
//        String val = getEnvVar(name);
//        if (val == null) {
//            val = getExParam(name);
//        }
//        if (val == null) {
//            name = name.toLowerCase();
//            val = getEnvVar(name);
//        }
//        return val == null ? getExParam(name) : val;
//    }
//
//    /**
//     * Matches an ex_param against t|true and converts it to a boolean
//     *
//     * @param name name of parameter
//     * @return true if parameter exists and matches "t|true"
//     */
//    public static boolean booleanParam(String name) {
//        String param = getEnvOrExParam(name);
//        return param != null && param.matches("t|true");
//    }
//
//    /**
//     * Retrieves all the dependencies for the given project
//     *
//     * @param project name of project in format domain.projectName
//     * @return list of all projects the given project depends on
//     */
//    private static List<String> getDependencies(String project) {
//        ArrayList<String> deps = new ArrayList<>();
//        String pom = workspace + project.replace(".", "/") + "/pom.xml";
//        String domain = project.split("\\.")[0];
//        try {
//            Document doc = Jsoup.parse(Utils.readTextFile(new File(pom)), "", Parser.xmlParser());
//            for (Element e : doc.select("dependencies dependency artifactid")) {
//                if (e.html().startsWith("sdt-")) {
//                    String[] name = e.html().split("-");
//                    String projectName = name[1] + "." + name[2];
//                    projectName = projectName.replace("${project.domain}", domain);
//                    if (!deps.contains(projectName)) {
//                        deps.add(projectName);
//                    }
//                    log.debug("Found dependency: " + name[1] + "." + name[2]);
//                    List<String> sub = getDependencies(projectName);
//                    for (String dep : sub) {
//                        if (!deps.contains(dep)) {
//                            deps.add(dep);
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.error("Unable to read pom file: " + e);
//        }
//        return deps;
//    }
//
//    /**
//     * Gets all header values passed in the given header file (if present)
//     */
//    private static void getHeaders() {
//        if (headerFile == null) {
//            return;
//        }
//        try {
//            if (headerFile.matches("t|true")) {
//                headerFile = "headers.json";
//            }
//            if (!headerFile.endsWith(".json")) {
//                headerFile += ".json";
//            }
//            File headers = Utils.getResourceFile(headerFile);
//            if (!headers.exists()) {
//                return;
//            }
//            useProxy = true;
//            JSONObject headerJSON = new JSONObject(Utils.readTextFile(headers));
//            for (String key : headerJSON.keySet()) {
//                Object o = headerJSON.get(key);
//                if (o instanceof String) {
//                    // don't overwrite user-agent if we already have it from user-agent arg
//                    if (key.equals("User-Agent") && RunConfig.headers.containsKey("User-Agent")) {
//                        continue;
//                    }
//                    RunConfig.headers.put(key, (String) headerJSON.get(key));
//                } else {
//                    log.warn("Bad header: " + key);
//                }
//            }
//        } catch (IOException e) {
//            log.error("Unable to read header file");
//        }
//    }
//
//    private static void sanitizeEfcKey() {
//        if (efcKey == null) {
//            return;
//        }
//        ArrayList<String> split = new ArrayList<>();
//        Collections.addAll(split, efcKey.split(","));
//        efcKey = "\"" + Utils.listToString(split, "\",\"", null) + "\"";
//    }
//
//    /**
//     * Checks if current project is using MST resources directory
//     */
//    private static boolean usingMSTResources() {
//        File mstPages = new File(projectResourceDirs.get(0) + "elements/website/mst");
//        return mstPages.exists() && mstPages.isDirectory();
//    }
//
//    private static void setupWorkspace() {
//        if (workspace == null) {
//            workspace = System.getProperty("user.dir");
//        }
//        workspace = workspace.replace('\\', '/');
//        workspace = workspace.endsWith("/") ? workspace : workspace + "/";
//
//        Utils.createLogDirectory(logs = workspace + "logs/");
//        Utils.createDirectory(temp = workspace + "temp/", true);
//
//        // in debug mode, set stdout to show debug messages and sdt-debug.log to show trace messages
//        if (debugMode) {
//            ((ConsoleAppender) org.apache.log4j.Logger.getRootLogger().getAppender("STDOUT")).setThreshold(Level.DEBUG);
//            ((FileAppender) org.apache.log4j.Logger.getRootLogger().getAppender("FILE")).setThreshold(Level.TRACE);
//        }
//
//        if (remoteOS == null) {
//            log.trace("Remote OS not specified. Using default (Windows 7)");
//            remoteOS = "Windows 7";
//        }
//
//        // sanitize url arg
//        if (url != null && !url.matches("^https?://.*")) {
//            url = "https://" + url;
//        }
//
//        // having a slash on the end messes up relative navigation & cookie domain
//        if (url != null && url.endsWith("/")) {
//            url = url.substring(0, url.length() - 1);
//        }
//    }
//
//    private static void setupAnalytics() {
//        String analyticsClass = getEnvOrExParam("analytics");
//        if (analyticsClass != null) {
//            useProxy = true;
//            if (analyticsClass.equals("da")) {
//                analytics = new DigitalAnalytics();
//            }else if (analyticsClass.equals("aa")) {
//                analytics = new AdobeAnalytics();
//            }
//
//            if (analytics != null) {
//                log.info("Using Analytics: " + analytics.getClass().getSimpleName());
//            }
//        }
//
//        String akamaiClass = getEnvOrExParam("akamai");
//        if (akamaiClass != null && !akamaiClass.toLowerCase().equals("false")) {
//            akamai = true;
//            useProxy = true;
//            if (akamaiClass.toLowerCase().equals("staging")) {
//                useStaging = true;
//                log.info("Testing Akamai using staging config");
//            } else {
//                log.info("Testing Akamai using production config");
//            }
//        }
//
//        if (bandwidth > 0) {
//            useProxy = true;
//        }
//
//        // tag_collection
//        tagCollection = booleanParam("tag_collection");
//        if (tagCollection) {
//            log.info("tag_collection is enabled");
//        }
//    }
//
//    /**
//     * get cucumber scenarios from args if not in env - cucumber config does this in intellij
//     *
//     * @param args program arguments from main
//     */
//    static void getScenariosFromArgs(String[] args) {
//        scenarios = scenarios == null ? "" : scenarios;
//        if (scenarios.isEmpty() && args != null && args.length > 0) {
//            StringBuilder temp = new StringBuilder("");
//            for (String arg : args) {
//                File f = new File(arg);
//                if (f.exists() || f.getAbsoluteFile().exists()) {
//                    temp.append(arg);
//                }
//            }
//            scenarios = temp.toString();
//        }
//        scenarios = scenarios.replace('\\', '/');
//    }
//
//    /**
//     * Set various setting based on the given env variables
//     */
//    private static void inferTestSettings() {
//        // use saucelabs when valid "sauce_user" and "sauce_key" is provided
//        useSauceLabs = sauceUser != null && sauceKey != null;
//
//        // use testobject when valid "testobject_api_key" and "testObjectUser" is provided
//        useTestObject = testObjectAPIKey != null && testObjectUser != null;
//        if (useTestObject) {
//            // test object tests always run using appium
//            useAppium = true;
//        }
//
//        // use chrome emulation when it is mobile device and use of Appium is not mentioned
//        useChromeEmulation = StepUtils.mobileDevice() && !useAppium;
//
//        // Test is appTest when use of Appium is mentioned and app_location is given
//        appTest = useAppium && (appLocation != null);
//
//        // close browser at exist unless debugMode is on or test is appTest
//        closeBrowserAtExit = !(debugMode || appTest);
//
//        if (browser == null && !appTest) {
//            log.debug("No browser given, using default (chrome)");
//            browser = "chrome";
//        }
//
//        if (browserVersion == null) {
//            browserVersion = WebDriverConfigurator.defaultBrowserVersion();
//            log.debug("No Browser Version given, using default : " + browserVersion);
//        }
//
//        if (timeout == -1) {
//            timeout = StepUtils.safari() ? 130 : 95;
//        }
//    }
//}