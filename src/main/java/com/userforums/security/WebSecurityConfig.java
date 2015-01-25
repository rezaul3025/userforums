package com.userforums.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
			.antMatchers("/","/home","/image/*","/js/**,/css/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login").defaultSuccessUrl("/groups_overview", true).permitAll()
			.failureUrl("/login?error")
			.and()
			.logout().permitAll()
			.logoutSuccessUrl("/?logout");
	}
	
	/*@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication()
		.withUser("tom").password("pass").roles("USER");
	}*/
	
	@Configuration
	protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter 
	{

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception 
		{
			/*auth.ldapAuthentication().userDnPatterns("uid={0},ou=people")
					.groupSearchBase("ou=groups").contextSource()
					.ldif("classpath:test-server.ldif");*/
			
			
            LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> ldapAuthenticationProviderConfigurer = auth.ldapAuthentication();

            ldapAuthenticationProviderConfigurer
                //.userSearchFilter("cn={0}")
                //.userSearchBase("ou=people,dc=local")
            .userDnPatterns("uid={0},ou=people,dc=local")
            .groupSearchBase("ou=groups,dc=local")
                .contextSource(contextSource());
		}
		
		@Bean
		public DefaultSpringSecurityContextSource contextSource() throws Exception 
		{
			DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldap://192.168.1.60:389");
            contextSource.setUserDn("cn=admin,dc=local");
            contextSource.setPassword("admin");
            contextSource.setReferral("follow"); 
            contextSource.afterPropertiesSet();
            
            return contextSource;
		}
		
		@Bean
	    public LdapTemplate ldapTemplate() throws Exception {
	        return new LdapTemplate(contextSource());        
	    }
	}
}
