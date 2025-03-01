package nextstep.security.config.annotation;

import nextstep.security.config.annotation.web.HttpSecurity;

public interface SecurityConfigurer {
    void init(HttpSecurity http);

    void configure(HttpSecurity http);
}
