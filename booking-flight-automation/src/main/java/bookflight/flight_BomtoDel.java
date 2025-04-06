package bookflight;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class flight_BomtoDel {
    public static void main(String[] args) {
        // Set path for geckodriver
        System.setProperty("webdriver.gecko.driver", "D:\\Gecko\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Step 1: Open Booking.com Flights page
            driver.get("https://www.booking.com/flights/");
            
            // Step 2: Select 'One-way' radio button
            WebElement oneWay = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='search_type_option_ONEWAY']")));
            oneWay.click();

            // Step 3: Enter 'Mumbai (BOM)' in the 'From' field
            WebElement fromInput = driver.findElement(By.xpath("//*[@id=\"basiclayout\"]/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div/div/div[1]/div/button[1]/div[1]/span[2]/span/span"));
            fromInput.click();
            //fromInput.sendKeys(Keys.BACK_SPACE);
            WebElement fromInputText = driver.findElement(By.xpath("//*[@id=\":R12dl1995:\"]/div/div/div/div[1]/div/div/div/div/input"));
            fromInputText.click();
            fromInputText.sendKeys(Keys.BACK_SPACE);
            Thread.sleep(2000);
            fromInputText.sendKeys("Mumbai (BOM)");//
            Thread.sleep(2000); // Allow autocomplete to load
            WebElement checkBom = driver.findElement(By.xpath("//*[@id=\"flights-searchbox_suggestions\"]/li/span[3]/div"));
            checkBom.sendKeys(Keys.ENTER);

            // Step 4: Enter 'Delhi (DEL)' in the 'To' field
            WebElement toInput = driver.findElement(By.xpath("//*[@id=\"basiclayout\"]/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div/div/div[1]/div/button[3]/div[1]/span[2]/span/span"));
            toInput.click();
            WebElement toInputText = driver.findElement(By.xpath("//*[@id=\":R16dl1995:\"]/div/div/div/div[1]/div/div/div/div/input"));
            toInputText.click();
            toInputText.sendKeys(Keys.BACK_SPACE);
            Thread.sleep(1000);
            toInputText.sendKeys("Delhi (DEL)");
            Thread.sleep(1000);
            WebElement checkDel = driver.findElement(By.xpath("//*[@id=\"flights-searchbox_suggestions\"]/li[1]/span[3]/div/label/span[2]/span"));
            checkDel.click();
            Thread.sleep(2000);
           //checkDel.sendKeys(Keys.ENTER);
           //Thread.sleep(1000);

            // Step 5: Select today's date from the calendar
            
           
            WebElement clickDate = driver.findElement(By.xpath("//button[@placeholder='Choose departure date']"));
            clickDate.click();
            WebElement todayDate = driver.findElement(By.xpath("//*[@id=\":R4ll1995:\"]/div/div/div/div/div/div[1]/table/tbody/tr[3]/td[5]/span"));
            todayDate.click();
            // Step 6: Click 'Search' button
            WebElement searchBtn = driver.findElement(By.xpath("//button[@data-ui-name='button_search_submit']"));
            searchBtn.click();

            Thread.sleep(5000); 
            WebElement cheapestTab = driver.findElement(By.id("TAB-CHEAPEST"));
            cheapestTab.click();
            Thread.sleep(2000);
            // Wait for the page to refresh with the cheapest results
           

            // Locate the first flight displayed (cheapest flight)
           // WebElement cheapestFlight = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-testid='flight_card']")));

            WebElement cheapestFlight = driver.findElement(By.xpath("//*[@id=\"flight-card-0\"]/div/div"));
            
            String airline = cheapestFlight.findElement(By.xpath("//*[@id=\"flight-card-0\"]/div/div/div[1]/div[3]/div")).getText();
            String departureTime = cheapestFlight.findElement(By.xpath("//*[@id=\"flight-card-0\"]/div/div/div[1]/div[2]/div/div/div/div[2]/div[1]/div[2]/div/div[1]/div")).getText();
            String price = cheapestFlight.findElement(By.xpath("//*[@id=\"flight-card-0\"]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div")).getText();

            System.out.println("Cheapest Flight Details:");
            System.out.println("Airline: " + airline);
            System.out.println("Departure Time: " + departureTime);
            System.out.println("Price: " + price);

            // Step 8: Write flight details to an Excel file
            writeToExcel(airline, departureTime, price);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close browser
            driver.quit();
        }
    }

    private static void writeToExcel(String airline, String departureTime, String price) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Flight Details");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Airline");
        headerRow.createCell(1).setCellValue("Departure Time");
        headerRow.createCell(2).setCellValue("Price");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(airline);
        dataRow.createCell(1).setCellValue(departureTime);
        dataRow.createCell(2).setCellValue(price);

        try (FileOutputStream fileOut = new FileOutputStream("FlightDetails.xlsx")) {
            workbook.write(fileOut);
        }

        workbook.close();
        System.out.println("Flight details saved to FlightDetails.xlsx");
    }
}


	



