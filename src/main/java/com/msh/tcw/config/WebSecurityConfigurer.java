package com.msh.tcw.config;


import com.msh.tcw.security.JWTAdminAuthenticationFilter;
import com.msh.tcw.security.JWTAuthenticationFilter;
import com.msh.tcw.security.WxAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
            http.antMatcher("/wx/**")
                    .authorizeRequests()
                    // 所有 /login 的POST请求 都放行
                    .antMatchers(HttpMethod.POST, "/wx/user/_login").permitAll()
                    // 所有请求需要身份认证
                    .anyRequest().authenticated();
            http.csrf().disable()
                    // don't create session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            // 添加一个过滤器验证其他请求的Token是否合法
            http.addFilterBefore(authenticationTokenFilterBean(),
                    UsernamePasswordAuthenticationFilter.class);
            // disable page caching
            http.headers().cacheControl();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            // AuthenticationTokenFilter will ignore the below paths
            web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");
        }

        @Bean
        public JWTAuthenticationFilter authenticationTokenFilterBean() throws Exception {
            return new JWTAuthenticationFilter();
        }
    }

    @Configuration
    @Order(1)
    public static class AdminConfigurerAdapter extends WebSecurityConfigurerAdapter {
        // 设置 HTTP 验证规则
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // 关闭csrf验证
            http.antMatcher("/admin/**")
                    .authorizeRequests()
                    // 所有 /login 的POST请求 都放行
                    .antMatchers(HttpMethod.POST, "/admin/user/_login").permitAll()
                    // 所有请求需要身份认证
                    .anyRequest().authenticated();
            http.csrf().disable()
                    // don't create session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            // 添加一个过滤器验证其他请求的Token是否合法
            http.addFilterBefore(authenticationAdminTokenFilterBean(),
                    UsernamePasswordAuthenticationFilter.class);
            // disable page caching
            http.headers().cacheControl();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            // AuthenticationTokenFilter will ignore the below paths
            web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");
        }

        @Bean
        public JWTAdminAuthenticationFilter authenticationAdminTokenFilterBean() throws Exception {
            return new JWTAdminAuthenticationFilter();
        }
    }

}
