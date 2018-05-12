package com.msh.tcw.config;


import com.msh.tcw.security.JWTAdminAuthenticationFilter;
import com.msh.tcw.security.JWTAuthenticationFilter;
import com.msh.tcw.security.WxAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class WebSecurityConfigurer {

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(wxAuthenticationProvider());
        authenticationManagerBuilder.inMemoryAuthentication().passwordEncoder(passwordEncoderBean()).withUser("admin")
                .password("$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi")
                .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WxAuthenticationProvider wxAuthenticationProvider() {
        return new WxAuthenticationProvider();
    }

    @Configuration
    @Order(2)
    public static class WxConfigurerAdapter extends WebSecurityConfigurerAdapter {
        // 设置 HTTP 验证规则
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // 关闭csrf验证
            http.antMatcher("/api/wx/**")
                    .addFilterBefore(authenticationTokenFilterBean(),
                            UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    // 所有 /login 的POST请求 都放行
                    .antMatchers(HttpMethod.POST, "/api/wx/user/_login").permitAll()
                    .antMatchers("/ws").permitAll()
                    // 所有请求需要身份认证
                    .anyRequest().authenticated();
            http.csrf().disable()
                    // don't create session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            // disable page caching
            http.headers().cacheControl();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            // AuthenticationTokenFilter will ignore the below paths
            web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/ws");
        }

        @Bean
        public JWTAuthenticationFilter authenticationTokenFilterBean() throws Exception {
            return new JWTAuthenticationFilter();
        }

        @Bean
        public FilterRegistrationBean registration(JWTAuthenticationFilter filter) {
            FilterRegistrationBean registration = new FilterRegistrationBean(filter);
            registration.setEnabled(false);
            return registration;
        }
    }

    @Configuration
    @Order(1)
    public static class AdminConfigurerAdapter extends WebSecurityConfigurerAdapter {
        // 设置 HTTP 验证规则
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // 关闭csrf验证
            http.antMatcher("/api/admin/**")
                    .addFilterBefore(authenticationAdminTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    // 所有 /login 的POST请求 都放行
                    .antMatchers(HttpMethod.POST, "/api/admin/user/_login").permitAll()
                    .antMatchers("/ws").permitAll()
                    // 所有请求需要身份认证
                    .anyRequest().authenticated();
            http.csrf().disable()
                    // don't create session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            // disable page caching
            http.headers().cacheControl();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            // AuthenticationTokenFilter will ignore the below paths
            web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/ws");
        }

        @Bean
        public JWTAdminAuthenticationFilter authenticationAdminTokenFilterBean() throws Exception {
            return new JWTAdminAuthenticationFilter();
        }

        @Bean
        public FilterRegistrationBean registration(JWTAdminAuthenticationFilter filter) {
            FilterRegistrationBean registration = new FilterRegistrationBean(filter);
            registration.setEnabled(false);
            return registration;
        }
    }

}
