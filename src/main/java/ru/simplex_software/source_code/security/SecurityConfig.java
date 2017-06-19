package ru.simplex_software.source_code.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.simplex_software.security.ulogin.UloginAuthenticationFilter;
import ru.simplex_software.security.ulogin.UloginAuthentifiactionProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UloginAuthenticationFilter uloginAuthenticationFilter;
    private SimpleUrlAuthenticationSuccessHandler successHandler;

    public SecurityConfig() {

        uloginAuthenticationFilter = new UloginAuthenticationFilter("/ulogin");
        successHandler = new SimpleUrlAuthenticationSuccessHandler("/");
        uloginAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                DaoAuthenticationProvider daoAuthenticationProvider,
                                UloginAuthentifiactionProvider uloginAuthentifiactionProvider) throws Exception {
        auth.authenticationProvider(uloginAuthentifiactionProvider);
        auth.authenticationProvider(daoAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        uloginAuthenticationFilter.setAuthenticationManager(authenticationManager());
        HttpSecurity httpSecurity = http.
                addFilterBefore(uloginAuthenticationFilter, AnonymousAuthenticationFilter.class);
        httpSecurity.headers().frameOptions().sameOrigin();

//        http.formLogin().and().httpBasic();

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("customer*").authenticated()
                .antMatchers("/wedding.html").authenticated();
//                .antMatchers("/judgeQueue.html").authenticated()
//                .antMatchers("/questionListPage.html").authenticated()
//                .antMatchers("/notifications.html").authenticated()
//                .antMatchers("/answerListPage.html").authenticated()
//                .antMatchers("/edit.html").authenticated()
//
//                .antMatchers("/?*").permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers("/wicket/resource/**").permitAll()
//                .antMatchers("/wicket/websocket/**").permitAll()
//                .antMatchers("/css/**").permitAll()
//                .antMatchers("/img/**").permitAll()
//                .antMatchers("/js/**").permitAll()
//                .antMatchers("/logout").permitAll()
//                .antMatchers("/registration.html").permitAll()
//                .antMatchers("/passwordReset.html").permitAll()
//                .antMatchers("/informationMessage.html").permitAll()
//                .antMatchers("/wicket/bookmarkable/*").permitAll()

//                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login.html")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("j_username")
                .passwordParameter("j_password");
//        formLogin.failureUrl("/login.html?error=true");
//        formLogin.defaultSuccessUrl("/");
//
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
//        http.rememberMe().userDetailsService(userDetailsService);


    }

}
