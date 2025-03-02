package nextstep.security.config.annotation.web.configurers;

import nextstep.security.config.annotation.SecurityConfigurer;
import nextstep.security.config.annotation.web.HttpSecurity;
import nextstep.security.context.SecurityContextHolderFilter;

public class SecurityContextConfigurer implements SecurityConfigurer {
    @Override
    public void init(HttpSecurity http) {

    }

    @Override
    public void configure(HttpSecurity http) {
        SecurityContextHolderFilter securityContextHolderFilter = new SecurityContextHolderFilter();
        http.addFilter(securityContextHolderFilter);
    }
}

