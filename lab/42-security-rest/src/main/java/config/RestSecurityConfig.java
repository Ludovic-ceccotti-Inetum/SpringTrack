package config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity // enable @PreAuthorize annotation on method level
@Configuration
public class RestSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // - Allow DELETE on the /accounts resource (or any sub-resource)
        //   for "SUPERADMIN" role only
        // - Allow POST or PUT on the /accounts resource (or any sub-resource)
        //   for "ADMIN" or "SUPERADMIN" role only
        // - Allow GET on the /accounts resource (or any sub-resource)
        //   for all roles - "USER", "ADMIN", "SUPERADMIN"
        // - Allow GET on the /authorities resource
        //   for all roles - "USER", "ADMIN", "SUPERADMIN"
        // @formatter:off
        // Deny any request that doesn't match any authorization rule
        // @formatter:on
        http.authorizeHttpRequests((authz) -> authz
						.requestMatchers(HttpMethod.DELETE,"/accounts/**").hasRole("SUPERADMIN")
						.requestMatchers(HttpMethod.POST,"/accounts/**").hasAnyRole("SUPERADMIN","ADMIN")
						.requestMatchers(HttpMethod.PUT,"/accounts/**").hasAnyRole("SUPERADMIN","ADMIN")
						.requestMatchers(HttpMethod.GET,"/accounts/**").hasAnyRole("SUPERADMIN", "ADMIN", "USER")
						.requestMatchers(HttpMethod.GET,"/authorities").hasAnyRole("SUPERADMIN", "ADMIN", "USER")
						.anyRequest().denyAll()) // Deny any request that doesn't match any authorization rule
				.httpBasic(withDefaults())
				.csrf(CsrfConfigurer::disable);

        return http.build();
    }

    // TODO-14b (Optional): Remove the InMemoryUserDetailsManager definition
    // - Comment the @Bean annotation below

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

        // - "user"/"user" with "USER" role (example code is provided below)
        // - "admin"/"admin" with "USER" and "ADMIN" roles
        // - "superadmin"/"superadmin" with "USER", "ADMIN", and "SUPERADMIN" roles
        // (Make sure to store the password in encoded form.)
        // - pass all users in the InMemoryUserDetailsManager constructor
        UserDetails user = User.withUsername("user").password(passwordEncoder.encode("user")).roles("USER").build();
        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("ADMIN","USER").build();
        UserDetails superAdmin = User.withUsername("superadmin").password(passwordEncoder.encode("superadmin")).roles("ADMIN", "SUPERADMIN","USER").build();

        return new InMemoryUserDetailsManager(user,admin,superAdmin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
