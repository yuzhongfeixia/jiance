package com.hippo.nky.controller.report;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.report.PlantSituationEntity;
import com.hippo.nky.entity.report.VegetablesSituationEntity;
import com.hippo.nky.service.report.PlantSituationServiceI;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;

/**
 * @Title: Controller
 * @Description:  监测信息统计分析(种植) 
 * @author nky
 * @date 2013-11-05 14:15:03
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/plantSituationController")
public class PlantSituationController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PlantSituationController.class);

	@Autowired
	private PlantSituationServiceI plantSituationService;
//	@Autowired
//	private SamplingInfoServiceI samplingInfoService;
//	@Autowired
//	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 总体情况表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toTotleSituation")
	public ModelAndView toTotleSituation(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/standardForZZ_totle");
	}

	/**
	 * 取得当前用户项目级别
	 * 
	 * @return

	private Map<String, String> getLoginUserLevelMap() {
		Map<String, String> proLevel = new HashMap<String, String>();
		// 管理部门 0 质检机构 1
		if (ResourceUtil.getSessionUserName().getUsertype().equals("0")) {// 管理部门
			// 当未admin管理员
			if (ResourceUtil.getSessionUserName().getUserName().equals("admin")) {
				proLevel.put("-1", "");
				proLevel.put("1", "省任务");
				proLevel.put("2", "市任务");
				proLevel.put("3", "县任务");
			} else {
				String countryCode = ResourceUtil.getSessionUserName()
						.getTSDepart().getAreacode2();
				String cityCode = ResourceUtil.getSessionUserName()
						.getTSDepart().getCode();
				if (StringUtil.isNotEmpty(countryCode)) {
					proLevel.put("-1", "");
					proLevel.put("3", "县任务");
				} else {
					if (StringUtil.isNotEmpty(cityCode)) {
						proLevel.put("-1", "");
						proLevel.put("2", "市任务");
						proLevel.put("3", "县任务");
					}
				}
			}
		} else {// 质检机构 1
			proLevel.put("-1", "");
			proLevel.put("1", "省任务");
			proLevel.put("2", "市任务");
			proLevel.put("3", "县任务");
		}
		return proLevel;
	}
		 */

	/**
	 * 总体情况表 统计
	 * 
	 * @param detectionEntity
	 * @param request
	 */
	@RequestMapping(params = "totleSituationStatistics")
	@ResponseBody
	public AjaxJson totleSituationStatistics(PlantSituationEntity plantSituationEntity,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> objMap = plantSituationService.totleSituationStatistics(plantSituationEntity);
		j.setObj(objMap);
		return j;
	}

	public void responseDatagrid(HttpServletResponse response,
			JSONObject jObject) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter pw = response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


