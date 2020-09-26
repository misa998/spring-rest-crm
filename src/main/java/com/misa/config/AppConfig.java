package com.misa.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.misa")
@EnableTransactionManagement
@PropertySource({"classpath:persistence-mysql.properties"})
public class AppConfig implements WebMvcConfigurer {

//	public ViewResolver viewResolver() {
//		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
//		irvr.setPrefix("/WEB-INF/view/");
//		irvr.setSuffix(".jsp");
//		return irvr;
//	}
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource myDataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
		} catch(PropertyVetoException e) {
			e.getMessage();
		}
		
		cpds.setJdbcUrl(env.getProperty("jdbc.url"));
		cpds.setUser(env.getProperty("jdbc.user"));
		cpds.setPassword(env.getProperty("jdbc.password"));
		
		cpds.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		cpds.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		cpds.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));		
		cpds.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

		return cpds;
	}
	
	private int getIntProperty(String propName) {
		String propVal = env.getProperty(propName);
		return Integer.parseInt(propVal);
	}	
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(myDataSource());
		lsfb.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		lsfb.setHibernateProperties(getHibernateProperties());
		
		return lsfb;
	}
	
	private Properties getHibernateProperties(){
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		
		return props;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionMng(SessionFactory sf) {
		HibernateTransactionManager htm = new HibernateTransactionManager();
		htm.setSessionFactory(sf);
		
		return htm;
	}
}
