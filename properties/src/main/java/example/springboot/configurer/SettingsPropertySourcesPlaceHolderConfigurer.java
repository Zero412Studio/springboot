package example.springboot.configurer;

import example.springboot.Settings.Settings;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * SettingsPropertySourcesPlaceHolderConfigurer
 *
 * read settings from database and load into spring context
 */
@ConfigurationProperties()
public class SettingsPropertySourcesPlaceHolderConfigurer extends PropertySourcesPlaceholderConfigurer {
    private static final String PROPERTY_DB_DRIVER_NAME = "spring.datasource.driverClassName";
    private static final String PROPERTY_DB_URL = "spring.datasource.url";
    private static final String PROPERTY_DB_USERNAME = "spring.datasource.username";
    private static final String PROPERTY_DB_PASSWORD = "spring.datasource.password";

    String sqlScript = null;

    public SettingsPropertySourcesPlaceHolderConfigurer() {
    }

    public SettingsPropertySourcesPlaceHolderConfigurer(String script) {
        this.sqlScript = script;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        super.postProcessBeanFactory(beanFactory);
        PropertySource source = (PropertySource) this.getAppliedPropertySources().get(ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME);

        String dbDriver = null, dbUrl = null, dbUsername = null, dbPassword = null;
        if (source != null)
        {
            dbDriver = (String) source.getProperty(PROPERTY_DB_DRIVER_NAME);
            dbUrl = (String) source.getProperty(PROPERTY_DB_URL);
            dbUsername = (String) source.getProperty(PROPERTY_DB_USERNAME);
            dbPassword = (String) source.getProperty(PROPERTY_DB_PASSWORD);

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(dbDriver);
            dataSource.setUrl(dbUrl);
            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);

            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

            if (sqlScript != null) {
                Resource scriptResource = resourceLoader.getResource(sqlScript);
                if (!scriptResource.exists()) {
                    throw new IllegalArgumentException("script file does not exist");
                }

                try {
                    Connection conn = dataSource.getConnection();
                    ScriptUtils.executeSqlScript(conn, scriptResource);
                    conn.commit();
                } catch (SQLException sqle)
                {
                    throw new FatalBeanException("SQL script exception", sqle);
                }
            }

            Settings dbProps = new Settings(dataSource);
            setProperties(dbProps);

            // update db properties as environment
            PropertiesPropertySource propertySource = new PropertiesPropertySource("mySettings", dbProps);
            MutablePropertySources propertySources = (MutablePropertySources) this.getAppliedPropertySources();
            propertySources.addFirst(propertySource);
            PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(propertySources);

            super.processProperties(beanFactory, resolver);
        }
    }
}
