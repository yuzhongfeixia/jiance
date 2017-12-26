package com.hippo.nky.controller.sample;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jeecg.system.pojo.base.TSUser;
import jeecg.system.service.SystemService;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.TwoDimensionCode;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.sample.BarcodePrintEntity;
import com.hippo.nky.entity.sample.TwoDimensionEntity;
import com.hippo.nky.service.sample.BarcodePrintServiceI;
import com.hippo.nky.service.sample.TwoDimensionServiceI;

/**   
 * @Title: Controller
 * @Description: 样品条码打印
 * @author nky
 * @date 2013-11-05 13:51:43
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/barcodePrintController")
public class BarcodePrintController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BarcodePrintController.class);

	@Autowired
	private BarcodePrintServiceI barcodePrintService;
	@Autowired
	private TwoDimensionServiceI twoDimensionService;
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
	 * 样品条码打印列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "barcodePrint")
	public ModelAndView barcodePrint(HttpServletRequest request) {
		return new ModelAndView("com/hippo/nky/sample/barcodePrintList");
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
	public void datagrid(BarcodePrintEntity barcodePrint,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(BarcodePrintEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, barcodePrint, request.getParameterMap());
//		this.barcodePrintService.getDataGridReturn(cq, true);
		TSUser user = ResourceUtil.getSessionUserName();
		//没有版本id 返回空页面
//		if(StringUtil.isEmpty(barcodePrint.getId())){
//			TagUtil.datagrid(response, dataGrid);
//		}else{
			JSONObject jObject = barcodePrintService.getDatagrid(barcodePrint,user.getTSDepart().getId(), dataGrid);
			responseDatagrid(response, jObject);
//		}
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
	 * 删除样品条码打印
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BarcodePrintEntity barcodePrint, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		barcodePrint = systemService.getEntity(BarcodePrintEntity.class, barcodePrint.getId());
		message = "样品条码打印删除成功";
		barcodePrintService.delete(barcodePrint);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 取得要打印的二维码集合
	 * @return
	 */
	private List<TwoDimensionEntity> getPrintCodeList(BarcodePrintEntity barcodePrint, HttpServletRequest request) {
		String basePath = request.getSession().getServletContext().getRealPath("") + File.separator + "twodimension"+ File.separator;
        String encoderContent = "";  
        //加密
        TwoDimensionCode handler = new TwoDimensionCode();  
        // 检查目录
		File deployDir = new File(basePath);
		if (!deployDir.exists()) {
			deployDir.mkdirs();
		}
		/**
		 * 生成文明名称
		 * deptCode 登录者地区编码前4位
		 */
		String deptCode = ResourceUtil.getLoginUserArea().substring(0, 4);
		SimpleDateFormat sdf = new SimpleDateFormat("yy");
		String midCode = deptCode + DataUtils.date2Str(sdf);
		//文件真实路径
		//D:\dev\working\framework3.3.2\WebContent\twodimension\20131107150506_323.png
		//打印数量循环、printcount
		int printSize = barcodePrint.getPrintCount();
		int startNum = twoDimensionService.getMaxNum(midCode).intValue();//取得当前部门下
		int endNum = startNum + printSize;
		String titles = "";
		List<TwoDimensionEntity> twoLst = new ArrayList<TwoDimensionEntity>();
		for (int i = startNum; i < endNum; i++) {
			int head =  new Random().nextInt(9);
			encoderContent= head + getMDStr(midCode,head) + getNumber(i+1);
			String imgPath = (deployDir+File.separator+ encoderContent) + ".png";
			TwoDimensionEntity two = new TwoDimensionEntity();
			two.setProjectCode(barcodePrint.getProjectCode());
			two.setRandNo(head);
			two.setMdCode(midCode);
			two.setSerialNo(getNumber(i+1));
			two.setTitle(encoderContent);
			
			two.setRealpath(imgPath.substring(imgPath.indexOf("twodimension")));
			twoLst.add(two);
			titles += encoderContent + ",";
			handler.encoderQRCode(encoderContent, imgPath, "png",4);  
		}
		// 将lst放入缓存，便于存储
		HttpSession session = ContextHolderUtils.getSession();
		session.setMaxInactiveInterval(60 * 30);
		session.setAttribute("twoLst", twoLst);
		session.setAttribute("titles", titles);
		return twoLst;
	}

	/**
	 * 添加样品条码打印
	 * 
	 * @param ids
	 * @return
	 */
