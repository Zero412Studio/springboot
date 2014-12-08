package example.springboot;

import example.springboot.Settings.ConfiguredSettings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.Assert;

/**
 * SettingsTest
 */
@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SettingsTest {

    @Autowired
    ConfiguredSettings configuredSettings;

    @Test
    public void testConfiguredSettings() throws Exception {
        System.out.println(configuredSettings.toString());
        Assert.assertEquals(configuredSettings.getPropertyBar(), "bar");
        org.junit.Assert.assertEquals(configuredSettings.getPropertyFoo(), "foo");
    }
}
