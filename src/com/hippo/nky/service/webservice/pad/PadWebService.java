package com.hippo.nky.service.webservice.pad;
/**
 * @author XQ
 * @show 此类提供和Pad手持端通讯的相关接口 
 * @Version 1.0
 */
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import jeecg.system.pojo.base.TSType;

import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskDetailsEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskEntity;
import com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity;
import com.hippo.nky.entity.sample.SamplingInfoPadEntity;

@WebService(targetNamespace="http://webservice.hippo.nky.com/")
public interface PadWebService {
		
	    /**
	     * @author XQ
	     * @show  登录验证
	     * @param userName
	     * @param passWord
	     * @return NkyMonitoringPadEntity
	     */
	    public NkyMonitoringPadEntity checkLogInInfo(@WebParam(name="userName", targetNamespace="http://webservice.hippo.nky.com/") String userName, @WebParam(name="passWord", targetNamespace="http://webservice.hippo.nky.com/") String passWord);
	    
	    /**
	     * @author XQ
	     * @show  获取项目列表
	     * @param orgCode
	     * @return List<MonitoringProjectEntity>
	     */
	    public List<MonitoringProjectEntity> getMonitoringProjectInfoListByOrgCode(@WebParam(name="orgCode", targetNamespace="http://webservice.hippo.nky.com/") String orgCode,
	    		@WebParam(name="padId", targetNamespace="http://webservice.hippo.nky.com/") String padId,
	    		@WebParam(name="projectName", targetNamespace="http://webservice.hippo.nky.com/") String projectName);
	    
	    /**
	     * @author XQ
	     * @show  获取任务列表
	     * @param monitoringProjectEntity
	     * @return List<MonitoringTaskEntity>
	     */
	    public List<MonitoringTaskEntity> getMonitoringTaskList(@WebParam(name="monitoringProjectEntity", targetNamespace="http://webservice.hippo.nky.com/") String monitoringProjectEntity);
	    
	    /**
	     * 取得监测类型名称
	     * @param monitorType
	     * @return
	     */
	    public String getNameForMonitorType(@WebParam(name="monitorType", targetNamespace="http://webservice.hippo.nky.com/") String monitorType);
	    
		/**
		 * 根据项目取得抽样环节列表
		 */
	    public List<TSType> getSampleMonitoringLink(@WebParam(name="industry", targetNamespace="http://webservice.hippo.nky.com/") String industry);
	    
	    /**
	     * @author XQ
	     * @show  获取单个任务详细信息
	     * @param taskCode
	     * @return MonitoringTaskInfo
	     */
	    public MonitoringTaskDetailsEntity getMonitoringTaskInfoByTaskCode(@WebParam(name="taskCode", targetNamespace="http://webservice.hippo.nky.com/") String taskCode
	    										,@WebParam(name="padId", targetNamespace="http://webservice.hippo.nky.com/") String padId); 
	    
	    /**
	     * 取得抽样品种
	     * @param taskCode
	     * @return
	     */
	    public List<MonitoringBreedEntity> getMonitoringBreedForTask(@WebParam(name="taskCode", targetNamespace="http://webservice.hippo.nky.com/") String taskCode
	    				,@WebParam(name="projectCode", targetNamespace="http://webservice.hippo.nky.com/") String projectCode);
	    
		/**
		 * 取得抽样地区
		 * @param taskCode
		 */
	    public List<String> getMonitoringAreaForTask(@WebParam(name="taskCode", targetNamespace="http://webservice.hippo.nky.com/") String taskCode);

		/**
		 * 取得抽样环节
		 * @param taskCode
		 */
	    public List<String> getMonitoringLinkForTask(@WebParam(name="taskCode", targetNamespace="http://webservice.hippo.nky.com/") String taskCode);
	    
