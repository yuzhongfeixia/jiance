package org.jeecgframework.core.annotation.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
	/**
	 *导出时，对应数据库的字段 主要是用户区分每个字段，
	 *不能有annocation重名的
	 *导出时的列名 导出排序跟定义了annotation的字段的顺序有关
	 *可以使用a_id,b_id来确实是否使用
	 */
	public String exportName();

	/**
	 * 导出时在excel中每个列的宽 单位为字符，一个汉字=2个字符
	 *如 以列名列内容中较合适的长度 例如姓名列6 【姓名一般三个字】 性别列4【男女占1，但是列标题两个汉字】
	 *限制1-255
	 */
	public int exportFieldWidth() default 10;
	/**
	 * 导出时在excel中每个列的高度 单位为字符，一个汉字=2个字符
	 */
	public int exportFieldHeight() default 10;

	/**
	 * 若是sign为1,则需要在pojo中加入一个方法 convertGet字段名()
	 * 例如，字段sex ，需要加入 public String convertGetSex() 返回值为string
	 * 若是sign为0,则不转换   建议使用  @Transient  这个注解让hibernate忽略这个方法
	 */
	public int exportConvertSign() default 0;

	/**
	 * 导入数据是否需要转化 及 对已有的excel，是否需要将字段转为对应的数据
	 * 若是sign为1,则需要在pojo中加入 void set字段名Convert(String text)
	 */
	public int importConvertSign() default 0;
	
	/**
	 *  导出类型 1 是文本 2 是图片 默认是文本
	 */
	public int exportType() default 1;
	/**
	 *  展示到第几个
	 */
	public String orderNum() default "0";
	/**
	 * 是否换行 即支持\n
	 */
	public boolean isWrap() default true;
	/**
	 * 是否需要纵向合并单元格(用于含有list中,单个的单元格,合并list创建的多个row)
	 */
	public boolean needMerge() default false;
	
	/**
	 * 导出时列排序顺序
	 * @return
	 */
	public int sortNum() default 0;
}
