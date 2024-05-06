package org.project.crm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//@Slf4j
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final UserRepository userRepository;
//
//    @Bean //возвращаем кастомный MyUserDetailsService, который напишем далее
//    public UserDetailsService userDetailsService(){
//        return new MyUserDetailsService(userRepository);
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService());
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return  http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth.requestMatchers("api/welcome").permitAll() //вход без авторизации
//                        .requestMatchers("api/**").hasAnyRole("ADMIN").requestMatchers("api/users/users").hasAnyRole("USER", "ADMIN"))//с авторизацией и аутентификацией
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//                .build();
//    }
//    @Bean //Ставим степень кодировки, с которой кодировали пароль в базе
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//        private final UserRepository userRepository;
//    @Bean //возвращаем кастомный MyUserDetailsService, который напишем далее
//    public UserDetailsService userDetailsService(){
//        return new MyUserDetailsService(userRepository);
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService());
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return  http
//                .csrf(AbstractHttpConfigurer::disable)
//
//                .authorizeHttpRequests(auth ->
//                    auth
//                            .requestMatchers("/api/users/welcome").permitAll()
//                            .requestMatchers("/api/users/admins").hasAuthority("ADMIN")
//                            .requestMatchers("/api/users/all").hasAnyAuthority("USER", "ADMIN")
//                            .requestMatchers("/api/users/users").hasAnyAuthority("USER", "ADMIN")
//                            .anyRequest().permitAll()
//                )
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth ->
                                auth
//                                        .requestMatchers("/v3/api-docs/**",
//                                "/swagger-ui/**", "/swagger-ui.html")
//                        .permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll()
//                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }


    @Bean //Ставим степень кодировки, с которой кодировали пароль в базе
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(5);
    }

}