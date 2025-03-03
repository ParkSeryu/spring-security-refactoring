package nextstep.security.config.annotation.web.configuration;

import jakarta.servlet.Filter;
import java.util.Collections;
import java.util.List;
import nextstep.security.access.AnyRequestMatcher;
import nextstep.security.authorization.PermitAllAuthorizationManager;
import nextstep.security.config.Customizer;
import nextstep.security.config.DefaultSecurityFilterChain;
import nextstep.security.config.FilterChainProxy;
import nextstep.security.config.SecurityFilterChain;
import nextstep.security.config.annotation.web.HttpSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfiguration {
    @Autowired(required = false)
    private HttpSecurity httpSecurity;
    private List<SecurityFilterChain> securityFilterChains = Collections.emptyList();

    @Bean(
            name = {"springSecurityFilterChain"}
    )
    public Filter springSecurityFilterChain() {
        boolean hasFilterChain = !this.securityFilterChains.isEmpty();

        if (!hasFilterChain) {
            this.httpSecurity.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
            this.httpSecurity.formLogin(Customizer.withDefaults());
            this.httpSecurity.httpBasic(Customizer.withDefaults());
            DefaultSecurityFilterChain filterChain = (DefaultSecurityFilterChain) this.httpSecurity.build();
            securityFilterChains.add(filterChain);
        }
        return new FilterChainProxy(securityFilterChains);
    }

    @Autowired(required = false)
    void setFilterChains(List<SecurityFilterChain> securityFilterChains) {
        this.securityFilterChains = securityFilterChains;
    }
}
