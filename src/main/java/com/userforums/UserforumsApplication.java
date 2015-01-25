package com.userforums;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//(exclude=DataSourceTransactionManagerAutoConfiguration.class)
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude={DataSourceTransactionManagerAutoConfiguration.class, MultipartAutoConfiguration.class})
@EnableTransactionManagement
//@EnableWebMvc
public class UserforumsApplication extends WebMvcConfigurerAdapter{

    public static void main(String[] args) {
        SpringApplication.run(UserforumsApplication.class, args);
    }
    
    @Autowired
	Environment env;
    
    @Bean
	public LocalSessionFactoryBean sessionFactory()
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.userforums.pojo");
		sessionFactory.setHibernateProperties(hibernatePropertise());
		
		return sessionFactory;
	}
    
    @Bean
	public DataSource dataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
		dataSource.setUrl(env.getRequiredProperty("db.url"));
		dataSource.setUsername(env.getRequiredProperty("db.user"));
		dataSource.setPassword(env.getRequiredProperty("db.password"));
		
		return dataSource;
	}
	
	@Bean
	public HibernateTransactionManager hibernateTranactionManager(SessionFactory sf)
	{
		HibernateTransactionManager hTanctionManager = new HibernateTransactionManager();
		hTanctionManager.setSessionFactory(sf);
		
		return hTanctionManager;
	}
	
	private Properties hibernatePropertise()
	{
		Properties hPropertise = new Properties();
		
		hPropertise.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		hPropertise.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
		hPropertise.put("hibernate.package.scan", env.getRequiredProperty("hibernate.package.scan"));
		hPropertise.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		//hPropertise.put("hibernate.current_session_context_class", env.getRequiredProperty("hibernate.current_session_context_class"));
		
		return hPropertise;
	}
	
	/*@Bean
	 public MultipartConfigElement multipartConfigElement() {
	     return new MultipartConfigElement("");
	 }*/

	 @Bean
	 public MultipartResolver multipartResolver() {
	   CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	     multipartResolver.setMaxUploadSize(4000000);
	     multipartResolver.setMaxInMemorySize(4000000);
	     return multipartResolver;
	 }
}
