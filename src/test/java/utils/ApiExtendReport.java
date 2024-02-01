package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class ApiExtendReport {

        private static ExtentReports extent;
        private static ExtentTest test;

        @BeforeTest(alwaysRun = true)
        public static void initializeReport() {
            String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            String filePath = System.getProperty("user.dir") + "/target/Rapor/rapor"+date+".html";
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Enviroment","QA");
            extent.setSystemInfo("Automation Engineer", "Esma");
        }

        public static void createTest(String testName) {
            test = extent.createTest(testName);
        }

        public static void logPass(String logMessage) {
            test.log(Status.PASS, logMessage);
        }

        public static void logFail(String logMessage) {
            test.log(Status.FAIL, logMessage);
        }

        @AfterMethod
        public static void tearDown(ITestResult result){
            if (result.isSuccess()) {
                logPass("Test geçti.");
            } else if (result.getStatus() == ITestResult.FAILURE) {
               logFail("Test fail oldu.");
            }
        }

        public static void flushReport() {
        extent.flush();
    }


    }

