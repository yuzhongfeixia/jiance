package com.hippo.nky.controller.detection;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.common.ExportPdf;
import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.detection.DetectionEntity;
import com.hippo.nky.entity.detection.DetectionSetBackEntity;
import com.hippo.nky.entity.detection.DetectionStatisticalEntity;
import com.hippo.nky.entity.detection.NkyDetectionInformationEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.sample.RoutinemonitoringEntity;
import com.hippo.nky.entity.sample.SamplingInfoEntity;
import com.hippo.nky.entity.sample.SamplingSetBackEntity;
import com.hippo.nky.service.detection.DetectionServiceI;
import com.hippo.nky.service.sample.SamplingInfoServiceI;

/**
 * @Title: Controller
 * @Description: 抽样信息录入
 * @author nky
 * @date 2013-11-05 14:15:03
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/detectionController")
public class DetectionController extends BaseController {
	

	private static final String NAME_SPACE = "com.hippo.nky.entity.detection.DetectionEntity.";
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DetectionController.class);

	@Autowired
	private DetectionServiceI detectionService;
	@Autowired
	private SamplingInfoServiceI samplingInfoService;
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
	 * 样品接收管理 页面跳转 1 是 --- 0否
	 * 
	 * @return
	 */
	@RequestMapping(params = "toSampleReceive")
	public ModelAndView toSampleReceive(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/detection/sample_receive");
	}

	/**
	 * 样品接收管理列表页面
	 * 
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(DetectionEntity detectionEntity,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		OrganizationEntity org = ResourceUtil.getSessionUserName().getOrganization();
		detectionEntity.setOrgCode(org.getCode());
		JSONObject jObject = detectionService.getDatagrid(detectionEntity,
				dataGrid, "0");
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 设置默认的实验室编码前缀和序号
	 * 
	 * @param samplingInfoEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "setDefaultPrefix")
	@ResponseBody
	public AjaxJson setDefaultPrefix(DetectionEntity detectionEntity,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		OrganizationEntity org = ResourceUtil.getSessionUserName().getOrganization();
		if (StringUtil.isNotEmpty(detectionEntity.getProjectCode())) {
			// 当前项目下已接收的样品信息
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("projectCode", detectionEntity.getProjectCode());
			param.put("orgCode", org.getCode());
			List<String> sampleInfoList = detectionService.getLabCodeForRecv(param);
			if (sampleInfoList.size() > 0) {
				String labCode = sampleInfoList.get(0);
				String labCodePre = labCode.substring(0, labCode.lastIndexOf("-"));
				String maxLabSer = detectionService.getMaxLabSer(sampleInfoList);
				attributesMap.put("labCodePre", labCodePre);
				attributesMap.put("maxLabSer", maxLabSer);
			}	 
		}
		j.setAttributes(attributesMap);
		return j;
	}
	
	/**
	 * 样品接收 生成实验室编码
	 * 
	 * @param samplingInfoEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "validateLabPre")
	@ResponseBody
	public AjaxJson validateLabPre(DetectionEntity detectionEntity,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		
		OrganizationEntity org = ResourceUtil.getSessionUserName().getOrganization();
		Map<String, Object> selcodtion = new HashMap<String, Object>();
		selcodtion.put("ogrCode", org.getCode());
		selcodtion.put("labPre", detectionEntity.getLabPre());
		
	    Integer count = (Integer)detectionService.getObjectByMyBatis(NAME_SPACE+"selectInfoByLabPreAndDetectionCode", selcodtion);
	    if (count > 0) {
	    	attributesMap.put("labPreFlg", false);
	    } else {
	    	attributesMap.put("labPreFlg", true);
	    }
	    j.setAttributes(attributesMap);
		return j;
	}
	

	/**
	 * 样品接收 生成实验室编码
	 * 
	 * @param samplingInfoEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateDetection")
	@ResponseBody
	public AjaxJson updateDetection(DetectionEntity detectionEntity,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		OrganizationEntity org = ResourceUtil.getSessionUserName().getOrganization();
		detectionEntity.setOrgCode(org.getCode());
		if (StringUtil.isNotEmpty(detectionEntity.getLabPre())) {
			try {
				message = "实验室编码生成成功!";
				detectionService.updateDetection(detectionEntity);
				systemService.addLog(message, Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "实验室编码生成失败";
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 实验室样品管理 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toLabSample")
	public ModelAndView toLabSample(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/detection/lab_sample");
	}

	/**
	 * 实验室样品列表管理
	 * 
	 * @param detectionEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "labSampleDatagrid")
	public void labSampleDatagrid(DetectionEntity detectionEntity,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		String orgCode = ResourceUtil.getSessionUserName().getOrganization().getCode();
		// 过滤用户所能看到的数据 部门=质检机构
		detectionEntity.setOrgCode(orgCode);
		JSONObject jObject = detectionService.getDatagrid(detectionEntity,
				dataGrid, "1");
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 实验室样品管理 修改页面跳转
	 * @param detectionEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "toUpdateSample")
	public ModelAndView toUpdateSample(DetectionEntity detectionEntity,HttpServletRequest request) {
		SamplingInfoEntity sEntity = samplingInfoService.get(SamplingInfoEntity.class, detectionEntity.getSampleCode());
		request.setAttribute("sEntity", sEntity);
		return new ModelAndView("com/hippo/nky/detection/lab_sample_edit");
	}
	
	/**
	 * 实验室样品修改 需要验证
	 * @param detectionEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateSampleLabCode")
	@ResponseBody
	public AjaxJson updateSampleLabCode(DetectionEntity detectionEntity,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(detectionEntity.getSampleCode())) {
			SamplingInfoEntity sEntity = samplingInfoService.get(SamplingInfoEntity.class, detectionEntity.getSampleCode());
			try {
				message = "实验室编码修改成功!";
				sEntity.setLabCode(detectionEntity.getLabCode());
				samplingInfoService.updateEntitie(sEntity);
				systemService.addLog(message, Globals.Log_Type_UPDATE,Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "实验室编码修改失败";
			}
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 验证实验室编码
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkLabCode")
	@ResponseBody
	public boolean checkLabCode(DetectionEntity detectionEntity,HttpServletRequest request) {
		String labCode = detectionEntity.getLabCode();
		if (!labCode.contains("-") || !labCode.substring(labCode.lastIndexOf("-")).matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
			return false;
		}
		detectionEntity.setOrgCode(ResourceUtil.getSessionUserName().getOrganization().getCode());
		int count = detectionService.checkLabCode(detectionEntity); 
		if(count > 0)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 重置实验室编码
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "resetLabCode")
	@ResponseBody
	public AjaxJson resetLabCode(DetectionEntity detectionEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		detectionEntity.setOrgCode(ResourceUtil.getSessionUserName().getOrganization().getCode());
	
		int count = detectionService.resetProjectLabCode(detectionEntity);
		message = "重置实验室编码失败";
		if (count > 0) {
			message = "重置实验室编码成功";
		} 
		j.setMsg(message);
		return j;
	}

	/**
	 * 实验室样品标签打印页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "toPrint")
	public ModelAndView toPrint(HttpServletRequest request) {
		String ids=oConvertUtils.getString(request.getParameter("ids"));
		request.setAttribute("ids", ids);
		return new ModelAndView("com/hippo/nky/detection/lab_barcode_print");
	}
	/**
	 * 实验室样品标签打印
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "printLabBarcode")
	@ResponseBody
	public AjaxJson printLabBarcode(HttpServletRequest request) {
		String ids=oConvertUtils.getString(request.getParameter("ids"));
		AjaxJson j = new AjaxJson();
		PropertiesUtil prop = new PropertiesUtil();
		String fileName = DataUtils.getDate("yyyyMMddHHmmss")+"_";
		//D:\dev\working\framework3.3.2\WebContent\pdf\ == basepath 
		// 文件保存目录URL
		String basePath = request.getSession().getServletContext().getRealPath("") + File.separator + "pdf"+ File.separator;
        // 检查目录
		File deployDir = new File(basePath);
		if (!deployDir.exists()) {
			deployDir.mkdirs();
		}
		String  filePath = (deployDir+File.separator+ fileName) + ".pdf";
		
		DetectionEntity detectionEntity = new DetectionEntity();

		String httpPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		detectionEntity.setPdfURL((httpPath + filePath.substring(filePath.indexOf("pdf"))).replaceAll("\\\\", "/"));
		List<Map<String, Object>> mapLst = detectionService.findSampleById(ids);
		try {
			message = "实验室样品标签打印成功!";
			ExportPdf.createPdf(prop.readProperty("fontTTF"),mapLst,filePath);
			detectionService.updateSamplesByIds(ids);
//			System.out.println("pdf_url = " + (prop.readProperty("wwwURL") + filePath.substring(filePath.indexOf("pdf"))).replaceAll("\\\\", "/"));
			systemService.addLog(message, Globals.Log_Type_UPDATE,Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "实验室样品标签打印失败";
		}
		j.setMsg(message);
		j.setObj(detectionEntity);
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
	 * 检测信息汇总画面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "detectionInfoCollect")
	public ModelAndView detectionInfoCollect(HttpServletRequest request) {
		// 取得当前年
		String currYear = ConverterUtil.getCurrentTime("yyyy");
		// 取得前后5年，年度列表
		List<String> yearList = ConverterUtil.getYearList(10);
		request.setAttribute("currYear", currYear);
		request.setAttribute("yearList", yearList);
		return new ModelAndView("com/hippo/nky/detection/detectionInfo_collect");
	}
	

    /**
     * 检测信息汇总画列表取得
     * @param barcodeInfoInput
     * @param request
     * @param response
     * @param dataGrid
     */
	@RequestMapping(params = "dectionInfoCollectDatagrid")
	@ResponseBody
	public AjaxJson dectionInfoCollectDatagrid(SamplingInfoEntity samplingInfoEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = detectionService.getDectionInfoCollectDatagrid(samplingInfoEntity,  dataGrid);
		//JSONObject jObject = detectionService.getDectionInfoCollectDatagrid(samplingInfoEntity,  dataGrid);
		attributesMap.put("pollSize", resultMap.get("pollSize"));
		attributesMap.put("htmls", resultMap.get("htmls"));
		j.setAttributes(attributesMap);
		return j;
	}
	
	/**
	 * 检测信息汇总详情画面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "detectionInfoCollectDetail")
	public ModelAndView detectionInfoCollectDetail(SamplingInfoEntity samplingInfoEntity, HttpServletRequest request) {
		request.setAttribute("sampleId", samplingInfoEntity.getId());
		Map<String, Object> tmap = detectionService.getDectionInfoCollectDetail(samplingInfoEntity.getId());
		request.setAttribute("taskName", tmap.get("taskName"));
		request.setAttribute("spCode", tmap.get("spCode"));
		request.setAttribute("cname", tmap.get("cname"));
		request.setAttribute("unitFullname", tmap.get("unitFullname"));
		request.setAttribute("monitoringLink", detectionService.getMonitoringLinkName((String)tmap.get("monitoringLink")));
		request.setAttribute("unitAddress", tmap.get("unitAddress"));
		request.setAttribute("samplingDate", tmap.get("samplingDate"));
		request.setAttribute("samplingOgrname", tmap.get("samplingOgrname"));
		request.setAttribute("detectionOgrname", tmap.get("detectionOgrname"));
		request.setAttribute("projectCode", request.getParameter("projectCode"));
		
		String projectCode = oConvertUtils.getString(request.getParameter("projectCode"));
		List<MonitoringProjectEntity> lst = systemService.findByProperty(MonitoringProjectEntity.class, "projectCode", projectCode);
		if (lst.size() > 0){
			MonitoringProjectEntity mp = lst.get(0);
			if(mp.getDetached().equals("0")){
				request.setAttribute("detached", 0);// 抽检不分离
			} else {
				request.setAttribute("detached", 1);// 抽检分离
			}
		}
		
		// 行业种植
		if (Constants.INDUSTRY_PLANT.equalsIgnoreCase(ConverterUtil.toString(tmap.get("industryCode")))) {
			request.setAttribute("templete", "detectionReportPlant");
		} else if (Constants.INDUSTRY_LIVESTOCK.equalsIgnoreCase(ConverterUtil.toString(tmap.get("industryCode")))) {
			// 行业畜禽
			request.setAttribute("templete", "detectionReportPlant");
		}
		if (StringUtils.equals((String)tmap.get("isOverproof"), "0")) {
			request.setAttribute("detectionResult", "合格");
		} else {
			request.setAttribute("detectionResult", "不合格");
		}
		// 取得样品污染检出值
		List<NkyDetectionInformationEntity> detectionInfo = 
				detectionService.getPollDetectionResult(samplingInfoEntity.getSampleCode());
		request.setAttribute("detectionInfo", detectionInfo);
		return new ModelAndView("com/hippo/nky/detection/detectionInfo_collect_detail");
		
	}

	/**
	 * 跳转到检测信息录入
	 * 
	 * @param request
	 *            request对象
	 * @return
	 */
	@RequestMapping(params = "detectionInformationList")
	public ModelAndView detectionInformationList(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/detection/detectionInformationList");
	}

	/**
	 * 取得检测信息录入dataGrid数据农产品
	 * 
	 * @param detectionEntity  检测信息实体
	 * @param request request对象
	 * @param response response对象
	 * @param dataGrid dataGrid对象
	 */
	@RequestMapping(params = "detectionInformationSamGrid")
	public void detectionInformationSamGrid(DetectionEntity detectionEntity, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		// 取得当前用户的部门
		String detectionCode = ResourceUtil.getSessionUserName().getTSDepart().getId();
		// 过滤用户所能看到的数据 部门=质检机构
		detectionEntity.setOrgCode(detectionCode);
		detectionService.detectionInformationSamGrid(detectionEntity, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 取得检测信息录入dataGrid数据污染物
	 * 
	 * @param detectionEntity  检测信息实体
	 * @param request request对象
	 * @param response response对象
	 * @param dataGrid dataGrid对象
	 */
	@RequestMapping(params = "detectionInformationPollGrid")
	public void detectionInformationPollGrid(DetectionEntity detectionEntity, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		// 取得当前用户的部门
		String detectionCode = ResourceUtil.getSessionUserName().getTSDepart().getId();
		// 过滤用户所能看到的数据 部门=质检机构
		detectionEntity.setOrgCode(detectionCode);
		detectionService.detectionInformationPollGrid(detectionEntity, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 更新检测值
	 * 
	 * @param detectionEntity
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "addOrUpdateDetectionInformation")
	public ModelAndView addOrUpdateDetectionInformation(DetectionEntity detectionEntity, HttpServletRequest request) throws UnsupportedEncodingException{
		boolean isSample = ConverterUtil.toBoolean(request.getParameter("isSample"));
		boolean isDetail = ConverterUtil.toBoolean(request.getParameter("isDetail"));

		boolean setBackFlg = Boolean.valueOf(request.getParameter("setBackFlg"));//是否为退回样品
		// 是否为退回
		request.setAttribute("setBackFlg", setBackFlg);
		if (setBackFlg) {
			detectionEntity.setSampleStatus("5");
		} else {
			detectionEntity.setSampleStatus("4");
		}
		if (detectionEntity.getOrgCode() == null) {
			// 取得当前用户的部门
			String detectionCode = ResourceUtil.getSessionUserName().getTSDepart().getId();
			// 过滤用户所能看到的数据 部门=质检机构
			detectionEntity.setOrgCode(detectionCode);
			if (isSample) {
				//detectionEntity.setLabCode(new String(request.getParameter("labCode").getBytes("ISO-8859-1"), "UTF-8"));
				//detectionEntity.setLabCode(new String(detectionEntity.getLabCode().getBytes("ISO-8859-1"), "UTF-8"));
			}
		} else {
			String sampleCode = request.getParameter("sampleCode");
			SamplingInfoEntity sie = systemService.findByProperty(SamplingInfoEntity.class, "sampleCode", sampleCode).get(0);
			detectionEntity.setLabCode(sie.getLabCode());//由于实验室编码可能有中文，此处对实验室编码进行重设
			// 牵头单位操作修改数据
			request.setAttribute("isQtOper", true);
			request.setAttribute("orgCode", detectionEntity.getOrgCode());
			request.setAttribute("dCode", request.getParameter("dCode"));
		}
		List<NkyDetectionInformationEntity> detectionInformationList = new ArrayList<NkyDetectionInformationEntity>();
		if(isSample){
			detectionInformationList = detectionService.getDetectionInfoPollItem(detectionEntity);
			request.setAttribute("agrCode", detectionEntity.getAgrCode());
			request.setAttribute("labCode", detectionEntity.getLabCode());
			request.setAttribute("sampleName", request.getParameter("sampleName"));
		} else {
			detectionInformationList = detectionService.getDetectionInfoAgrItem(detectionEntity);
			request.setAttribute("pollName", request.getParameter("pollName"));
		}
		request.setAttribute("projectCode", detectionEntity.getProjectCode());
		request.setAttribute("detInfoList", detectionInformationList);
		request.setAttribute("isSample", isSample);
		if (isDetail) {
			return new ModelAndView("com/hippo/nky/detection/detectionInformationDetail");
		} else {
			return new ModelAndView("com/hippo/nky/detection/detectionInformation");
		}
	}
	
	/**
	 * 取得样品对应的污染物
	 * 
	 * @param detectionEntity
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "getDetectionInfoItems")
	@ResponseBody
	public AjaxJson getDetectionInfoItems(DetectionEntity detectionEntity, HttpServletRequest request) throws UnsupportedEncodingException {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(detectionEntity.getLabCode())) {
			//detectionEntity.setLabCode(new String(detectionEntity.getLabCode().getBytes("ISO-8859-1"), "UTF-8"));
		}
		// 取得当前用户的部门
		String detectionCode = ResourceUtil.getSessionUserName().getTSDepart().getId();
		// 过滤用户所能看到的数据 部门=质检机构
		detectionEntity.setOrgCode(detectionCode);
		List<NkyDetectionInformationEntity> detectionInformationList = new ArrayList<NkyDetectionInformationEntity>();

		boolean isSample = ConverterUtil.toBoolean(request.getParameter("isSample"));
		if (isSample) {
			detectionInformationList = detectionService.getDetectionInfoPollItem(detectionEntity);
		} else {
			detectionInformationList = detectionService.getDetectionInfoAgrItem(detectionEntity);
		}
		attributesMap.put("detInfoList", detectionInformationList);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 设置当前污染物对应的全部的样品检出值=未检
	 * 
	 * @param detectionEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "setAllWithUnDetection")
	@ResponseBody
	public AjaxJson setAllWithUnDetection(DetectionEntity detectionEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		// 取得当前用户的部门
		String detectionCode = ResourceUtil.getSessionUserName().getTSDepart().getId();
		// 过滤用户所能看到的数据 部门=质检机构
		detectionEntity.setOrgCode(detectionCode);
		detectionService.setAllWithUnDetection(detectionEntity);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 保存设定的检出值
	 * 
	 * @param detectionEntity
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "saveDetectionValue")
	@ResponseBody
	public AjaxJson saveDetectionValue(DetectionEntity detectionEntity, HttpServletRequest request) throws UnsupportedEncodingException {
		AjaxJson j = new AjaxJson();
		Map<?, ?> paramMap = request.getParameterMap();
		boolean isQtOper = Boolean.valueOf(request.getParameter("isQtOper"));// 牵头单位操作的修改信息
		if (!isQtOper) {
			// 取得当前用户的部门
			String detectionCode = ResourceUtil.getSessionUserName().getTSDepart().getId();
			// 过滤用户所能看到的数据 部门=质检机构
			detectionEntity.setOrgCode(detectionCode);
		}
		List<NkyDetectionInformationEntity> detInfoList = new ArrayList<NkyDetectionInformationEntity>();
		for (Object key : paramMap.keySet()) {
			String idKeyString = ConverterUtil.toString(key);
			// 取得画面ID对应的实体类
			if (idKeyString.startsWith("pld_")) {
				NkyDetectionInformationEntity deteInfoEntity = detectionService.get(
						NkyDetectionInformationEntity.class, ConverterUtil.toString(idKeyString.replace("pld_", "")));
				// 设置检出值
				deteInfoEntity.setDetectionValue(ConverterUtil.toBigDecimal(ConverterUtil.toString(paramMap.get(idKeyString))));
				// 设置一种没有对应的code(下面更新该字段时使用)
				deteInfoEntity.setIsOverproof("3");
				detInfoList.add(deteInfoEntity);
			}
		}
//		if (detectionEntity.getLabCode() != null) {			
//			detectionEntity.setLabCode(new String(detectionEntity.getLabCode().getBytes("ISO-8859-1"), "UTF-8"));
//		}
		if(detInfoList.size() > 0){
			detectionService.batchSaveOrUpdate(detInfoList);
			if (detectionEntity.getLabCode() != null) {	
				// 设置样品检测是否超标
				detectionService.setOverproofRecord(detectionEntity);
			} else {
				detectionService.setOverproofRecord1(detectionEntity);
			}
		}
		boolean setBackFlg = Boolean.valueOf(request.getParameter("setBackFlg"));
	
		if (setBackFlg && !isQtOper) { // 如果为样品退回则还需更新退回表信息
			//DetectionSetBackEntity dsbe = systemService.findByProperty(DetectionSetBackEntity.class, "code", detectionEntity.getLabCode()).get(0);
			CriteriaQuery cq = new CriteriaQuery(DetectionSetBackEntity.class);
			cq.eq("code", detectionEntity.getLabCode());
			cq.eq("ogrCode",ResourceUtil.getSessionUser().getOrganization().getCode());
			cq.add();
			List<DetectionSetBackEntity> dsbeList =  systemService.getListByCriteriaQuery(cq, false);
			systemService.delete(dsbeList.get(0));
		}
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 检测信息上报页跳转
	 * 
	 * @param detectionEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "detectionInformationReport")
	public ModelAndView detectionInformationReport(DetectionEntity detectionEntity, HttpServletRequest request){
		return new ModelAndView("com/hippo/nky/detection/detectionInformationReport");
	}
	
	/**
	 * 取得检测信息上报list
	 * 
	 * @param detectionEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getDetectionReportList")
	@ResponseBody
	public AjaxJson getDetectionReportList(DetectionEntity detectionEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		
		// 取得当前用户的部门
		String detectionCode = ResourceUtil.getSessionUserName().getTSDepart().getId();
		// 过滤用户所能看到的数据 部门=质检机构
		detectionEntity.setOrgCode(detectionCode);
		
//		List<DetectionEntity> reportPollList = detectionService.getProjectPollInfo(detectionEntity);
//		List<Map<String, Object>> reportList = detectionService.getReportList(detectionEntity);
		List<Map<String, Object>> reportList = detectionService.getReportListNew(detectionEntity);
		attributes.put("reportList", reportList);
		j.setAttributes(attributes);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 设置样品的状态为上报完成
	 * 
	 * @param detectionEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "setDetectionReported")
	@ResponseBody
	public AjaxJson setDetectionReported(DetectionEntity detectionEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		// 取得当前用户的部门
		String detectionCode = ResourceUtil.getSessionUserName().getTSDepart().getId();
		// 过滤用户所能看到的数据 部门=质检机构
		detectionEntity.setOrgCode(detectionCode);
		
		detectionService.setDetectionReported(detectionEntity);
		j.setSuccess(true);
		return j;
	}
	
	
	/**
	 * 检测完成情况统计画面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "detectionStatisticalList")
	public ModelAndView detectionStatisticalList(DetectionEntity detectionEntity, HttpServletRequest request) {

		return new ModelAndView("com/hippo/nky/detection/detection_statistical");
	}
	
	public List<List<DetectionStatisticalEntity>> setInitRes(int agrCount, List<String> agrtableHeader, List<DetectionStatisticalEntity> agrStatisticalList) {
		List<List<DetectionStatisticalEntity>> resultList = new ArrayList<List<DetectionStatisticalEntity>>();
		for (int i = 0; i< agrCount; i++) {
			List<DetectionStatisticalEntity> dList = new ArrayList<DetectionStatisticalEntity>();
			for (String header : agrtableHeader) {
				DetectionStatisticalEntity de = new DetectionStatisticalEntity();
				de.setAgrName(header);
				de.setTaskCount(-1);
				de.setActualCount(-1);
				dList.add(de);
			}
			resultList.add(dList);
		}

		int count = 0;
		boolean firstFlg = true;
		String tmpName="";
		String compareName="";
		for (DetectionStatisticalEntity agrst : agrStatisticalList) {
			tmpName = agrst.getTaskName() + agrst.getDectectionOrgName();
			if (firstFlg) {
				compareName =tmpName;
				firstFlg = false;
			}
			if (!StringUtils.equals(tmpName, compareName)) {
				count++;
			}
			setLoadRes(resultList, count, agrst);
		
			compareName =tmpName;
		}
		return resultList;
	}
	
	public void setLoadRes(List<List<DetectionStatisticalEntity>> initList, int index, DetectionStatisticalEntity detectionStatisticalEntity) {	
		List<DetectionStatisticalEntity> decDataList = initList.get(index);
		for (DetectionStatisticalEntity decData : decDataList) {
			if (StringUtils.equals(decData.getAgrName(), detectionStatisticalEntity.getAgrName())) {
				decData.setTaskCount(detectionStatisticalEntity.getTaskCount());
				decData.setActualCount(detectionStatisticalEntity.getActualCount());
			}
		}
	}
	
	@RequestMapping(params = "getStatisticalTableList")
	@ResponseBody
	public AjaxJson getStatisticalTableList(DetectionEntity detectionEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		// 总抽样数统计列表取得
		List<DetectionStatisticalEntity> detectionStatisticalList = detectionService.getDetectionStatisticalList(detectionEntity);
		// 表头农产品列表取得
//		List<String> agrtableHeader = detectionService.getAgrTableHeader(detectionEntity);
		// 各农产品统计状况取得
//		List<DetectionStatisticalEntity> agrStatisticalList = detectionService.getAgrStatisticalList(detectionEntity);
//		List<List<DetectionStatisticalEntity>> agrStatList = setInitRes(detectionStatisticalList.size(), agrtableHeader, agrStatisticalList);

		attributesMap.put("detectionStatisticalList", detectionStatisticalList);
//		attributesMap.put("agrtableHeader", agrtableHeader);
//		attributesMap.put("agrStatList", agrStatList);
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
		
	}
	
	/**
	 * 项目是否抽检分离
	 * 1 是 0 否
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkIsDetached")
	@ResponseBody
	public AjaxJson checkIsDetached(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String projectCode=oConvertUtils.getString(request.getParameter("projectCode"));
		List<MonitoringProjectEntity> lst = systemService.findByProperty(MonitoringProjectEntity.class,"projectCode",projectCode);
		if(lst.size()>0)
		{
			MonitoringProjectEntity mp = lst.get(0);
			if(mp.getDetached().equals("0")){
				j.setSuccess(false);
			}
		}
		return j;
	}
	
	/**
	 * 查询项目抽样完成信息(抽检不分离)
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getNotRecvSample")
	@ResponseBody
	public AjaxJson getNotRecvSample(DetectionEntity detectionEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		detectionEntity.setOrgCode(ResourceUtil.getSessionUser().getOrganization().getCode());
		boolean hasNotRecvFlg = detectionService.getNotRecvSample(detectionEntity);
		attributesMap.put("hasNotRecvFlg", hasNotRecvFlg);
		j.setAttributes(attributesMap);
		return j;
	}
	
	
	/**
	 * 检测信息退回 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "detectionSetBack")
	public ModelAndView detectionSetBack(HttpServletRequest request) {

		return new ModelAndView("com/hippo/nky/detection/detectionSetBack");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getDetectionSetBack")
	public void getDetectionSetBack(DetectionEntity detectionEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = detectionService.getDetectionSetBack(detectionEntity, dataGrid);
		responseDatagrid(response, jObject);
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getDetectionSetBack2")
	public void getDetectionSetBack2(DetectionEntity detectionEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = detectionService.getDetectionSetBack2(detectionEntity, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 抽样信息退回添加申请 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addSetBack")
	public ModelAndView addSetBack(HttpServletRequest request) {
		String labCode = request.getParameter("labCode");
		String id = request.getParameter("id");
		request.setAttribute("labCode", labCode);
		request.setAttribute("id", id);
		return new ModelAndView("com/hippo/nky/detection/addDetectionSetBack");
	}
	
	/**
	 * 取得检测信息退回检测添加申请搜索结果
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getAddSetBackData")
	@ResponseBody
	public AjaxJson getAddSetBackData(DetectionEntity detectionEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		Map<String, Object> selConditon = new HashMap<String, Object>();
		selConditon.put("orgCode", ResourceUtil.getSessionUser().getOrganization().getCode());
		selConditon.put("labCode", detectionEntity.getLabCode());
		selConditon.put("dCode", request.getParameter("dCode"));
		
		List<Map<String, Object>> mList = systemService.findListByMyBatis(NAME_SPACE+"getAddSetBackData", selConditon);
		if (mList.size() == 0) {
			message = "无此样品信息";
			j.setSuccess(false);
			j.setMsg(message);
			return j;
		}
		attributesMap = mList.get(0);
		// 取sampleCode
		String sampleCode = (String)attributesMap.get("SAMPLE_CODE");
		List<NkyDetectionInformationEntity> detectionInfo = 
				detectionService.getPollDetectionResult(sampleCode);
		attributesMap.put("detectionInfo", detectionInfo);
		
		j.setAttributes(attributesMap);;
		return j;
	}
	
	/**
	 * 检查样品是否已经添加过申请退回,若审核退回则视为没申请
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkDetectionSetBack")
	@ResponseBody
	public AjaxJson checkDetectionSetBack(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String labCode = request.getParameter("labCode");
		//List<DetectionSetBackEntity> mList = systemService.findByProperty(DetectionSetBackEntity.class, "code", labCode);
		CriteriaQuery cq = new CriteriaQuery(DetectionSetBackEntity.class);
		cq.eq("code", labCode);
		cq.eq("ogrCode",ResourceUtil.getSessionUser().getOrganization().getCode());
		cq.add();
		List<DetectionSetBackEntity> resultList =  systemService.getListByCriteriaQuery(cq, false);
		if (resultList.size() > 0) {
			if (resultList.get(0).getStatus() != 3) {
				j.setSuccess(false);
			}
		}
		return j;
	}
	
	/**
	 * 申请退回
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doDetectionSetBack")
	@ResponseBody
	public AjaxJson doDetectionSetBack(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String ogrCode = ResourceUtil.getSessionUser().getOrganization().getCode();
		String labCode = request.getParameter("labCode");
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			DetectionSetBackEntity dsbe = systemService.getEntity(DetectionSetBackEntity.class, id);
			dsbe.setCode(labCode);
			dsbe.setStatus(1);
			systemService.saveOrUpdate(dsbe);
		} else {
			if (StringUtils.isNotEmpty(labCode)) {
				// 添加时根据labCode判断在数据库里是否存在，若存在则更新(只有审核退回才能出现此此现象)
				DetectionSetBackEntity dsbe = null;
				//List<DetectionSetBackEntity> dsbeList  = systemService.findByProperty(DetectionSetBackEntity.class, "code", labCode);
				CriteriaQuery cq = new CriteriaQuery(DetectionSetBackEntity.class);
				cq.eq("code", labCode);
				cq.eq("ogrCode",ResourceUtil.getSessionUser().getOrganization().getCode());
				cq.add();
				List<DetectionSetBackEntity> dsbeList =  systemService.getListByCriteriaQuery(cq, false);
				if (dsbeList.size() > 0) {
					dsbe = dsbeList.get(0);
					dsbe.setStatus(1);
					systemService.saveOrUpdate(dsbe);
				} else {
					dsbe = new DetectionSetBackEntity();
					dsbe.setCode(labCode);
					dsbe.setStatus(1);
					dsbe.setOgrCode(ogrCode);
					systemService.save(dsbe);
				}
			}
		}
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 删除申请退回
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delSetBack")
	@ResponseBody
	public AjaxJson delSetBack(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		
		DetectionSetBackEntity dsbe = systemService.getEntity(DetectionSetBackEntity.class, id);
		systemService.delete(dsbe);
		
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 取得样品主要信息(退回时编辑样品信息用)
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getDetectionKeyInfo")
	@ResponseBody
	public AjaxJson getDetectionKeyInfo(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String labCode = request.getParameter("labCode");
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		Map<String, Object> selConditon = new HashMap<String, Object>();
		selConditon.put("ogrCode", ResourceUtil.getSessionUser().getOrganization().getCode());
		selConditon.put("labCode", labCode);
		
		List<Map<String, Object>> mList = systemService.findListByMyBatis(NAME_SPACE+"getDetectionKeyInfo", selConditon);
	
		if (mList.size() > 0) {
			attributesMap.put("projectCode", (String)mList.get(0).get("PROJECT_CODE"));
			attributesMap.put("labCode", (String)mList.get(0).get("LAB_CODE"));
			attributesMap.put("agrCode", (String)mList.get(0).get("AGR_CODE"));
		}
		j.setAttributes(attributesMap);
		return j;
	}
	
	/**
	 * 检测信息退回审核 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "detectionSetBackCheck")
	public ModelAndView detectionSetBackCheck(HttpServletRequest request) {

		return new ModelAndView("com/hippo/nky/detection/detectionSetBackCheck");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getDetectionSetBackCheck")
	public void getDetectionSetBackCheck(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = detectionService.getDetectionSetBackCheck(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 检测信息退回审核通过后台处理
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "detectionSetBackCheckPast")
	@ResponseBody
	public AjaxJson detectionSetBackCheckPast(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String idArr = request.getParameter("idArr");
		idArr = idArr.substring(0,idArr.length() - 1);
		String[] arr = idArr.split(",");		
		for (String id : arr) {
			DetectionSetBackEntity dsbe = systemService.getEntity(DetectionSetBackEntity.class, id);
			dsbe.setStatus(2);
			systemService.saveOrUpdate(dsbe);
		}
		return j;
	}
	
	/**
	 * 检测信息退回审核退回后台处理
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "detectionSetBackChecksetBack")
	@ResponseBody
	public AjaxJson detectionSetBackChecksetBack(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String idArr = request.getParameter("idArr");
		idArr = idArr.substring(0,idArr.length() - 1);
		String[] arr = idArr.split(",");		
		for (String id : arr) {
			DetectionSetBackEntity dsbe = systemService.getEntity(DetectionSetBackEntity.class, id);
			dsbe.setStatus(3);
			systemService.saveOrUpdate(dsbe);
		}
		return j;
	}
	
	/**
	 * 取得指定检测信息
	 * @return
	 */
	@RequestMapping(params = "getTargetSampleInfo")
	@ResponseBody
	public AjaxJson getTargetSampleInfo(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectCode", request.getParameter("projectCode"));
		paramMap.put("spCode", request.getParameter("spCode"));
		paramMap.put("labCode", request.getParameter("labCode"));
		paramMap.put("dCode", request.getParameter("dCode"));
		
		Map<String,Object> rMap = systemService.getObjectByMyBatis(NAME_SPACE+"getTargetSampleInfo", paramMap);
		if (rMap == null || rMap.size() <= 0) {
			j.setSuccess(false);
			return j;
		}
		
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		attributesMap.put("labCode", rMap.get("LABCODE"));
		attributesMap.put("agrCode", rMap.get("AGRCODE"));
		attributesMap.put("dCode", rMap.get("DCODE"));
		attributesMap.put("ogrId", rMap.get("OGRID"));
		attributesMap.put("sampleCode", rMap.get("SAMPLECODE"));
		
		j.setAttributes(attributesMap);
		
		return j;
	}
	
	/**
	 * 用户所在项目是否是牵头单位
	 * @return
	 */
	@RequestMapping(params = "isQtCheck")
	@ResponseBody
	public AjaxJson isQtCheck(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		OrganizationEntity org = ResourceUtil.getSessionUserName().getOrganization();

		CriteriaQuery cq = new CriteriaQuery(MonitoringProjectEntity.class);
		cq.eq("projectCode", request.getParameter("projectCode"));
		cq.eq("leadunit", org.getCode());
		cq.add();
		List<MonitoringProjectEntity> projectList =  systemService.getListByCriteriaQuery(cq, false); 
		
		if (projectList != null && projectList.size() > 0) {
			j.setSuccess(true);
		} else {
			j.setSuccess(false);
		}
		return j;
	}
}
