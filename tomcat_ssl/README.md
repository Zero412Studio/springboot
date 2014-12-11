springboot
==========

Tomcat SSL example

1) generate a self signed keystore
    keytool -keystore keystore.jks -genkey -alias tomcat

2) start application, -Dexample.ssl.keystoreFile=PATH_TO_KEYSTORE_FILE