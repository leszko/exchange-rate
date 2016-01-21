package component;

import com.summercoding.zooplus.Application;
import cucumber.api.java.en.Given;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class SampleStepDefinitions {
    @Given("^Sample step$")
    public void sampleStep() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("SAMPLE STEP");
    }
}
