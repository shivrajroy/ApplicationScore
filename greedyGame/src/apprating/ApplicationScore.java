package apprating;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ApplicationScore {
	
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

	public static LocalDate getDateFromString(String string, DateTimeFormatter format) {
		// Convert the string to date in the specified format
		LocalDate date = LocalDate.parse(string, format);
		// return the converted date
		return date;
	}

	public static void main(String[] args) throws InterruptedException, ParseException {
		
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\shivr\\Downloads\\chromedriver_win32\\chromedriver.exe");
		
		// Initialize browser
		WebDriver driver = new ChromeDriver();
		driver.get("https://play.google.com/store/apps/details?id=com.s2apps.game2048&hl=en");
      	driver.manage().window().maximize();
      	
      	//extracting application name
		StringBuffer windowTitle = new StringBuffer(driver.getTitle());
		String extraTitle = " - Apps on Google Play";
		int windowTitleLength = windowTitle.length();
		int extraTitleLength = extraTitle.length();
		int actualNameLength = windowTitleLength - extraTitleLength;
		windowTitle.delete(actualNameLength, windowTitleLength);
		System.out.println("The name of the application is: " + windowTitle);

		//extracting the number of reviews
		WebElement reviewElement = driver.findElement(By.xpath("(//span[@class='EymY4b'])/span[2]"));
		String reviews = reviewElement.getText();
		reviews = reviews.replaceAll("[^a-zA-Z0-9]", "");
		double reviewCount = Double.parseDouble(reviews);
		System.out.println("Number of Reviews of the application: " + reviewCount);

		//extracting last update date
		WebElement updatedDateLoc = driver.findElement(By.xpath("(//div[@class='IQ1z0d']/span)[1]"));
		String updatedDate = updatedDateLoc.getText();
		DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");

		//printing last updated date and today's date
		Date currentDate = new Date();
		System.out.println("Last updated date is: " + updatedDate);
		System.out.print("Today's date is: ");
		System.out.println(dateFormat.format(currentDate));

		//computing difference between today's date and last updated date
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date1 = simpleDateFormat.parse(updatedDate);
		long differenceInTime = currentDate.getTime() - date1.getTime();
		System.out.println("Number of days since last update is: " + TimeUnit.DAYS.convert(differenceInTime, TimeUnit.MILLISECONDS));

        //computing score of the application
		double differenceInDays = TimeUnit.DAYS.convert(differenceInTime, TimeUnit.MILLISECONDS);
		double score = reviewCount/differenceInDays;

		System.out.println("Score for the App is :" + decimalFormat.format(score));

	}

}
