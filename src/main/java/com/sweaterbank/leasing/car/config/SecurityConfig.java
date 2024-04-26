package com.sweaterbank.leasing.car.config;

import com.sweaterbank.leasing.car.repository.UserRepository;
import com.sweaterbank.leasing.car.repository.mappers.LeaseMapper;
import com.sweaterbank.leasing.car.repository.mappers.ObligationMapper;
import com.sweaterbank.leasing.car.repository.mappers.UserMapper;
import com.sweaterbank.leasing.car.services.JwtService;
import com.sweaterbank.leasing.car.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Environment environment;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SecurityConfig(Environment environment, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.environment = environment;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorizeRequests) -> {
                authorizeRequests.requestMatchers("api/auth/login").permitAll();
                authorizeRequests.requestMatchers("api/auth/logout").permitAll();
                authorizeRequests.requestMatchers("api/auth/register").permitAll();
                authorizeRequests.requestMatchers("api/admin/**").hasAuthority("admin");
                authorizeRequests.anyRequest().authenticated();
            })
            .cors(withDefaults())
            .logout(logout -> logout.logoutUrl("api/auth/logout"))
            .httpBasic(withDefaults());

        // TODO: set unauthorized requests exception handling

        http.addFilterBefore(
            jwtAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    @Bean
    @Autowired
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        String allowedOrigin = this.environment.getProperty("sweatbank-backend.cors.allowed-origin");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigin));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "HEAD"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public JwtService jwtService(){
        return new JwtService();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService(), userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(namedParameterJdbcTemplate, userMapper(), passwordEncoder());
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public LeaseMapper leaseMapper() { return new LeaseMapper(); }

    @Bean
    public ObligationMapper obligationMapper() { return new ObligationMapper(); }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}