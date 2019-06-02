package com.CO3098.MiniWeb.tc225.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/GE/person/").permitAll()
                .antMatchers("/GE/person/add").permitAll()
                .antMatchers("/GE/person/create").permitAll()
                .antMatchers("/GE/person/delete/{id}").permitAll()
                .antMatchers("/GE/person/edit/{id}").permitAll()
                .antMatchers("/GE/person/get/{id}").permitAll()
                .antMatchers("/GE/person/familyTree").permitAll()
                .antMatchers("/GE/person/persons").permitAll()
                .antMatchers("/GE/person/printJSON").permitAll()
                .antMatchers("/GE/person/ancestors/{id}").permitAll()
                .antMatchers("/GE/person/descendants/{id}").permitAll()
                .antMatchers("/GE/person/change").permitAll()
                .antMatchers("/GE/person/keyAvailability").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/*.js").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/GE/person/")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }


}