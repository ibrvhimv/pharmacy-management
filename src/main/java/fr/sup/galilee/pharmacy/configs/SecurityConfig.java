package fr.sup.galilee.pharmacy.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactiver CSRF avec la nouvelle API
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Utiliser une politique de session sans état
                .authorizeRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS,"/usr/**").permitAll() // Autoriser toutes les requêtes OPTIONS
                        .requestMatchers(HttpMethod.OPTIONS,"/fact/**").permitAll() // Autoriser toutes les requêtes OPTIONS
                        .requestMatchers(HttpMethod.POST, "/usr/**").permitAll() // Autoriser les requêtes POST sur /usr
                        .requestMatchers(HttpMethod.POST, "/stock/**").permitAll() // Autoriser les requêtes POST sur /usr
                        .requestMatchers(HttpMethod.POST, "/cart/**").permitAll() // Autoriser les requêtes POST sur /usr
                        .requestMatchers(HttpMethod.POST, "/fact/**").permitAll() // Autoriser les requêtes POST sur /usr
                        .requestMatchers(HttpMethod.DELETE, "/usr/**").permitAll() // Autoriser les requêtes POST sur /usr
                        .requestMatchers(HttpMethod.DELETE, "/stock/**").permitAll() // Autoriser les requêtes POST sur /usr
                        .requestMatchers(HttpMethod.DELETE, "/cart/**").permitAll() // Autoriser les requêtes POST sur /usr
                        .requestMatchers(HttpMethod.DELETE, "/fact/**").permitAll() // Autoriser les requêtes POST sur /usr
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .addFilterBefore(jwtValidatorFilter(), UsernamePasswordAuthenticationFilter.class); // Ajouter votre JwtValidatorFilter

        return http.build();
    }


    // Bean pour JwtValidatorFilter (assurez-vous que cette classe est bien définie)
    @Bean
    public JwtValidatorFilter jwtValidatorFilter() {
        return new JwtValidatorFilter();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200/**")); // Ajoutez vos origines autorisées ici
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
