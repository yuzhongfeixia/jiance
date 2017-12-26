package jeecg.system.controller.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jeecg.system.pojo.base.TSConfig;
import jeecg.system.pojo.base.TSFunction;
import jeecg.system.pojo.base.TSRole;
import jeecg.system.pojo.base.TSRoleFunction;
import jeecg.system.pojo.base.TSRoleUser;
import jeecg.system.pojo.base.TSUser;
import jeecg.system.pojo.base.TSVersion;
import jeecg.system.service.SystemService;
import jeecg.system.service.UserService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.common.SessionInfo;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.datasource.DataSourceContextHolder;
import org.jeecgframework.core.extend.datasource.DataSourceType;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.NumberComparator;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.organization.OrganizationEntity;

/**
 * 登陆初始化控制器
 * 
 * 
 */
@Controller
@RequestMapping("/loginController")
public class LoginController {
	private Logger log = Logger.getLogger(LoginController.class);
	private SystemService systemService;
	private UserService userService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {

		this.userService = userService;
	}

	@RequestMapping(params = "goPwdInit")
	public String goPwdInit() {
		return "login/pwd_init";
	}

	/**
	 * admin账户密码初始化
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "pwdInit")
	public ModelAndView pwdInit(HttpServletRequest request) {
		ModelAndView modelAndView = null;
		TSUser user = new TSUser();
		user.setUserName("admin");
		String newPwd = "123456";
		userService.pwdInit(user, newPwd);
		modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));
		return modelAndView;
	}

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
		HttpSession session = ContextHolderUtils.getSession();
		DataSourceContextHolder
				.setDataSourceType(DataSourceType.dataSource_jeecg);
		AjaxJson j = new AjaxJson();
		TSUser u = userService.checkUserExits(user);
		if (u != null) {
			// date:20130318-------for:注释掉U盾的校验
			// if (user.getUserKey().equals(u.getUserKey())) {
			if (true) {
				// date:20130318-------for:注释掉U盾的校验
				message = "用户: " + user.getUserName() +"[]" +"登录成功";
				SessionInfo sessionInfo = new SessionInfo();
				sessionInfo.setUser(u);
				sessionInfo.setAreaCode(getLoginAreaCode(u));
				session.setMaxInactiveInterval(60 * 30);
				session.setAttribute(Globals.USER_SESSION, sessionInfo);
				// 添加登陆日志
				systemService.addLog(message, Globals.Log_Type_LOGIN,
						Globals.Log_Leavel_INFO);

			} else {
				j.setMsg("请检查U盾是否正确");
				j.setSuccess(false);
			}
		} else {
			j.setMsg("用户名或密码错误!");
			j.setSuccess(false);
		}
		return j;
	}

	private String getLoginAreaCode(TSUser u) {
		String rtnAreaCode = "";
		// 管理部门 0 质检机构 1
		if (u.getUsertype().equals("0")) {// 管理部门
			String cityCode = u.getTSDepart().getCode();
			String countryCode = u.getTSDepart().getAreacode2();
			if (StringUtil.isNotEmpty(countryCode)) {
				rtnAreaCode = countryCode;
			}
			if (StringUtil.isNotEmpty(cityCode)) {
				rtnAreaCode = cityCode;
			}
		} else {// 质检机构 1
			String orgId = u.getTSDepart().getId();
			OrganizationEntity orgEntity = systemService.get(OrganizationEntity.class, orgId);
			u.setOrganization(orgEntity);
			String cityCode = orgEntity.getAreacode();
			String countryCode = orgEntity.getAreacode2();
			if (StringUtil.isNotEmpty(countryCode)) {
				rtnAreaCode = countryCode;
			}
			if (StringUtil.isNotEmpty(cityCode)) {
				rtnAreaCode = cityCode;
			}
		}
		return rtnAreaCode;
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "login")
	public String login(HttpServletRequest request) {
		DataSourceContextHolder
				.setDataSourceType(DataSourceType.dataSource_jeecg);
		TSUser user = ResourceUtil.getSessionUserName();
		String roles = "";
		if (user != null) {
			List<TSRoleUser> rUsers = systemService.findByProperty(
					TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleName()+"," ;
			}
			if(roles.length()>0){
				roles = roles.substring(0,roles.length()-1);
			}
			request.setAttribute("roleName", roles);
			request.setAttribute("userName", user.getRealName());
			//默认风格
			String indexStyle = "default";
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if(cookie==null || StringUtils.isEmpty(cookie.getName())){
					continue;
				}
				if(cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")){
					indexStyle = cookie.getValue();
				}
			}
			//要添加自己的风格，复制下面三行即可
			if(StringUtils.isNotEmpty(indexStyle) && indexStyle.equalsIgnoreCase("bootstrap")){
				return "main/bootstrap_main";
			}
			return "main/index";
		} else {
			return "login/login";
		}

	}

	/**
	 * 退出系统
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView modelAndView = null;

		HttpSession session = ContextHolderUtils.getSession();
		String versionCode = oConvertUtils.getString(request
				.getParameter("versionCode"));
		TSUser user = ResourceUtil.getSessionUserName();
		// 根据版本编码获取当前软件版本信息
		TSVersion version = systemService.findUniqueByProperty(TSVersion.class,
				"versionCode", versionCode);
		List<TSRoleUser> rUsers = systemService.findByProperty(
				TSRoleUser.class, "TSUser.id", user.getId());
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			session.removeAttribute(role.getId());
		}

		// 判断用户是否为空不为空则清空session中的用户object
		session.removeAttribute(Globals.USER_SESSION);// 注销该操作用户
		systemService.addLog("用户" + user.getUserName() + "已退出",
				Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
		modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));

		return modelAndView;
	}

	/**
	 * 菜单跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/left")
	public ModelAndView left(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		Map<Integer, List<TSFunction>> menuMap = getFunctionMap(user);
		request.setAttribute("menuMap", menuMap);
		Map<String, String> menuAutoCompleteMap = getSidebarDataSource(menuMap);
		request.setAttribute("dataSource", menuAutoCompleteMap.get("dataSource"));
		request.setAttribute("dataIds", menuAutoCompleteMap.get("dataIds"));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/sidebar");
	}
	
	/**
	 * 取得自动完成菜单项
	 * 
	 * @param menuMap
	 * @return
	 */
	private Map<String, String> getSidebarDataSource(Map<Integer, List<TSFunction>> menuMap){
		Map<String, String> result = new HashMap<String, String>();
		StringBuffer dataSource = new StringBuffer();
		StringBuffer dataIds = new StringBuffer();
		dataSource.append("[");
		dataIds.append("[");
		for(Integer inte: menuMap.keySet()){
			for(TSFunction function: menuMap.get(inte)){
				StringBuffer tempSf = new StringBuffer();
				tempSf.append(Constants.HTML_MARK_DOUBLE_QUOTES);
				tempSf.append(function.getFunctionName());
				tempSf.append(Constants.HTML_MARK_DOUBLE_QUOTES);
				tempSf.append(",");
				dataSource.append(tempSf);
				
				dataIds.append(Constants.HTML_MARK_DOUBLE_QUOTES);
				dataIds.append(function.getId());
				dataIds.append(Constants.HTML_MARK_DOUBLE_QUOTES);
				dataIds.append(",");
			}
		}
		
		String sourceStr = dataSource.toString();
		sourceStr = sourceStr.substring(0, sourceStr.length() - 1) + "]";
		result.put("dataSource", sourceStr);
		
		String idsStr = dataIds.toString();
		idsStr = idsStr.substring(0, idsStr.length() - 1) + "]";
		result.put("dataIds", idsStr);
		
		return result;
	}
	
