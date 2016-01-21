package component;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Component test feature runner.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:feature")
public class FeatureComponentTest {
}
