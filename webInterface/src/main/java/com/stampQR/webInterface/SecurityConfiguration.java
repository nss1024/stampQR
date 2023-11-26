package com.stampQR.webInterface;

import com.stampQR.webInterface.services.SecurityUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final SecurityUserDetailsService myUserDetailsService;

    public SecurityConfiguration(SecurityUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http.
                csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/css/**").permitAll();
                    auth.requestMatchers("/js/**").permitAll();
                    auth.requestMatchers("/assets/**").permitAll();
                    auth.requestMatchers("/js/customScripts/customScripts.js")
                            .hasAnyAuthority("ADMINISTRATOR","USER");
                    auth.requestMatchers("/js/customScripts/**").hasAuthority("ADMINISTRATOR");
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/home").permitAll();
                    auth.requestMatchers("/contact").permitAll();
                    auth.requestMatchers("/faq").permitAll();
                    auth.requestMatchers("/about").permitAll();
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/admin").hasAuthority("ADMINISTRATOR");
                    auth.anyRequest().authenticated();
                })
                .userDetailsService(myUserDetailsService)
                .formLogin(form ->{
                    form.loginPage("/login")
                            .permitAll();
                    form.defaultSuccessUrl("/home");
                })
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
