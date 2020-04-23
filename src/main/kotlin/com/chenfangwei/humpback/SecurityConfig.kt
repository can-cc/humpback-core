package com.chenfangwei.humpback

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // TODO: check role
        // https://stackoverflow.com/questions/47069345/how-to-use-spring-security-remotetokenservice-with-keycloak
        // https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide
        http
                .authorizeRequests()
                .antMatchers("/**")
                .hasAuthority("SCOPE_openid")
                // .antMatchers("/**")
                // .hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable().xssProtection().disable()
                .and()
                .formLogin().disable()
                .oauth2ResourceServer()
                .jwt()
    }
}
