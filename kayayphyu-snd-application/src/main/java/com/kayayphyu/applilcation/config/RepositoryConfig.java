package com.kayayphyu.applilcation.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class RepositoryConfig {

	@Value("${jdbc.driver}")
	private String driverClassName;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.user}")
	private String username;
	@Value("${jdbc.password}")
	private String password;

	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddlAuto;

	@Bean()
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager htm = new HibernateTransactionManager();
		htm.setSessionFactory(sessionFactory);
		return htm;
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(getDataSource());
		localSessionFactoryBean.setHibernateProperties(getHibernateProperties());
		localSessionFactoryBean.setPackagesToScan(new String[] { "com.khayayphyu.domain" });
		return localSessionFactoryBean;
	}

	@Bean
	public Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", hibernateDialect);
		properties.put("hibernate.show_sql", hibernateShowSql);
		properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
		properties.put("hibernate.connection.Charset", "utf8mb4");
		properties.put("hibernate.connection.characterEncoding", "utf8mb4");
		properties.put("hibernate.connection.useUnicode", "true");

		/*
		 * Commented out for now due to stuck thread issues with tomcat
		 * 
		 * @NT 07/10/2018
		 */
		// properties.put("hibernate.search.default.directory_provider",
		// hibernateSearchDirectory);
		// properties.put("hibernate.search.default.indexBase",
		// hibernateSearchIndexBase);
		return properties;

	}

	// @Bean(name = "permanentTasks")
	// public List<WorkItemType> permanentTasks() {
	// List<WorkItemType> workItemType = new ArrayList<>();
	// for (String type : permanentTasks.split(",")) {
	// workItemType.add(WorkItemType.valueOf(type.trim()));
	// }
	// return workItemType;
	// }
	//
	// @Bean(name = "probationTasks")
	// public List<WorkItemType> probationTask() {
	// List<WorkItemType> workItemType = new ArrayList<>();
	// for (String type : probationTasks.split(",")) {
	// workItemType.add(WorkItemType.valueOf(type.trim()));
	// }
	// return workItemType;
	// }
	//
	// @Bean(name = "allowedTasks")
	// public HashSet<WorkItemType> allowedTasks() {
	// List<WorkItemType> permanentTasks = permanentTasks();
	// List<WorkItemType> probationTasks = probationTask();
	// HashSet<WorkItemType> allowedTasks = new HashSet<>();
	// allowedTasks.addAll(probationTasks);
	// allowedTasks.addAll(permanentTasks);
	// allowedTasks.add(WorkItemType.EMPLOYMENT_APPLICATION);
	// return allowedTasks;
	// }

	/*
	 * @Bean(name = "holidayType") public List<HolidayType> holidayTypes() {
	 * List<HolidayType> allowHolidayType = new ArrayList<>(); for (String type :
	 * holidayTypes.split(",")) {
	 * allowHolidayType.add(HolidayType.valueOf(type.trim())); } return
	 * allowHolidayType; }
	 * 
	 * @Bean(name = "employmentType") public List<EmploymentStatus>
	 * allowEmploymentStatus() { List<EmploymentStatus> employmentStatusList = new
	 * ArrayList<>(); for (String type : allowEmploymentStatus.split(",")) {
	 * employmentStatusList.add(EmploymentStatus.valueOf(type.trim())); } return
	 * employmentStatusList; }
	 */

}