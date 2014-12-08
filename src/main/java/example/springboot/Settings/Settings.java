package example.springboot.Settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * load settings from database
 */
public class Settings extends Properties {
    static Logger log = LoggerFactory.getLogger(Settings.class);

    private static final long serialVersionUID = 1L;
    private static final String LOAD_CONFIG_QUERY = "select propertyName, propertyValue from settings";

    public Settings(DataSource dataSource)
    {
        super();
        new JdbcTemplate(dataSource).query(LOAD_CONFIG_QUERY, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                String name = rs.getString("propertyName");
                String value = rs.getString("propertyValue");
                if(value == null) value = new String();
                log.info(String.format("Loading from DB: [%s:%s]", name, value));
                setProperty(name, value);
            }
        });
    }
}
