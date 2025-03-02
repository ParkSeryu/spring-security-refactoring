package nextstep.security.config.annotation.web.configurers;

import nextstep.security.authentication.AuthenticationManager;
import nextstep.security.authentication.BasicAuthenticationFilter;
import nextstep.security.config.annotation.SecurityConfigurer;
import nextstep.security.config.annotation.web.HttpSecurity;

public class HttpBasicConfigurer implements SecurityConfigurer {
    @Override
    public void init(HttpSecurity http) {

    }

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        BasicAuthenticationFilter basicAuthenticationFilter = new BasicAuthenticationFilter(authenticationManager);
        http.addFilter(basicAuthenticationFilter);
    }
}
