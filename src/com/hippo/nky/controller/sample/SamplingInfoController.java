package com.hippo.nky.controller.sample;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.pojo.base.TSBaseUser;
import jeecg.system.pojo.base.TSUser;
import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.detection.DetectionEntity;
import com.hippo.nky.entity.detection.NkyDetectionInformationEntity;
import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskEntity;
import com.hippo.nky.entity.monitoring.NkyMonitoringSiteEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.sample.BlindSampleEntity;
import com.hippo.nky.entity.sample.GeneralcheckEntity;
import com.hippo.nky.entity.sample.LivestockEntity;
import com.hippo.nky.entity.sample.NkyFreshMilkEntity;
import com.hippo.nky.entity.sample.NkyProjectCompleteEntity;
import com.hippo.nky.entity.sample.RoutinemonitoringEntity;
import com.hippo.nky.entity.sample.SamplingInfoEntity;
import com.hippo.nky.entity.sample.SamplingSetBackEntity;
import com.hippo.nky.entity.sample.SuperviseCheckEntity;
import com.hippo.nky.entity.sample.TwoDimensionEntity;
import com.hippo.nky.entity.system.SysAreaCodeEntity;
import com.hippo.nky.service.detection.DetectionServiceI;
import com.hippo.nky.service.sample.SamplingInfoServiceI;
import com.hippo.nky.service.standard.AgrCategoryServiceI;

