package com.ece.boot.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
                .authorizeRequests(x -> x.requestMatchers("/login").permitAll().anyRequest().authenticated())
				.formLogin(form -> form

						.loginPage("/login"));
		return http.build();
	}
}
