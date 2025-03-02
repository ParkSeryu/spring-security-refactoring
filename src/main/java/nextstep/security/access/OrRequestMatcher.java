package nextstep.security.access;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class OrRequestMatcher implements RequestMatcher {
    private final List<RequestMatcher> requestMatchers;

    public OrRequestMatcher(List<RequestMatcher> requestMatchers) {
        this.requestMatchers = requestMatchers;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        for (RequestMatcher matcher : this.requestMatchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

}
