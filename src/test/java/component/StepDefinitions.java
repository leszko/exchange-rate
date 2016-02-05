package component;

import com.summercoding.zooplus.Application;
import com.summercoding.zooplus.api.ExchangeRateController;
import com.summercoding.zooplus.service.ExchangeRateService;
import com.summercoding.zooplus.model.User;
import com.summercoding.zooplus.repository.UserRepository;
import com.summercoding.zooplus.repository.HistoryElementRepository;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Date;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {Application.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class StepDefinitions {
    private final static String SERVER = "http://localhost:8080/";

    private final WebDriver browser = new FirefoxDriver();

    @Autowired
    private HistoryElementRepository historyElementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ExchangeRateController exchangeRateController;

    private ExchangeRateService exchangeRateService = mock(ExchangeRateService.class);

    @Before
    public void before() {
        ReflectionTestUtils.setField(exchangeRateController, "exchangeRateService", exchangeRateService);
    }

    @After
    public void after() {
        historyElementRepository.deleteAll();
        userRepository.deleteAll();
        browser.close();
    }

    @Given("^I go to the (.*) page$")
    public void I_go_to_the_page(String pageName) throws Throwable {
        browser.get(SERVER + pageName);
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
        assertThat(userRepository.findByName(name)).isNotNull();
    }

    @Then("^User (.*) should not be registered$")
    public void User_should_not_be_registered(String name) throws Throwable {
        assertThat(userRepository.findByName(name)).isNull();
    }

    @Then("^Page (.*) is displayed$")
    public void Page_is_displayed(String pageName) throws Throwable {
        assertThat(browser.getCurrentUrl()).isEqualTo(SERVER + pageName);
    }

    @Given("^User with name: '(.*)' and password: '(.*)' is registered$")
    public void User_with_name_and_password_is_registered(String name, String password) throws Throwable {
        User user = new User();
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setBirthDate(new Date());
        user.setCity("city");
        user.setCountry("country");
        user.setEmail("test@test.com");
        user.setStreet("street");
        user.setZipCode("zip-code");

        userRepository.save(user);
    }

    @Given("^User is logged in$")
    public void User_is_logged_in() throws Throwable {
        User_with_name_and_password_is_registered("test", "test123");
        I_go_to_the_page("login");
        I_enter("name", "test");
        I_enter("password", "test123");
        I_click_submit();
    }

    @Given("^live (.*) exchange rate is (.*)$")
    public void live_USD_exchange_rate_is_(String currency, String exchangeRate) throws Throwable {
        given(exchangeRateService.live(currency)).willReturn(new BigDecimal(exchangeRate));
    }

    @Given("^historical (.*) rate for date (.*) is (.*)$")
    public void historical_USD_rate_for_date_is_(String currency, String date, String exchangeRate) throws Throwable {
        given(exchangeRateService.historical(currency, date)).willReturn(new BigDecimal(exchangeRate));
    }

    @Then("^Page contains text '(.*)'$")
    public void Page_contains_text(String text) throws Throwable {
        assertThat(browser.getPageSource()).contains(text);
    }
}