/**
	 * 复制文本 
	 * @param plantSituationEntity
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@RequestMapping(params="exportWord")
	public ModelAndView onSubmit(PlantSituationEntity plantSituationEntity,HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException{
		OutputStream os = null;
		String fileName = Calendar.getInstance().getTimeInMillis()+".doc";
		response.setContentType("application/msword");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		os = response.getOutputStream();
		Map<String, Object> objMap = plantSituationService.totleSituationStatistics(plantSituationEntity);
		InputStream inputStream = this.getWordFile(objMap);
		byte b[] = new byte[10240];
		int length;
		while((length = inputStream.read(b)) > 0){
			os.write(b,0,length);
		}
		os.flush();
		os.close();
		inputStream.close();
		return null;
	}
	
	/**
	 * 创建word文档
	 * @param objMap
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	private InputStream getWordFile(Map<String, Object> objMap) throws DocumentException, IOException {
		Document doc=new Document(PageSize.A4);//创建DOC
		ByteArrayOutputStream byteArrayOut=new ByteArrayOutputStream();//创建新字节输出流
		RtfWriter2.getInstance(doc, byteArrayOut);//建立一个书写器与document对象关联，通过书写器可以将文档写入到输出流中
		doc.open();//打开文档
//		RtfFont flagFont=new RtfFont("宋体", 12, Font.NORMAL, Color.RED);//高亮显示字体
		RtfFont contextFont=new RtfFont("宋体", 12, Font.NORMAL, Color.BLACK);//内容字体
		StringBuffer sb = new StringBuffer();
		sb.append("总体情况：");
		sb.append(objMap.get("year")).append("年");
		sb.append(objMap.get("projectName")).append("对");
		sb.append(objMap.get("projectLevelName").toString());
		sb.append("共");
		sb.append(objMap.get("areaCount")).append("个区县和");
		sb.append(objMap.get("areaCount1")).append("个市辖区");
		sb.append(objMap.get("argCount")).append("种产品");
		sb.append(objMap.get("sampCount")).append("个样品进行了");
		sb.append(objMap.get("pollCount")).append("种污染物监测，合格数量为");
		sb.append(objMap.get("qualifiedCount")).append("，合格率为");
		sb.append(objMap.get("pct")).append("。其中区县共");
		sb.append(objMap.get("sampCount1")).append("个样品，合格率为");
		sb.append(objMap.get("pct1")).append("。市辖区共");
		sb.append(objMap.get("sampCount2")).append("个样品，合格率为");
		sb.append(objMap.get("pct2"));
		
		Paragraph context = new Paragraph(sb.toString());  
		context.setAlignment(Element.ALIGN_LEFT);  
		context.setFont(contextFont);  
		doc.add(context);
		doc.close();
		        
		ByteArrayInputStream byteArray = new ByteArrayInputStream(
				byteArrayOut.toByteArray());
		byteArrayOut.close();
		return byteArray;
	}

	/**
	 * 通过年份、项目级别取得 检测项目
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getProjectCode")
	@ResponseBody
	public AjaxJson getProjectCode(PlantSituationEntity plantSituationEntity,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		List<Map<String, Object>> projectCodeList = plantSituationService.getProjectCode(plantSituationEntity);
		if(projectCodeList.size() <=0){
			j.setSuccess(false);
			return j;
		}
		attributesMap.put("projectCodeList", projectCodeList);
		attributesMap.put("projectLevel",plantSituationEntity.getProjectLevel());
		attributesMap.put("yearVal", plantSituationEntity.getYear());
		attributesMap.put("projectCode", plantSituationEntity.getProjectCode());

		j.setSuccess(true);
		j.setAttributes(attributesMap);
		return j;
	}
	
	/**
	 * 各区县超标情况表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toStatisticsOverProof")
	public ModelAndView toStatisticsOverProof(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/standardForZZ_overproof");
	}

//	/**
//	 * 各区县超标情况表
//	 * @param plantSituationEntity
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "getTableOverProof")
//	@ResponseBody
//	public AjaxJson getTableOverProof(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		Map<String, Object> attributesMap = new HashMap<String, Object>();
//		// 固定各区县超标情况表
//		List<Map<String, String>> totalList = plantSituationService.getSampleOverProofList(plantSituationEntity);
//		// 检测环节类型情况
//		Map<String, Object> activityList  = plantSituationService.getSampleOverProofActList(plantSituationEntity);
//
//		attributesMap.put("totalList", totalList);
//		attributesMap.put("activityList", activityList);
//
//		j.setAttributes(attributesMap);
//		j.setSuccess(true);
//		return j;
//	}
	
	/**
	 * 各区县超标情况表
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getTableOverProof")
	@ResponseBody
	public AjaxJson getTableOverProof(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		String htmls =  plantSituationService.getSampleOverProofList(plantSituationEntity);

		attributesMap.put("htmls", htmls);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 各省辖市批发市场超标情况表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toSuperMarketOverProof")
	public ModelAndView toSuperMarketOverProof(HttpServletRequest request) {
		setInitYear(request, 10);	
		// 所属行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 监测类型
		request.setAttribute("type", request.getParameter("type"));
		return new ModelAndView("com/hippo/nky/report/standardForZZ_2");
	}
	
	
	/**
	 * 各省辖市批发市场超标情况表
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getSuperMarketOverProof")
	@ResponseBody
	public AjaxJson getSuperMarketOverProof(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		// 取得页面列表,构造table元素
		String htmls = plantSituationService.getProvincialCitiesOverStandard(plantSituationEntity);

		attributesMap.put("htmls", htmls);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 各市超标样品情况表 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "toSampleOverProof")
	public ModelAndView toSampleOverProof(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/standardForZZ_3");
	}
	
//	/**
//	 * 各市超标样品情况表
//	 * @param plantSituationEntity
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "getSampleOverProof")
//	@ResponseBody
//	public AjaxJson getSampleOverProof(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		Map<String, Object> attributesMap = new HashMap<String, Object>();
//		// 左侧固定市超标样品情况
//		List<Map<String, String>> totalList = plantSituationService.getCitySampleOverProof(plantSituationEntity);
//		// 右侧活动市超标样品情况
//		Map<String, Object> activityList  = plantSituationService.getCitySampleOverProofAct(plantSituationEntity);
//
//		attributesMap.put("totalList", totalList);
//		attributesMap.put("activityList", activityList);
//
//		j.setAttributes(attributesMap);
//		j.setSuccess(true);
//		return j;
//	}
	
	/**
	 * 各市超标样品情况表
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getSampleOverProof")
	@ResponseBody
	public AjaxJson getSampleOverProof(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		// 左侧固定市超标样品情况
		String htmls = plantSituationService.getCitySampleOverProof(plantSituationEntity);
		attributesMap.put("htmls", htmls);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 设置年度列表
	 * @param request
	 * @param num
	 */
	private void setInitYear(HttpServletRequest request,int num) {
		request.setAttribute("currYear", ConverterUtil.getCurrentTime("yyyy"));
		request.setAttribute("yearList", ConverterUtil.getYearList(num));
	}	
	
	//chenyingqin↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 不同蔬菜来源超标情况统计表页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "standardForZZ_4")
	public ModelAndView toVegetablesMonitoringLinkOverStanderd(HttpServletRequest request) {
		setInitYear(request, 10);	
		// 所属行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 监测类型
		request.setAttribute("type", request.getParameter("type"));
		return new ModelAndView("com/hippo/nky/report/standardForZZ_4");
	}
	
	/**
	 * 不同蔬菜来源超标情况统计表列表取得
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getVegetablesMonitoringLinkOverStanderd")
	@ResponseBody
	public AjaxJson getVegetablesMonitoringLinkOverStanderd(PlantSituationEntity plantSituationEntity,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		List<VegetablesSituationEntity> vegetablesSituationList = 
				plantSituationService.getVegetablesMonitoringLinkOverStanderd(plantSituationEntity);

		attributesMap.put("vegetablesSituationList", vegetablesSituationList);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 *不同蔬菜类别超标情况统计表页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "standardForZZ_5")
	public ModelAndView toVegetablesCategoryOverStanderd(HttpServletRequest request) {
		setInitYear(request, 10);	
		// 所属行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 监测类型
		request.setAttribute("type", request.getParameter("type"));
		return new ModelAndView("com/hippo/nky/report/standardForZZ_5");
	}
	
	/**
	 * 不同蔬菜来源超标情况统计表列表取得
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getVegetablesCategoryOverStanderd")
	@ResponseBody
	public AjaxJson getVegetablesCategoryOverStanderd(PlantSituationEntity plantSituationEntity,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		List<VegetablesSituationEntity> vegetablesSituationList = 
				plantSituationService.getVegetablesCategoryOverStanderd(plantSituationEntity);

		attributesMap.put("vegetablesSituationList", vegetablesSituationList);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 不同蔬菜品种超标情况统计表页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "standardForZZ_7")
	public ModelAndView toVegetablesVarietyOverStanderd(HttpServletRequest request) {
		setInitYear(request, 10);	
		// 所属行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 监测类型
		request.setAttribute("type", request.getParameter("type"));
		return new ModelAndView("com/hippo/nky/report/standardForZZ_7");
	}
	
	/**
	 * 不同蔬菜来源超标情况统计表列表取得
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getVegetablesVarietyOverStanderd")
	@ResponseBody
	public AjaxJson getVegetablesVarietyOverStanderd(PlantSituationEntity plantSituationEntity,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		List<VegetablesSituationEntity> vegetablesSituationList = 
				plantSituationService.getVegetablesVarietyOverStanderd(plantSituationEntity);

		attributesMap.put("vegetablesSituationList", vegetablesSituationList);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 各区县超标情况表（详情）取得页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "standardForZZ_8")
	public ModelAndView toCountiesOverStandardDetail(HttpServletRequest request) {
		setInitYear(request, 10);	
		// 所属行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 监测类型
		request.setAttribute("type", request.getParameter("type"));
		return new ModelAndView("com/hippo/nky/report/standardForZZ_8");
	}
	
	/**
	 * 各区县超标情况表（详情）取得
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getCountiesOverStandardDetail")
	@ResponseBody
	public AjaxJson getCountiesOverStandardDetail(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		// 取得页面列表,构造table元素
		String htmls = plantSituationService.getCountiesOverStandardDetail(plantSituationEntity);

		attributesMap.put("htmls", htmls);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 各省辖市批发市场超标情况表(详表)取得页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "standardForZZ_9")
	public ModelAndView toProvincialCitiesOverStandardDetail(HttpServletRequest request) {
		setInitYear(request, 10);	
		// 所属行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 监测类型
		request.setAttribute("type", request.getParameter("type"));
		return new ModelAndView("com/hippo/nky/report/standardForZZ_9");
	}
	
	/**
	 * 各省辖市批发市场超标情况表(详表)取得
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getProvincialCitiesOverStandardDetail")
	@ResponseBody
	public AjaxJson getProvincialCitiesOverStandardDetail(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		// 取得页面列表,构造table元素
		String htmls = plantSituationService.getProvincialCitiesOverStandardDetail(plantSituationEntity);

		attributesMap.put("htmls", htmls);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	//chenyingqin↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	/**
	 * 超标样品情况详细表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "overProofSampleDeatil")
	public ModelAndView overProofSampleDeatil(HttpServletRequest request) {
		// 设置行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 设置监测类型
		request.setAttribute("type", request.getParameter("type"));
		// 取得系统默认单位code
		String sysConfigValString = ResourceUtil.getConfigByName(Constants.PROPEERTIS_SYSTEMUNIT);
		// 取得系统默认单位名
		request.setAttribute("sysUnit", ConverterUtil.getDictionaryName("unit",sysConfigValString));
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/overProofSampleDeatil");
	}
	
	/**
	 * 取得超标样品情况详细表dataGrid数据农产品
	 * 
	 * @param detectionEntity  检测信息实体
	 * @param request request对象
	 * @param response response对象
	 * @param dataGrid dataGrid对象
	 */
	@RequestMapping(params = "getOverProofSampleInfo")
	public void getOverProofSampleInfo(PlantSituationEntity plantSituationEntity, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		if(ConverterUtil.isEmpty(plantSituationEntity.getProjectCode())){
			// 没有项目code时按照没有数据来处理，否则grid会报错
			dataGrid.setTotal(0);
			dataGrid.setReaults(new ArrayList<Object>());
			TagUtil.datagrid(response, dataGrid);
			return;
		}
		// 设置翻页信息到Entity
		ConverterUtil.setPageNavigateInfo(dataGrid, plantSituationEntity);
		plantSituationService.getOverProofSampleInfo(plantSituationEntity, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 各区县药物检出及超标情况页跳转
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "countryDetectionAndOverPoll")
	public ModelAndView countryDetectionAndOverPoll(PlantSituationEntity plantSituationEntity, HttpServletRequest request){
		// 设置行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 设置监测类型
		request.setAttribute("type", request.getParameter("type"));
		// 设置监测类型
		request.setAttribute("selCondtion", request.getParameter("selCondtion"));
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/countryDetectionAndOverPoll");
	}
	
	/**
	 * 各区县药物检出及超标情况
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getCountryDetectionAndOverPollGrid")
	@ResponseBody
	public AjaxJson getCountryDetectionAndOverPollGrid(PlantSituationEntity plantSituationEntity, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			paramMap = ConverterUtil.entityToMap(plantSituationEntity);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			return j;
		}
		paramMap.put("selCondtion", request.getParameter("selCondtion"));
		List<Map<String, Object>> countryAndOverList = plantSituationService.getPollDetectionInfo(paramMap);
		attributes.put("countryAndOverList", countryAndOverList);
		j.setAttributes(attributes);
		j.setSuccess(true);
		return j;
	}
}
