package com.coolprodbp.tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class COOLProdBPTest {

	ExtentReports extent;
    ExtentTest test;
    WebDriver driver;
     
    @BeforeTest
    public void init()
    {
    	driver = new FirefoxDriver();
        extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/MonitoringResults.html", true);
    }
     
    @Test
    public void coolProdBPTest() throws InterruptedException
    {
        test = extent.startTest("monitoringTest");        
        test.log(LogStatus.PASS, "Browser started");
        driver.get("https://cool.podc.sl.edst.ibm.com/cool/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000);
        WebElement ele=driver.findElement(By.xpath("//*[@id='ng-mobile-signUp']"));
        String content=ele.getText();
        System.out.println("Create an ID link is :  "+content);
        Assert.assertEquals("Create an IBMid.", content);
        test.log(LogStatus.PASS, "Create an IBMId");
        Thread.sleep(1000);
        ele.click();
        Thread.sleep(3000);
        boolean searchEmailPresence = driver.findElement(By.xpath("//input[@name='emailAddress']")).isDisplayed();
              
        if (searchEmailPresence==true)
        {
        	WebElement email=driver.findElement(By.xpath("//button[@id='continueButton']"));
        	email.click();        	
        }    
       
        WebElement elem=driver.findElement(By.xpath("//span[contains(text(),'Email is required')]"));
        String alert=elem.getText();
        System.out.println("Alert Content is :  "+alert);
        Assert.assertEquals("Email is required", alert);
        test.log(LogStatus.PASS, "Email required alert content");
        
        
    }
        
    @AfterMethod
    public void getResult(ITestResult result) throws IOException
    {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            String screenShotPath = GetScreenShot.capture(driver, "Monitoring");
            test.log(LogStatus.FAIL, result.getThrowable());
            test.log(LogStatus.FAIL, "Snapshot below: " + test.addScreenCapture(screenShotPath));
        }
        extent.endTest(test);
    }
     
         
    @AfterTest
    public void endreport()
    {
        driver.close();
        extent.flush();
        extent.close();
    }
}



