package Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporter {

    public static ExtentReports reporter;
    private static ThreadLocal<ExtentTest> testReportThread = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> parentTestThread = new ThreadLocal<>();

    private static final String timeRep = new SimpleDateFormat("HHmm-dd-MM-yyyy").format(new Date());
    private static final String folderRepName = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + timeRep + File.separator;
    private static final String folderScreenshootsName = folderRepName + "screenshots" + File.separator;


    public static void initReport(String env) throws UnknownHostException {
        new File(folderRepName).mkdirs();
        new File(folderScreenshootsName).mkdirs();

        ExtentSparkReporter spark = new ExtentSparkReporter(folderRepName + "FinalReport.html")
                .viewConfigurer()
                .viewOrder()
                .as(new ViewName[] {
                        ViewName.DASHBOARD,
                        ViewName.TEST,
                        ViewName.EXCEPTION,
                        ViewName.LOG
                })
                .apply();

        spark.config().setDocumentTitle("Automation Report - Extent Report");
        spark.config().setTimeStampFormat("hh:mm:ss");
        spark.config().setReportName("Automation Tests");
        spark.config().thumbnailForBase64(Boolean.TRUE);
        spark.config().setEncoding("UTF-8");
        spark.config().setTheme(Theme.STANDARD);

        reporter = new ExtentReports();
        reporter.attachReporter(spark);
        reporter.setSystemInfo("OS", System.getProperty("os.name"));
        reporter.setSystemInfo("Environment", env);
        reporter.setSystemInfo("Hostname", InetAddress.getLocalHost().getHostName());
    }

    public static void initTestMethod(String testNGTestName, String testMethodName) {
        ExtentTest parentTest = parentTestThread.get();
        if (parentTest == null || !parentTest.getModel().getName().equals(testNGTestName)) {
            parentTest = reporter.createTest(testNGTestName);
            parentTestThread.set(parentTest);
        }
        ExtentTest childTest = parentTest.createNode(testMethodName);
        testReportThread.set(childTest);
    }


    public static void log(Status status, String description)  {
        ExtentTest currentTest = testReportThread.get();
        if (currentTest != null) {
            currentTest.log(status, description);
        } else {
            System.err.println("Error: ExtentTest is null for this thread. Call initTestMethod() before logging.");
        }
    }

    public static void log(Exception e)  {
        ExtentTest currentTest = testReportThread.get();
        if (currentTest != null) {
            currentTest.fail(e);
        } else {
            System.err.println("Error: ExtentTest is null for this thread. Call initTestMethod() before logging an exception.");
        }
    }

    public static void closeReport(String suitName){
        if (reporter != null) {
            reporter.flush();
            reporter = null;
        } else {
            System.err.println("Error: ExtentReports '" + suitName + "' is null. Cannot flush report.");
        }
    }

    public static void getSnapshoot(WebDriver driver) {
        ExtentTest currentTest = testReportThread.get();
        if (currentTest == null) {
            System.err.println("Error: ExtentTest is null for this thread. Cannot capture screenshot.");
            return;
        }

        String timeStamp = new SimpleDateFormat("mmss").format(new Date());
        String screenshotPath = folderScreenshootsName + timeStamp + ".png";

        TakesScreenshot scrShot =((TakesScreenshot)driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File(screenshotPath);
        try {
            FileUtils.copyFile(SrcFile, DestFile);
            currentTest.log(Status.FAIL, "Screenshot: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (IOException e) {
            currentTest.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
            System.err.println("Error al guardar la captura de pantalla: " + e.getMessage());
        }
    }
}