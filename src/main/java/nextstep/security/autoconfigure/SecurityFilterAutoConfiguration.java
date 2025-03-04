package nextstep.security.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(
        after = {SecurityAutoConfiguration.class}
)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class SecurityFilterAutoConfiguration {
    private static final String DEFAULT_FILTER_NAME = "springSecurityFilterChain";

    @Bean
    public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration() {
        return new DelegatingFilterProxyRegistrationBean(
                DEFAULT_FILTER_NAME);
    }
}