/**   
 * @Title: Controller
 * @Description: 抽样信息录入
 * @author nky
 * @date 2013-11-05 14:15:03
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/samplingInfoController")
public class SamplingInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final String NAME_SPACE = "com.hippo.nky.entity.sample.SamplingInfoEntity.";

//	private static final Logger logger = Logger.getLogger(SamplingInfoController.class);

	@Autowired
	private SamplingInfoServiceI samplingInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AgrCategoryServiceI agrCategoryService; 
	@Autowired
	private DetectionServiceI detectionService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 抽样信息录入列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "barcodeInfoInput")
	public ModelAndView barcodeInfoInput(HttpServletRequest request) {
		//return new ModelAndView("com/hippo/nky/sample/aa");
		return new ModelAndView("com/hippo/nky/sample/barcodeInfoInputList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getDatagrid(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}

	/**
	 * 
	 * 删除抽样信息录入
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SamplingInfoEntity barcodeInfoInput, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		// 项目编码
		String projectCode = request.getParameter("projectCode");
		// 取得抽样单类型
		String sampleMonadType = samplingInfoService.getSampleMonadType(projectCode);
		// 例行监测情况下
		// 样品编码
		barcodeInfoInput = systemService.getEntity(SamplingInfoEntity.class, barcodeInfoInput.getId());
		String sampleCode = barcodeInfoInput.getSampleCode();
		
		if (StringUtils.equals(sampleMonadType, "1")) {
			List<RoutinemonitoringEntity> routinemonitoringList =  systemService.findByProperty(RoutinemonitoringEntity.class, "sampleCode", sampleCode);//例行监测
			if(routinemonitoringList != null && routinemonitoringList.size() > 0) {
				barcodeInfoInput.setRoutinemonitoringList(routinemonitoringList);//例行监测
			}
		} else if (StringUtils.equals(sampleMonadType, "2")) {
			List<SuperviseCheckEntity> superviseCheckList = systemService.findByProperty(SuperviseCheckEntity.class, "sampleCode", sampleCode);//监督抽查
			if (superviseCheckList != null && superviseCheckList.size() > 0) {
				barcodeInfoInput.setSuperviseCheckEntity(superviseCheckList.get(0));//监督抽查
			}
		} else if (StringUtils.equals(sampleMonadType, "3")) {
			List<GeneralcheckEntity> generalcheckList =  systemService.findByProperty(GeneralcheckEntity.class, "sampleCode", sampleCode);//风险
			if (generalcheckList != null && generalcheckList.size() > 0) {
				barcodeInfoInput.setGeneralcheckEntity(generalcheckList.get(0));//风险
			}
		} else if (StringUtils.equals(sampleMonadType, "4")) {
			List<NkyFreshMilkEntity> nkyFreshMilkList = systemService.findByProperty(NkyFreshMilkEntity.class, "sampleCode", sampleCode);//生鲜乳
			if (nkyFreshMilkList != null && nkyFreshMilkList.size() > 0) {//生鲜乳
				barcodeInfoInput.setNkyFreshMilkEntity(nkyFreshMilkList.get(0));
			}
		} else if (StringUtils.equals(sampleMonadType, "5")) {
			List<LivestockEntity> livestockList = systemService.findByProperty(LivestockEntity.class, "sampleCode", sampleCode);//畜禽
			if (livestockList != null && livestockList.size() > 0) {
				barcodeInfoInput.setLivestockEntityList(livestockList);//畜禽
			}
		}

		message = "抽样信息录入删除成功";
		samplingInfoService.delMain(barcodeInfoInput);

		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加抽样信息录入
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SamplingInfoEntity barcodeInfoInput, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String projectCode = barcodeInfoInput.getProjectCode();
		String pre_spcode = barcodeInfoInput.getPre_spcode();
		String input_spcode = barcodeInfoInput.getSpCode();
		// 取得抽样单类型
		String sampleMonadType = samplingInfoService.getSampleMonadType(projectCode);
		String vdcode = request.getParameter("vdcode");
		if (StringUtils.isNotEmpty(input_spcode) && (StringUtils.equals(sampleMonadType, "2") || StringUtils.equals(sampleMonadType, "3") || StringUtils.equals(sampleMonadType, "4"))) {
			barcodeInfoInput.setSpCode(pre_spcode + "-" + input_spcode);
		}
		if (StringUtil.isNotEmpty(barcodeInfoInput.getId())) {
			message = "抽样信息录入更新成功";
			SamplingInfoEntity t = null;
			if (StringUtils.equals(sampleMonadType, "2") || StringUtils.equals(sampleMonadType, "3")
					|| StringUtils.equals(sampleMonadType, "4")) {
			    t = samplingInfoService.get(SamplingInfoEntity.class, barcodeInfoInput.getId());
				t.setProjectCode(barcodeInfoInput.getProjectCode());
			}

			try {
				if (StringUtils.equals(sampleMonadType, "2") || StringUtils.equals(sampleMonadType, "3")
						|| StringUtils.equals(sampleMonadType, "4")) {
					MyBeanUtils.copyBeanNotNull2Bean(barcodeInfoInput, t);
					samplingInfoService.updateMain(t);
				} else {
					samplingInfoService.updateMain(barcodeInfoInput);
				}
				//barcodeInfoInputService.saveOrUpdate(t);
				if (StringUtils.isNotEmpty(vdcode)) { // 如果是退回，须更新抽样信息退回表的状态
					SamplingSetBackEntity ssbe = systemService.findByProperty(SamplingSetBackEntity.class, "code", vdcode).get(0);
					//ssbe.setStatus(4);
					systemService.delete(ssbe);
				}
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "抽样信息录入更新失败";
			}
		} else {
			
			message = "抽样信息录入添加成功";
			samplingInfoService.addMain(barcodeInfoInput);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

//	/**
//	 * 抽样信息录入列表页面跳转
//	 * 
//	 * @return
//	 */
//	@RequestMapping(params = "addorupdate")
//	public ModelAndView addorupdate(SamplingInfoEntity barcodeInfoInput, HttpServletRequest req) {
//		// 项目编码
//		String projectCode = req.getParameter("projectCode");
//		
//		CriteriaQuery cq = new CriteriaQuery(MonitoringTaskEntity.class);
//		cq.eq("projectCode", projectCode);
//		cq.eq("orgCode", getCurrentOrgCode());
//		cq.add();
//		List<MonitoringTaskEntity> monitoringTaskList = systemService.getListByCriteriaQuery(cq, false);
//		// 取得行业
//		String industry = samplingInfoService.getMonitorTypeAndIndustry(projectCode)[1];
//		// 取得抽样单类型
//		String sampleMonadType = samplingInfoService.getSampleMonadType(projectCode);
//		
//		if (StringUtil.isNotEmpty(barcodeInfoInput.getId())) {
//			barcodeInfoInput = samplingInfoService.getEntity(SamplingInfoEntity.class, barcodeInfoInput.getId());
//			barcodeInfoInput.setProjectCode(projectCode);
//			// 例行监测情况下
//			// 农产品编码
//			String agrCode = barcodeInfoInput.getAgrCode();
//			if (StringUtils.isNotEmpty(agrCode)) {
//				// 取得样品名称
//				String sampleName = samplingInfoService.getLastVersionAgrName(agrCode, projectCode);
//				req.setAttribute("sampleName", sampleName);
//			}
//
//			// 样品编码
//			String sampleCode = barcodeInfoInput.getSampleCode();
//			if (StringUtils.equals(sampleMonadType, "1")) {
//				List<RoutinemonitoringEntity> routinemonitoringList =  systemService.findByProperty(RoutinemonitoringEntity.class, "sampleCode", sampleCode);//例行监测
//				if(routinemonitoringList != null && routinemonitoringList.size() > 0) {
//					routinemonitoringList.get(0).setTaskCode(barcodeInfoInput.getTaskCode());
//					routinemonitoringList.get(0).setdCode(barcodeInfoInput.getdCode());
//					routinemonitoringList.get(0).setAgrCode(barcodeInfoInput.getAgrCode());
//					routinemonitoringList.get(0).setSamplingAddress(barcodeInfoInput.getSamplingAddress());
//					req.setAttribute("routinemonitoringList", routinemonitoringList);//例行监测
//				}
//			} else if (StringUtils.equals(sampleMonadType, "2")) {
//				List<SuperviseCheckEntity> superviseCheckList = systemService.findByProperty(SuperviseCheckEntity.class, "sampleCode", sampleCode);//监督抽查
//				if (superviseCheckList != null && superviseCheckList.size() > 0) {
//					barcodeInfoInput.setSuperviseCheckEntity(superviseCheckList.get(0));//监督抽查
//				}
//			} else if (StringUtils.equals(sampleMonadType, "3")) {
//				List<GeneralcheckEntity> generalcheckList =  systemService.findByProperty(GeneralcheckEntity.class, "sampleCode", sampleCode);//风险
//				if (generalcheckList != null && generalcheckList.size() > 0) {
//					barcodeInfoInput.setGeneralcheckEntity(generalcheckList.get(0));//风险
//				}
//			} else if (StringUtils.equals(sampleMonadType, "4")) {
//				List<NkyFreshMilkEntity> nkyFreshMilkList = systemService.findByProperty(NkyFreshMilkEntity.class, "sampleCode", sampleCode);//生鲜乳
//				if (nkyFreshMilkList != null && nkyFreshMilkList.size() > 0) {//生鲜乳
//					barcodeInfoInput.setNkyFreshMilkEntity(nkyFreshMilkList.get(0));
//				}
//			} else if (StringUtils.equals(sampleMonadType, "5")) {
//				List<LivestockEntity> livestockList = systemService.findByProperty(LivestockEntity.class, "sampleCode", sampleCode);//畜禽
//				if (livestockList != null && livestockList.size() > 0) {
//					livestockList.get(0).setTaskCode(barcodeInfoInput.getTaskCode());
//					livestockList.get(0).setdCode(barcodeInfoInput.getdCode());
//					livestockList.get(0).setSamplingAddress(barcodeInfoInput.getSamplingAddress());
//					req.setAttribute("livestockEntityList", livestockList);//畜禽
//				}
//			}
//		
//			String cityCode = barcodeInfoInput.getCityCode();
//			if(ConverterUtil.isNotEmpty(cityCode)){
//				// 取得行政区划
//				req.setAttribute("areacodeList2", samplingInfoService.getSysAreaForString(cityCode));
//			}
//		}
//		// 取得行政区划
//		req.setAttribute("areacodeList", samplingInfoService.getSysAreaForString("320000"));
//		// 抽样任务列表
//		req.setAttribute("monitoringTaskList", monitoringTaskList);
//		// 行业所对应的抽样环节
//		req.setAttribute("industryMonitoringLink", industry+"monitoringLink");
//		req.setAttribute("barcodeInfoInputPage", barcodeInfoInput);
//		// 取得页面受检单位自动完成数据
//		Map<String, String> autoDataMap = getAutoDataSource();
//		req.setAttribute("nameSource", autoDataMap.get("nameSource"));
//		req.setAttribute("codeNameSource", autoDataMap.get("codeNameSource"));
//		// 抽样单位信息
//		req.setAttribute("org", getSampingOrganization());
//		
//		String flg = req.getParameter("flg");
//		if (StringUtils.equals(flg, "show")) {
//			//根据不同的类型返回不同的监测类型抽样单页面
//			if (StringUtils.equals(sampleMonadType, "1")) {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow1"); //例行监测
//			} else if (StringUtils.equals(sampleMonadType, "2")) {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow2"); //监督抽查
//			} else if (StringUtils.equals(sampleMonadType, "3")) {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow3"); //风险
//			} else if (StringUtils.equals(sampleMonadType, "4")) {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow4"); //生鲜乳
//			} else {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow5"); //畜禽
//			}
//		} else {
//			//根据不同的类型返回不同的监测类型抽样单页面
//			if (StringUtils.equals(sampleMonadType, "1")) {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType1"); //例行监测
//			} else if (StringUtils.equals(sampleMonadType, "2")) {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType2"); //监督抽查
//			} else if (StringUtils.equals(sampleMonadType, "3")) {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType3"); //风险
//			} else if (StringUtils.equals(sampleMonadType, "4")) {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType4"); //生鲜乳
//			} else {
//				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType5"); //畜禽
//			}
//		}
//	}
	
	/**
	 * 抽样信息录入列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SamplingInfoEntity barcodeInfoInput, HttpServletRequest req) {
		// 项目编码
		String projectCode = req.getParameter("projectCode");
		String flg = req.getParameter("flg");
		String detecthedFlg = req.getParameter("detecthedFlg");
		boolean setBackFlg = Boolean.valueOf(req.getParameter("setBackFlg"));//是否为退回样品
		
		CriteriaQuery cq = new CriteriaQuery(MonitoringTaskEntity.class);
		cq.eq("projectCode", projectCode);
		cq.eq("orgCode", getCurrentOrgCode());
		cq.add();
		List<MonitoringTaskEntity> monitoringTaskList = systemService.getListByCriteriaQuery(cq, false);
		// 取得行业
		String industry = samplingInfoService.getMonitorTypeAndIndustry(projectCode)[1];
		// 取得抽样单类型
		String sampleMonadType = samplingInfoService.getSampleMonadType(projectCode);
		
		if (StringUtil.isNotEmpty(barcodeInfoInput.getId())) {
			barcodeInfoInput = samplingInfoService.getEntity(SamplingInfoEntity.class, barcodeInfoInput.getId());
			barcodeInfoInput.setProjectCode(projectCode);
			
			String samplingMonadId = "";
			List<SamplingInfoEntity> samplingInfoList = null;
			// 取得页面各列表农产品列表
			List<String> sampleNameList = new ArrayList<String>();
			String sampleName = "";
			if (StringUtils.equals(sampleMonadType, "1") || StringUtils.equals(sampleMonadType, "5")) {
				// 取得抽样单ID
				samplingMonadId = barcodeInfoInput.getSamplingMonadId();
				// 取所有主表信息（包含部分存在主表并要显示在附表的信息）
			    cq = new CriteriaQuery(SamplingInfoEntity.class);
				cq.eq("samplingMonadId", samplingMonadId);
				//cq.eq("sampleStatus", "1");
				cq.notEq("sampleStatus", "2");
				cq.addOrder("sampleCode", SortDirection.asc);
				cq.add();
				samplingInfoList = systemService.getListByCriteriaQuery(cq, false);
				for (SamplingInfoEntity one : samplingInfoList) {
					sampleName = samplingInfoService.getLastVersionAgrName(one.getAgrCode(), projectCode);
					sampleNameList.add(sampleName);
				}
			}

			// 样品编码
			String sampleCode = barcodeInfoInput.getSampleCode();
			String spCode = barcodeInfoInput.getSpCode();
			if (StringUtils.equals(sampleMonadType, "1")) {
				if (StringUtils.equals(flg, "show")) {
					sampleName = samplingInfoService.getLastVersionAgrName(barcodeInfoInput.getAgrCode(), projectCode);
					req.setAttribute("sampleName", sampleName);
					List<RoutinemonitoringEntity> routinemonitoringList =  systemService.findByProperty(RoutinemonitoringEntity.class, "sampleCode", sampleCode);//例行监测
					if(routinemonitoringList != null && routinemonitoringList.size() > 0) {
						routinemonitoringList.get(0).setTaskCode(barcodeInfoInput.getTaskCode());
						routinemonitoringList.get(0).setdCode(barcodeInfoInput.getdCode());
						routinemonitoringList.get(0).setAgrCode(barcodeInfoInput.getAgrCode());
						routinemonitoringList.get(0).setSamplingAddress(barcodeInfoInput.getSamplingAddress());
						routinemonitoringList.get(0).setRemark(barcodeInfoInput.getRemark());
						routinemonitoringList.get(0).setSamplePath(barcodeInfoInput.getSamplePath());
						if (StringUtils.isNotEmpty(spCode)) {
							routinemonitoringList.get(0).setSpCode(spCode.substring(spCode.lastIndexOf("-")+1));
						}
						req.setAttribute("routinemonitoringList", routinemonitoringList);//例行监测
					}
				} else {
				    cq = new CriteriaQuery(RoutinemonitoringEntity.class);
					cq.eq("samplingMonadId", samplingMonadId);
					cq.addOrder("sampleCode", SortDirection.asc);
					cq.add();
					List<RoutinemonitoringEntity> routinemonitoringList =  systemService.getListByCriteriaQuery(cq, false);//例行监测
					// 排除废样
					setRouList(samplingInfoList, routinemonitoringList);
					for (int i=0;i<routinemonitoringList.size();i++) {
						String ispCode = samplingInfoList.get(i).getSpCode();
						routinemonitoringList.get(i).setTaskCode(samplingInfoList.get(i).getTaskCode());
						routinemonitoringList.get(i).setdCode(samplingInfoList.get(i).getdCode());
						routinemonitoringList.get(i).setAgrCode(samplingInfoList.get(i).getAgrCode());
						routinemonitoringList.get(i).setSamplingAddress(samplingInfoList.get(i).getSamplingAddress());
						routinemonitoringList.get(i).setRemark(samplingInfoList.get(i).getRemark());
						routinemonitoringList.get(i).setSampleName(sampleNameList.get(i));
						routinemonitoringList.get(i).setSamplePath(samplingInfoList.get(i).getSamplePath());
						if (StringUtils.isNotEmpty(ispCode)) {
							routinemonitoringList.get(i).setSpCode(ispCode.substring(ispCode.lastIndexOf("-")+1));
						}
						req.setAttribute("routinemonitoringList", routinemonitoringList);//例行监测
					}
				}
			} else if (StringUtils.equals(sampleMonadType, "2")) {
				sampleName = samplingInfoService.getLastVersionAgrName(barcodeInfoInput.getAgrCode(), projectCode);
				req.setAttribute("sampleName", sampleName);
				List<SuperviseCheckEntity> superviseCheckList = systemService.findByProperty(SuperviseCheckEntity.class, "sampleCode", sampleCode);//监督抽查
				if (superviseCheckList != null && superviseCheckList.size() > 0) {
					barcodeInfoInput.setSuperviseCheckEntity(superviseCheckList.get(0));//监督抽查
				}
			} else if (StringUtils.equals(sampleMonadType, "3")) {
				sampleName = samplingInfoService.getLastVersionAgrName(barcodeInfoInput.getAgrCode(), projectCode);
				req.setAttribute("sampleName", sampleName);
				List<GeneralcheckEntity> generalcheckList =  systemService.findByProperty(GeneralcheckEntity.class, "sampleCode", sampleCode);//风险
				if (generalcheckList != null && generalcheckList.size() > 0) {
					barcodeInfoInput.setGeneralcheckEntity(generalcheckList.get(0));//风险
				}
			} else if (StringUtils.equals(sampleMonadType, "4")) {
				sampleName = samplingInfoService.getLastVersionAgrName(barcodeInfoInput.getAgrCode(), projectCode);
				req.setAttribute("sampleName", sampleName);

				List<NkyFreshMilkEntity> nkyFreshMilkList = systemService.findByProperty(NkyFreshMilkEntity.class, "sampleCode", sampleCode);//生鲜乳
				if (nkyFreshMilkList != null && nkyFreshMilkList.size() > 0) {//生鲜乳
					barcodeInfoInput.setNkyFreshMilkEntity(nkyFreshMilkList.get(0));
				}
			} else if (StringUtils.equals(sampleMonadType, "5")) {
				sampleName = samplingInfoService.getLastVersionAgrName(barcodeInfoInput.getAgrCode(), projectCode);
				req.setAttribute("sampleName", sampleName);
				if (StringUtils.equals(flg, "show")) {
					List<LivestockEntity> livestockList =  systemService.findByProperty(LivestockEntity.class, "sampleCode", sampleCode);//例行监测
					if(livestockList != null && livestockList.size() > 0) {
						livestockList.get(0).setTaskCode(barcodeInfoInput.getTaskCode());
						livestockList.get(0).setdCode(barcodeInfoInput.getdCode());
						livestockList.get(0).setSamplingAddress(barcodeInfoInput.getSamplingAddress());
						livestockList.get(0).setRemark(barcodeInfoInput.getRemark());
						livestockList.get(0).setSamplePath(barcodeInfoInput.getSamplePath());
						if (StringUtils.isNotEmpty(spCode)) {
							livestockList.get(0).setSpCode(spCode.substring(spCode.lastIndexOf("-")+1));
						}
						req.setAttribute("livestockEntityList", livestockList);
					}
				} else {
				    cq = new CriteriaQuery(LivestockEntity.class);
					cq.eq("samplingMonadId", samplingMonadId);
					cq.addOrder("sampleCode", SortDirection.asc);
					cq.add();
					List<LivestockEntity> livestockList =  systemService.getListByCriteriaQuery(cq, false);
					// 排除废样
					setLivList(samplingInfoList, livestockList);
					for (int i=0;i<livestockList.size();i++) {
						String ispCode = samplingInfoList.get(i).getSpCode();
						livestockList.get(i).setTaskCode(samplingInfoList.get(i).getTaskCode());
						livestockList.get(i).setdCode(samplingInfoList.get(i).getdCode());
						livestockList.get(i).setSamplingAddress(samplingInfoList.get(i).getSamplingAddress());
						livestockList.get(i).setRemark(samplingInfoList.get(i).getRemark());
						livestockList.get(i).setSamplePath(samplingInfoList.get(i).getSamplePath());
						if (StringUtils.isNotEmpty(ispCode)) {
							livestockList.get(i).setSpCode(ispCode.substring(ispCode.lastIndexOf("-")+1));
						}
						req.setAttribute("livestockEntityList", livestockList);
					}
				}
			}
		
			String cityCode = barcodeInfoInput.getCityCode();
			if(ConverterUtil.isNotEmpty(cityCode)){
				// 取得行政区划
				req.setAttribute("areacodeList2", samplingInfoService.getSysAreaForString(cityCode));
			}
			String countyCode = barcodeInfoInput.getCountyCode();
			// 设置制氧编码前缀
			req.setAttribute("pre_spcode", this.getSysAreaSelfCode(countyCode));
		}
		if (StringUtils.equals(flg, "show")) {//非本单位查看时,任务没显示出来
//			if (monitoringTaskList == null || monitoringTaskList.size() == 0) {
				monitoringTaskList = new ArrayList<MonitoringTaskEntity>();
				monitoringTaskList.add((MonitoringTaskEntity)(systemService.findByProperty(MonitoringTaskEntity.class, "taskcode", barcodeInfoInput.getTaskCode()).get(0)));
//			}
		}
		// 取得行政区划
		req.setAttribute("areacodeList", samplingInfoService.getSysAreaForString("320000"));
		// 抽样任务列表
		req.setAttribute("monitoringTaskList", monitoringTaskList);
		// 行业所对应的抽样环节
		req.setAttribute("industryMonitoringLink", industry+"monitoringLink");
		String showSpcode = barcodeInfoInput.getSpCode();
		if (StringUtil.isNotEmpty(showSpcode)) {
			barcodeInfoInput.setShow_spcode(showSpcode.substring(showSpcode.lastIndexOf("-")+1));
		}
		req.setAttribute("barcodeInfoInputPage", barcodeInfoInput);
//		// 取得页面受检单位自动完成数据
//		Map<String, String> autoDataMap = getAutoDataSource();
//		req.setAttribute("nameSource", autoDataMap.get("nameSource"));
//		req.setAttribute("codeNameSource", autoDataMap.get("codeNameSource"));
		// 抽样单位信息
		req.setAttribute("org", getSampingOrganization());
		// 是否为退回
		req.setAttribute("setBackFlg", setBackFlg);
		// 如果为退回或编辑，则须记录具体条码，使其页面上可编辑
		//req.setAttribute("vdcode", req.getParameter("vdcode"));
		req.setAttribute("vdcode", barcodeInfoInput.getdCode());
		req.setAttribute("detecthedFlg", detecthedFlg);
		
		// 取得项目抽样品种
		List<MonitoringBreedEntity> breedList = samplingInfoService.findListByMyBatis(NAME_SPACE+"getProjectBreed", projectCode);
		req.setAttribute("breedList", breedList);
		req.setAttribute("jsonBreedList", setBreedList(breedList));
		
		req.setAttribute("imagePath", "/" + ResourceUtil.getConfigByName("uploadpath")+"/images");

		
		if (StringUtils.equals(flg, "show")) {
			//根据不同的类型返回不同的监测类型抽样单页面
			if (StringUtils.equals(sampleMonadType, "1")) {
				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow1"); //例行监测
			} else if (StringUtils.equals(sampleMonadType, "2")) {
				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow2"); //监督抽查
			} else if (StringUtils.equals(sampleMonadType, "3")) {
				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow3"); //风险
			} else if (StringUtils.equals(sampleMonadType, "4")) {
				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow4"); //生鲜乳
			} else {
				return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_forshow5"); //畜禽
			}
		} else {
			String isPrint = req.getParameter("isprint");
			if(isPrint != null && "true".equals(isPrint)){
				//根据不同的类型返回不同的监测类型抽样单页面
				if (StringUtils.equals(sampleMonadType, "1")) {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_printType1"); //例行监测
				} else if (StringUtils.equals(sampleMonadType, "2")) {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_printType2"); //监督抽查
				} else if (StringUtils.equals(sampleMonadType, "3")) {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_printType3"); //风险
				} else if (StringUtils.equals(sampleMonadType, "4")) {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_printType4"); //生鲜乳
				} else {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_printType5"); //畜禽
				}
			}else{
				//根据不同的类型返回不同的监测类型抽样单页面
				if (StringUtils.equals(sampleMonadType, "1")) {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType1"); //例行监测
				} else if (StringUtils.equals(sampleMonadType, "2")) {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType2"); //监督抽查
				} else if (StringUtils.equals(sampleMonadType, "3")) {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType3"); //风险
				} else if (StringUtils.equals(sampleMonadType, "4")) {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType4"); //生鲜乳
				} else {
					return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_monType5"); //畜禽
				}
			}
		}
	}
	
	private String setBreedList(List<MonitoringBreedEntity> breedList){
		String str= "[";
		int i = 0;
		for (MonitoringBreedEntity breed : breedList) {
			str += "{id:'" + breed.getAgrCode() + "',text:'" + breed.getAgrName() + "'}";
			if (i < breedList.size() -1 ) {
				str += ",";
			}
			i++;
		}
		str +="]";
		return str;
	}
	
	/**
	 * 取得当前用户质检机构code
	 * @return
	 */
	private String getCurrentOrgCode () {
		String departid = ResourceUtil.getSessionUserName().getTSDepart().getId();
		// 取得用户类型
		String userType = systemService.findByProperty(TSBaseUser.class, "TSDepart.id", departid).get(0).getUsertype();
		if (StringUtil.equals(userType, "1")) {
			OrganizationEntity org = systemService.getEntity(OrganizationEntity.class, departid);
			return org.getCode();
		}
		return "";
	}
	
	/**
	 * 取得页面受检单位自动完成数据
	 * @return
	 */
	private Map<String, String> getAutoDataSource(){
		Map<String, String> resultMap = new HashMap<String, String>();
		StringBuffer nameSourceBuf = new StringBuffer("[");
		StringBuffer codeNameSourceBuf = new StringBuffer("{");
		CriteriaQuery cq = new CriteriaQuery(NkyMonitoringSiteEntity.class);
		List<NkyMonitoringSiteEntity> list = systemService.getListByCriteriaQuery(cq, false);
		for (NkyMonitoringSiteEntity entity : list) {
			String code = entity.getCode();
			String name = entity.getName();
			if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(name)) {
				nameSourceBuf.append("&quot;");
				nameSourceBuf.append(name);
				nameSourceBuf.append("&quot;");
				nameSourceBuf.append(",");
				
				codeNameSourceBuf.append("&quot;");
				codeNameSourceBuf.append(name);
				codeNameSourceBuf.append("&quot;");
				codeNameSourceBuf.append(":");
				codeNameSourceBuf.append("&quot;");
				codeNameSourceBuf.append(code);
				codeNameSourceBuf.append("&quot;");
				codeNameSourceBuf.append(",");
			}
		}
		String nameSource = nameSourceBuf.toString();
		String codeNameSource = codeNameSourceBuf.toString();
		resultMap.put("nameSource", nameSource.substring(0, nameSource.length() - 1) + "]");
		resultMap.put("codeNameSource", codeNameSource.substring(0, codeNameSource.length() - 1) + "}");
		
		return resultMap;
	}
	
	/**
	 * 取得抽样单位信息
	 * @return
	 */
	private OrganizationEntity getSampingOrganization () {
		OrganizationEntity org = systemService.getEntity(
				OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		return org;
	}
	
	/**
	 * 排除废样(例行检测抽样单)
	 */
	private void setRouList(List<SamplingInfoEntity> samplingInfoList, List<RoutinemonitoringEntity> routinemonitoringList){
		if (routinemonitoringList.size() > samplingInfoList.size()) {
			List<String> sampleCodeList = new ArrayList<String>();
			for (SamplingInfoEntity sample : samplingInfoList) {
				sampleCodeList.add(sample.getSampleCode());
			}
			List<RoutinemonitoringEntity> removeList = new ArrayList<RoutinemonitoringEntity>();
			for (RoutinemonitoringEntity rou : routinemonitoringList) {
				if (!sampleCodeList.contains(rou.getSampleCode())) {
					removeList.add(rou);
				}
			}
			routinemonitoringList.removeAll(removeList);
		}
	}
	
	/**
	 * 排除废样（畜禽抽样单）
	 */
	private void setLivList(List<SamplingInfoEntity> samplingInfoList, List<LivestockEntity> liveList){
		if (liveList.size() > samplingInfoList.size()) {
			List<String> sampleCodeList = new ArrayList<String>();
			for (SamplingInfoEntity sample : samplingInfoList) {
				sampleCodeList.add(sample.getSampleCode());
			}
			List<LivestockEntity> removeList = new ArrayList<LivestockEntity>();
			for (LivestockEntity livestock : liveList) {
				if (!sampleCodeList.contains(livestock.getSampleCode())) {
					removeList.add(livestock);
				}
			}
			liveList.removeAll(removeList);
		}
	}
	
	/**
	 * 抽样信息录入列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "selprogram")
	public ModelAndView selprogram(SamplingInfoEntity barcodeInfoInput, HttpServletRequest req) {

		return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_selProgram");
	}
	
	/**
	 * 抽样信息录入列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "selsample")
	public ModelAndView selsample(SamplingInfoEntity barcodeInfoInput, HttpServletRequest req) {
		// 加载最新版农产品信息(树状结构)
//		List<StandardVersionEntity> agrVersionList = 
//				samplingInfoService.findHql("from StandardVersionEntity where category = ? and publishmark = 1 and stopflag = 0 order by begindate desc ", 0);
//		String data = "{}";
//		if(agrVersionList != null && agrVersionList.size() > 0){
//			data = agrCategoryService.agrCategoryTreeData(agrVersionList.get(0).getId());
//		}
		// 查询农产品版本号
		String data = "{}";
		Object agrVersionId = samplingInfoService.getObjectByMyBatis(NAME_SPACE+"getAgrVersionId", barcodeInfoInput.getProjectCode());
		if(ConverterUtil.isNotEmpty(agrVersionId)){
			data = agrCategoryService.agrCategoryTreeData(ConverterUtil.toString(agrVersionId));
		}
		req.setAttribute("zTreeData", data);
		return new ModelAndView("com/hippo/nky/sample/barcodeInfoInput_selSample");
	}
	

	public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter pw=response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 废样信息录入列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toObsolete")
	public ModelAndView toObsolete(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/sample/obsoleteList");
	}
	
	/**
	 * 废样信息录入列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "obsoleteList")
	public void obsoleteList(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
		barcodeInfoInput.setProjectCode("FEIYANGDATA");
		JSONObject jObject = samplingInfoService.getDatagrid(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 废样信息录入列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toAddObsolete")
	public ModelAndView toAddObsolete(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/sample/obsolete");
	}
	
	
	/**
	 * 制样编码管理页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sampleMakeCode")
	public ModelAndView sampleMakeCode(SamplingInfoEntity barcodeInfoInput, HttpServletRequest req){
		req.setAttribute("cityCodeList", samplingInfoService.getCityAndCountryList(barcodeInfoInput, 1));
		return new ModelAndView("com/hippo/nky/sample/sample_makeCode");
	}
	
	/**
	 * 设置制样编码
	 * @param barcodeInfoInput
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getCityOrCountryCodeList")
	@ResponseBody
	public AjaxJson getCityOrCountryCodeList(HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		String isCity = req.getParameter("isCity");
		String projectCode = req.getParameter("projectCode");
		String cityCode = req.getParameter("cityCode");
		SamplingInfoEntity siEntity = new SamplingInfoEntity();
		siEntity.setProjectCode(projectCode);
		siEntity.setCityCode(cityCode);
		
		String codeStr = "";
		if(ConverterUtil.toBoolean(isCity)){
			codeStr = samplingInfoService.getCityAndCountryList(siEntity, 1);
		} else {
			codeStr = samplingInfoService.getCityAndCountryList(siEntity, 0);
		}
		
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		attributesMap.put("codeList", ConverterUtil.formatOptions(codeStr, null));
		
		j.setAttributes(attributesMap);
		
		j.setSuccess(true);
		
		return j;
	}
	
	/**
	 * 取项目的抽检分离flg
	 * @param samplingInfoEntity
	 */
	@RequestMapping(params = "getDetecthedFlg")
	@ResponseBody
	public AjaxJson getDetecthedFlg(String projectCode) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		String flag = samplingInfoService.getDetecthedFlg(projectCode);
		attributesMap.put("detecthedFlg", flag);
		j.setAttributes(attributesMap);
		return j;
	}
	
	/**
	 * AJAX请求数据
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "sampleMakCodeDatagrid")
	public void sampleMakCodeDatagrid(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSampleMakeCodeDatagrid(barcodeInfoInput, dataGrid,null);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 取得制样编码的按钮是否可用
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getMakeCodeButtonEnable")
	@ResponseBody
	public AjaxJson getMakeCodeButtonEnable(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		boolean isEnable = samplingInfoService.getMakeCodeButtonEnable(request.getParameter("projectCode"));
		attributesMap.put("makeCodeEnable", isEnable);
		j.setAttributes(attributesMap);
		j.setSuccess(isEnable);
		return j;
	}

	/**
	 * 设置制样编码
	 * 
	 * @param barcodeInfoInput
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "makeSpCode")
	@ResponseBody
	public AjaxJson makeSpCode(SamplingInfoEntity barcodeInfoInput, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		// 设置制样编码
		samplingInfoService.setSpCode(barcodeInfoInput.getProjectCode());
		
		j.setSuccess(true);
		j.setMsg("制样编码成功");
		return j;
	}
	
	/**
	 * 取得省市县的项目列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getProjects")
	@ResponseBody
	public AjaxJson getProjects(HttpServletRequest request) {

		AjaxJson j = new AjaxJson();
		String monitorType = request.getParameter("monitorType");
		String year = request.getParameter("year");
		String proDefVal = request.getParameter("proDefVal");
		String citDefVal = request.getParameter("citDefVal");
		String areDefVal = request.getParameter("areDefVal");
		String showBelowGradeRepFlg = request.getParameter("showBelowGradeRepFlg");//是否查看下级上报的项目FLG

		Map<String, Object> attributesMap = new HashMap<String, Object>();
		TSUser user = ResourceUtil.getSessionUserName();
		List<MonitoringProjectEntity> projectList = samplingInfoService.getUserMonintorProgaram(user, monitorType, year, showBelowGradeRepFlg);
		List<MonitoringProjectEntity> proTaskList = new ArrayList<MonitoringProjectEntity>();
		List<MonitoringProjectEntity> cityTaskList = new ArrayList<MonitoringProjectEntity>();
		List<MonitoringProjectEntity> areaTaskList = new ArrayList<MonitoringProjectEntity>();
		for (MonitoringProjectEntity mp : projectList) {
			String plevel = mp.getPlevel();
			// 设置省级任务
			if ("1".equals(plevel)) {
				proTaskList.add(mp);
			} else if ("2".equals(plevel)) {
				cityTaskList.add(mp);
			} else {
				areaTaskList.add(mp);
			}
		}
		attributesMap.put("proTaskList", proTaskList);
		attributesMap.put("cityTaskList", cityTaskList);
		attributesMap.put("areaTaskList", areaTaskList);
		// 取得默认选中项
		attributesMap.put("proDefVal", proDefVal);
		attributesMap.put("citDefVal", citDefVal);
		attributesMap.put("areDefVal", areDefVal);
		j.setSuccess(true);
		j.setAttributes(attributesMap);
		return j;
	}
	
	/**
	 * 废样信息 搜索
	 * 
	 * @return
	 */
	@RequestMapping(params = "obsoleteSearch")
	@ResponseBody
	public AjaxJson obsoleteSearch(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String dcode = oConvertUtils.getString(request.getParameter("dcode"));
		Object obj = null;
		if(StringUtil.isNotEmpty(dcode)){
			obj = samplingInfoService.findObsoleteByCode(dcode,ResourceUtil.getSessionUser().getOrganization().getCode());
			request.setAttribute("obj", obj);
		}
		
		if(obj != null){
			message = "样品信息搜索成功";
			j.setObj(obj);
			j.setMsg(message);
		}else{
			message = "无此样品信息";
			j.setSuccess(false);
			j.setMsg(message);
		}
		return j;
	}
	
	/**
	 * 废样信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "obsoleteUpdate")
	@ResponseBody
	public AjaxJson obsoleteUpdate(SamplingInfoEntity samplingInfoEntity,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if(StringUtil.isNotEmpty(samplingInfoEntity.getId())){
			SamplingInfoEntity t = samplingInfoService.get(SamplingInfoEntity.class, samplingInfoEntity.getId());
			try {
				message = "废样信息成功";
				t.setSampleStatus("2");
				samplingInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "废样信息更新失败";
			}
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "checkcode")
	@ResponseBody
	public AjaxJson checkcode(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String dCode = request.getParameter("param");
		String projectCode = request.getParameter("projectCode");
		// 验证条码为系统生成过的条码
		CriteriaQuery cq = new CriteriaQuery(TwoDimensionEntity.class);
		cq.eq("title", dCode);
		cq.eq("projectCode", projectCode);
		cq.add();
		List<TwoDimensionEntity> twoCode = systemService.getListByCriteriaQuery(cq, false);
		//List<TwoDimensionEntity> twoCode = systemService.findByProperty(TwoDimensionEntity.class, "title", dCode);
		if (twoCode.size() == 0) {
			j.setSuccess(false);
			message = "该条码不是系统生成的条码,请核实!";
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			j.setMsg(message);
		} else {
			int count = this.samplingInfoService.checkCode(dCode);
			if (count != 0) {
				j.setSuccess(false);
				message = "该条码已经使用,请核实!";
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				j.setMsg(message);
			}
		}
		return j;
	}
	
	@RequestMapping(params = "checkSpCode")
	@ResponseBody
	public AjaxJson checkSpCode(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String spCode = request.getParameter("param");
		String projectCode = request.getParameter("projectCode");
		String countyCode = request.getParameter("countyCode");
		if (countyCode == null) {
			j.setSuccess(false);
			message = "请选择抽样地区!";
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			j.setMsg(message);
			return j;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("spCode", this.getSysAreaSelfCode(countyCode)+ "-" +spCode);
		paramMap.put("projectCode", projectCode);
		paramMap.put("countyCode", countyCode);
		Integer count = samplingInfoService.getObjectByMyBatis(NAME_SPACE+"checkUniqueSpcode", paramMap);
		if (count > 0) {
			j.setSuccess(false);
			message = "该制样编码已经使用,请核实!";
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			j.setMsg(message);
		} 
		return j;
	}

	/**
	 * 信息上报
	 * @param samplingInfoEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "sampleReport")
	public ModelAndView sampleReport(SamplingInfoEntity samplingInfoEntity, HttpServletRequest req){
		return new ModelAndView("com/hippo/nky/sample/sample_reporting");
	}
	
	
	/**
	 * 抽样信息可上报列表
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "sampleReportDatagrid")
	public void sampleReportDatagrid(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String projectCode = barcodeInfoInput.getProjectCode();
		if (StringUtils.isNotEmpty(projectCode)) {
			barcodeInfoInput.setProjectCode(projectCode.substring(1));
		}
		JSONObject jObject = samplingInfoService.getDatagrid(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 抽样信息汇总列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "samplecollect")
	public ModelAndView samplecollectList(SamplingInfoEntity barcodeInfoInput, HttpServletRequest request) {
		request.setAttribute("cityCodeList", samplingInfoService.getCityAndCountryList(barcodeInfoInput, 1));
		//取得当前年
		String currYear = ConverterUtil.getCurrentTime("yyyy");
		//取得前后5年，年度列表
		List<String> yearList = ConverterUtil.getYearList(10);
		request.setAttribute("currYear", currYear);
		request.setAttribute("yearList", yearList);
		request.setAttribute("samplingOrgCode", this.getCurrentOrgCode());
		return new ModelAndView("com/hippo/nky/sample/sample_collect");
	}
	
	/**
	 * 抽样信息可上报列表
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "sampleCollectDatagrid")
	public void sampleCollectDatagrid(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSampleCollectDatagrid(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 抽检分离的制样编码是否为空的验证
	 * @param samplingInfoEntity
	 */
	@RequestMapping(params = "checkSampleDetached")
	@ResponseBody
	public AjaxJson checkSampleDetached(SamplingInfoEntity samplingInfoEntity) {
		AjaxJson j = new AjaxJson();
		int flag = samplingInfoService.checkSampleDetached(samplingInfoEntity);
		if(flag == 0){
			j.setSuccess(false);
		}
		return j;
	}
	
	/**
	 * 抽样信息可上报数量
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "sampleReportCount")
	@ResponseBody
	public AjaxJson sampleReportCount(SamplingInfoEntity samplingInfoEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		AjaxJson j = new AjaxJson();
		samplingInfoEntity.setOrgCode(ResourceUtil.getSessionUser().getOrganization().getCode());
		String tmpSampIdArr = request.getParameter("sampleIdArr");
		String sampleIdList = "";
		if (StringUtil.isNotEmpty(tmpSampIdArr)) {
			String sampleIdArr = tmpSampIdArr.substring(0,tmpSampIdArr.length() - 1);
			String[] arr = sampleIdArr.split(",");
			for ( int i = 0 ; i < arr.length; i++) {
				sampleIdList += "'" +arr[i]+ "',";
			}
			sampleIdList = sampleIdList.substring(0, sampleIdList.length() - 1);
		}
		Map<String,Integer> map = samplingInfoService.sampleReportCount(samplingInfoEntity, sampleIdList);
		j.setObj(map);
		return j;
	}

	/**
	 * 去上报数量
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "toReport")
	@ResponseBody
	public AjaxJson toReport(SamplingInfoEntity samplingInfoEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		AjaxJson j = new AjaxJson();
		int boolInt = 0;
		samplingInfoEntity.setOrgCode(ResourceUtil.getSessionUser().getOrganization().getCode());
		String tmpSampIdArr = request.getParameter("sampleIdArr");
		String sampleIdList = "";
		if (StringUtil.isNotEmpty(tmpSampIdArr)) {
			String sampleIdArr = tmpSampIdArr.substring(0,tmpSampIdArr.length() - 1);
			String[] arr = sampleIdArr.split(",");
			for ( int i = 0 ; i < arr.length; i++) {
				sampleIdList += "'" +arr[i]+ "',";
			}
			sampleIdList = sampleIdList.substring(0, sampleIdList.length() - 1);
			
			boolInt = samplingInfoService.toReport(samplingInfoEntity, sampleIdList);
		} else {
			boolInt = samplingInfoService.toReport(samplingInfoEntity, null);
		}
		if(boolInt != 0){
			message = "上报抽样信息成功!";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			j.setMsg(message);
		}else{
			message = "上报抽样信息失败!";
			j.setMsg(message);
		}
		return j;
	}

	/**
	 * 抽样完成情况统计列表
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "toStatistics")
	public ModelAndView toStatistics(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
		//取得当前年
		String currYear = ConverterUtil.getCurrentTime("yyyy");
		//取得前后5年，年度列表
		List<String> yearList = ConverterUtil.getYearList(10);
		request.setAttribute("currYear", currYear);
		request.setAttribute("yearList", yearList);
		return new ModelAndView("com/hippo/nky/sample/sample_inquire");
	}
	
	/**
	 * 抽样完成情况统计
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "statisticsDataGrid")
	public void statisticsDataGrid(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		barcodeInfoInput.setOrgCode(ResourceUtil.getSessionUser().getOrganization().getCode());
		JSONObject jObject = samplingInfoService.findForStatistics(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 质检机构下的抽样列表 跳转
	 * @param samplingInfoEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "toBarcodeInput")
	public ModelAndView toBarcodeInput(SamplingInfoEntity samplingInfoEntity, HttpServletRequest req){
		req.setAttribute("cityCodeList", samplingInfoService.getCityAndCountryList(samplingInfoEntity, 1));
		req.setAttribute("projectCode", samplingInfoEntity.getProjectCode());
		req.setAttribute("orgCode", samplingInfoEntity.getOrgCode());
		return new ModelAndView("com/hippo/nky/sample/sample_inquire_detailList");
	}
	
	/**
	 * 质检机构下的抽样列表
	 * @param barcodeInfoInput
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "barcodeInputList")
	public void barcodeInputList(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getDatagrid(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 抽样详情
	 * @param samplingInfoEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "barcodeInputDetail")
	public ModelAndView barcodeInputDetail(SamplingInfoEntity samplingInfoEntity, HttpServletRequest req){
		SamplingInfoEntity infoEntity = systemService.get(SamplingInfoEntity.class, samplingInfoEntity.getId());
		req.setAttribute("infoEntity", infoEntity);
		return new ModelAndView("com/hippo/nky/sample/sample_inquire_detail");
	}
	
	/**
	 * 样品分发列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "sample_distribute")
	public ModelAndView sampleDistribute(MonitoringProjectEntity monitoringProjectEntity, HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/sample/sample_distribution");
	}
	
	/**
	 * 样品分发数据取得
	 */
	@RequestMapping(params = "sampleDistributeDataGrid")
	public void sampleDistributeDataGrid(MonitoringProjectEntity monitoringProjectEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSampleDistributeDataGrid(monitoringProjectEntity, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 样品分发整包分发列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "packingdistribute")
	public ModelAndView packingDistribute(MonitoringProjectEntity monitoringProjectEntity, HttpServletRequest request) {
		String projectCode = request.getParameter("projectCode");
		request.setAttribute("projectCode", projectCode);
		return new ModelAndView("com/hippo/nky/sample/packingDistribute");
	}
	
	/**
	 * 样品分发整包分发数据取得
	 */
	@RequestMapping(params = "packingDistributeDataGrid")
	public void packingDistributeDataGrid(MonitoringProjectEntity monitoringProjectEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getPackingDistributeDataGrid(monitoringProjectEntity, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 样品分发拆包分发列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "unpackingdistribute")
	public ModelAndView unpackingDistribute(MonitoringProjectEntity monitoringProjectEntity, HttpServletRequest request) {
		String projectCode = request.getParameter("projectCode");
		request.setAttribute("projectCode", projectCode);
		return new ModelAndView("com/hippo/nky/sample/unpackingDistribute");
	}
	
	/**
	 * 样品分发拆包分发数据取得
	 */
	@RequestMapping(params = "unpackingDistributeDataGrid")
	public void unpackingDistributeDataGrid(MonitoringProjectEntity monitoringProjectEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getUnpackingDistributeDataGrid(monitoringProjectEntity, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 质检中心列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "samplingorg")
	public ModelAndView samplingorg(HttpServletRequest request) {
		String flg = request.getParameter("flg");
		// 抽样单位
		String orgCode = request.getParameter("orgCode");
		// 项目code
		String projectCode = request.getParameter("projectCode");
		// 城市代码
		String cityCode = request.getParameter("cityCode");
		// 区县代码
		String countyCode = request.getParameter("countyCode");

		if (StringUtils.equals(flg, "packing")){
			request.setAttribute("orgCode", orgCode);
			request.setAttribute("cityCode", cityCode);
			request.setAttribute("countyCode", countyCode);
		} else {
			// 指定的拆包样品
			String sampleIdList = "";
			String tmpSampIdArr = request.getParameter("sampleIdArr");
			String sampleIdArr = tmpSampIdArr.substring(0,tmpSampIdArr.length() - 1);
			String[] arr = sampleIdArr.split(",");
			for ( int i = 0 ; i < arr.length; i++) {
				sampleIdList += "'" +arr[i]+ "',";
			}
			sampleIdList = sampleIdList.substring(0, sampleIdList.length() - 1);
			request.setAttribute("sampleIdList", sampleIdList);
		}
		request.setAttribute("projectCode", projectCode);	

		return new ModelAndView("com/hippo/nky/sample/packingDistribute_orgList");

		
	}
	
	@RequestMapping(params = "projectdatagrid")
	public void projectOrgdatagrid(MonitoringProjectEntity monitoringProjectEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String orgCode = request.getParameter("orgCode");// 整包
		String sampleIdList = request.getParameter("sampleIdList");// 拆包
		JSONObject jObject = samplingInfoService.getProjectOrgdatagrid(monitoringProjectEntity, orgCode, sampleIdList, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 样品分发信息保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveSamplingDistribute")
	@ResponseBody
	public AjaxJson saveSamplingDistribute(SamplingInfoEntity samplingInfoEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		AjaxJson j = new AjaxJson();
//		String detectOrgid = request.getParameter("detectOrgid");
//	    OrganizationEntity org = systemService.getEntity(OrganizationEntity.class, detectOrgid);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("detectionOrgCode", request.getParameter("detectOrgid"));
		paramMap.put("orgCode", request.getParameter("orgCode"));
		paramMap.put("projectCode", request.getParameter("projectCode"));
		paramMap.put("sampleIdList",  request.getParameter("sampleIdList"));
		paramMap.put("cityCode", request.getParameter("cityCode"));
		paramMap.put("countyCode", request.getParameter("countyCode"));
		
		// 整包
		int updateRes = 0;
		if (StringUtils.isNotEmpty(request.getParameter("orgCode"))) {
			updateRes = samplingInfoService.saveSamplingDistribute(paramMap, 1);
		// 拆包
		} else {
			updateRes = samplingInfoService.saveSamplingDistribute(paramMap, 2);
		}

		if(updateRes != 0){
			j.setSuccess(true);
			message = "样品分发成功!";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			j.setMsg(message);
		}else{
			message = "样品分发失败!";
			j.setMsg(message);
		}
		return j;
	}
	
	/**
	 * 整包样品分发   样品详情页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "showsampledetails")
	public ModelAndView showSampleDetails(SamplingInfoEntity samplingInfoEntity, HttpServletRequest request) {
		// 抽样单位
		String samplingOrgCode = request.getParameter("samplingOrgCode");
		// 项目id
		String projectCode = request.getParameter("projectCode");
		request.setAttribute("samplingOrgCode", samplingOrgCode);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("cityCode", request.getParameter("cityCode"));
		request.setAttribute("countyCode", request.getParameter("countyCode"));
		return new ModelAndView("com/hippo/nky/sample/packingDistribute_detail");
	}
	
	/**
	 * 整包分发前确认样品是否接收
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkRecvPacking")
	@ResponseBody
	public AjaxJson checkRecvPacking(SamplingInfoEntity samplingInfoEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		boolean checkRes = samplingInfoService.checkRecvPacking(samplingInfoEntity);
		if (!checkRes) {
			j.setSuccess(false);
		}
		return j;
	}
	
	/**
	 * 拆包分发前确认样品是否接收
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkRecvUnPacking")
	@ResponseBody
	public AjaxJson checkRecvUnPacking(SamplingInfoEntity samplingInfoEntity, HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		String sampleIdList = "";
		String tmpSampIdArr = request.getParameter("sampleIdArr");
		String sampleIdArr = tmpSampIdArr.substring(0,tmpSampIdArr.length() - 1);
		String[] arr = sampleIdArr.split(",");
		for ( int i = 0 ; i < arr.length; i++) {
			sampleIdList += "'" +arr[i]+ "',";
		}
		sampleIdList = sampleIdList.substring(0, sampleIdList.length() - 1);

		paramMap.put("sampleIdList", sampleIdList);
		paramMap.put("projectCode", samplingInfoEntity.getProjectCode());
		AjaxJson j = new AjaxJson();
		boolean checkRes = samplingInfoService.checkRecvUnPacking(paramMap);
		if (!checkRes) {
			j.setSuccess(false);
		}
		return j;
	}
	
	/**
	 * 判断项目样品是否上报
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkReport")
	@ResponseBody
	public AjaxJson checkReport(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String projectCode = request.getParameter("projectCode");
		int checkRes = samplingInfoService.checkReport(projectCode);
		if (checkRes == 0) {
			j.setSuccess(false);
		}
		return j;
	}
	
	/**
	 * 判断样品信息是否上报
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkSampleReport")
	@ResponseBody
	public AjaxJson checkSampleReport(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String samplingInfoId = request.getParameter("samplingInfoId");
		int checkRes = samplingInfoService.checkSampleReport(samplingInfoId);
		if (checkRes == 0) {
			j.setSuccess(false);
		}
		return j;
	}
	
	/**
	 * 取得监测点信息
	 * @param plantSituationEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getMonitoringSiteInfo")
	@ResponseBody
	public AjaxJson getMonitoringSiteInfo(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			j.setAttributes(attributesMap);
			j.setSuccess(true);
			return j;
		}
		List<NkyMonitoringSiteEntity> monitoringSiteList = 
				systemService.findByProperty(NkyMonitoringSiteEntity.class, "name", name);
		if (monitoringSiteList != null && monitoringSiteList.size() > 0) {
			NkyMonitoringSiteEntity obj = monitoringSiteList.get(0);
			if (StringUtils.isNotEmpty(obj.getCode())) {
				attributesMap.put("monitoringSiteInfo", obj);
			}
		}
		j.setAttributes(attributesMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 样品信息详情
	 * 
	 * @return
	 */
	@RequestMapping(params = "samplingInfoDetails")
	public ModelAndView samplingInfoDetails(SamplingInfoEntity samplingInfoEntity,HttpServletRequest request) {
		Map<String,Object> detailMap = samplingInfoService.getCommonSampleDetail(samplingInfoEntity);
		request.setAttribute("samplingInfo", detailMap);
		return new ModelAndView("com/hippo/nky/sample/samplingInfoDetails");
	}
	
	/**
	 * 任务数量验证
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkTaskCount")
	@ResponseBody
	public AjaxJson checkTaskCount(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String msg = "";
		//String projectCode = request.getParameter("projectCode");
		Map<String, Object> taskCountMap = ConverterUtil.requestParamsToMap(request.getParameterMap());
		String isEditFlg = (String)taskCountMap.get("isEdit");
		
		for (String taskCode : taskCountMap.keySet()) {
			if (!StringUtils.equals("isEdit", taskCode)) {
				MonitoringTaskEntity task = systemService.findByProperty(MonitoringTaskEntity.class, "taskcode", taskCode).get(0);
				// 任务分配的抽样数
				int totalSamplingCount = task.getSamplingCount();

				CriteriaQuery cq = new CriteriaQuery(SamplingInfoEntity.class);
				cq.eq("taskCode", taskCode);
				cq.eq("sampleStatus", "1");
				cq.add();
				List<SamplingInfoEntity> samplingInfo = systemService.getListByCriteriaQuery(cq, false);
				// 录入总抽样数
				int samplingCount = 0;
				if (samplingInfo != null) {
					samplingCount = samplingInfo.size();
				}
				int addSamplingCount = Integer.parseInt((String)taskCountMap.get(taskCode));
				if (StringUtils.equals(isEditFlg, "0")){
					if (samplingCount + addSamplingCount > totalSamplingCount) {
						msg += task.getTaskname() + "录入样品信息数量("+(samplingCount + addSamplingCount) +")应小于等于分配数量("+totalSamplingCount+"),";
						j.setSuccess(false);
					}
				} else {
					if (addSamplingCount > totalSamplingCount) {
						msg += task.getTaskname() + "录入样品信息数量("+(addSamplingCount) +")应小于等于分配数量("+totalSamplingCount+"),";
						j.setSuccess(false);
					}
				}
		
			}	
		}		
	
		if (StringUtils.isNotEmpty(msg)) {
			j.setMsg(msg.substring(0, msg.length()-1));
		}
		return j;
	}
	
	/**
	 * 设置项目抽样完成(抽检不分离)
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "setProjectComplete")
	@ResponseBody
	public AjaxJson setProjectComplete(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String orgCode = ResourceUtil.getSessionUser().getOrganization().getCode();
		String projectCode = request.getParameter("projectCode");
		NkyProjectCompleteEntity nkyProjectCompleteEntity = new NkyProjectCompleteEntity();
		nkyProjectCompleteEntity.setOrgCode(orgCode);
		nkyProjectCompleteEntity.setProjectCode(projectCode);
		try {
			systemService.save(nkyProjectCompleteEntity);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("设置失败!");
		}
		return j;
	}
	
	/**
	 * 查询项目抽样完成信息(抽检不分离)
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getProjectComplete")
	@ResponseBody
	public AjaxJson getProjectComplete(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		String orgCode = ResourceUtil.getSessionUser().getOrganization().getCode();
		String projectCode = request.getParameter("projectCode");
		CriteriaQuery cq = new CriteriaQuery(NkyProjectCompleteEntity.class);
		cq.eq("orgCode", orgCode);
		cq.eq("projectCode", projectCode);
		cq.add();
		List<NkyProjectCompleteEntity> infoList = systemService.getListByCriteriaQuery(cq, false);
		
		if (infoList.size() > 0) {
			attributesMap.put("isSetted", true);
		} else {
			attributesMap.put("isSetted", false);
		}
		j.setAttributes(attributesMap);
		return j;
	}
	
	
	/**
	 * 抽样信息退回 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "samplingSetBack")
	public ModelAndView samplingSetBack(HttpServletRequest request) {

		return new ModelAndView("com/hippo/nky/sample/samplingSetBack");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getSamplingSetBack")
	public void getSamplingSetBack(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSamplingSetBack(barcodeInfoInput, dataGrid);
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

	@RequestMapping(params = "getSamplingSetBack2")
	public void getSamplingSetBack2(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSamplingSetBack2(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 抽样信息退回添加申请 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addSetBack")
	public ModelAndView addSetBack(HttpServletRequest request) {
		String code = request.getParameter("code");
		String id = request.getParameter("id");
		request.setAttribute("code", code);
		request.setAttribute("id", id);
		return new ModelAndView("com/hippo/nky/sample/addSamplingSetBack");
	}
	
	/**
	 * 取得检测信息退回检测添加申请搜索结果
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getAddSetBackData")
	@ResponseBody
	public AjaxJson getAddSetBackData(SamplingInfoEntity samplingInfoEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = null;
		Map<String, Object> selConditon = new HashMap<String, Object>();
		selConditon.put("orgCode", ResourceUtil.getSessionUser().getOrganization().getCode());
		selConditon.put("dcode", request.getParameter("dcode"));
		
		List<Map<String, Object>> mList = systemService.findListByMyBatis(NAME_SPACE+"getAddSetBackData", selConditon);
		if (mList.size() == 0) {
			message = "无此样品信息";
			j.setSuccess(false);
			j.setMsg(message);
			return j;
		}
		attributesMap = mList.get(0);
		j.setAttributes(attributesMap);;
		return j;
	}
	
	/**
	 * 申请退回
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doSamplingSetBack")
	@ResponseBody
	public AjaxJson doSamplingSetBack(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String dcode = request.getParameter("dcode");
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			SamplingSetBackEntity ssbe = systemService.getEntity(SamplingSetBackEntity.class, id);
			ssbe.setCode(dcode);
			ssbe.setStatus(1);
			systemService.saveOrUpdate(ssbe);
		} else {
			if (StringUtils.isNotEmpty(dcode)) {
				// 添加时根据dcode判断在数据库里是否存在，若存在则更新(只有审核退回才能出现此此现象)
				SamplingSetBackEntity ssbe = null;
				List<SamplingSetBackEntity> ssbeList  = systemService.findByProperty(SamplingSetBackEntity.class, "code", dcode);
				if (ssbeList.size() > 0) {
					ssbe = ssbeList.get(0);
					ssbe.setStatus(1);
					systemService.saveOrUpdate(ssbe);
				} else {
					ssbe = new SamplingSetBackEntity();
					ssbe.setCode(dcode);
					ssbe.setStatus(1);
					systemService.save(ssbe);
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
		
		SamplingSetBackEntity ssbe = systemService.getEntity(SamplingSetBackEntity.class, id);
		systemService.delete(ssbe);
		
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 检查样品是否已经添加过申请退回
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkSamplingSetBack")
	@ResponseBody
	public AjaxJson checkSamplingSetBack(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String dcode = request.getParameter("dcode");
		List<SamplingSetBackEntity> mList = systemService.findByProperty(SamplingSetBackEntity.class, "code", dcode);
		if (mList.size() > 0) {
			if (mList.get(0).getStatus() != 3) {
				j.setSuccess(false);
			}
		}
		return j;
	}
	
	/**
	 * 取得样品主要信息(退回时编辑样品信息用)
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getSamplingKeyInfo")
	@ResponseBody
	public AjaxJson getSamplingKeyInfo(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		String dcode = request.getParameter("dcode");
		List<Map<String, Object>> mList = systemService.findListByMyBatis(NAME_SPACE+"getSamplingKeyInfo", dcode);
	
		if (mList.size() > 0) {
			attributesMap.put("projectCode", (String)mList.get(0).get("PROJECT_CODE"));
			attributesMap.put("id", (String)mList.get(0).get("ID"));
		}
		j.setAttributes(attributesMap);
		return j;
	}
	
	
	/**
	 * 抽样信息退回 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "samplingSetBackCheck")
	public ModelAndView samplingSetBackCheck(HttpServletRequest request) {

		return new ModelAndView("com/hippo/nky/sample/samplingSetBackCheck");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getSamplingSetBackCheck")
	public void getSamplingSetBackCheck(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSamplingSetBackCheck(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	
	/**
	 * 抽样信息退回审核通过后台处理
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "samplingSetBackCheckPast")
	@ResponseBody
	public AjaxJson samplingSetBackCheckPast(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String idArr = request.getParameter("idArr");
		idArr = idArr.substring(0,idArr.length() - 1);
		String[] arr = idArr.split(",");		
		for (String id : arr) {
			SamplingSetBackEntity ssbe = systemService.getEntity(SamplingSetBackEntity.class, id);
			ssbe.setStatus(2);
			systemService.saveOrUpdate(ssbe);
		}
		return j;
	}
	
	/**
	 * 抽样信息退回审核退回后台处理
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "samplingSetBackChecksetBack")
	@ResponseBody
	public AjaxJson samplingSetBackChecksetBack(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String idArr = request.getParameter("idArr");
		idArr = idArr.substring(0,idArr.length() - 1);
		String[] arr = idArr.split(",");		
		for (String id : arr) {
			SamplingSetBackEntity ssbe = systemService.getEntity(SamplingSetBackEntity.class, id);
			ssbe.setStatus(3);
			systemService.saveOrUpdate(ssbe);
		}
		return j;
	}
	
	/**
	 * 确认提交确认 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "confirmSubmit")
	public ModelAndView confirmSubmit(HttpServletRequest request) {
		request.setAttribute("isSampleNameChange", request.getParameter("isSampleNameChange"));
		request.setAttribute("vdcode", request.getParameter("vdcode"));
		return new ModelAndView("com/hippo/nky/sample/confirmSubmit");
	}
	
	
	/**
	 * 盲样信息管理 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "samplingBlind")
	public ModelAndView samplingBlind(HttpServletRequest request) {
		setInitYear(request, 10);
		return new ModelAndView("com/hippo/nky/sample/samplingBlind");
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
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "getSamplingBlindData")
	public void getSamplingBlindData(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSamplingBlindData(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 增加盲样 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addSamplingBlind")
	public ModelAndView addSamplingBlind(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/sample/addSamplingBlind");
	}
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "getAddSamplingBlindData")
	public void getAddSamplingBlindData(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getAddSamplingBlindData(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 增加盲样选择样品 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "selectSamplingBlind")
	public ModelAndView selectSamplingBlind(HttpServletRequest request) {
		request.setAttribute("taskCode", request.getParameter("taskCode"));
		return new ModelAndView("com/hippo/nky/sample/selectSamplingBlind");
	}
	
	/**
	 * 增加盲样选择样品 数据取得
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "getSelectSamplingBlind")
	public void getSelectSamplingBlind(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSelectSamplingBlind(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 保存增加盲样选择样品
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveSamplingBlind")
	@ResponseBody
	public AjaxJson saveSamplingBlind(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String sampleCode = request.getParameter("sampleCode");
		BlindSampleEntity bse = new BlindSampleEntity();
		bse.setSampleCode(sampleCode);
		bse.setStatus(0);
		systemService.save(bse);
		
		return j;
	}
	
	/**
	 * 替换盲样 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "replaceSamplingBlind")
	public ModelAndView replaceSamplingBlind(HttpServletRequest request) {
		String sampleCode = request.getParameter("sampleCode");
		String projectCode = request.getParameter("projectCode");
		
		List<BlindSampleEntity> blindSampleList = samplingInfoService.findHql("from BlindSampleEntity where sampleCode = ? ", sampleCode);
		BlindSampleEntity blindSampleEntity = blindSampleList.get(0);
		List<SamplingInfoEntity> samplingInfoList = samplingInfoService.findHql("from SamplingInfoEntity where sampleCode = ? ", sampleCode);
		SamplingInfoEntity samplingInfoEntity = samplingInfoList.get(0);
		
		DetectionEntity detectionEntity = new DetectionEntity();
		detectionEntity.setSampleStatus("5");
		detectionEntity.setProjectCode(projectCode);
		detectionEntity.setAgrCode(samplingInfoEntity.getAgrCode());
		detectionEntity.setLabCode(samplingInfoEntity.getLabCode());
		
		// 取得当前用户的部门
		List<OrganizationEntity> list = detectionService.findHql("from OrganizationEntity where code = ? ", samplingInfoEntity.getDetectionCode());
		OrganizationEntity organizationEntity = list.get(0);
		// 过滤用户所能看到的数据 部门=质检机构
		detectionEntity.setOrgCode(organizationEntity.getId());

		List<NkyDetectionInformationEntity> detectionInformationList = detectionService.getDetectionInfoPollItem(detectionEntity);

		if(blindSampleEntity.getStatus() == null || blindSampleEntity.getStatus() == 0){
			String blindSampleValue = "";
			for(int i=0;i<detectionInformationList.size();i++){
				NkyDetectionInformationEntity nkyDetectionInformationEntity = detectionInformationList.get(i);
				if(nkyDetectionInformationEntity.getDetectionValue().doubleValue() > 0){
					blindSampleValue = blindSampleValue + nkyDetectionInformationEntity.getPollName()+nkyDetectionInformationEntity.getDetectionValue().doubleValue()+";";
				}
				nkyDetectionInformationEntity.setDetectionValue(new BigDecimal(0));
			}
			blindSampleEntity.setBlindSampleValue(blindSampleValue);
		}
		
		request.setAttribute("blindSample", blindSampleEntity);
		request.setAttribute("detInfoList", detectionInformationList);
		request.setAttribute("samplingInfo", samplingInfoEntity);
		
		request.setAttribute("sampleCode", sampleCode);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("cname", request.getParameter("cname"));
		request.setAttribute("sogrName", request.getParameter("sogrName"));
		request.setAttribute("dogrName", request.getParameter("dogrName"));
		
		return new ModelAndView("com/hippo/nky/sample/replaceSamplingBlind");
	}
	

	/**
	 * 保存设定的检出值
	 * 
	 * @param detectionEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveDetectionValue")
	@ResponseBody
	public AjaxJson saveDetectionValue(DetectionEntity detectionEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<?, ?> paramMap = request.getParameterMap();
		// 取得当前用户的部门
		List<OrganizationEntity> list = detectionService.findHql("from OrganizationEntity where code = ? ", request.getParameter("detectionCode"));
		OrganizationEntity organizationEntity = list.get(0);
		// 过滤用户所能看到的数据 部门=质检机构
		detectionEntity.setOrgCode(organizationEntity.getId());
		List<NkyDetectionInformationEntity> detInfoList = new ArrayList<NkyDetectionInformationEntity>();
		
		
		List<BlindSampleEntity> blindSampleList = samplingInfoService.findHql("from BlindSampleEntity where sampleCode = ? ", request.getParameter("sampleCode"));
		BlindSampleEntity blindSampleEntity = blindSampleList.get(0);
		if(blindSampleEntity.getStatus() == 0){
			blindSampleEntity.setStatus(1);
			blindSampleEntity.setBlindSampleValue(request.getParameter("blindSampleValue"));			
			detectionService.save(blindSampleEntity);
		}
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
		if(detInfoList.size() > 0){
			detectionService.batchSaveOrUpdate(detInfoList);
			// 设置样品检测是否超标
			detectionService.setOverproofRecord(detectionEntity);
		}

		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 取得行政区划自定义编码
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getSysAreaSelfCode")
	@ResponseBody
	public AjaxJson getSysAreaSelfCode(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String code = request.getParameter("code");
		
		CriteriaQuery cq = new CriteriaQuery(SysAreaCodeEntity.class);
		cq.eq("code", code);
		cq.eq("flag", "0");
		cq.add();
		List<SysAreaCodeEntity> monitoringTaskList = systemService.getListByCriteriaQuery(cq, false);
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		attributesMap.put("selfCode", monitoringTaskList.get(0).getSelfcode());
		
		j.setAttributes(attributesMap);
		return j;
	}
	
	public String getSysAreaSelfCode(String code) {
		CriteriaQuery cq = new CriteriaQuery(SysAreaCodeEntity.class);
		cq.eq("code", code);
		cq.eq("flag", "0");
		cq.add();
		List<SysAreaCodeEntity> monitoringTaskList = systemService.getListByCriteriaQuery(cq, false);
		return monitoringTaskList.get(0).getSelfcode();
	}
	
	
	/**
	 * 抽样信息批量退回 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "samplingBatchSetBack")
	public ModelAndView samplingBatchSetBack(HttpServletRequest request) {

		return new ModelAndView("com/hippo/nky/sample/samplingBatchSetBack");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getSamplingBatchSetBack")
	public void getSamplingBatchSetBack(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSamplingBatchSetBack(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 抽样信息批量退回详情 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "samplingBatchSetBackDetail")
	public ModelAndView samplingBatchSetBackDetail(HttpServletRequest request) {
		String projectCode = request.getParameter("projectCode");
		request.setAttribute("projectCode", projectCode);
		return new ModelAndView("com/hippo/nky/sample/samplingBatchSetBackDetail");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getSamplingBatchSetBackDetail")
	public void getSamplingBatchSetBackDetail(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getSamplingBatchSetBackDetail(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	
	/**
	 * 抽样信息批量退回样品信息 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "batchSetBackSampleList")
	public ModelAndView batchSetBackSampleList(HttpServletRequest request) {
		request.setAttribute("projectCode", request.getParameter("projectCode"));
		request.setAttribute("ogrCode", request.getParameter("ogrCode"));
		return new ModelAndView("com/hippo/nky/sample/batchSetBackSampleList");
	}
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getBatchSetBackSampleList")
	public void getBatchSetBackSampleList(SamplingInfoEntity barcodeInfoInput,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = samplingInfoService.getBatchSetBackSampleList(barcodeInfoInput, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 抽样信息批量退回后台处理
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doBatchSetBack")
	@ResponseBody
	public AjaxJson doBatchSetBack(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String idArr = request.getParameter("idArr");
		idArr = idArr.substring(0,idArr.length() - 1);
		String[] arr = idArr.split(",");	
		
		SamplingSetBackEntity ssbe = null;
		for (String dcode : arr) {
			List<SamplingSetBackEntity> ssbeList = systemService.findByProperty(SamplingSetBackEntity.class,"code", dcode);
			if (ssbeList != null && ssbeList.size() == 0) {
				ssbe  = new SamplingSetBackEntity();
				ssbe.setCode(dcode);
				ssbe.setStatus(2);
				systemService.save(ssbe);
			} else {
				ssbe = ssbeList.get(0);
				ssbe.setStatus(2);
				systemService.saveOrUpdate(ssbe);
			}
		}
		return j;
	}
	
	/**
	 * 受检单位Ajax自动匹配
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getMatchResult")
	@ResponseBody
	public AjaxJson getMatchResult(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String keyWords = request.getParameter("keyWords");
		List<NkyMonitoringSiteEntity> monitoringSiteList = samplingInfoService.findListByMyBatis(NAME_SPACE+"getMatchResult", keyWords);
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		attributesMap.put("monitoringSiteList", monitoringSiteList);
		j.setAttributes(attributesMap);
		return j;
	}
	
	/**
	 * 取得是否是牵头单位flg
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getLeadUnitFlg")
	@ResponseBody
	public AjaxJson getLeadUnitFlg(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String samplingOrgCode = request.getParameter("samplingOrgCode");
		if (StringUtils.isEmpty(samplingOrgCode)) {// 管理部门
			return j;
		}
		String projectCode = request.getParameter("projectCode");
		CriteriaQuery cq = new CriteriaQuery(MonitoringProjectEntity.class);
		cq.eq("projectCode", projectCode);
		cq.eq("leadunit", samplingOrgCode);
		cq.add();
		List<MonitoringProjectEntity> projectList =  samplingInfoService.getListByCriteriaQuery(cq, false); 
		if (projectList != null && projectList.size() > 0) {//牵头单位
			j.setSuccess(true);
		} else {
			j.setSuccess(false);
		}
		return j;
	}
	
}
