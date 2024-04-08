package com.pruebas.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/**
 * Configuration class to disable security settings for testing purposes.
 * This configuration is activated when the "test" profile is active.
 */
@Configuration
@Profile("test")
public class ApplicationNoSecurity {

    /**
     * Bean definition for WebSecurityCustomizer that disables security for all endpoints.
     *
     * @return WebSecurityCustomizer instance that configures web security to ignore all requests
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/**");
    }
}
