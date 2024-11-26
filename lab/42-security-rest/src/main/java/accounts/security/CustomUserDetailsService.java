package accounts.security;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Primary
//@Component
public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails mary = User.withUsername("mary").password(passwordEncoder.encode("mary")).roles("USER").build();
        UserDetails joe= User.withUsername("joe").password(passwordEncoder.encode("joe")).roles("ADMIN","USER").build();
        System.out.println("/*************** " + username + "       **********/");
        return Stream.of(mary,joe).filter(user -> user.getUsername().equalsIgnoreCase(username)).findFirst().get();
    }
}
