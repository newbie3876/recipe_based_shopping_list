package lt.techin.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${jwt.public.key}")
  private RSAPublicKey key;

  @Value("${jwt.private.key}")
  private RSAPrivateKey priv;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .cors(Customizer.withDefaults()) // ✅ Enable CORS
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/token").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/users").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/users/{id}").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/users/{id}").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").authenticated()


                    .requestMatchers(HttpMethod.GET, "/api/ingredients").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/ingredients/{id}").authenticated()
                    .requestMatchers(HttpMethod.POST, "/api/ingredients").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api//ingredients/{id}").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/recipes").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/api/recipes/{id}").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/recipes/{id}").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/recipes").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/images").authenticated()
                    .requestMatchers(HttpMethod.POST, "/api/images").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/images/{id}").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/shoppinglists").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/shoppinglists/{userId}").authenticated()
                    .requestMatchers(HttpMethod.POST, "/api/shoppinglists").authenticated()
                    .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:5173"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(key).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(key).privateKey(priv).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }
}

