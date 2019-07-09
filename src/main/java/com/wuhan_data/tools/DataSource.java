package com.wuhan_data.tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
/**
 * @author linmeng
 *
 */
 
/*	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)*/
	@Target({ElementType.TYPE,ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DataSource{
		String value();
	}

