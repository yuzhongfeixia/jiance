package com.hippo.nky.controller.standard;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.standard.AgrCategoryEntity;
import com.hippo.nky.service.standard.AgrCategoryServiceI;

/**   
 * @Title: Controller
 * @Description: 农产品分类表
 * @author zhangdaihao
 * @date 2013-06-18 11:03:05
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/toxicologyInfoController")
public class ToxicologyInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ToxicologyInfoController.class);

	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 农产品分类 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toxicologyInfo")
	public ModelAndView agrCategory(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/standard/toxicologyInfo");
	}
}
