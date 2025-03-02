package nextstep.security.config.annotation.authentication;

import java.util.ArrayList;
import java.util.List;
import nextstep.security.authentication.AuthenticationManager;
import nextstep.security.authentication.AuthenticationProvider;
import nextstep.security.authentication.DaoAuthenticationProvider;
import nextstep.security.authentication.ProviderManager;
import nextstep.security.userdetails.UserDetailsService;
import org.springframework.context.ApplicationContext;

public class AuthenticationManagerBuilder {
    private final List<AuthenticationProvider> authenticationProviders = new ArrayList<>();

    public AuthenticationManagerBuilder(ApplicationContext context) {
        final List<UserDetailsService> userDetailsServices = new ArrayList<>(
                context.getBeansOfType(UserDetailsService.class).values());
        if (userDetailsServices.size() != 1) {
            return;
        }
        authenticationProvider(new DaoAuthenticationProvider(
                userDetailsServices.get(0)
        ));
    }

    public AuthenticationManagerBuilder authenticationProvider(AuthenticationProvider authenticationProvider) {
        authenticationProviders.add(authenticationProvider);
        return this;
    }

    public AuthenticationManager build() {
        return new ProviderManager(this.authenticationProviders);
    }
}
