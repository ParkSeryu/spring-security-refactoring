package nextstep.security.config.annotation.web;

import jakarta.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nextstep.security.authentication.AuthenticationManager;
import nextstep.security.config.Customizer;
import nextstep.security.config.DefaultSecurityFilterChain;
import nextstep.security.config.SecurityFilterChain;
import nextstep.security.config.annotation.SecurityConfigurer;
import nextstep.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import nextstep.security.config.annotation.web.configurers.CsrfConfigurer;
import nextstep.security.config.annotation.web.configurers.FormLoginConfigurer;
import nextstep.security.config.annotation.web.configurers.HttpBasicConfigurer;
import nextstep.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.context.ApplicationContext;

public class HttpSecurity {
    private final LinkedHashMap<Class<? extends SecurityConfigurer>, SecurityConfigurer> configurers = new LinkedHashMap<>();
    private final List<Filter> filters = new ArrayList<>();
    private final Map<Class<?>, Object> sharedObjects = new HashMap<>();

    public HttpSecurity(AuthenticationManager authenticationManager, Map<Class<?>, Object> sharedObjects) {
        setSharedObject(AuthenticationManager.class, authenticationManager);
        for (Map.Entry<Class<?>, Object> entry : sharedObjects.entrySet()) {
            setSharedObject((Class<Object>) entry.getKey(), entry.getValue());
        }
    }

    public <C> C getSharedObject(Class<C> sharedType) {
        return (C) this.sharedObjects.get(sharedType);
    }

    public <C> void setSharedObject(Class<C> sharedType, C object) {
        this.sharedObjects.put(sharedType, object);
    }

    public SecurityFilterChain build() {
        init();
        configure();
        return new DefaultSecurityFilterChain(filters);
    }

    private void init() {
        for (SecurityConfigurer configurer : this.configurers.values()) {
            configurer.init(this);
        }
    }

    private void configure() {
        for (SecurityConfigurer configurer : this.configurers.values()) {
            configurer.configure(this);
        }
    }

    public HttpSecurity csrf(Customizer<CsrfConfigurer> csrfCustomizer) {
        csrfCustomizer.customize(getOrApply(new CsrfConfigurer()));
        return HttpSecurity.this;
    }

    public HttpSecurity formLogin(Customizer<FormLoginConfigurer> formLoginCustomizer) {
        formLoginCustomizer.customize(getOrApply(new FormLoginConfigurer()));
        return HttpSecurity.this;
    }

    public HttpSecurity httpBasic(Customizer<HttpBasicConfigurer> httpBasicCustomizer) {
        httpBasicCustomizer.customize(getOrApply(new HttpBasicConfigurer()));
        return HttpSecurity.this;
    }

    public HttpSecurity securityContext(Customizer<SecurityContextConfigurer> securityContextCustomizer) {
        securityContextCustomizer.customize(getOrApply(new SecurityContextConfigurer()));
        return HttpSecurity.this;
    }

    public HttpSecurity authorizeHttpRequests(
            Customizer<AuthorizeHttpRequestsConfigurer> authorizeHttpRequestsCustomizer) {
        authorizeHttpRequestsCustomizer
                .customize(getOrApply(new AuthorizeHttpRequestsConfigurer()));
        return HttpSecurity.this;
    }


    private <C extends SecurityConfigurer> C getOrApply(C configurer) {
        Class<? extends SecurityConfigurer> clazz = configurer.getClass();
        this.configurers.put(clazz, configurer);
        return configurer;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }
}
