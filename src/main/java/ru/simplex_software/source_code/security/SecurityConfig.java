package ru.simplex_software.source_code.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import ru.simplex_software.security.ulogin.UloginAuthenticationFilter;
import ru.simplex_software.security.ulogin.UloginAuthentifiactionProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UloginAuthentifiactionProvider uloginAuthentifiactionProvider;

    private UloginAuthenticationFilter uloginAuthenticationFilter ;

    public SecurityConfig() {
        uloginAuthenticationFilter = new UloginAuthenticationFilter("/ulogin");
        uloginAuthenticationFilter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/"));
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(uloginAuthentifiactionProvider);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        uloginAuthenticationFilter.setAuthenticationManager(authenticationManager());
        http.csrf().disable();
        HttpSecurity httpSecurity = http.addFilterBefore(uloginAuthenticationFilter, AnonymousAuthenticationFilter.class);
        httpSecurity.authorizeRequests().antMatchers("/login.html").permitAll()
                .antMatchers("/*").permitAll()
                .antMatchers("index.html*").permitAll()
                .antMatchers("/favicon.png").permitAll()
                .antMatchers("/about.html").permitAll()
                .antMatchers("/img/*").permitAll()
                .antMatchers("/css/*").permitAll()
                .antMatchers("/js/*").permitAll()
                .antMatchers("/fonts/*").permitAll()
                .anyRequest().authenticated() ;
        final FormLoginConfigurer<HttpSecurity> formLogin = httpSecurity.formLogin();
        formLogin.loginPage("/login.html").defaultSuccessUrl("/");
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessUrl("/");

    }

}
