package nextstep.security.config.annotation.web.configuration;

import java.util.HashMap;
import java.util.Map;
import nextstep.security.authentication.AuthenticationManager;
import nextstep.security.config.Customizer;
import nextstep.security.config.annotation.authentication.AuthenticationManagerBuilder;
import nextstep.security.config.annotation.web.HttpSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class HttpSecurityConfiguration {

    private static final String BEAN_NAME_PREFIX = "nextstep.security.config.annotation.web.configuration.HttpSecurityConfiguration.";

    private static final String HTTPSECURITY_BEAN_NAME = BEAN_NAME_PREFIX + "httpSecurity";

    private ApplicationContext context;

    @Autowired
    void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }


    @Bean(HTTPSECURITY_BEAN_NAME)
    @Scope("prototype")
    HttpSecurity httpSecurity() {
        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(context);
        return new HttpSecurity(authenticationManagerBuilder, createSharedObjects()).securityContext(
                Customizer.withDefaults());
    }

    private Map<Class<?>, Object> createSharedObjects() {
        Map<Class<?>, Object> sharedObjects = new HashMap<>();
        sharedObjects.put(ApplicationContext.class, this.context);
        return sharedObjects;
    }

}
