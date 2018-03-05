package com.fuze.takehome.test;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fuze.takehome.Main;

public class AbstractEntityTest {

	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	private static final String SPRING_CONTEXT_PATH = "classpath:spring/application.xml";
	
	private ConfigurableApplicationContext ctx;
	
	@Before
	public void initialize()
	{
		try
		{
			ctx = new ClassPathXmlApplicationContext(SPRING_CONTEXT_PATH);
			AutowireCapableBeanFactory factory = ctx.getAutowireCapableBeanFactory();
			factory.autowireBean(this);
		}
		catch(Exception e)
		{
			log.error("Failed to initialize Spring Application Context. Halting.", e);
			System.exit(1);
		}
	}
	
	@After
	public void destroy()
	{
		if (null != ctx)
		{
			ctx.close();
		}
	}
}
