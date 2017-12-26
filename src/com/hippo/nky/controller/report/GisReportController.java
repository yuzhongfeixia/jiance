package com.hippo.nky.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jeecg.system.service.SystemService;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.monitoring.NkyMonitoringSiteEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.service.report.GisReportServiceI;

/**
 * @Title: Controller
 * @Description: gis统计分析
 * @author nky
 * @date 2013-11-05 14:15:03
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/gisReportController")
public class GisReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GisReportController.class);
	@Autowired private GisReportServiceI gisReportService;
	@Autowired
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 监测点分布
	 */
	@RequestMapping(params = "monitoringSiteDistribution")
	public ModelAndView monitoringSiteDistribution( HttpServletRequest request) {
		String hql0 = "from NkyMonitoringSiteEntity where latitude is not null and longitude is not null ";
	    List<NkyMonitoringSiteEntity> monitoringSiteEntityList = systemService.findHql(hql0);
	    request.setAttribute("monitoringSiteEntityList", monitoringSiteEntityList);
		return new ModelAndView("com/hippo/nky/report/monitoringSiteDistribution");
	}
	
	/**
	 * 检测机构分布
	 */
	@RequestMapping(params = "organizationDistribution")
	public ModelAndView organizationDistribution( HttpServletRequest request) {
		String hql0 = "from OrganizationEntity where longitude is not null and latitude is not null ";
	    List<OrganizationEntity> organizationEntity = systemService.findHql(hql0);
	    request.setAttribute("organizationEntity", organizationEntity);
		return new ModelAndView("com/hippo/nky/report/organizationDistribution");
	}
	
	/**
	 * 参检农产品分布
	 */
	@RequestMapping(params = "monitoringAgrDistribution")
	public ModelAndView monitoringAgrDistribution( HttpServletRequest request) {
		//取得当前年
		String currYear = ConverterUtil.getCurrentTime("yyyy");
		//取得前后10年，年度列表
		List<String> yearList = ConverterUtil.getYearList(10);
		request.setAttribute("currYear", currYear);
		request.setAttribute("yearList", yearList);
		return new ModelAndView("com/hippo/nky/report/monitoringAgrDistribution");
	}
	
	/**
	 * 参检农产品分布  选择项目
	 */
	@RequestMapping(params = "selectProject")
	@ResponseBody
	public AjaxJson selectProject( HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String projectCode = request.getParameter("projectCode");
		//String monitoringLink = request.getParameter("monitoringLink");
		String monitoringLink = "";
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("projectCode", projectCode);
		modelMap.put("monitoringLink", monitoringLink);
		gisReportService.selectProject(j,modelMap);
		//j.setMsg(message);
		return j;
	}
	
	/**
	 * 参检农产品分布  农产品分布检索
	 */
	@RequestMapping(params = "searchBreed")
	@ResponseBody
	public AjaxJson searchBreed( HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("projectCode", request.getParameter("projectCode"));
		modelMap.put("breed", request.getParameter("breed"));
		modelMap.put("monitoringLink", request.getParameter("monitoringLink"));
		modelMap.put("dataLevel", request.getParameter("dataLevel"));
		gisReportService.searchBreed(j,modelMap);
		//j.setMsg(message);
		return j;
	}
	
	/**
	 * 各类工作各类产品区域态势图查询 
	 */
	@RequestMapping(params = "statisticsDistribution")
	public ModelAndView statisticsDistribution( HttpServletRequest request) {
		//取得当前年
		String currYear = ConverterUtil.getCurrentTime("yyyy");
		//取得前后10年，年度列表
		List<String> yearList = ConverterUtil.getYearList(10);
		request.setAttribute("currYear", currYear);
		request.setAttribute("yearList", yearList);
		return new ModelAndView("com/hippo/nky/report/statisticsDistribution");
	}
	
	/**
	 * 各类工作各类产品区域态势图查询   选择项目
	 */
	@RequestMapping(params = "statisticsDistributionSelectProject")
	@ResponseBody
	public AjaxJson statisticsDistributionSelectProject( HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String projectCode = request.getParameter("projectCode");
		String breed = request.getParameter("breed");
		//String monitoringLink = request.getParameter("monitoringLink");
		String monitoringLink = "";
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("projectCode", projectCode);
		modelMap.put("breed", breed);
		modelMap.put("poll", "");
		modelMap.put("monitoringLink", monitoringLink);
		gisReportService.statisticsDistributionSelectProject(j,modelMap);
		//j.setMsg(message);
		return j;
	}
	
	/**
	 * 各类工作各类产品区域态势图查询   查询完成情况
	 */
	@RequestMapping(params = "statisticsSearchBreed")
	@ResponseBody
	public AjaxJson statisticsSearchBreed( HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("projectCode", request.getParameter("projectCode"));
		modelMap.put("breed", request.getParameter("breed"));
		modelMap.put("poll", request.getParameter("poll"));
		modelMap.put("monitoringLink", request.getParameter("monitoringLink"));
		modelMap.put("dataLevel", request.getParameter("dataLevel"));
		gisReportService.statisticsSearchBreed(j,modelMap);
		//j.setMsg(message);
		return j;
	}

	/**
	 * 监测点参检情况、超标情况
	 */
	@RequestMapping(params = "getDetectedUnitInfo")
	public ModelAndView getDetectedUnitInfo(HttpServletRequest request) {
		//取得当前年
		String currYear = ConverterUtil.getCurrentTime("yyyy");
		//取得前后10年，年度列表
		List<String> yearList = ConverterUtil.getYearList(10);
		request.setAttribute("currYear", currYear);
		request.setAttribute("yearList", yearList);
		
		return new ModelAndView("com/hippo/nky/report/detectedUnitInfo");
	}
	
	/**
	 * 取得参检情况的数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getDetectedInfoData")
	@ResponseBody
	public AjaxJson getDetectedInfoData(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		// 取得区分flg
		String infoFlg = request.getParameter("infoFlg");
		if(ConverterUtil.isNotEmpty(infoFlg)){
			Map<String, Object> attributes = new HashMap<String, Object>();
			// 项目code
			String projectCode = request.getParameter("projectCode");
			// 参检情况的数据list
			List<Map<String, Object>> detectedInfoList = new ArrayList<Map<String,Object>>();
			// 1:查看参检情况 2:查看超标情况
			if("1".equals(infoFlg)){
				// 取得参检受检单位情况
				detectedInfoList = gisReportService.getDetectedInfo(projectCode);
			}
			if ("2".equals(infoFlg)) {
				// 取得超标受检单位情况
				detectedInfoList = gisReportService.getOverPoofInfo(projectCode);
			}
			// 替换地图标注
			for (Map<String, Object> detectedInfo : detectedInfoList) {
				if ("r".equalsIgnoreCase(ConverterUtil.toString(detectedInfo.get("icon")))) {
					// 如果返回的图标为r 则认为是红色mark
					detectedInfo.put("icon", Constants.MAP_MARKER_RED);
				} else if ("g".equalsIgnoreCase(ConverterUtil.toString(detectedInfo.get("icon")))) {
					// 如果返回的图标为g 则认为是绿色mark
					detectedInfo.put("icon", Constants.MAP_MARKER_GREEN);
				}
			}
			attributes.put("detectedInfoList", detectedInfoList);
			j.setAttributes(attributes);
			j.setSuccess(true);
		} else {
			j.setSuccess(false);
		}
		return j;
	}
	
	/**
	 * 监测点参检情况、超标情况
	 */
	@RequestMapping(params = "getmonitoringSiteTaskInfo")
	public ModelAndView getmonitoringSiteTaskInfo(HttpServletRequest request) {
		//取得当前年
		String currYear = ConverterUtil.getCurrentTime("yyyy");
		//取得前后10年，年度列表
		List<String> yearList = ConverterUtil.getYearList(10);
		request.setAttribute("currYear", currYear);
		request.setAttribute("yearList", yearList);
		
		return new ModelAndView("com/hippo/nky/report/monitoringSiteTaskInfo");
	}
	
	/**
	 * 取得任务完成情况的数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getmonitoringSiteTaskInfoListData")
	@ResponseBody
	public AjaxJson getmonitoringSiteTaskInfoListData(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		// 取得区分flg
		String infoFlg = request.getParameter("infoFlg");
		if (ConverterUtil.isNotEmpty(infoFlg)) {
			Map<String, Object> attributes = new HashMap<String, Object>();
			// 项目code
			String projectCode = request.getParameter("projectCode");
			// 参检任务完成情况的数据list
			List<Map<String, Object>> monitoringSiteTaskInfoList = new ArrayList<Map<String, Object>>();
			// 1:抽样任务完成情况 2:检测任务完成情况
			if ("1".equals(infoFlg)) {
				// 抽样任务完成情况
				monitoringSiteTaskInfoList = gisReportService.getSampleTaskInfo(projectCode);
			}
			if ("2".equals(infoFlg)) {
				// 检测任务完成情况
				monitoringSiteTaskInfoList = gisReportService.getDetectionTaskInfo(projectCode);
			}
			// 替换地图标注
			for (Map<String, Object> monitoringSiteInfo : monitoringSiteTaskInfoList) {
				if ("r".equalsIgnoreCase(ConverterUtil.toString(monitoringSiteInfo.get("icon")))) {
					// 如果返回的图标为r 则认为是红色mark
					monitoringSiteInfo.put("icon", Constants.MAP_MARKER_RED);
				} else if ("g".equalsIgnoreCase(ConverterUtil.toString(monitoringSiteInfo.get("icon")))) {
					// 如果返回的图标为g 则认为是绿色mark
					monitoringSiteInfo.put("icon", Constants.MAP_MARKER_GREEN);
				}
			}
			attributes.put("monitoringSiteTaskInfoList", monitoringSiteTaskInfoList);
			j.setAttributes(attributes);
			j.setSuccess(true);
		} else {
			j.setSuccess(false);
		}
		return j;
	}
}
