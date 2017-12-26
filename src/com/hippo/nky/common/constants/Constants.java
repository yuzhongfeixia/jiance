package com.hippo.nky.common.constants;

public class Constants {
	/**
	 * 根节点的PID
	 */
	public static String ROOT_ID = "00000000000000000000000000000000";
	
	/**
	 * 保存Flag 0：新增 1:修改
	 */
	public static int INSERT = 0;
	
	/**
	 * 保存Flag 0：新增 1:修改
	 */
	public static int UPDATE = 1;
	
	/**
	 * 数字0
	 */
	public static Integer ZERO = 0;
	
	/**
	 * 数字1
	 */
	public static Integer ONE = 1;
	
	/**
	 * 文件上传类型:图片
	 */
	public static String UPLOAD_TYPE_IMAGE = "image";
	
	/**
	 * 文件上传类型:文件
	 */
	public static String UPLOAD_TYPE_FILE = "file";
	
	/**
	 * 上传标题:上传图片
	 */
	public static String UPLOAD_TITLE_IMAGE = "上传图片";
	
	/**
	 * 上传标题:上传附件
	 */
	public static String UPLOAD_TITLE_FILE = "选择附件";
	
	/**
	 * 文件上传路径:图片
	 */
	public static String UPLOAD_PATH_IMAGES = "images";
	
	/**
	 * 文件上传路径:文件
	 */
	public static String UPLOAD_PATH_FILES = "files";
	
	/**
	 * 文件上传路径:文件
	 */
	public static String UPLOAD_FILE_MAX_SIZE = "attachmentMaxSize";
	
	/**
	 * 配置文件:uploadImageDefaultSize(上传图片的默认尺寸)
	 */
	public static String PROPEERTIS_UPLOADIMAGEDEFAULTSIZE = "uploadImageDefaultSize";
	
	/**
	 * 配置文件:uploadImageMaxSize(上传图片的最大尺寸)
	 */
	public static String PROPEERTIS_UPLOADIMAGEMAXSIZE = "uploadImageMaxSize";
	
	/**
	 * 配置文件:uploadImageMinSize(上传图片的最小尺寸)
	 */
	public static String PROPEERTIS_UPLOADIMAGEMINSIZE = "uploadImageMinSize";
	
	/**
	 * 配置文件:systemUnit(系统默认的单位)
	 */
	public static String PROPEERTIS_SYSTEMUNIT = "systemUnit";
	
	/**
	 * 图片路径：暂无图片的路径
	 */
	public static String IMAGE_PATH_NOIMAGE = "assets/systemImages/NOIMAGE.jpg";
	
	/**
	 * 标准国别：中国
	 */
	public static String STANDARD_COUNTRY_CN = "CN";
	
	/**
	 * 标准类型：国标
	 */
	public static String STANDARD_TYPE_GB = "GB";
	
	/**
	 * 导入每次提交数据行
	 */
	public static String IMPORT_COMMIT_LINE = "importCommitLine";
	
	/**
	 * 导出每页最大数据行
	 */
	public static String EXPORT_MAX_LINE = "exportMaxLine";
	
	/**
	 * 导出word模板:检测报告-种植
	 */
	public static String EXPORT_WORD_TEMPLETE_PLANT = "/com/hippo/nky/entity/detection/template/detectionReportPlant.ftl";
	
	/**
	 * 导出word模板:检测报告-畜禽
	 */
	public static String EXPORT_WORD_TEMPLETE_LIVESTOCK = "/com/hippo/nky/entity/detection/template/detectionReportLivestock.ftl";
	
	/**
	 * 导入文件类别:农产品
	 */
	public static String IMPORT_TYPE_AGR = "农产品";
	
	/**
	 * 导入文件类别:污染物
	 */
	public static String IMPORT_TYPE_POLL = "污染物";
	
	/**
	 * 导入文件类别:限量标准
	 */
	public static String IMPORT_TYPE_LIMIT = "限量标准";
	
	/**
	 * 类相对路径:实体类
	 */
	public static String CLASS_PATH_ENTITY = "com\\hippo\\nky\\entity\\standard"; 
	
	/**
	 * 包路径:实体类
	 */
	public static String PACKAGE_ENTITY = "com.hippo.nky.entity.standard.";
	
	/**
	 * 文件后缀:【.class】
	 */
	public static String SUFFIX_CLASS = ".class"; 
	
	/**
	 * 地图标注:绿色
	 */
	public static String MAP_MARKER_GREEN = "../../../../../assets/img/marker_green.png";
	
	/**
	 * 地图标注:红色
	 */
	public static String MAP_MARKER_RED = "../../../../../assets/img/marker_red.png";
	
	/**
	 * 菜单文字:首页
	 */
	public static String SIDEBAR_CONTEXT_HOME = "siderbarHome";
	
	/**
	 * 行业code:种植
	 */
	public static String INDUSTRY_PLANT = "f";
	
	/**
	 * 行业code:畜禽
	 */
	public static String INDUSTRY_LIVESTOCK = "a";
	
	/**
	 * HTML标记符号:"[双引号]
	 */
	public static String HTML_MARK_DOUBLE_QUOTES = "&quot;";
}