//	@RequestMapping(params = "save")
//	@ResponseBody
//	public AjaxJson save(BarcodePrintEntity barcodePrint, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		//D:\dev\working\framework3.3.2\WebContent\twodimension\ == basepath 
//		// 文件保存目录URL
//		String basePath = request.getSession().getServletContext().getRealPath("") + File.separator + "twodimension"+ File.separator;
//        String encoderContent = "";  
//        //加密
//        TwoDimensionCode handler = new TwoDimensionCode();  
//        // 检查目录
//		File deployDir = new File(basePath);
//		if (!deployDir.exists()) {
//			deployDir.mkdirs();
//		}
//		/**
//		 * 生成文明名称
//		 * deptCode 登录者地区编码前4位
//		 */
//		String deptCode = ResourceUtil.getLoginUserArea().substring(0, 4);
//		SimpleDateFormat sdf = new SimpleDateFormat("yy");
//		String midCode = deptCode + DataUtils.date2Str(sdf);
//		//文件真实路径
//		//D:\dev\working\framework3.3.2\WebContent\twodimension\20131107150506_323.png
//		//打印数量循环、printcount
//		int printSize = barcodePrint.getPrintCount();
//		int startNum = twoDimensionService.getMaxNum(midCode).intValue();//取得当前部门下
//		int endNum = startNum + printSize;
//		String titles = "";
//		List<TwoDimensionEntity> twoLst = new ArrayList<TwoDimensionEntity>();
//		for (int i = startNum; i < endNum; i++) {
//			int head =  new Random().nextInt(9);
//			encoderContent= head + getMDStr(midCode,head) + getNumber(i+1);
//			String imgPath = (deployDir+File.separator+ encoderContent) + ".png";
//			TwoDimensionEntity two = new TwoDimensionEntity();
//			two.setProjectCode(barcodePrint.getProjectCode());
//			two.setRandNo(head);
//			two.setMdCode(midCode);
//			two.setSerialNo(getNumber(i+1));
//			two.setTitle(encoderContent);
//			
//			two.setRealpath(imgPath.substring(imgPath.indexOf("twodimension")));
//			twoLst.add(two);
//			titles += encoderContent + ",";
//			handler.encoderQRCode(encoderContent, imgPath, "png",4);  
//		}
//		twoDimensionService.batchSave(twoLst);
//		
//		if (!barcodePrint.getId().equals("null") && StringUtil.isNotEmpty(barcodePrint.getId())) {
//			message = "样品条码打印修改成功";
//			BarcodePrintEntity t = barcodePrintService.get(BarcodePrintEntity.class, barcodePrint.getId());
//			try {
//				t.setPrintCount(t.getPrintCount() + barcodePrint.getPrintCount());
//				barcodePrintService.updateEntitie(t);
//				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//			} catch (Exception e) {
//				e.printStackTrace();
//				message = "样品条码打印修改失败";
//			}
//		} else {
//			message = "样品条码打印添加成功";
//			barcodePrintService.save(barcodePrint);
//			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//		}
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		rtnMap.put("projectCode", barcodePrint.getProjectCode());
//		rtnMap.put("titles", titles);
//		rtnMap.put("copies", barcodePrint.getPrintNumberCopies());
//		j.setMsg(message);
//		j.setObj(rtnMap);
//		return j;
//	}
	
	/**
	 * 自定义加密
	 * @param midCode
	 * @param head
	 * @return
	 */
	private String getMDStr(String midCode, int head) {
		String rtnStr = "";//存储拆分后的数字
		char mChar[] = midCode.toCharArray();
		for (int i = 0; i < mChar.length; i++) {
			int vm = Integer.valueOf(String.valueOf(mChar[i]));
			int mdNum = vm + head;
			if(mdNum >= 10){
				rtnStr += String.valueOf(mdNum % 10); //获得个位数字
			}else{
				rtnStr += String.valueOf(mdNum);
			}
		}
		return rtnStr;
	}

	/**
	 * 生成流水号
	 * @param string
	 * @param num
	 * @return
	 */
	private String getNumber(int num) {
		NumberFormat formatter = new DecimalFormat("000000");
		String number = formatter.format(num);
		return number;
	}

	/**
	 * 样品条码打印列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BarcodePrintEntity barcodePrint, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(barcodePrint.getProjectCode())) {
			MonitoringProjectEntity monitoringProject = systemService.findUniqueByProperty(MonitoringProjectEntity.class, "projectCode", barcodePrint.getProjectCode());
			req.setAttribute("monitoringProject", monitoringProject);
		}
		req.setAttribute("barcodeId", barcodePrint.getId());
		return new ModelAndView("com/hippo/nky/sample/barcodePrint");
	}
	
	/**
	 * 条码补打 跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "complementplay")
	public ModelAndView complementplay(BarcodePrintEntity barcodePrint, HttpServletRequest req) {
		String dcode = oConvertUtils.getString(req.getParameter("dcode"));
		if(StringUtil.isNotEmpty(dcode)){
			Object obj = barcodePrintService.findByCode(dcode);
			req.setAttribute("obj", obj);
		}
		return new ModelAndView("com/hippo/nky/sample/ComplementPlay");
	}
	
	/**
	 * 条码补打 搜索
	 * 
	 * @return
	 */
	@RequestMapping(params = "complementsearch")
	@ResponseBody
	public AjaxJson complementsearch(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String dcode = oConvertUtils.getString(request.getParameter("dcode"));
		Object obj = null;
		if(StringUtil.isNotEmpty(dcode)){
			obj = barcodePrintService.findByCode(dcode);
			request.setAttribute("obj", obj);
		}
		
		if(obj != null){
			message = "样品条码搜索成功";
			j.setObj(obj);
			j.setMsg(message);
		}else{
			message = "无此样品条码信息";
			j.setSuccess(false);
			j.setMsg(message);
		}
		return j;
	}
	
	/**
	 * 样品条码打印
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "print")
	public ModelAndView print(BarcodePrintEntity barcodePrint, HttpServletRequest request) {
		String dcode = oConvertUtils.getString(request.getParameter("dcode"));
		if(StringUtil.isNotEmpty(dcode)){//dcode不为空，则为样品条码补打
			String basePath = request.getSession().getServletContext().getRealPath("") + File.separator + "twodimension"+ File.separator;
	        //加密
	        TwoDimensionCode handler = new TwoDimensionCode();  
	        // 检查目录
			File deployDir = new File(basePath);
			if (!deployDir.exists()) {
				deployDir.mkdirs();
			}
			List<TwoDimensionEntity> twoList = twoDimensionService.findByProperty(TwoDimensionEntity.class, "title", dcode);
			String titles = "";
			// 为条码设置二维码图片
			for (TwoDimensionEntity two : twoList) {
				String title = two.getTitle();
				String imgPath = (deployDir+File.separator+ title) + ".png";
				two.setRealpath(imgPath.substring(imgPath.indexOf("twodimension")));
				handler.encoderQRCode(title, imgPath, "png",4);  
				titles += title + ",";
			}
			request.setAttribute("titles", titles);
			request.setAttribute("twoList", twoList);
			request.setAttribute("printCopies", "4");
		}else{
			//String titles = request.getParameter("titles");
			//List<TwoDimensionEntity> twoList = twoDimensionService.findTwoListForPrint(barcodePrint.getProjectCode(),titles);
			List<TwoDimensionEntity> twoList = this.getPrintCodeList(barcodePrint, request);
			request.setAttribute("twoList", twoList);
			request.setAttribute("printCopies", barcodePrint.getPrintNumberCopies());
			//HttpSession session = ContextHolderUtils.getSession();
			request.setAttribute("titles", ContextHolderUtils.getSession().getAttribute("titles"));
		}
		return new ModelAndView("com/hippo/nky/sample/barcode_print_iframe");
	}
	
	/**
	 * 保存二维码数据
	 * @param barcodePrint
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveCode")
	@ResponseBody
	public AjaxJson saveCode(BarcodePrintEntity barcodePrint, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		if (!barcodePrint.getId().equals("null") && StringUtil.isNotEmpty(barcodePrint.getId())) {
			String title = request.getParameter("title");
			// 判断title是否打印过,若打印过无需更新数量
			CriteriaQuery cq = new CriteriaQuery(TwoDimensionEntity.class);
			cq.eq("projectCode", barcodePrint.getProjectCode());
			cq.eq("title", title);
			cq.add();
			List<TwoDimensionEntity> targetTitle = systemService.getListByCriteriaQuery(cq, false);
			if (targetTitle.size() == 0) {
				HttpSession session = ContextHolderUtils.getSession();
				List<TwoDimensionEntity> twoLst = (List<TwoDimensionEntity>)session.getAttribute("twoLst");
				twoDimensionService.batchSave(twoLst);

				BarcodePrintEntity t = barcodePrintService.get(BarcodePrintEntity.class, barcodePrint.getId());
				try {
					t.setPrintCount(t.getPrintCount() + barcodePrint.getPrintCount());
					barcodePrintService.updateEntitie(t);
					//return barcodePrint.getId();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
			
		} else {
			HttpSession session = ContextHolderUtils.getSession();
			List<TwoDimensionEntity> twoLst = (List<TwoDimensionEntity>)session.getAttribute("twoLst");
			twoDimensionService.batchSave(twoLst);

			barcodePrintService.save(barcodePrint);
			//return barcodePrint.getId();
		}
		attributesMap.put("id", barcodePrint.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		attributesMap.put("printTime", sdf.format(new Date()));
		j.setAttributes(attributesMap);
		return j;
	}
	
	
	/**
	 * 样品条码打印
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "printView")
	public ModelAndView printView(BarcodePrintEntity barcodePrint, HttpServletRequest request) {
		//request.setAttribute("titles", request.getParameter("titles"));
		List<MonitoringProjectEntity> pList = systemService.findByProperty(MonitoringProjectEntity.class, "projectCode", barcodePrint.getProjectCode());
		barcodePrint.setProjectName(pList.get(0).getName());
		request.setAttribute("barcodePrint", barcodePrint);
		return new ModelAndView("com/hippo/nky/sample/barcode_print_view");
	}
	
	/**
	 * 样品条码打印补打
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "printView1")
	public ModelAndView printView1(BarcodePrintEntity barcodePrint, HttpServletRequest request) {
		//request.setAttribute("titles", request.getParameter("titles"));
		String dcode = oConvertUtils.getString(request.getParameter("dcode"));
		barcodePrint.setPrintNumberCopies(4);
		List<TwoDimensionEntity> pList = systemService.findByProperty(TwoDimensionEntity.class, "title", dcode);
		barcodePrint.setProjectName(systemService.findByProperty(MonitoringProjectEntity.class, "projectCode", pList.get(0).getProjectCode()).get(0).getName());
		request.setAttribute("barcodePrint", barcodePrint);
		request.setAttribute("dcode", dcode);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		request.setAttribute("printTime", sdf.format(new Date()));
		return new ModelAndView("com/hippo/nky/sample/barcode_print_view1");
	}
	
}
