package nextstep.app;

import nextstep.oauth2.OAuth2ClientProperties;
import nextstep.oauth2.registration.ClientRegistration;
import nextstep.oauth2.registration.ClientRegistrationRepository;
import nextstep.security.access.AnyRequestMatcher;
import nextstep.security.access.MvcRequestMatcher;
import nextstep.security.access.hierarchicalroles.RoleHierarchy;
import nextstep.security.access.hierarchicalroles.RoleHierarchyImpl;
import nextstep.security.authorization.*;
import nextstep.security.config.Customizer;
import nextstep.security.config.SecurityFilterChain;
import nextstep.security.config.annotation.EnableWebSecurity;
import nextstep.security.config.annotation.web.HttpSecurity;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;

import java.util.*;

@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecuredMethodInterceptor securedMethodInterceptor() {
        return new SecuredMethodInterceptor();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.with()
                .role("ADMIN").implies("USER")
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(config -> config.ignoringRequestMatchers("/login"))
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestsMatchers(new MvcRequestMatcher(HttpMethod.GET, "/members"),
                                new AuthorityAuthorizationManager(roleHierarchy(), "ADMIN"))
                        .requestsMatchers(new MvcRequestMatcher(HttpMethod.GET, "/members/me"),
                                new AuthorityAuthorizationManager(roleHierarchy(), "USER"))
                        .requestsMatchers(AnyRequestMatcher.INSTANCE, new PermitAllAuthorizationManager<Void>())
                )
                .build();
    }
}

