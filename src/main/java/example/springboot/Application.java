package example.springboot;

import example.springboot.Settings.ConfiguredSettings;
import example.springboot.configurer.SettingsPropertySourcesPlaceHolderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Spring boot example
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public SettingsPropertySourcesPlaceHolderConfigurer settingsPropertySourcesPlaceholderConfigurer() {
        SettingsPropertySourcesPlaceHolderConfigurer placeholder =
                new SettingsPropertySourcesPlaceHolderConfigurer("tables.sql");
        placeholder.setOrder(1);
        placeholder.setLocalOverride(true);
        return placeholder;
    }

    @Bean
    public ConfiguredSettings configuredSettings() {
        return new ConfiguredSettings();
    }
}
