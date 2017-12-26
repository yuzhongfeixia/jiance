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
import com.hippo.nky.service.report.LivestockPlantSituationServiceI;
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
 * @Description:  监测信息统计分析(畜禽) 
 * @author nky
 * @date 2013-11-05 14:15:03
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/livestockSituationController")
public class LivestockSituationController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(LivestockSituationController.class);

	@Autowired
	private PlantSituationServiceI plantSituationService;
	
	@Autowired
	private LivestockPlantSituationServiceI livestockPlantSituationService;

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
		//return new ModelAndView("com/hippo/nky/report/standardForXQ_totle");
		return new ModelAndView("com/hippo/nky/report/standardForZZ_totle");
	}

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
		//Map<String, Object> objMap = livestockPlantSituationService.totleSituationStatistics(plantSituationEntity);
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
		Map<String, Object> objMap = livestockPlantSituationService.totleSituationStatistics(plantSituationEntity);
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
//		RtfFont titleFont=new RtfFont("宋体", 14, Font.NORMAL, Color.BLACK);//标题字体
		RtfFont contextFont=new RtfFont("宋体", 12, Font.NORMAL, Color.BLACK);//内容字体
		StringBuffer sb = new StringBuffer();
		sb.append("总体情况：");
		sb.append(objMap.get("year")).append("年");
		sb.append(objMap.get("projectName")).append("对");
		String plevel = objMap.get("projectLevel").toString();
		if(plevel.equals("1")){
			sb.append("全省");
		}else if(plevel.equals("2")){
			sb.append("全市");
		}else{
			sb.append("全县");
		}
		sb.append("共");
		sb.append(objMap.get("areaCount")).append("个城市");
		sb.append(objMap.get("argCount")).append("种产品");
		sb.append(objMap.get("sampCount")).append("个样品进行了");
		sb.append(objMap.get("pollCount")).append("种污染物监测，合格数量为");
		sb.append(objMap.get("qualifiedCount")).append("，合格率为");
		sb.append(objMap.get("pct")).append("。");
		
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
		List<Map<String, Object>> projectCodeList = livestockPlantSituationService.getProjectCode(plantSituationEntity);
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
	 * 县级畜禽产品监测结果 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toStatisticsMonitor")
	public ModelAndView toStatisticsMonitor(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/standardForXQ_monitor");
	}



	/**
	 *  县级畜禽产品监测结果
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getTableMonitor")
	@ResponseBody
	public AjaxJson getTableMonitor(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		String htmls = livestockPlantSituationService.getCommonLiveStockSampleOverProofList(plantSituationEntity, "2");
		attributesMap.put("htmls", htmls);

		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 *  省辖市区畜禽产品监测结果 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toDistrictMonitor")
	public ModelAndView toDistrictMonitor(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/standardForXQ_district_monitor");
	}
	
	/**
	 * 省辖市区畜禽产品监测结果
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getTableForDistrict")
	@ResponseBody
	public AjaxJson getTableForDistrict(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();	
		String htmls = livestockPlantSituationService.getCommonLiveStockSampleOverProofList(plantSituationEntity, "1");
		attributesMap.put("htmls", htmls);
		
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 全省生鲜乳监测结果 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "toFreshMilk")
	public ModelAndView toFreshMilk(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/standardForXQ_freshMilk");
	}
	
	/**
	 * 全省生鲜乳监测结果
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getFreshMilkMonitor")
	@ResponseBody
	public AjaxJson getFreshMilkMonitor(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		String htmls = livestockPlantSituationService.getCommonLiveStockSampleOverProofList(plantSituationEntity, "3");
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
		return new ModelAndView("com/hippo/nky/report/standardForZZ_10");
	}
	
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
		String htmls = livestockPlantSituationService.getCommonLiveStockSampleOverProofList(plantSituationEntity, "4");
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
	 * 各类畜产品监测合格率情况页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "livestockVarietyQualifiedSituation")
	public ModelAndView toLivestockVarietyQualifiedSituation(HttpServletRequest request) {
		setInitYear(request, 10);	
		// 所属行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 监测类型
		request.setAttribute("type", request.getParameter("type"));
		return new ModelAndView("com/hippo/nky/report/livestockVarietyQualifiedSituation");
	}
	
	/**
	 * 各类畜产品监测合格率情况列表取得
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getLivestockVarietyQualifiedSituation")
	@ResponseBody
	public AjaxJson getLivestockVarietyQualifiedSituation(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		// 取得页面列表,构造table元素
		String htmls = livestockPlantSituationService.getLivestockVarietyQualifiedSituation(plantSituationEntity);

		attributesMap.put("htmls", htmls);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 各监测环节畜产品监测合格率情况页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "monitoringLinkLivestockQualifiedSituation")
	public ModelAndView toMonitoringLinkLivestockQualifiedSituation(HttpServletRequest request) {
		setInitYear(request, 10);	
		// 所属行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 监测类型
		request.setAttribute("type", request.getParameter("type"));
		return new ModelAndView("com/hippo/nky/report/monitoringLinkLivestockQualifiedSituation");
	}
	
	/**
	 * 各监测环节畜产品监测合格率情况列表取得
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getMonitoringLinkLivestockQualifiedSituation")
	@ResponseBody
	public AjaxJson getMonitoringLinkLivestockQualifiedSituation(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		// 取得页面列表,构造table元素
		String htmls = livestockPlantSituationService.getMonitoringLinkLivestockQualifiedSituation(plantSituationEntity);

		attributesMap.put("htmls", htmls);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	

	/**
	 * 不同品种超标情况统计表页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "diffVarietyOverStanderd")
	public ModelAndView toDiffVarietyOverStanderd(HttpServletRequest request) {
		setInitYear(request, 10);	
		// 所属行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 监测类型
		request.setAttribute("type", request.getParameter("type"));
		return new ModelAndView("com/hippo/nky/report/diffVarietyOverStanderd");
	}
	
	/**
	 * 不同品种超标情况统计表取得
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getDiffVarietyOverStanderd")
	@ResponseBody
	public AjaxJson getDiffVarietyOverStanderd(PlantSituationEntity plantSituationEntity,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		// 取得页面列表,构造table元素
		String htmls = livestockPlantSituationService.getDiffVarietyOverStanderd(plantSituationEntity);

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
		List<Map<String, Object>> countryAndOverList = livestockPlantSituationService.getPollDetectionInfo(paramMap);
		attributes.put("countryAndOverList", countryAndOverList);
		j.setAttributes(attributes);
		j.setSuccess(true);
		return j;
	}
	
	
	/**
	 * 各监测项目不合格情况页跳转
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "pollDetectionCount")
	public ModelAndView pollDetectionCount(PlantSituationEntity plantSituationEntity, HttpServletRequest request){
		// 设置行业
		request.setAttribute("industryCode", request.getParameter("industryCode"));
		// 设置监测类型
		request.setAttribute("type", request.getParameter("type"));
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/pollDetectionCount");
	}
	
	/**
	 * 各监测项目不合格情况
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getPollDetectionCountGrid")
	@ResponseBody
	public AjaxJson getPollDetectionCountGrid(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
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
		List<Map<String, Object>> pollDetectionCountList = livestockPlantSituationService
				.getPollDetectionCount(paramMap);
		attributes.put("pollDetectionCountList", pollDetectionCountList);
		j.setAttributes(attributes);
		j.setSuccess(true);
		return j;
	}
	
	/**====================================================================================================================新增统计表*/
	/**
	 *  市各类畜产品监测合格率情况 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toCityLiveStockSitu")
	public ModelAndView toCityLiveStockSitu(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/cityLiveStockSitu");
	}
	
	/**
	 * 市各类畜产品监测合格率情况
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getCityLiveStockSitu")
	@ResponseBody
	public AjaxJson getCityLiveStockSitu(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();	
		Map<String, Object> resultMap = livestockPlantSituationService.getCommonLiveStockSitu(plantSituationEntity, "1");
		attributesMap.put("htmls", resultMap.get("htmls"));
		attributesMap.put("headerSize", resultMap.get("headerSize"));
		
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 *  区县各类畜产品监测合格率情况 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toAreaLiveStockSitu")
	public ModelAndView toAreaLiveStockSitu(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/areaLiveStockSitu");
	}
	
	/**
	 * 区县各类畜产品监测合格率情况
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getAreaLiveStockSitu")
	@ResponseBody
	public AjaxJson getAreaLiveStockSitu(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();	
		Map<String, Object> resultMap = livestockPlantSituationService.getCommonLiveStockSitu(plantSituationEntity, "2");
		attributesMap.put("htmls", resultMap.get("htmls"));
		attributesMap.put("headerSize", resultMap.get("headerSize"));
		
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	 
	/**
	 *  市各监测项目不合格情况 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toCityDetectionItemSitu")
	public ModelAndView toCityDetectionItemSitu(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/cityDetectionItemSitu");
	}

	/**
	 * 市各监测项目不合格情况 
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getCityDetectionItemSitu")
	@ResponseBody
	public AjaxJson getCityDetectionItemSitu(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();	
		Map<String, Object> resultMap = livestockPlantSituationService.getCommonDetectionItemSitu(plantSituationEntity, "1");
		attributesMap.put("htmls", resultMap.get("htmls"));
		attributesMap.put("headerSize", resultMap.get("headerSize"));
		
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 *  市各监测项目不合格情况 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toAreaDetectionItemSitu")
	public ModelAndView toAreaDetectionItemSitu(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/report/areaDetectionItemSitu");
	}

	/**
	 * 市各监测项目不合格情况 
	 * 
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getAreaDetectionItemSitu")
	@ResponseBody
	public AjaxJson getAreaDetectionItemSitu(PlantSituationEntity plantSituationEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();	
		Map<String, Object> resultMap = livestockPlantSituationService.getCommonDetectionItemSitu(plantSituationEntity, "2");
		attributesMap.put("htmls", resultMap.get("htmls"));
		attributesMap.put("headerSize", resultMap.get("headerSize"));
		
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
}
