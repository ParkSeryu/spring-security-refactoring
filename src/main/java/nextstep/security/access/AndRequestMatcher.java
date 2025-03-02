package nextstep.security.access;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class AndRequestMatcher implements RequestMatcher {
    private final List<RequestMatcher> requestMatchers;

    public AndRequestMatcher(List<RequestMatcher> requestMatchers) {
        this.requestMatchers = requestMatchers;
    }

    public AndRequestMatcher(RequestMatcher... requestMatchers) {
        this(Arrays.asList(requestMatchers));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        for (RequestMatcher matcher : this.requestMatchers) {
            if (!matcher.matches(request)) {
                return false;
            }
        }
        return true;
    }

}
