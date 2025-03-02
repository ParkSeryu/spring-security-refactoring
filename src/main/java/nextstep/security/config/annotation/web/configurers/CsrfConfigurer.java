package nextstep.security.config.annotation.web.configurers;

import java.util.ArrayList;
import java.util.List;
import nextstep.security.access.AndRequestMatcher;
import nextstep.security.access.MvcRequestMatcher;
import nextstep.security.access.NegatedRequestMatcher;
import nextstep.security.access.OrRequestMatcher;
import nextstep.security.access.RequestMatcher;
import nextstep.security.config.annotation.SecurityConfigurer;
import nextstep.security.config.annotation.web.HttpSecurity;
import nextstep.security.web.csrf.CsrfFilter;
import nextstep.security.web.csrf.CsrfTokenRepository;

public class CsrfConfigurer implements SecurityConfigurer {
    private final CsrfTokenRepository csrfTokenRepository = new CsrfTokenRepository();
    private final RequestMatcher requireCsrfProtectionMatcher = CsrfFilter.DEFAULT_CSRF_MATCHER;
    private final List<RequestMatcher> ignoredCsrfProtectionMatchers = new ArrayList<>();

    @Override
    public void init(HttpSecurity http) {

    }

    @Override
    public void configure(HttpSecurity http) {
        CsrfFilter filter = new CsrfFilter(this.csrfTokenRepository);
        RequestMatcher requireCsrfProtectionMatcher = getRequireCsrfProtectionMatcher();
        if (requireCsrfProtectionMatcher != null) {
            filter.setRequireCsrfProtectionMatcher(requireCsrfProtectionMatcher);
        }
        http.addFilter(filter);
    }

    private RequestMatcher getRequireCsrfProtectionMatcher() {
        if (this.ignoredCsrfProtectionMatchers.isEmpty()) {
            return this.requireCsrfProtectionMatcher;
        }
        return new AndRequestMatcher(this.requireCsrfProtectionMatcher,
                new NegatedRequestMatcher(new OrRequestMatcher(this.ignoredCsrfProtectionMatchers)));
    }

    public void ignoringRequestMatchers(String... patterns) {
        for (String pattern : patterns) {
            MvcRequestMatcher mvc = new MvcRequestMatcher(null, pattern);
            ignoredCsrfProtectionMatchers.add(mvc);
        }
    }
}
