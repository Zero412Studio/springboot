package example.springsamples.ssl;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="example.ssl")
public class SSLProperties {

    private int port;

    private String clientAuth;

    private boolean enabled;

    private boolean secure;

    private String schema;

    private String protocol;

    private String keystoreAlias;

    private String keystorePassword;

    private String keystoreFile;

    private String ciphers;

    public int getPort() { return port; }

    public void setPort(int port) { this.port = port; }

    public String getClientAuth() { return clientAuth; }

    public void setClientAuth(String clientAuth) { this.clientAuth = clientAuth; }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public boolean isSecure() { return secure; }

    public void setSecure(boolean secure) { this.secure = secure; }

    public String getSchema() { return schema; }

    public void setSchema(String schema) { this.schema = schema; }

    public String getProtocol() { return protocol; }

    public void setProtocol(String protocol) { this.protocol = protocol; }

    public String getKeystoreAlias() { return keystoreAlias; }

    public void setKeystoreAlias(String keystoreAlias) { this.keystoreAlias = keystoreAlias; }

    public String getKeystorePassword() { return keystorePassword; }

    public void setKeystorePassword(String keystorePassword) { this.keystorePassword = keystorePassword; }

    public String getKeystoreFile() { return keystoreFile; }

    public void setKeystoreFile(String keystoreFile) { this.keystoreFile = keystoreFile; }

    public String getCiphers() {
        return ciphers;
    }

    public void setCiphers(String ciphers) {
        this.ciphers = ciphers;
    }
}