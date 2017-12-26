package org.jeecgframework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)   
@Target(ElementType.METHOD)
public @interface PrimaryKeyPolicy{
	/**
	 * 策略类型
	 * <p>
	 * auto:自动
	 * semi：半自动(属性为空[""|NULL]时,不进行设置；不为空时按照method属性定义的方法进行设置)
	 */
	public String policy();
	
	/**
	 * 主键生成方法
	 * <p>
	 * default:默认(method=default时，生成方式采用JAVA的UUID)
	 * oralce:ORACLE(method=oralce时,生成方式采用ORACLE的sys_guid())
	 * package.class.method：包名.类名.方法名(例:com.hippo.framework.core.util.ConverterUtil.getXXId)
	 */
	public String method();
}

