package example.springboot.Settings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * ConfiguredSettings
 */
public class ConfiguredSettings {

    @Value("${property.name.foo}")
    String propertyFoo;

    @Value("${property.name.bar}")
    String propertyBar;

    public String getPropertyFoo() {
        return propertyFoo;
    }

    public void setPropertyFoo(String propertyFoo) {
        this.propertyFoo = propertyFoo;
    }

    public String getPropertyBar() {
        return propertyBar;
    }

    public void setPropertyBar(String propertyBar) {
        this.propertyBar = propertyBar;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("propertyFoo = ").append(propertyFoo);
        sb.append("\n").append("propertyBar = ").append(propertyBar);

        return sb.toString();
    }
}
