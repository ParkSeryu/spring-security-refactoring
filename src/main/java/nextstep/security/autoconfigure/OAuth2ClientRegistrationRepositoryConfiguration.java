package nextstep.security.autoconfigure;

import java.util.HashMap;
import java.util.Map;
import nextstep.oauth2.OAuth2ClientProperties;
import nextstep.oauth2.registration.ClientRegistration;
import nextstep.oauth2.registration.ClientRegistrationRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(nextstep.oauth2.OAuth2ClientProperties.class)
class OAuth2ClientRegistrationRepositoryConfiguration {

    @Bean
    @ConditionalOnMissingBean(ClientRegistrationRepository.class)
    ClientRegistrationRepository clientRegistrationRepository(nextstep.oauth2.OAuth2ClientProperties properties) {
        Map<String, ClientRegistration> registrations = getClientRegistrations(properties);
        return new ClientRegistrationRepository(registrations);
    }

    private static Map<String, ClientRegistration> getClientRegistrations(
            nextstep.oauth2.OAuth2ClientProperties properties) {
        Map<String, ClientRegistration> clientRegistrations = new HashMap<>();
        properties.getRegistration().forEach((key, value) -> clientRegistrations.put(key,
                getClientRegistration(key, value, properties.getProvider().get(key))));
        return clientRegistrations;
    }

    private static ClientRegistration getClientRegistration(String registrationId,
                                                            nextstep.oauth2.OAuth2ClientProperties.Registration registration, OAuth2ClientProperties.Provider provider) {
        return new ClientRegistration(registrationId, registration.getClientId(), registration.getClientSecret(), registration.getRedirectUri(), registration.getScope(), provider.getAuthorizationUri(), provider.getTokenUri(), provider.getUserInfoUri(), provider.getUserNameAttributeName());
    }
}

