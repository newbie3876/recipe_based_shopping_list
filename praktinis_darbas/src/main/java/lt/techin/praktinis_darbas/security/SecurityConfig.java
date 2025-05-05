package lt.techin.praktinis_darbas.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/books").permitAll() // Leidžiame GET užklausas be autentifikacijos// leidžia H2
                    .anyRequest().authenticated()
            )
            .formLogin(withDefaults()) // leidžia prisijungimą
            .csrf(csrf -> csrf.disable()) // kad H2 galėtų veikti
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));
    return http.build();
  }
}
