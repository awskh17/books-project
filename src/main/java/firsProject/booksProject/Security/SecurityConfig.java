package firsProject.booksProject.Security;

import firsProject.booksProject.Entity.MyUser;
import firsProject.booksProject.Exceptions.UserNotFoundException;
import firsProject.booksProject.Repositories.MyUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(
                        req -> req
                                .requestMatchers("/api/books/**").hasRole("ADMIN")
                                .requestMatchers("front/api/books/add").hasRole("ADMIN")
                                .requestMatchers("front/api/books/addAuthor").hasRole("ADMIN")
                                .requestMatchers("front/api/books/update/**").hasRole("ADMIN")
                                .requestMatchers("front/api/books/save").hasRole("ADMIN")
                                .requestMatchers("front/api/books/deleteBook/**").hasRole("ADMIN")
                                .requestMatchers("front/api/books/signup").permitAll()
                                .requestMatchers("front/api/books/signedup").permitAll()
                                .requestMatchers("front/api/books/**").hasRole("USER")
                                .requestMatchers("/api/books/get/**").hasRole("USER")

                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form ->form.loginPage("/login"))
                .logout(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(MyUserRepo myUserRepo) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                MyUser myUser = myUserRepo.findByUsername(username);
                if (myUser == null)
                {throw new UserNotFoundException(username+"not found");}
                UserDetails userDetails=User.builder()
                        .username(myUser.getUsername())
                        .password(myUser.getPassword())
                        .roles(myUser.getRoles().toArray(String[]::new))
                        .build();
                return userDetails;
            }
        };
    }
    @Bean
    public CommandLineRunner load (MyUserRepo myUserRepo) {
        return args -> {
            MyUser user=new MyUser();
            user.setUsername("admin");
            user.setPassword(passwordEncoder().encode("admin"));
            user.setRoles(Set.of("USER","ADMIN"));
            myUserRepo.save(user);
            };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
