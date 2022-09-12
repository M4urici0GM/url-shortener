package dev.mgbarbosa.urlshortner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final UserDetailService userDetailService;

    public SecurityConfiguration(JwtFilter jwtFilter, UserDetailService userDetailService) {
        this.jwtFilter = jwtFilter;
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .httpBasic().disable()
            .cors()
            .and().authorizeHttpRequests()
            .antMatchers("/api/v*/auth/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v*/shortener").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v*/user").permitAll()
            .antMatchers( "/api/v*/user").hasRole("ADMIN")
            .antMatchers("/api/v*/**").authenticated()
            .and()
            .userDetailsService(userDetailService)
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            })
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
