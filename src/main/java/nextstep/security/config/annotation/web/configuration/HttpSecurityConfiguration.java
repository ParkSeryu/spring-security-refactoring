package nextstep.security.config.annotation.web.configuration;

import nextstep.security.config.annotation.web.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class HttpSecurityConfiguration {

    private static final String BEAN_NAME_PREFIX = "nextstep.security.config.annotation.web.configuration.HttpSecurityConfiguration.";

    private static final String HTTPSECURITY_BEAN_NAME = BEAN_NAME_PREFIX + "httpSecurity";

    @Bean(HTTPSECURITY_BEAN_NAME)
    @Scope("prototype")
    HttpSecurity httpSecurity() {
        return new HttpSecurity();
    }
}
