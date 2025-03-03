package nextstep.security.config.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import nextstep.security.config.annotation.web.configuration.HttpSecurityConfiguration;
import nextstep.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({HttpSecurityConfiguration.class, WebSecurityConfiguration.class})
public @interface EnableWebSecurity {
}
