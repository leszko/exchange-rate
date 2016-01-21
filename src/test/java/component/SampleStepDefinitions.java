package component;

import com.summercoding.zooplus.Application;
import com.summercoding.zooplus.repository.AccountRepository;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.google.common.truth.Truth.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class SampleStepDefinitions {
    private WebDriver browser = new FirefoxDriver();

    @Autowired
    private AccountRepository accountRepository;

    @After
    public void after() {
        accountRepository.deleteAll();
        browser.close();
    }

    @Given("^I go to the register page$")
    public void I_go_to_the_register_page() throws Throwable {
        browser.get("http://localhost:8080/register");
    }

    @When("^I enter (.*): (.*)$")
    public void I_enter(String id, String value) throws Throwable {
        browser.findElement(By.id(id)).sendKeys(value);
    }

    @When("^I click submit$")
    public void I_click_submit() throws Throwable {
        browser.findElement(By.id("submit")).click();
    }

    @Then("^User (.*) should be registered$")
    public void User_should_be_registered(String name) throws Throwable {
        assertThat(accountRepository.findByName(name)).isNotNull();
    }

    @Then("^User (.*) should not be registered$")
    public void User_should_not_be_registered(String name) throws Throwable {
        assertThat(accountRepository.findByName(name)).isNull();
    }
}