	/**
	 * 获取权限的map
	 * @param user
	 * @return
	 */
	private Map<Integer, List<TSFunction>> getFunctionMap(TSUser user) {
		Map<Integer, List<TSFunction>> functionMap  = new HashMap<Integer, List<TSFunction>>();
		Map<String,TSFunction> loginActionlist = getUserFunction(user);
		if (loginActionlist.size() > 0) {
			Collection<TSFunction> allFunctions = loginActionlist.values();
			for (TSFunction function : allFunctions) {
				if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
					functionMap.put(function.getFunctionLevel() + 0,
							new ArrayList<TSFunction>());
				}
				functionMap.get(function.getFunctionLevel() + 0).add(function);
			}
			// 菜单栏排序
			Collection<List<TSFunction>> c = functionMap.values();
			for (List<TSFunction> list :c) {
				Collections.sort(list, new NumberComparator());
			}
		}
		return functionMap;
	}

	/**
	 * 获取用户菜单列表
	 * @param user
	 * @return 
	 */
	private Map<String, TSFunction> getUserFunction(TSUser user) {
		HttpSession session = ContextHolderUtils.getSession();
		Map<String,TSFunction> loginActionlist = new HashMap<String, TSFunction>();
		List<TSRoleUser> rUsers = systemService.findByProperty(
				TSRoleUser.class, "TSUser.id", user.getId());
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			List<TSRoleFunction> roleFunctionList = ResourceUtil
					.getSessionTSRoleFunction(role.getId());
			if (roleFunctionList == null || roleFunctionList.size() == 0) {
				session.setMaxInactiveInterval(60 * 30);
				roleFunctionList = systemService.findByProperty(
						TSRoleFunction.class, "TSRole.id", role.getId());
				session.setAttribute(role.getId(), roleFunctionList);
			} else {
				if (roleFunctionList.get(0).getId() == null) {
					roleFunctionList = systemService.findByProperty(
							TSRoleFunction.class, "TSRole.id", role.getId());
				}
			}
			for (TSRoleFunction roleFunction : roleFunctionList) {
				TSFunction function = roleFunction.getTSFunction();
				loginActionlist.put(function.getId(),function);
			}
		}		
		return loginActionlist;
	}

	/**
	 * 首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		return new ModelAndView("main/home");
	}
	/**
	 * 无权限页面提示跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));

		return modelAndView;
	}
		/**
	* @Title: top
	* @Description: bootstrap头部菜单请求
	* @param request
	* @return ModelAndView    
	* @throws
	 */
	@RequestMapping(params = "top")
	public ModelAndView top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
			return new ModelAndView("main/bootstrap_top");
	}
}