	    /**
	     * @author XQ
	     * @show  保存抽样信息
	     * @param samplingInfo
	     * @return int
	     */
	    public int saveSamplingInfoOnDb(@WebParam(name="samplingInfo", targetNamespace="http://webservice.hippo.nky.com/") String samplingInfo
	    				,@WebParam(name="sampleMonadType", targetNamespace="http://webservice.hippo.nky.com/") String sampleMonadType);
	    
	    /**
	     * @author XQ
	     * @show  转发和替抽时
	     * @param orgCode
	     * @param total
	     * @return Boolean
	     */
	    public Boolean forwardMonitoringTaskInfo(@WebParam(name="paramMonitoringTaskDetailsEntity", targetNamespace="http://webservice.hippo.nky.com/") String paramMonitoringTaskDetailsEntity,
	    		@WebParam(name="padId", targetNamespace="http://webservice.hippo.nky.com/") String padId, @WebParam(name="total", targetNamespace="http://webservice.hippo.nky.com/") int total,
	    		@WebParam(name="flagStr", targetNamespace="http://webservice.hippo.nky.com/") String flagStr) ;
	    
		/**
		 * 取得质检机构下其他抽样员信息
		 * @param padId
		 * @return
		 */
		public List<NkyMonitoringPadEntity> getOtherPadInfo(@WebParam(name="padId", targetNamespace="http://webservice.hippo.nky.com/") String padId,
				@WebParam(name="taskCode", targetNamespace="http://webservice.hippo.nky.com/") String taskCode);

		/**
		 * 取得抽样地区行政区划全部信息
		 */
		public String getSysMonitoringArea();

		
	    /**
	     * 取得已完成的抽样信息
	     * @param padId
	     * @param taskCode
	     * @param sampleTableType
	     */
	    public List<SamplingInfoPadEntity> getFinishSamplingInfo(@WebParam(name="padId", targetNamespace="http://webservice.hippo.nky.com/")String padId,
	    		@WebParam(name="taskCode", targetNamespace="http://webservice.hippo.nky.com/")String taskCode,
	    		@WebParam(name="sampleMonadType", targetNamespace="http://webservice.hippo.nky.com/")String sampleMonadType,
	    		@WebParam(name="projectCode", targetNamespace="http://webservice.hippo.nky.com/")String projectCode);
	    
	    /**
		 * 取得所有受检单位信息
		 * @return
		 */
		//public List<NkyMonitoringSiteEntity> getAllMonitoringSite();
		public String getAllMonitoringSite();
	    
	    /**
	     * 修改pad用户密码
	     * @param userName
	     * @param oldPassword
	     * @param newPassword
	     */
	    public String modifyUserInfo(@WebParam(name="userName", targetNamespace="http://webservice.hippo.nky.com/")String userName,
	    		@WebParam(name="oldPassWord", targetNamespace="http://webservice.hippo.nky.com/")String oldPassWord,
	    		@WebParam(name="newPassWord", targetNamespace="http://webservice.hippo.nky.com/")String newPassWord);
	    /**
	     * PAD轮询取数据接口 
	     */
	    public String receiverMQ(@WebParam(name="padId", targetNamespace="http://webservice.hippo.nky.com/")String padId);
	    
		/**
		 * 二维码验证
		 */
		public String checkDcode(@WebParam(name="dCode", targetNamespace="http://webservice.hippo.nky.com/")String dCode, 
				     @WebParam(name="projectCode", targetNamespace="http://webservice.hippo.nky.com/")String projectCode);
		
		/**
		 * 保存图片样品信息
		 * @param imgContentMap
		 */
		public String saveImageForSample(@WebParam(name="imgContentMap", targetNamespace="http://webservice.hippo.nky.com/")String imgContentMap) ;
		
		
		public String downLoadInitData(@WebParam(name="orgCode", targetNamespace="http://webservice.hippo.nky.com/")String orgCode, @WebParam(name="padId", targetNamespace="http://webservice.hippo.nky.com/")String padId) throws IOException;
		
		/**
		 * 
		 * @return
		 */
		public String getWebContextPath(@WebParam(name="type", targetNamespace="http://webservice.hippo.nky.com/")String type);
}