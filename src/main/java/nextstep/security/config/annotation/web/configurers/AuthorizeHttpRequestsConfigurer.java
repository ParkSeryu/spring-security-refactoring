package nextstep.security.config.annotation.web.configurers;

import java.util.ArrayList;
import java.util.List;
import nextstep.security.access.RequestMatcher;
import nextstep.security.access.RequestMatcherEntry;
import nextstep.security.authorization.AuthorizationFilter;
import nextstep.security.authorization.AuthorizationManager;
import nextstep.security.authorization.RequestMatcherDelegatingAuthorizationManager;
import nextstep.security.config.annotation.SecurityConfigurer;
import nextstep.security.config.annotation.web.HttpSecurity;

public class AuthorizeHttpRequestsConfigurer implements SecurityConfigurer {
    private final List<RequestMatcherEntry<AuthorizationManager>> mappings = new ArrayList<>();

    @Override
    public void init(HttpSecurity http) {

    }

    @Override
    public void configure(HttpSecurity http) {
        AuthorizationFilter authorizationFilter = new AuthorizationFilter(
                new RequestMatcherDelegatingAuthorizationManager(mappings));
        http.addFilter(authorizationFilter);
    }

    public AuthorizeHttpRequestsConfigurer requestsMatchers(RequestMatcher requestMatcher,
                                                            AuthorizationManager manager) {
        this.mappings.add(new RequestMatcherEntry<>(requestMatcher, manager));
        return this;
    }
}
