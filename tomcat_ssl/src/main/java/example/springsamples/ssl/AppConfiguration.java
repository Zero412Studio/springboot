package example.springsamples.ssl;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Configuration
@EnableConfigurationProperties(SSLProperties.class)
public class AppConfiguration {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new SSLCustomizer();
    }

    private static class SSLCustomizer implements
            EmbeddedServletContainerCustomizer {

    @Autowired
    private SSLProperties sslProperties;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer factory) {
            if (factory instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
                if (sslProperties.isEnabled()) {
                    containerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
                        @Override
                        public void customize(Connector connector) {
                            String absoluteKeystoreFile = null;
                            try {
                                absoluteKeystoreFile = ResourceUtils.getFile(sslProperties.getKeystoreFile()).getAbsolutePath();
                            } catch (FileNotFoundException e) {
                                System.err.println(e.getMessage());
                                System.out.println(absoluteKeystoreFile);
                                System.exit(1);
                            }

                            connector.setPort(sslProperties.getPort());
                            connector.setSecure(sslProperties.isSecure());
                            connector.setScheme(sslProperties.getSchema());

                            Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
                            proto.setSSLEnabled(true);
                            proto.setSslProtocol(sslProperties.getProtocol());
                            proto.setClientAuth(sslProperties.getClientAuth());

                            proto.setKeystoreFile(absoluteKeystoreFile);
                            proto.setKeystorePass(sslProperties.getKeystorePassword());
                            proto.setKeyAlias(sslProperties.getKeystoreAlias());

                            if (sslProperties.getCiphers() != null) {
                                proto.setCiphers(sslProperties.getCiphers());
                            }
                        }
                    });
                }
            }
        }
    }
}
