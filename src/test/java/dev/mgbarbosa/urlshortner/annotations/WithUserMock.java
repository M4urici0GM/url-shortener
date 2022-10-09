package dev.mgbarbosa.urlshortner.annotations;

import dev.mgbarbosa.urlshortner.factories.SecurityContextMockFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = SecurityContextMockFactory.class)
public @interface WithUserMock {
    String id() default "632019a2c40a443d1ce69c3a";
    String name() default "John Doe";

    String email() default "john@doe.com";

    String username() default "\"john.doe123\"";
}
