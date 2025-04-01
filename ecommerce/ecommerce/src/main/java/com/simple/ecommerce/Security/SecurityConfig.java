package com.simple.ecommerce.Security;

import com.simple.ecommerce.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;  // Make sure you're using javax.servlet here
import javax.servlet.http.HttpServletResponse;  // Make sure you're using javax.servlet here
import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    // for the beans: Spring Boot picks them up and wires them into the app automatically
    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) //CSRF (Cross-Site Request Forgery)
                .cors(Customizer.withDefaults()) // (Cross-Origin Resource Sharing) allows the frontend (like localhost:3000) to call the backend (localhost:8080).
                .formLogin(httpForm -> {
                    httpForm.loginPage("/req/login"); //tells Spring to use your custom login form at that url

                    // Use custom success handler to redirect after login
                    httpForm.successHandler(customAuthenticationSuccessHandler());
                })
                .httpBasic(Customizer.withDefaults()) // for postman
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // Use session management when needed

                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/req/signup", "/req/login", "/css/**", "/js/**").permitAll();
                    //get is for all
                    registry.requestMatchers(HttpMethod.GET, "/api/products/**", "/api/categories/**").permitAll();

                    // restrict product & category modifications to admin
                    registry.requestMatchers(HttpMethod.POST, "/api/products/**", "/api/categories/**").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.PUT, "/api/products/**", "/api/categories/**").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.DELETE, "/api/products/**", "/api/categories/**").hasRole("ADMIN");

                    registry.anyRequest().authenticated();
                })
                .build();
    }


    // what to do after successfull login
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String username = authentication.getName();
            Object credentials = authentication.getCredentials();

            // NOTE: Credentials will not contain the plain password here.
            // So we just use username check instead.

            if ("abed".equalsIgnoreCase(username)) {
                response.sendRedirect("http://localhost:3000/admin/dashboard");
            } else {
                response.sendRedirect("http://localhost:3000/user/dashboard");
            }
        };
    }

}




