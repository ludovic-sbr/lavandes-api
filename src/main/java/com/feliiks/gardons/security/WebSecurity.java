package com.feliiks.gardons.security;

import com.feliiks.gardons.entities.RoleEnum;
import com.feliiks.gardons.services.TokenService;
import com.feliiks.gardons.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurity implements WebMvcConfigurer {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://camping-lavandes.com/")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler accessDeniedHandler
    ) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter =
                new JwtAuthenticationFilter(tokenService, userService);
        http
                .cors()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                // AUTHENTICATION
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/authenticate/admin").permitAll()
                // USER
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/user/me").authenticated()
                .antMatchers(HttpMethod.GET, "/user/reservation").authenticated()
                .antMatchers(HttpMethod.GET, "/user").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.DEVELOPER.name())
                .antMatchers(HttpMethod.GET, "/user/*").authenticated()
                .antMatchers(HttpMethod.GET, "/user/*/reservation").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.DEVELOPER.name())
                .antMatchers(HttpMethod.PATCH, "/user/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/user/*").authenticated()
                // RESERVATION
                .antMatchers(HttpMethod.POST, "/reservation").authenticated()
                .antMatchers(HttpMethod.POST, "/reservation/*/confirm").authenticated()
                .antMatchers(HttpMethod.POST, "/reservation/*/complete").authenticated()
                .antMatchers(HttpMethod.GET, "/reservation").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.DEVELOPER.name())
                .antMatchers(HttpMethod.GET, "/reservation/*").authenticated()
                .antMatchers(HttpMethod.PATCH, "/reservation/*").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.DEVELOPER.name())
                .antMatchers(HttpMethod.DELETE, "/reservation/*").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.DEVELOPER.name())
                // LOCATION
                .antMatchers(HttpMethod.POST, "/location").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.DEVELOPER.name())
                .antMatchers(HttpMethod.GET, "/location").permitAll()
                .antMatchers(HttpMethod.GET, "/location/*").permitAll()
                .antMatchers(HttpMethod.PATCH, "/location/*").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.DEVELOPER.name())
                .antMatchers(HttpMethod.DELETE, "/location/*").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.DEVELOPER.name())
                // PAYMENT
                .antMatchers(HttpMethod.POST, "/stripe/*").authenticated()
                .antMatchers(HttpMethod.GET, "/stripe/*").authenticated()
                // API
                .antMatchers("/api/*").authenticated()
                .antMatchers("/api/**/*").authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf().disable();

        return http.build();
    }
}