package org.jeecgframework.core.util;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jeecg.system.pojo.base.TSRoleFunction;
import jeecg.system.pojo.base.TSRoleUser;
import jeecg.system.pojo.base.TSUser;

import org.jeecgframework.core.common.model.common.SessionInfo;
import org.jeecgframework.core.constant.Globals;


/**
 * 项目参数工具类
 * 
 */
public class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("sysConfig");

	/**
	 * 获取session定义名称
	 * 
	 * @return
	 */
	public static final String getSessionattachmenttitle(String sessionName) {
		return bundle.getString(sessionName);
	}
	public static final TSUser getSessionUserName() {
		HttpSession session = ContextHolderUtils.getSession();
		session.setMaxInactiveInterval(-1);
		if (session.getAttributeNames().hasMoreElements()) {
			SessionInfo sessionInfo1 = (SessionInfo) session.getAttribute(Globals.USER_SESSION);
			if (sessionInfo1 != null) {
				return sessionInfo1.getUser();
			} else {

				return null;
			}

		} else {
			return null;
		}
	}
	public static final TSUser getSessionUser() {
		return getSessionUserName();
	}
	public static final List<TSRoleFunction> getSessionTSRoleFunction(String roleId) {
		HttpSession session = ContextHolderUtils.getSession();
		session.setMaxInactiveInterval(-1);
		if (session.getAttributeNames().hasMoreElements()) {
			List<TSRoleFunction> TSRoleFunctionList = (List<TSRoleFunction>)session.getAttribute(roleId);
			if (TSRoleFunctionList != null) {
				return TSRoleFunctionList;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}

	/**
	 * 获取配置文件参数
	 * 
	 * @param name
	 * @return
	 */
	public static final String getConfigByName(String name) {
		return bundle.getString(name);
	}

	/**
	 * 获取配置文件参数
	 * 
	 * @param name
	 * @return
	 */
	public static final Map<Object, Object> getConfigMap(String path) {
		ResourceBundle bundle = ResourceBundle.getBundle(path);
		Set set = bundle.keySet();
		return oConvertUtils.SetToMap(set);
	}

	
	
	public static String getSysPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String temp = path.replaceFirst("file:/", "").replaceFirst("WEB-INF/classes/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator).replaceAll("%20", " ");
		return resultPath;
	}

	/**
	 * 获取项目根目录
	 * 
	 * @return
	 */
	public static String getPorjectPath() {
		String nowpath; // 当前tomcat的bin目录的路径 如
						// D:\java\software\apache-tomcat-6.0.14\bin
		String tempdir;
		nowpath = System.getProperty("user.dir");
		tempdir = nowpath.replace("bin", "webapps"); // 把bin 文件夹变到 webapps文件里面
		tempdir += "\\"; // 拼成D:\java\software\apache-tomcat-6.0.14\webapps\sz_pro
		return tempdir;
	}

	public static String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String temp = path.replaceFirst("file:/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator);
		return resultPath;
	}

	public static String getSystempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	public static String getSeparator() {
		return System.getProperty("file.separator");
	}

	public static String getParameter(String field) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		return request.getParameter(field);
	}

	/**
	 * 获取数据库类型
	 * 
	 * @return
	 */
	public static final String getJdbcUrl() {
		return bundle.getString("jdbc.url.jeecg").toLowerCase();
	}
	
	/**
	 * 登陆人地区编码
	 * @return
	 */
	public static final String getLoginUserArea() {
		HttpSession session = ContextHolderUtils.getSession();
		session.setMaxInactiveInterval(-1);
		if (session.getAttributeNames().hasMoreElements()) {
			SessionInfo sessionInfo1 = (SessionInfo) session.getAttribute(Globals.USER_SESSION);
			if (sessionInfo1 != null) {
				return sessionInfo1.getAreaCode();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getPorjectPath());
		System.out.println(getSysPath());

	}
}
