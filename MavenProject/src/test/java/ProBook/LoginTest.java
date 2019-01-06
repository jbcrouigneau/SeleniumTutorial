package ProBook;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {
	// Déclaration des variables que nous allons utiliser dans ce script.
    String url = "http://probook.progideo.com";
    String expectedTitle1 = "Dashboard - ProBook";
    String actualTitle1 = null;
    String username = "jbcrouigneau";
    String password = "NJAc1975";
    String expectedTitle2 = "(6) Dashboard - ProBook";
    String actualTitle2 = null;
    WebDriver driver;

	@Test
	public void f() {
        // On clique sur le lien "Se connecter / s'inscrire".
        driver.findElement(By.linkText("Sign in / up")).click();
        // On récupère le titre de la page actuelle
        actualTitle1 = driver.getTitle();
        // On affiche dans le log un message d'information
        Assert.assertEquals(actualTitle1, expectedTitle1);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login_username")));

        // On saisit le username et le password
        driver.findElement(By.id("login_username")).sendKeys(username);
        driver.findElement(By.id("login_password")).sendKeys(password);
        // On clique sur le bouton "Sign in"
        driver.findElement(By.id("loginBtn")).click();
        
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-dropdown-link")));
        
        // On vérifie le titre de la page suite à la tentative de connexion
        actualTitle2 = driver.getTitle();
        // On affiche dans le log un message d'information
        Assert.assertEquals(actualTitle2, expectedTitle2);
	}
	
	@BeforeMethod
	public void beforeMethod() {
        // Chemin vers le driver Gecko (pour Firefox uniquement)
        System.setProperty("webdriver.gecko.driver","/home/progideo/eclipse/drivers/geckodriver");
        // Invocation du navigateur Firefox, qui sera identifié avec le nom "driver".
        driver = new FirefoxDriver();
        // Ouvrir la page "http://probook.progideo.com".
        driver.get(url);
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		int status = result.getStatus();
		// Si le test est un échec, on prend une capture d'écra
		if (status == ITestResult.FAILURE) {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Date date = new Date();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			FileHandler.copy(scrFile, new File("./screenshots/" + result.getName() + "_" + dateFormat.format(date) + ".png" ));
		}
        // On ferme Firefox
        driver.close();
	}

}