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
                                .requestMatchers("/api/books/get/**").permitAll()
                                .requestMatchers("/api/books/**").hasRole("ADMIN")
                                .requestMatchers("front/api/books/add").hasRole("ADMIN")
                                .requestMatchers("front/api/books/addAuthor").hasRole("ADMIN")
                                .requestMatchers("front/api/books/update/**").hasRole("ADMIN")
                                .requestMatchers("front/api/books/save").hasRole("ADMIN")
                                .requestMatchers("front/api/books/deleteBook/**").hasRole("ADMIN")
                                .requestMatchers("front/api/books/**").permitAll()

                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
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
                System.out.println(myUser);
                if (myUser == null) {throw new UserNotFoundException(username+"not found");}
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
            MyUser user1=new MyUser();
            user1.setUsername("user1");
            user1.setPassword(passwordEncoder().encode("user1"));
            user1.setRoles(Set.of("USER"));
            myUserRepo.save(user1);
            MyUser user2=new MyUser();
            user2.setUsername("admin1");
            user2.setPassword(passwordEncoder().encode("admin1"));
            user2.setRoles(Set.of("USER","ADMIN"));
            myUserRepo.save(user2);
            };
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
