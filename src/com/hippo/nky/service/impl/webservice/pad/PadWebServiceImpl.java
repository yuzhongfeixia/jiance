package com.hippo.nky.service.impl.webservice.pad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;

import jeecg.system.pojo.base.TSType;
import jeecg.system.service.SystemService;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;

import sun.misc.BASE64Encoder;

import com.google.l99gson.Gson;
import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskDetailsEntity;
import com.hippo.nky.entity.monitoring.MonitoringTaskEntity;
import com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity;
import com.hippo.nky.entity.monitoring.NkyMonitoringSiteEntity;
import com.hippo.nky.entity.sample.GeneralcheckEntity;
import com.hippo.nky.entity.sample.LivestockEntity;
import com.hippo.nky.entity.sample.NkyFreshMilkEntity;
import com.hippo.nky.entity.sample.RoutinemonitoringEntity;
import com.hippo.nky.entity.sample.SamplingInfoEntity;
import com.hippo.nky.entity.sample.SamplingInfoPadEntity;
import com.hippo.nky.entity.sample.SuperviseCheckEntity;
import com.hippo.nky.entity.sample.TwoDimensionEntity;
import com.hippo.nky.entity.sample.padMQ;
import com.hippo.nky.entity.system.SysAreaCodeEntity;
import com.hippo.nky.service.sample.SamplingInfoServiceI;
import com.hippo.nky.service.webservice.pad.PadWebService;
@WebService(endpointInterface = "com.hippo.nky.service.webservice.pad.PadWebService")  
public class PadWebServiceImpl extends CommonServiceImpl implements PadWebService {
	public static final String NAME_SPACE = "com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity.";
	public static final String NAME_SPACE1 = "com.hippo.nky.entity.sample.SamplingInfoEntity.";
	public static final String NAME_SPACE2 = "com.hippo.nky.entity.monitoring.MonitoringTaskEntity.";
	public static final String NAME_SPACE3 = "com.hippo.nky.entity.monitoring.MonitoringTaskDetailsEntity.";
	public static final String NAME_SPACE4 = "com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity.";
	public static final String MQ_URL="tcp://localhost:61616";
	@Autowired  
	SamplingInfoServiceI service;//引入 SamplingInfoServiceI  调取保存抽样信息
	@Autowired
	private SystemService systemService;
	final Logger logger = Logger.getLogger(PadWebServiceImpl.class);
	/**
	 * 用户登录验证
	 */
	@Override
	public NkyMonitoringPadEntity checkLogInInfo(String userName, String passWord) {
		NkyMonitoringPadEntity monitoringPad = null;
		//将输入的用户名、密码等信息作为条件检索客户端(PAD)[NKY_MONITORING_PAD]表
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("username", userName);
		//加密与DB匹配的密码
		String jpass = PasswordUtil.encrypt(userName, passWord, PasswordUtil.getStaticSalt());
		selCodition.put("password", jpass);
		List<NkyMonitoringPadEntity> monitoringPadList = this.findListByMyBatis(NAME_SPACE+"checkLogInInfo", selCodition);
		if(monitoringPadList.size() == 1){
			monitoringPad = monitoringPadList.get(0);
			monitoringPad.setFlg("0");//登录成功
		} else {
			monitoringPad = new  NkyMonitoringPadEntity();
			monitoringPad.setFlg("1");//登录失败
		}
		//判断检索的记录条数，若有且仅有唯一记录则获取到所属OrgCode
		
		return monitoringPad;
	}
	
	/**
	 * 下载初始化数据
	 * @param padId
	 * @return 数据的输出流
	 */
	public String downLoadInitData(String orgCode, String padId) throws IOException{
		// 取得系统绝对路径
		String syspath = ResourceUtil.getSysPath();
		// 取得文件上传根目录
	    String uploadbasepath = ResourceUtil.getConfigByName("uploadpath")+"\\files";
	    // 模板文件路径
	    String templateFilePath = syspath + uploadbasepath +"/Produce_QS_DB.db";
	    // 临时生成文件路径
	    String tempFilePath = syspath + uploadbasepath+"/"+padId+"_tmp.db";

	    InputStream in = null;
	    OutputStream out = null;
	    byte bytes[] = null;  
	    try {
	    	in = new FileInputStream(templateFilePath);
	    	out = new FileOutputStream(tempFilePath);
	    	bytes = new byte[in.available()];
	    	in.read(bytes);  
	    	out.write(bytes);
	    } catch (IOException ioe) {
	    	ioe.printStackTrace();
	    } finally {
	    	try {
	    		out.close();
	    		in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    writeDataToDb(tempFilePath, orgCode, padId);
	  
	    //File resultFile = new File(tempFilePath);
	    System.out.println("存入db完成！");
	    //return new BASE64Encoder().encode(getBytesFromFile(resultFile));   	
	    HttpServletRequest request = ContextHolderUtils.getRequest();
		String url = request.getScheme() + "://" + request.getServerName() 
				+ ":" + request.getServerPort() + request.getContextPath();
	    return url +"/" + ResourceUtil.getConfigByName("uploadpath")+"/files/"+padId+"_tmp.db";
	}
	
	/**
	 * 向临时生成的db文件中写入数据
	 * @param tempFilePath
	 * @param orgCode
	 * @param padId
	 */
	private void writeDataToDb(String tempFilePath, String orgCode, String padId) {
		  // 连接sqlite数据
		java.sql.Connection connection = null;
	    PreparedStatement pstm1 = null;
	    PreparedStatement pstm2 = null;
	    PreparedStatement pstm3 = null;
	    PreparedStatement pstm4 = null;
	    PreparedStatement pstm5 = null;
//	    PreparedStatement pstm6 = null;
//	    PreparedStatement pstm7 = null;
	    PreparedStatement pstm8 = null;
	    PreparedStatement pstm9 = null;
	    PreparedStatement pstm10 = null;
	    PreparedStatement pstm11 = null;
	    PreparedStatement pstm12 = null;
	    PreparedStatement pstm13 = null;
	    try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:/" + tempFilePath);
			// 项目列表
			String sql1 = "insert into TB_MONITORING_PROJECT ('id','projectCode','name','sampleTemplet','industryCode','plevel','type','projectBree','linkInfo','templete')" +
		    		"values(?,?,?,?,?,?,?,?,?,?)";
		    // 任务列表
			String sql2 = "insert into TB_MONITORING_TASK ('id','projectCode','taskcode','taskname','orgCode','areacode','monitoringLink','samplingCount','agrCode', 'monitorType', 'sampleArea')" +
		    		"values(?,?,?,?,?,?,?,?,?,?,?)";
		    // 任务详情
			String sql3 = "insert into TB_MONITORING_TASK_DETAILS ('id','padId','taskCount','assignTime','taskCode','taskStatus','releaseunit','endTime')" +
		    		"values(?,?,?,?,?,?,?,?)";
		    // 抽样 环节列表(取项目时设置的)
			String sql4 = "insert into TB_MONITORING_LINK_INFO ('id','code','projectId','name')" +
		    		"values(?,?,?,?)";
		    // 抽样 抽样品种(取任务时设置的)
			String sql5 = "insert into TB_MONITORING_BREED ('id','projectCode','agrName', 'agrCode')" +
		    		"values(?,?,?,?)";
//		    // 行政区划市
//			String sql6= "insert into TB_MONITORING_AREA_CITY ('id','pid','name')" +
//		    		"values(?,?,?)";
//		    // 行政区划区县
//			String sql7 = "insert into TB_MONITORING_AREA_COUNTRY ('id','pid','name')" +
//		    		"values(?,?,?)";
			// 抽样信息主表
			String sql8 = "insert into TB_SAMPLING_INFO ('id','taskCode', 'dCode','samplePath','longitude','latitude','agrCode','cityCode','countyCode','cityAndCountry','monitoringLink','monitoringLinkName',"+
					"'padId','samplingDate','unitFullcode','unitFullname','unitAddress','zipCode','legalPerson','contact','telphone','fax','remark','sampleCode','sampleName','labCode','isQualified','sampleStatus'," +
					"'detectionCode','spCode','complete','reportingDate','samplingOrgCode','printCount','detectionReportingDate','samplingAddress','samplingPersons','samplingMonadId','samplingTime')" +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 附表-例行监测
			String sql9 = "insert into TB_ROUTINE_MONITORING ('id','sampleCode','sampleSource','sampleCount','taskSource','execStanderd','samplingMonadId')" +
					"values(?,?,?,?,?,?,?)";
			// 附表-监督抽查
			String sql10 = "insert into TB_SUPERVISE_CHECK ('id','sampleCode','tradeMark','pack','specifications','flag','batchNumber','execStandard','productCer','productCerNo','certificateTime','samplingCount','samplingBaseCount','noticeDetails')" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 附表-风险普查
			String sql11 = "insert into TB_GENERAL_CHECK ('id','sampleCode','tradeMark','pack','specifications','flag','execStandard','batchNumber','productCer','productCerNo','samplingCount'," +
					"'stall','telphone','fax','unitProperties','unitName','unitAddress','zipCode','legalPerson','contacts','telphone2','fax2','samplingNo','grade','samplingCardinal')" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 附表-生鲜乳
			String sql12 = "insert into TB_FRESH_MILK ('id','sampleCode','samplingCount','samplingBaseCount','type','typeRemark','buyLicence','licenceNo','licenceRemark','navicert','navicertNo'," +
					"'navicertRemark','deliveryReceitp','deliveryReceitpRemark','direction','telphone','examinee','telphone2','samplingDate')" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 附表-畜禽
			String sql13 = "insert into TB_LIVESTOCK ('id','sampleCode','tradeMark','pack','signFlg','cargoOwner','animalOrigin','cardNumber','taskSource','samplingCount','samplingBaseCount','saveSaveSituation','samplingMode','samplingMonadId')" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    pstm1 = connection.prepareStatement(sql1);
		    pstm2 = connection.prepareStatement(sql2);
		    pstm3 = connection.prepareStatement(sql3);
		    pstm4 = connection.prepareStatement(sql4);
		    pstm5 = connection.prepareStatement(sql5);
//		    pstm6 = connection.prepareStatement(sql6);
//		    pstm7 = connection.prepareStatement(sql7);
		    pstm8 = connection.prepareStatement(sql8);
		    pstm9 = connection.prepareStatement(sql9);
		    pstm10 = connection.prepareStatement(sql10);
		    pstm11 = connection.prepareStatement(sql11);
		    pstm12 = connection.prepareStatement(sql12);
		    pstm13 = connection.prepareStatement(sql13);
		    
		 // 取server端数据列表
			List<MonitoringProjectEntity> projectList = getMonitoringProjectInfoListByOrgCode(orgCode, padId, null);
			for (MonitoringProjectEntity project : projectList) {
				String templete = project.getTemplete();// 抽样单类型
				// 存储项目
					pstm1.setString(1, ConverterUtil.getUUID());
					pstm1.setString(2, project.getProjectCode());
					pstm1.setString(3, project.getName());
					pstm1.setString(4, project.getSampleTemplet());
					pstm1.setString(5, project.getIndustryCode());
					pstm1.setString(6, project.getPlevel());
					pstm1.setString(7, project.getType());
					pstm1.setString(8, project.getProjectBreed());
					pstm1.setString(9, project.getLinkInfo());
					pstm1.setString(10, project.getTemplete());
					pstm1.execute();
					
					// 抽样环节
					List<TSType> linkInfoList = project.getLinkInfoList();
					for (TSType link : linkInfoList) {
						pstm4.setString(1, ConverterUtil.getUUID());
						pstm4.setString(2, link.getTypecode());
						pstm4.setString(3, project.getProjectCode());
						pstm4.setString(4, link.getTypename());
						pstm4.execute();
					}
					
					// 抽样品种
					List<MonitoringBreedEntity> projectBreedList = getMonitoringBreedForTask(null, project.getProjectCode());
					for (MonitoringBreedEntity mBreed : projectBreedList) {
						pstm5.setString(1, ConverterUtil.getUUID());
						pstm5.setString(2, project.getProjectCode());
						pstm5.setString(3, mBreed.getAgrName());
						pstm5.setString(4, mBreed.getAgrCode());
						pstm5.execute();
					}

					// 任务
					List<MonitoringTaskEntity> monitoringTaskList = getMonitoringTaskList(project.getProjectCode(), padId);
					for (MonitoringTaskEntity task : monitoringTaskList) {
						pstm2.setString(1, task.getId());
						pstm2.setString(2, task.getProjectCode());
						pstm2.setString(3, task.getTaskcode());
						pstm2.setString(4, task.getTaskname());
						pstm2.setString(5, task.getOrgCode());
						pstm2.setString(6, task.getAreacode());
						pstm2.setString(7, task.getMonitoringLink());
						pstm2.setInt(8, task.getSamplingCount());
						pstm2.setString(9, task.getAgrCode());
						pstm2.setString(10, task.getMonitorType());
						pstm2.setString(11, task.getSampleArea());
						pstm2.execute();
						
						// 任务详情
						MonitoringTaskDetailsEntity taskDetail = getMonitoringTaskInfoByTaskCode(task.getTaskcode(), padId);
						pstm3.setString(1, taskDetail.getId());
						pstm3.setString(2, taskDetail.getPadId());
						pstm3.setInt(3, taskDetail.getTaskCount());
						// TODO
						pstm3.setString(4, null);
						pstm3.setString(5, taskDetail.getTaskCode());
						pstm3.setString(6, taskDetail.getTaskStatus());
						pstm3.setString(7, taskDetail.getReleaseunit());
						pstm3.setString(8, taskDetail.getEndTime());
						pstm3.execute();
						
						// 抽样信息
						List<SamplingInfoPadEntity>  samplingInfoList = getFinishSamplingInfo(padId, task.getTaskcode(), project.getTemplete(), project.getProjectCode());
						for(SamplingInfoPadEntity sinfo : samplingInfoList) {
							pstm8.setString(1, sinfo.getId());
							pstm8.setString(2, sinfo.getTaskCode());
							pstm8.setString(3, sinfo.getdCode());
							pstm8.setString(4, sinfo.getSamplePath());
							pstm8.setString(5, sinfo.getLongitude());
							pstm8.setString(6, sinfo.getLatitude());
							pstm8.setString(7, sinfo.getAgrCode());
							pstm8.setString(8, sinfo.getCityCode());
							pstm8.setString(9, sinfo.getCountyCode());
							pstm8.setString(10, sinfo.getCityAndCountry());
							pstm8.setString(11, sinfo.getMonitoringLink());
							pstm8.setString(12, sinfo.getMonitoringLinkName());
							pstm8.setString(13, sinfo.getPadId());
							pstm8.setString(14, sinfo.getSamplingDate());
							pstm8.setString(15, null);
							pstm8.setString(16, sinfo.getUnitFullname());
							pstm8.setString(17, sinfo.getUnitAddress());
							pstm8.setString(18, sinfo.getZipCode());
							pstm8.setString(19, sinfo.getLegalPerson());
							pstm8.setString(20, sinfo.getContact());
							pstm8.setString(21, sinfo.getTelphone());
							pstm8.setString(22, sinfo.getFax());
							pstm8.setString(23, sinfo.getRemark());
							pstm8.setString(24, sinfo.getSampleCode());
							pstm8.setString(25, sinfo.getSampleName());
							pstm8.setString(26, sinfo.getLabCode());
							pstm8.setString(27, sinfo.getIsQualified());
							pstm8.setString(28, sinfo.getSampleStatus());
							pstm8.setString(29, sinfo.getDetectionCode());
							pstm8.setString(30, sinfo.getSpCode());
							pstm8.setString(31, sinfo.getComplete());
							pstm8.setString(32, null);// 上报时间
							pstm8.setString(33, sinfo.getSamplingOrgCode());
							pstm8.setInt(34, 0);
							pstm8.setString(35, null);// 检测上报时间
							pstm8.setString(36, sinfo.getSamplingAddress());
							pstm8.setString(37, sinfo.getSamplingPersons());
							pstm8.setString(38, sinfo.getSamplingMonadId());
							// TODO
							pstm8.setString(39, sinfo.getSamplingTime());
							pstm8.execute();
							
							if (StringUtils.equals(templete, "1")) {
								List<RoutinemonitoringEntity> subList = sinfo.getRoutinemonitoringList();
								for (RoutinemonitoringEntity routinemonitoringEntity : subList) {
									pstm9.setString(1, routinemonitoringEntity.getId());
									pstm9.setString(2, routinemonitoringEntity.getSampleCode());
									pstm9.setString(3, routinemonitoringEntity.getSampleSource());
									pstm9.setString(4, routinemonitoringEntity.getSampleCount());
									pstm9.setString(5, routinemonitoringEntity.getTaskSource());
									pstm9.setString(6, routinemonitoringEntity.getExecStanderd());
									pstm9.setString(7, routinemonitoringEntity.getSamplingMonadId());
									pstm9.execute();
								}
							} else if (StringUtils.equals(templete, "2")){
								SuperviseCheckEntity superviseCheckEntity = sinfo.getSuperviseCheckEntity();
								pstm10.setString(1, superviseCheckEntity.getId());
								pstm10.setString(2, superviseCheckEntity.getSampleCode());
								pstm10.setString(3, superviseCheckEntity.getTradeMark());
								pstm10.setString(4, superviseCheckEntity.getPack());
								pstm10.setString(5, superviseCheckEntity.getSpecifications());
								pstm10.setString(6, superviseCheckEntity.getFlag());
								pstm10.setString(7, superviseCheckEntity.getBatchNumber());
								pstm10.setString(8, superviseCheckEntity.getExecStandard());
								pstm10.setString(9, superviseCheckEntity.getProductCer());
								pstm10.setString(10, superviseCheckEntity.getProductCerNo());
								// TODO
								pstm10.setDate(11, setDateTime(superviseCheckEntity.getCertificateTime()));
								pstm10.setString(12, superviseCheckEntity.getSamplingCount());
								pstm10.setString(13, superviseCheckEntity.getSamplingBaseCount());
								pstm10.setString(14, superviseCheckEntity.getNoticeDetails());
								pstm10.execute();
							} else if (StringUtils.equals(templete, "3")){
								GeneralcheckEntity generalcheckEntity = sinfo.getGeneralcheckEntity();
								pstm11.setString(1, generalcheckEntity.getId());
								pstm11.setString(2, generalcheckEntity.getSampleCode());
								pstm11.setString(3, generalcheckEntity.getTradeMark());
								pstm11.setString(4, generalcheckEntity.getPack());
								pstm11.setString(5, generalcheckEntity.getSpecifications());
								pstm11.setString(6, generalcheckEntity.getFlag());
								pstm11.setString(7, generalcheckEntity.getExecStandard());
								pstm11.setString(8, generalcheckEntity.getBatchNumber());
								pstm11.setString(9, generalcheckEntity.getProductCer());
								pstm11.setString(10, generalcheckEntity.getProductCerNo());
								pstm11.setString(11, generalcheckEntity.getSamplingCount());
								pstm11.setString(12, generalcheckEntity.getStall());
								pstm11.setString(13, generalcheckEntity.getTelphone());
								pstm11.setString(14, generalcheckEntity.getFax());
								pstm11.setString(15, generalcheckEntity.getUnitProperties());
								pstm11.setString(16, generalcheckEntity.getUnitName());
								pstm11.setString(17, generalcheckEntity.getUnitAddress());
								pstm11.setString(18, generalcheckEntity.getZipCode());
								pstm11.setString(19, generalcheckEntity.getLegalPerson());
								pstm11.setString(20, generalcheckEntity.getContacts());
								pstm11.setString(21, generalcheckEntity.getTelphone2());
								pstm11.setString(22, generalcheckEntity.getFax2());
								pstm11.setString(23, generalcheckEntity.getSamplingNo());
								pstm11.setString(24, generalcheckEntity.getGrade());
								pstm11.setString(25, generalcheckEntity.getSamplingCardinal());
								pstm11.execute();
								
							} else if (StringUtils.equals(templete, "4")){
								NkyFreshMilkEntity nkyFreshMilkEntity = sinfo.getNkyFreshMilkEntity();
								pstm12.setString(1, nkyFreshMilkEntity.getId());
								pstm12.setString(2, nkyFreshMilkEntity.getSampleCode());
								pstm12.setString(3, nkyFreshMilkEntity.getSamplingCount());
								pstm12.setString(4, nkyFreshMilkEntity.getSamplingBaseCount());
								pstm12.setString(5, nkyFreshMilkEntity.getType());
								pstm12.setString(6, nkyFreshMilkEntity.getTypeRemark());
								pstm12.setString(7, nkyFreshMilkEntity.getBuyLicence());
								pstm12.setString(8, nkyFreshMilkEntity.getLicenceNo());
								pstm12.setString(9, nkyFreshMilkEntity.getLicenceRemark());
							    pstm12.setString(10, nkyFreshMilkEntity.getNavicert());
								pstm12.setString(11, nkyFreshMilkEntity.getNavicertNo());
								pstm12.setString(12, nkyFreshMilkEntity.getNavicertRemark());
								pstm12.setString(13, nkyFreshMilkEntity.getDeliveryReceitp());
								pstm12.setString(14, nkyFreshMilkEntity.getDeliveryReceitpRemark());
								pstm12.setString(15, nkyFreshMilkEntity.getDirection());
								pstm12.setString(16, nkyFreshMilkEntity.getTelphone());
								pstm12.setString(17, nkyFreshMilkEntity.getExaminee());
								pstm12.setString(18, nkyFreshMilkEntity.getTelphone2());
								pstm12.setDate(19, null);
								pstm12.execute();
								
							} else if (StringUtils.equals(templete, "5")){
								List<LivestockEntity> subList = sinfo.getLivestockEntityList();
								for (LivestockEntity livestockEntity : subList) {
									pstm13.setString(1, livestockEntity.getId());
									pstm13.setString(2, livestockEntity.getSampleCode());
									pstm13.setString(3, livestockEntity.getTradeMark());
									pstm13.setString(4, livestockEntity.getPack());
									pstm13.setString(5, livestockEntity.getSignFlg());
									pstm13.setString(6, livestockEntity.getCargoOwner());
									pstm13.setString(7, livestockEntity.getAnimalOrigin());
									pstm13.setString(8, livestockEntity.getCardNumber());
									pstm13.setString(9, livestockEntity.getTaskSource());
								    pstm13.setString(10, livestockEntity.getSamplingCount());
								    pstm13.setString(11, livestockEntity.getSamplingBaseCount());
									pstm13.setString(12, livestockEntity.getSaveSaveSituation());
									pstm13.setString(13, livestockEntity.getSamplingMode());
									pstm13.setString(14, livestockEntity.getSamplingMonadId());
									pstm13.execute();
								}
							}
						}
					}
					
					// 行政区划江苏省所有市
//					List<SysAreaCodeEntity> cityList = this.findListByMyBatis(NAME_SPACE+"getSysCity", new HashMap<String, Object>());
//					for (SysAreaCodeEntity city : cityList) {
//						pstm6.setString(1, city.getCode());
//						pstm6.setString(2, city.getPcode());
//						pstm6.setString(3, city.getAreaname());
//						pstm6.execute();
//					}
					// 行政区划区县
//					List<SysAreaCodeEntity> countyList = this.findListByMyBatis(NAME_SPACE+"getSysCounty", new HashMap<String, Object>());
//					for (SysAreaCodeEntity county : countyList) {
//						pstm7.setString(1, county.getCode());
//						pstm7.setString(2, county.getPcode());
//						pstm7.setString(3, county.getAreaname());
//						pstm7.execute();
//					}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm1.close();
				pstm2.close();
				pstm3.close();
				pstm4.close();
				pstm5.close();
//				pstm6.close();
//				pstm7.close();
				pstm8.close();
				pstm9.close();
				pstm10.close();
				pstm11.close();
				pstm12.close();
				pstm13.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	
	// 返回一个byte数组
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        // 获取文件大小
        long length = file.length();
		if (length > Integer.MAX_VALUE) {
		     // 文件太大，无法读取
		     throw new IOException("File is to large "+file.getName());
		}

		// 创建一个数据来保存文件数据
        byte[] bytes = new byte[(int)length];
        // 读取数据到byte数组中
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
    
    /**
     * 日期时间转换
     * @param date
     * @return
     */
    private java.sql.Date setDateTime(java.util.Date date) {
    	if (date == null) {
    		return null;
    	} else {
    		return new java.sql.Date(date.getTime());
    	}
    }
    
	/**
	 * 取得项目列表
	 */
	@Override
	public List<MonitoringProjectEntity> getMonitoringProjectInfoListByOrgCode(String orgCode, String padId, String projectName) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("orgCode", orgCode);
		selCodition.put("padId", padId);
		if (StringUtils.isNotEmpty(projectName)) {
			selCodition.put("projectName", projectName);
		}
		List<MonitoringProjectEntity> progaramList = this.findListByMyBatis(NAME_SPACE1+"getPadUserMonintorProgaram", selCodition);
		// 为每一个项目在抽样单中设置抽样环节列表
		for (MonitoringProjectEntity project : progaramList) {
				String industry = project.getIndustryCode();
				project.setLinkInfoList(getSampleMonitoringLink(industry));
				project.setTemplete(service.getSampleMonadType(project.getProjectCode()));
		}
		return progaramList;
	}

	/**
	 * 取得任务列表
	 */
	@Override
	public List<MonitoringTaskEntity> getMonitoringTaskList(String monitoringProjectEntity) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		//JSON转换成List<Objecty>
		//JSONArray ja = JSONArray.fromObject(monitoringProjectEntity);
		//List<MonitoringProjectEntity> list = JSONArray.toList(ja, MonitoringProjectEntity.class);
		//JSON转换成Object
		JSONObject jsonObject = JSONObject.fromObject(monitoringProjectEntity);        
		MonitoringProjectEntity bean = (MonitoringProjectEntity) JSONObject.toBean( jsonObject, MonitoringProjectEntity.class ); 
		// 取得项目code
		selCodition.put("projectCode",bean.getProjectCode());
		// 取得padId
		selCodition.put("padId",bean.getPadId());
		// 任务名称
		selCodition.put("taskName",bean.getTaskName());
		List<MonitoringTaskEntity> taskList = this.findListByMyBatis(NAME_SPACE2+"getMonitoringTaskList", selCodition);
		// 为每一个任务取任务详情，在抽样单中设置抽样品种列表
//		for (MonitoringTaskEntity task : taskList) {
////			String taskCode = task.getTaskcode();
////			task.setTaskDetail(getMonitoringTaskInfoByTaskCode(taskCode, bean.getPadId()));
//
//			String projectCode = task.getProjectCode();
//			task.setProjectBreedList(getMonitoringBreedForTask(null, projectCode));
//		}
		return taskList;
	}
	
	private List<MonitoringTaskEntity> getMonitoringTaskList(String projectCode, String padId) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		// 取得项目code
		selCodition.put("projectCode", projectCode);
		// 取得padId
		selCodition.put("padId", padId);
		List<MonitoringTaskEntity> taskList = this.findListByMyBatis(NAME_SPACE2+"getMonitoringTaskList", selCodition);
//		// 为每一个任务取任务详情，在抽样单中设置抽样品种列表
//		for (MonitoringTaskEntity task : taskList) {
//			String taskCode = task.getTaskcode();
//			task.setTaskDetail(getMonitoringTaskInfoByTaskCode(taskCode, padId));
//			//String projectCode = task.getProjectCode();
//			task.setProjectBreedList(getMonitoringBreedForTask(null, projectCode));
//		}
		return taskList;
	}
	
	/**
	 * 取得监测类型名称
	 */
	@Override
	public String getNameForMonitorType(String monitorType) {
		// 数据字典类型组名称
		final String groupName = "monitorType";

		if (StringUtils.isNotEmpty(monitorType)) {
			return ConverterUtil.getDictionaryName(groupName, monitorType);
		}
		return null;
	}
	
	/**
	 * 根据项目取得抽样环节列表
	 */
	@Override
    public List<TSType> getSampleMonitoringLink(String industry) {
    	return service.getPadSampleMonitoringLink(industry);
    }

	/**
	 * 取得任务详情
	 */
	@Override
	public MonitoringTaskDetailsEntity getMonitoringTaskInfoByTaskCode(String taskCode, String padId) {
		MonitoringTaskDetailsEntity detail=null;
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("taskCode", taskCode);
		selCodition.put("padId", padId);
		List<MonitoringTaskDetailsEntity> details = this.findListByMyBatis(NAME_SPACE3+"getMonitoringTaskInfoByTaskCode", selCodition);
		if(details.size() == 1){
			detail = details.get(0);
		}
		return detail;
	}
	
	/**
	 * 取得抽样品种
	 */
	@Override
    public List<MonitoringBreedEntity> getMonitoringBreedForTask(String taskCode, String projectCode) {
		List<MonitoringBreedEntity> monitoringBreedList = null;
		Map<String, Object> selCodition = new HashMap<String, Object>();
		// 如果任务code不为空,则为任务详情页面抽样品种;否则为抽样单页面,抽样品种选择列表
		if (StringUtils.isNotEmpty(taskCode)) {
			selCodition.put("taskCode", taskCode);
	    	monitoringBreedList = this.findListByMyBatis(NAME_SPACE3 + "getMonitoringBreedForTask", selCodition);
		} else {
			monitoringBreedList = this.findListByMyBatis(NAME_SPACE1+"getProjectBreed", projectCode);
			//selCodition.put("projectCode", projectCode);
			//monitoringBreedList = this.findListByMyBatis(NAME_SPACE3 + "getMonitoringBreedForSelect", selCodition);
		}
    	return monitoringBreedList;
    }
	
	/**
	 * 取得抽样地区
	 */
	@Override
    public List<String> getMonitoringAreaForTask(String taskCode) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("taskCode", taskCode);
    	List<String> monitoringLinkList = this.findListByMyBatis(NAME_SPACE3+"getMonitoringAreaForTask", selCodition);
    	return monitoringLinkList;
    }
	/**
	 * 取得抽样环节
	 */
	@Override
    public List<String> getMonitoringLinkForTask(String taskCode) {
		Map<String, Object> selCodition = new HashMap<String, Object>();
		selCodition.put("taskCode", taskCode);
    	List<String> monitoringAreaList = this.findListByMyBatis(NAME_SPACE3+"getMonitoringLinkForTask", selCodition);
    	return monitoringAreaList;
    }

	/**
	 * 保存录入样品信息
	 */
	@Override
	public int saveSamplingInfoOnDb(String samplingInfo, String sampleMonadType) {
		try {
			System.out.println(samplingInfo);
			logger.info("PAD端 传过来的 json串原文"+samplingInfo);
			ObjectMapper jsonMapper = new ObjectMapper();
			SamplingInfoPadEntity samplingInfoEntity0 = jsonMapper.readValue(samplingInfo, SamplingInfoPadEntity.class);
			SamplingInfoEntity samplingInfoEntity = replaceObjectAToB(samplingInfoEntity0);
			samplingInfoEntity.setId(null);

			// 二维码重复性check
			String dCode = null;
			if (StringUtils.equals(sampleMonadType, "1")) {//例行监测抽样单
				List<RoutinemonitoringEntity> rouList = samplingInfoEntity.getRoutinemonitoringList();
				for (RoutinemonitoringEntity rou : rouList) {
					dCode = rou.getdCode();
					int count = service.checkCode(dCode);
					if (count >= 1) {
						return 2;//重复的二维码
					}
				}
			} else if (StringUtils.equals(sampleMonadType, "5")) {//畜禽抽样单
				List<LivestockEntity> livList = samplingInfoEntity.getLivestockEntityList();
				for (LivestockEntity rou : livList) {
					dCode = rou.getdCode();
					int count = service.checkCode(dCode);
					if (count >= 1) {
						return 2;//重复的二维码
					}
				}
			} else {
				dCode = samplingInfoEntity.getdCode();
				int count = service.checkCode(dCode);
				if (count >= 1) {
					return 2; //重复的二维码
				}
			}
			
			service.addMain(samplingInfoEntity);
		} catch (Exception e) {
			logger.error("我的异常"+e.getMessage());
			e.printStackTrace();
			return 0;  //录入失败
		}
		return 1; //录入成功
	}

	/**
	 * 转发和替抽时
	 */
	@Override
	public Boolean forwardMonitoringTaskInfo(String monitoringTaskDetailsEntity1,
			             					 String padId,
			             					 int total,
			             					 String flagStr) {
		//当替抽和确认接收时  返回true;转发 因为不一定对方确认 则返回false  没有填写flagStr 也将返回false
		boolean b=false;
		if("".equals(flagStr) || flagStr==null){
			 return false;
		}else{
			//将当前自己的信息的JSON转换成 MonitoringTaskDetailsEntity
			JSONObject jsonObject = JSONObject.fromObject(monitoringTaskDetailsEntity1);        
			MonitoringTaskDetailsEntity monitoringTaskDetailsEntity 
				= (MonitoringTaskDetailsEntity) JSONObject.toBean( jsonObject, MonitoringTaskDetailsEntity.class );
			//构建 发送消息到MQ队列的主要信息 
			//需要注意和 接收时的 MQ获取服务器的用户名密码保持一致 这里统一采用PADID作为 MQ队列名称、用户名、密码
//		    MessageProductor productor =new MessageProductor();
//	    	productor.setPassword(monitoringTaskDetailsEntity.getPadId());
//	    	productor.setUsername(monitoringTaskDetailsEntity.getPadId());
//	    	productor.setQueueName(monitoringTaskDetailsEntity.getPadId());
//	    	productor.setUrl(MQ_URL);//MQ_URL和接收的时候保持一致  通过常量设置
//			ArrayList modelList = new ArrayList();
//			padMQ padInfo=new padMQ();
//		    if(flagStr.equals("1")){
//		    	//替抽时
//				List<PadTCInfo> PadTCInfoS=new ArrayList<PadTCInfo>();
//				PadTCInfo TCInfo=new PadTCInfo();
//				//替抽数量
//				TCInfo.setCount(String.valueOf(total));
//				//当前替抽PadCode
//				TCInfo.setThisPadCode(monitoringTaskDetailsEntity.getPadId());
//				//被替抽PadCode 即所选的其他PAD用户
//				TCInfo.setThePadCode(padId);
//				//当前抽样的任务Code
//				TCInfo.setTaskCode(monitoringTaskDetailsEntity.getTaskCode());
//				PadTCInfoS.add(TCInfo);
//				padInfo.setPadTCInfoS(PadTCInfoS);
//		    }if(flagStr.equals("2")){
//		    	//转发时
//		    	List<PadforwardInfo> padforwardInfoS=new ArrayList<PadforwardInfo>();
//		    	PadforwardInfo forwardInfo=new PadforwardInfo();
//		    	//当前转发PadCode
//		    	forwardInfo.setThisPadCode(monitoringTaskDetailsEntity.getPadId());
//		    	forwardInfo.setCount(String.valueOf(total));
//		    	//接收转发PadCode 即所选的其他PAD用户列表
//		    	forwardInfo.setThePadCode(padId);
//		    	//当前转发的任务Code
//		    	forwardInfo.setTaskCode(monitoringTaskDetailsEntity.getTaskCode());
//		    	padforwardInfoS.add(forwardInfo);
//		    	padInfo.setPadforwardInfoS(padforwardInfoS);
//		    }
//		    if(flagStr.equals("3")){
//		    	//确认接收时  表示转发成功
//		    	List<PadforwardInfo> padforwardInfoSuccess=new ArrayList<PadforwardInfo>();
//		    	PadforwardInfo forwardInfo=new PadforwardInfo();
//		    	//当前转发PadCode 即谁转发给我 就记谁的PAD用户
//		    	forwardInfo.setThisPadCode(padId);
//		    	forwardInfo.setCount(String.valueOf(total));
//		    	//接收转发PadCode 即当前确认人的Pad用户
//		    	forwardInfo.setThePadCode(monitoringTaskDetailsEntity.getPadId());
//		    	//当前转发的任务Code
//		    	forwardInfo.setTaskCode(monitoringTaskDetailsEntity.getTaskCode());
//		    	padforwardInfoSuccess.add(forwardInfo);
//		    	padInfo.setPadforwardInfoSuccess(padforwardInfoSuccess);
//		    } 
		    int forwardCount1 = 0;
	  		int forwardCount2 = 0;
	  		Map<String, Object> selCodition = new HashMap<String, Object>();
		    selCodition.put("userPadId", monitoringTaskDetailsEntity.getPadId());
		    selCodition.put("taskCode", monitoringTaskDetailsEntity.getTaskCode());
		    selCodition.put("padId", padId);
		    selCodition.put("total", total);
		    // 替抽数据保存
		    if(flagStr.equals("1")){			    
		  	    // 更新主动替抽的任务详情
		  	    forwardCount1 = this.updateByMyBatis(NAME_SPACE3+"updateCurrentUserTask", selCodition);
		  	    // 更新被替抽的任务详情
		  	    forwardCount2 = this.updateByMyBatis(NAME_SPACE3+"updateDesignationUserTask", selCodition);
		    }
		    // 转发确认接收数据保存
		    if(flagStr.equals("2")){
		  	    // 更新主动替抽的任务详情
		  	    forwardCount1 = this.updateByMyBatis(NAME_SPACE3+"updateCurrentUserTask", selCodition);
		    	// 若接收的pad没有任务，则增加任务
				CriteriaQuery cq = new CriteriaQuery(MonitoringTaskDetailsEntity.class);
				cq.eq("taskCode", monitoringTaskDetailsEntity.getTaskCode());
				cq.eq("padId", padId);
				cq.add();
				List<MonitoringTaskDetailsEntity> taskDetailList =  this.getListByCriteriaQuery(cq, false); 
				
				if (taskDetailList != null && taskDetailList.size() > 0) {
			  	    // 更新被替抽的任务详情
			  	    forwardCount2 = this.updateByMyBatis(NAME_SPACE3+"updateDesignationUserTask", selCodition);
				} else {					
			  	    // 增加被替抽的任务详情信息
					MonitoringTaskDetailsEntity mtde = new MonitoringTaskDetailsEntity();
					mtde.setPadId(padId);
					mtde.setAssignTime(monitoringTaskDetailsEntity.getAssignTime());
					mtde.setTaskCode(monitoringTaskDetailsEntity.getTaskCode());
					mtde.setTaskCount(total);
					mtde.setTaskStatus("0");
					this.save(mtde);
					taskDetailList =  this.getListByCriteriaQuery(cq, false); 
					forwardCount2 = taskDetailList.size();
				}

		    }
	  	    if (forwardCount1 > 0 && forwardCount1 == forwardCount2) {
	  	    	b = true;
	  	    }
//		    try {
//		    	modelList.add(padInfo);
//				//productor.sendMessage(modelList);
//				sendMessage(modelList,productor.getQueueName());
//			} catch (JMSException e) {
//				e.printStackTrace();
//			}
		}
		return b;
	}
	
	/**
	 * 取得质检机构下其他抽样员信息
	 * @return
	 */
	@Override
	public List<NkyMonitoringPadEntity> getOtherPadInfo(String padId, String taskCode) {
		List<NkyMonitoringPadEntity> padInfoList = null;
		Map<String, Object> selCodition = new HashMap<String, Object>();
		// 取得当前用户padId
		selCodition.put("padId", padId);
		selCodition.put("taskCode", taskCode);
		if (StringUtil.isEmpty(taskCode)) {
		    padInfoList = this.findListByMyBatis(NAME_SPACE3+"getAllPadList", selCodition);
		} else {
			padInfoList = this.findListByMyBatis(NAME_SPACE3+"getAllPadList1", selCodition);
		}
		return padInfoList;
	}
	
	/**
	 * 取得抽样地区行政区划全部信息
	 */
	@Override
	public String getSysMonitoringArea() {
		JSONArray jsonPro = new JSONArray();
		JSONObject jo1 = null; 
		JSONObject jo2 = null;
		JSONObject jo3 = null; 
		JSONArray jsonCity = null;
		JSONArray jsonCounty = null;
		
		Map<String, Object> selCodition = new HashMap<String, Object>();
		List<SysAreaCodeEntity> proList = this.findListByMyBatis(NAME_SPACE+"getSysMonitoringArea", selCodition);
		for (SysAreaCodeEntity prov : proList) {
			jsonCity = new JSONArray();
			jo1 = new JSONObject();
			jo1.put("code", prov.getCode());
			jo1.put("areaname", prov.getAreaname());
			selCodition.put("parentId", prov.getId());
			List<SysAreaCodeEntity> cityList = this.findListByMyBatis(NAME_SPACE+"getSysMonitoringArea", selCodition);
			//prov.setSysAreaCodeEntitys(cityList);
			for (SysAreaCodeEntity city : cityList) {
				jsonCounty = new JSONArray();
				jo2 = new JSONObject();
				jo2.put("code", city.getCode());
				jo2.put("areaname", city.getAreaname());
	

				selCodition.put("parentId", city.getId());
				List<SysAreaCodeEntity> areaList = this.findListByMyBatis(NAME_SPACE+"getSysMonitoringArea", selCodition);
				for (SysAreaCodeEntity area : areaList) {
					jo3 = new JSONObject();
					jo3.put("code", area.getCode());
					jo3.put("areaname", area.getAreaname());
					jsonCounty.add(jo3);
				}
				//city.setSysAreaCodeEntitys(areaList);
				jo2.put("jsonCounty", jsonCounty.toString());
				jsonCity.add(jo2);
			}
			jo1.put("jsonCity", jsonCity.toString());
			jsonPro.add(jo1);
		}

		return jsonPro.toString();
	}

	/**
	 * 取得已完成抽样信息列表
	 * @param padId 当前用户padId
	 * @param taskCode 任务编码
	 * @param sampleTableType 抽样单类型（1：例行监测抽样单，2：风险抽样单 /3.4.5..）
	 */
	@Override
	public List<SamplingInfoPadEntity> getFinishSamplingInfo(String padId, String taskCode, String sampleMonadType, String projectCode) {
		List<SamplingInfoPadEntity> resultList = new ArrayList<SamplingInfoPadEntity>();
		Map<String, Object> selCodition = new HashMap<String, Object>();
		// 取得padId
		selCodition.put("padId",padId);
		// 取得任务code
		selCodition.put("taskCode",taskCode);
		// 根据padID和taskCode获取当前抽样人已完成的抽样信息List
		List<String> sampleCodeList = this.findListByMyBatis(NAME_SPACE2+"getPadCompleteSampleCodeList", selCodition);

		for (String samplecode : sampleCodeList) {
			List<SamplingInfoEntity> samplingInfoList =  this.findByProperty(SamplingInfoEntity.class, "sampleCode", samplecode);
			SamplingInfoEntity samplingInfoEntity = samplingInfoList.get(0);
			// 抽样市和抽样区县回显
			String cityCode = samplingInfoEntity.getCityCode();
			String countryCode = samplingInfoEntity.getCountyCode();
			String cityName = "";
			String countryName = "";
			if (StringUtil.isNotEmpty(cityCode)) {
				cityName = this.findByProperty(SysAreaCodeEntity.class, "code", samplingInfoEntity.getCityCode()).get(0).getAreaname();
			}
			if (StringUtil.isNotEmpty(countryCode)) {
				countryName = this.findByProperty(SysAreaCodeEntity.class, "code", samplingInfoEntity.getCountyCode()).get(0).getAreaname();
			}
			samplingInfoEntity.setCityAndCountry(cityName + countryName);
			
			// 抽样环节回显
//			if (StringUtil.isNotEmpty(samplingInfoEntity.getMonitoringLink())) {
//				String monitoringLinkName = service.getNameForMonitoringLink(samplingInfoEntity.getMonitoringLink());
//				samplingInfoEntity.setMonitoringLink(monitoringLinkName);
//			}
			
			//样品名称回显
			String sampleName = service.getLastVersionAgrName(samplingInfoEntity.getAgrCode(), projectCode);
			samplingInfoEntity.setSampleName(sampleName);
			
			if (StringUtils.equals(sampleMonadType, "1")) {//例行监测抽样单
				List<RoutinemonitoringEntity> subList =  this.findByProperty(RoutinemonitoringEntity.class, "sampleCode", samplecode);
				samplingInfoEntity.setRoutinemonitoringList(subList);
	
			} else if (StringUtils.equals(sampleMonadType, "2")) {//监督抽查抽样单
				List<SuperviseCheckEntity> subList =  this.findByProperty(SuperviseCheckEntity.class, "sampleCode", samplecode);
				if (subList != null && subList.size() > 0) {
					samplingInfoEntity.setSuperviseCheckEntity(subList.get(0));
				}				
			} else if (StringUtils.equals(sampleMonadType, "3")) {//风险(普查)抽样单
				List<GeneralcheckEntity> subList =  this.findByProperty(GeneralcheckEntity.class, "sampleCode", samplecode);
				if (subList != null && subList.size() > 0) {
					samplingInfoEntity.setGeneralcheckEntity(subList.get(0));
				}				
			}  else if (StringUtils.equals(sampleMonadType, "4")) {//生鲜乳抽样单
				List<NkyFreshMilkEntity> subList =  this.findByProperty(NkyFreshMilkEntity.class, "sampleCode", samplecode);
				if (subList != null && subList.size() > 0) {
					samplingInfoEntity.setNkyFreshMilkEntity(subList.get(0));
				}				
			} else if (StringUtils.equals(sampleMonadType, "5")) {//畜禽抽样单
				List<LivestockEntity> subList =  this.findByProperty(LivestockEntity.class, "sampleCode", samplecode);
				if (subList != null && subList.size() > 0) {
					samplingInfoEntity.setLivestockEntityList(subList);
				}				
			}
			SamplingInfoPadEntity samplingInfoEntity0=replaceObjectBToA(samplingInfoEntity);
			resultList.add(samplingInfoEntity0);
		}
		
		return resultList;
	}
	
	/**
	 * 取得所有受检单位信息
	 * @return
	 */
	@Override
	//public List<NkyMonitoringSiteEntity> getAllMonitoringSite() {
	public String getAllMonitoringSite() {
		CriteriaQuery cq = new CriteriaQuery(NkyMonitoringSiteEntity.class);
		cq.add();

		List<NkyMonitoringSiteEntity> motrSiteList =  this.getListByCriteriaQuery(cq, false);
		Gson gson = new Gson();
		
		logger.info("取受检单位："+motrSiteList.size());
		long stime = System.currentTimeMillis();
		String json = gson.toJson(motrSiteList);
		long etime = System.currentTimeMillis();
		logger.info("转化时间："+(etime-stime)/1000f);
		return json;
	}
	
	/**
	 * 保存图片样品信息
	 * @param imgContentMap
	 */
	@Override
	public String saveImageForSample(String imgContentMap) {
		return service.initDataForImage(imgContentMap);
	}

	/**
	 * 修改客户端密码
	 */
	@Override
	public String modifyUserInfo(String userName, String oldPassWord,
			String newPassWord) {
		String b;
		Map<String, Object> selCodition = new HashMap<String, Object>();
		//取得用户名
		selCodition.put("username",userName);
		//取得加密与db匹配的原密码
		selCodition.put("password",PasswordUtil.encrypt(userName, oldPassWord, PasswordUtil.getStaticSalt()));
		try {
			// 通过用户名和原密码检索pad客户端表
			List<NkyMonitoringPadEntity> padList = this.findListByMyBatis(NAME_SPACE4+"checkLogInInfo", selCodition);
			if(padList.size() == 1){
				// 根据用户名修改密码
				NkyMonitoringPadEntity newEntity = padList.get(0);
				newEntity.setPassword(PasswordUtil.encrypt(userName, newPassWord, PasswordUtil.getStaticSalt()));
				this.saveOrUpdate(newEntity);
				b = "1";//修改成功 下次登录有效
			}else{
				b ="2";//原密码输入错误
			}
		} catch (Exception e) {
			b = e.toString();//其他异常
		}
		return b;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String receiverMQ(String messageJsonStr) {
		
		String returnStr="";//申明返回的MQ消息JSON变量
		
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageConsumer consumer;
      //需要注意和 发送时的 MQ获取服务器的用户名密码保持一致 这里统一采用PADID作为MQ队列名称
      //MQ_URL和接收的时候保持一致  通过常量设置
        connectionFactory = new ActiveMQConnectionFactory(messageJsonStr,messageJsonStr, MQ_URL);
        ArrayList<padMQ> voList =null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
          //需要注意和 发送时的 MQ队列名称保持一致 这里统一采用PADID作为MQ队列名称
            destination = session.createQueue(messageJsonStr);
            consumer = session.createConsumer(destination);
            while (true) {
                final ObjectMessage message = (ObjectMessage) consumer.receive();
                if (null != message) {
                	voList = (ArrayList<padMQ>) message.getObject();
                	JSONArray jsonarray = JSONArray.fromObject(voList);  
                	returnStr=jsonarray.toString();
                	return returnStr;
                } else {
                    break;
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection) {
                    connection.stop();
                    connection.close();
                }
            } catch (final Throwable ignore) {
            }
        }
        return returnStr;
    }
	public SamplingInfoEntity replaceObjectAToB(SamplingInfoPadEntity samplingInfoPadEntity){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SamplingInfoEntity samplingInfoEntity=new SamplingInfoEntity();
		/** ID */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getId())){
			samplingInfoEntity.setId(samplingInfoPadEntity.getId());
		}
		/** 监测任务 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getTaskCode())){
			samplingInfoEntity.setTaskCode(samplingInfoPadEntity.getTaskCode());
		}
		/** 二维码 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getdCode())){
			samplingInfoEntity.setdCode(samplingInfoPadEntity.getdCode());
		}
		/** 样品图片 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplePath())){
			samplingInfoEntity.setSamplePath(samplingInfoPadEntity.getSamplePath());
		}
		/** 经度地理坐标 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getLongitude())){
			samplingInfoEntity.setLongitude(samplingInfoPadEntity.getLongitude());
		}
		/** 纬度地理坐标 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getLatitude())){
			samplingInfoEntity.setLatitude(samplingInfoPadEntity.getLatitude());
		}
		/** 农产品code */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getAgrCode())){
			samplingInfoEntity.setAgrCode(samplingInfoPadEntity.getAgrCode());
		}
		/** 抽样地市 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getCityCode())){
			samplingInfoEntity.setCityCode(samplingInfoPadEntity.getCityCode());
		}
		/** 抽样环节 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getMonitoringLink())){
			samplingInfoEntity.setMonitoringLink(samplingInfoPadEntity.getMonitoringLink());
		}
		/** 抽样人(Pad) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getPadId())){
			samplingInfoEntity.setPadId(samplingInfoPadEntity.getPadId());
		}
		/** 抽样时间 */
		if(samplingInfoPadEntity.getSamplingDate()!=null){
			Date sdate = null;
			try {
				sdate = sdf.parse(samplingInfoPadEntity.getSamplingDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			samplingInfoEntity.setSamplingDate(sdate);
		}
		/** 抽样时间(导出用) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingDateStr())){
			samplingInfoEntity.setSamplingDateStr(samplingInfoPadEntity.getSamplingDateStr());
		}
		/** 单位全称(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getUnitFullname())){
			samplingInfoEntity.setUnitFullname(samplingInfoPadEntity.getUnitFullname());
		}
		/** 通讯地址(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getUnitAddress())){
			samplingInfoEntity.setUnitAddress(samplingInfoPadEntity.getUnitAddress());
		}
		/** 邮编(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getZipCode())){
			samplingInfoEntity.setZipCode(samplingInfoPadEntity.getZipCode());
		}
		/** 法定代表人(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getLegalPerson())){
			samplingInfoEntity.setLegalPerson(samplingInfoPadEntity.getLegalPerson());
		}
		/** 联系人(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getContact())){
			samplingInfoEntity.setContact(samplingInfoPadEntity.getContact());
		}
		/** 电话(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getTelphone())){
			samplingInfoEntity.setTelphone(samplingInfoPadEntity.getTelphone());
		}
		/** 传真(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getFax())){
			samplingInfoEntity.setFax(samplingInfoPadEntity.getFax());
		}
		/** 备注 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getRemark())){
			samplingInfoEntity.setRemark(samplingInfoPadEntity.getRemark());
		}
		/** 样品编号 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSampleCode())){
			samplingInfoEntity.setSampleCode(samplingInfoPadEntity.getSampleCode());
		}
		/** 实验室编码 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getLabCode())){
			samplingInfoEntity.setLabCode(samplingInfoPadEntity.getLabCode());
		}
		/** 样品是否合格 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getIsQualified())){
			samplingInfoEntity.setIsQualified(samplingInfoPadEntity.getIsQualified());
		}
		/** 抽样单状态 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSampleStatus())){
			samplingInfoEntity.setSampleStatus(samplingInfoPadEntity.getSampleStatus());
		}
		/** 检测机构 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getDetectionCode())){
			samplingInfoEntity.setDetectionCode(samplingInfoPadEntity.getDetectionCode());
		}
		/** 制样编码 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSpCode())){
			samplingInfoEntity.setSpCode(samplingInfoPadEntity.getSpCode());
		}
		/** 抽样区县 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getCountyCode())){
			samplingInfoEntity.setCountyCode(samplingInfoPadEntity.getCountyCode());
		}
		/** 检测项目名称 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getProjectName())){
			samplingInfoEntity.setProjectName(samplingInfoPadEntity.getProjectName());
		}
		/** 样品名称(搜索条件) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSampleName())){
			samplingInfoEntity.setSampleName(samplingInfoPadEntity.getSampleName());
		}
		/**项目编码(搜索条件)*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getProjectCode())){
			samplingInfoEntity.setProjectCode(samplingInfoPadEntity.getProjectCode());
		}
		/** 信息完整度*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getComplete())){
			samplingInfoEntity.setComplete(samplingInfoPadEntity.getComplete());
		}
		/** 上报时间*/
		if(samplingInfoPadEntity.getReportingDate()!=null){
			samplingInfoEntity.setReportingDate(samplingInfoPadEntity.getReportingDate());
		}
		/** 抽样单位*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingOrgCode())){
			samplingInfoEntity.setSamplingOrgCode(samplingInfoPadEntity.getSamplingOrgCode());
		}
		/** 监测类型*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getMonitorType())){
			samplingInfoEntity.setMonitorType(samplingInfoPadEntity.getMonitorType());
		}
		/** 年度*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getYear())){
			samplingInfoEntity.setYear(samplingInfoPadEntity.getYear());
		}
		/**质检机构code(搜索条件) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getOrgCode())){
			samplingInfoEntity.setOrgCode(samplingInfoPadEntity.getOrgCode());
		}
		/** 抽样详细地址*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingAddress())) {
			samplingInfoEntity.setSamplingAddress(samplingInfoPadEntity.getSamplingAddress());
		}
		/** 抽样人员姓名*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingPersons())) {
			samplingInfoEntity.setSamplingPersons(samplingInfoPadEntity.getSamplingPersons());
		}
		/** 抽样单Id*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingMonadId())) {
			samplingInfoEntity.setSamplingMonadId(samplingInfoPadEntity.getSamplingMonadId());
		}
		/** 样品填报时间*/
		if(samplingInfoPadEntity.getSamplingTime() != null) {
			samplingInfoEntity.setSamplingTime(samplingInfoPadEntity.getSamplingTime());
		}
		/** 受检单位编码*/
		if(samplingInfoPadEntity.getUnitFullcode() != null) {
			samplingInfoEntity.setUnitFullcode(samplingInfoPadEntity.getUnitFullcode());
		}
		/**打印数量 */
			samplingInfoEntity.setPrintCount(samplingInfoPadEntity.getPrintCount());
		/** 图片内容*/
		if(samplingInfoPadEntity.getImgContent()!=null){
			samplingInfoEntity.setImgContent(samplingInfoPadEntity.getImgContent());
		}
		/** 地区(导出用)*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getCityAndCountry())){
			samplingInfoEntity.setCityAndCountry(samplingInfoPadEntity.getCityAndCountry());
		}
		/** 例行监测类信息-- */
		if(samplingInfoPadEntity.getRoutinemonitoringList()!=null && samplingInfoPadEntity.getRoutinemonitoringList().size() > 0){
			samplingInfoEntity.setRoutinemonitoringList(samplingInfoPadEntity.getRoutinemonitoringList());
		}
		/** 普查类信息 **/
		if(samplingInfoPadEntity.getGeneralcheckEntity()!=null){
			samplingInfoEntity.setGeneralcheckEntity(samplingInfoPadEntity.getGeneralcheckEntity());
		}
		/** 监督抽查类信息 */
		if(samplingInfoPadEntity.getSuperviseCheckEntity()!=null){
			samplingInfoEntity.setSuperviseCheckEntity(samplingInfoPadEntity.getSuperviseCheckEntity());
		}
		/** 生鲜乳实体 */
		if(samplingInfoPadEntity.getNkyFreshMilkEntity()!=null){
			samplingInfoEntity.setNkyFreshMilkEntity(samplingInfoPadEntity.getNkyFreshMilkEntity());
		}
		/** 畜禽类信息 */
		if(samplingInfoPadEntity.getLivestockEntityList() != null && samplingInfoPadEntity.getLivestockEntityList().size() > 0){
			samplingInfoEntity.setLivestockEntityList(samplingInfoPadEntity.getLivestockEntityList());
		}
		return samplingInfoEntity;
	}
	public SamplingInfoPadEntity replaceObjectBToA(SamplingInfoEntity samplingInfoPadEntity){
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String url = request.getScheme() + "://" + request.getServerName() 
				+ ":" + request.getServerPort() + request.getContextPath();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SamplingInfoPadEntity samplingInfoEntity=new SamplingInfoPadEntity();
		/** ID */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getId())){
			samplingInfoEntity.setId(samplingInfoPadEntity.getId());
		}
		/** 监测任务 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getTaskCode())){
			samplingInfoEntity.setTaskCode(samplingInfoPadEntity.getTaskCode());
		}
		/** 二维码 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getdCode())){
			samplingInfoEntity.setdCode(samplingInfoPadEntity.getdCode());
		}
		/** 样品图片 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplePath())){
			samplingInfoEntity.setSamplePath(samplingInfoPadEntity.getSamplePath());
			samplingInfoEntity.setServerUrlPathImg(url+samplingInfoPadEntity.getSamplePath());
		}
		/** 经度地理坐标 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getLongitude())){
			samplingInfoEntity.setLongitude(samplingInfoPadEntity.getLongitude());
		}
		/** 纬度地理坐标 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getLatitude())){
			samplingInfoEntity.setLatitude(samplingInfoPadEntity.getLatitude());
		}
		/** 农产品code */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getAgrCode())){
			samplingInfoEntity.setAgrCode(samplingInfoPadEntity.getAgrCode());
		}
		/** 抽样地市 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getCityCode())){
			samplingInfoEntity.setCityCode(samplingInfoPadEntity.getCityCode());
		}
		/** 抽样环节名称 */
		if (StringUtil.isNotEmpty(samplingInfoPadEntity.getMonitoringLink())) {
			String monitoringLinkName = service.getNameForMonitoringLink(samplingInfoPadEntity.getMonitoringLink());
			logger.info("抽样环节名称："+monitoringLinkName);
			samplingInfoEntity.setMonitoringLinkName(monitoringLinkName);
		}
		/** 抽样环节 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getMonitoringLink())){
			samplingInfoEntity.setMonitoringLink(samplingInfoPadEntity.getMonitoringLink());
		}
		/** 抽样人(Pad) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getPadId())){
			samplingInfoEntity.setPadId(samplingInfoPadEntity.getPadId());
		}
		/** 抽样时间 */
		if(samplingInfoPadEntity.getSamplingDate()!=null){
			samplingInfoEntity.setSamplingDate(sdf1.format(samplingInfoPadEntity.getSamplingDate()));
		}
		/** 抽样时间(导出用) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingDateStr())){
			samplingInfoEntity.setSamplingDateStr(samplingInfoPadEntity.getSamplingDateStr());
		}
		/** 单位全称(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getUnitFullname())){
			samplingInfoEntity.setUnitFullname(samplingInfoPadEntity.getUnitFullname());
		}
		/** 通讯地址(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getUnitAddress())){
			samplingInfoEntity.setUnitAddress(samplingInfoPadEntity.getUnitAddress());
		}
		/** 邮编(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getZipCode())){
			samplingInfoEntity.setZipCode(samplingInfoPadEntity.getZipCode());
		}
		/** 法定代表人(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getLegalPerson())){
			samplingInfoEntity.setLegalPerson(samplingInfoPadEntity.getLegalPerson());
		}
		/** 联系人(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getContact())){
			samplingInfoEntity.setContact(samplingInfoPadEntity.getContact());
		}
		/** 电话(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getTelphone())){
			samplingInfoEntity.setTelphone(samplingInfoPadEntity.getTelphone());
		}
		/** 传真(受检) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getFax())){
			samplingInfoEntity.setFax(samplingInfoPadEntity.getFax());
		}
		/** 备注 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getRemark())){
			samplingInfoEntity.setRemark(samplingInfoPadEntity.getRemark());
		}
		/** 样品编号 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSampleCode())){
			samplingInfoEntity.setSampleCode(samplingInfoPadEntity.getSampleCode());
		}
		/** 实验室编码 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getLabCode())){
			samplingInfoEntity.setLabCode(samplingInfoPadEntity.getLabCode());
		}
		/** 样品是否合格 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getIsQualified())){
			samplingInfoEntity.setIsQualified(samplingInfoPadEntity.getIsQualified());
		}
		/** 抽样单状态 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSampleStatus())){
			samplingInfoEntity.setSampleStatus(samplingInfoPadEntity.getSampleStatus());
		}
		/** 检测机构 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getDetectionCode())){
			samplingInfoEntity.setDetectionCode(samplingInfoPadEntity.getDetectionCode());
		}
		/** 制样编码 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSpCode())){
			samplingInfoEntity.setSpCode(samplingInfoPadEntity.getSpCode());
		}
		/** 抽样区县 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getCountyCode())){
			samplingInfoEntity.setCountyCode(samplingInfoPadEntity.getCountyCode());
		}
		/** 检测项目名称 */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getProjectName())){
			samplingInfoEntity.setProjectName(samplingInfoPadEntity.getProjectName());
		}
		/** 样品名称(搜索条件) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSampleName())){
			samplingInfoEntity.setSampleName(samplingInfoPadEntity.getSampleName());
		}
		/**项目编码(搜索条件)*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getProjectCode())){
			samplingInfoEntity.setProjectCode(samplingInfoPadEntity.getProjectCode());
		}
		/** 信息完整度*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getComplete())){
			samplingInfoEntity.setComplete(samplingInfoPadEntity.getComplete());
		}
		/** 上报时间*/
		if(samplingInfoPadEntity.getReportingDate()!=null){
			samplingInfoEntity.setReportingDate(samplingInfoPadEntity.getReportingDate());
		}
		/** 抽样单位*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingOrgCode())){
			samplingInfoEntity.setSamplingOrgCode(samplingInfoPadEntity.getSamplingOrgCode());
		}
		/** 监测类型*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getMonitorType())){
			samplingInfoEntity.setMonitorType(samplingInfoPadEntity.getMonitorType());
		}
		/** 年度*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getYear())){
			samplingInfoEntity.setYear(samplingInfoPadEntity.getYear());
		}
		/**质检机构code(搜索条件) */
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getOrgCode())){
			samplingInfoEntity.setOrgCode(samplingInfoPadEntity.getOrgCode());
		}
		/** 抽样详细地址*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingAddress())) {
			samplingInfoEntity.setSamplingAddress(samplingInfoPadEntity.getSamplingAddress());
		}
		/** 抽样人员姓名*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingPersons())) {
			samplingInfoEntity.setSamplingPersons(samplingInfoPadEntity.getSamplingPersons());
		}
		/** 抽样单Id*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getSamplingMonadId())) {
			samplingInfoEntity.setSamplingMonadId(samplingInfoPadEntity.getSamplingMonadId());
		}
		/** 样品填报时间*/
		if(samplingInfoPadEntity.getSamplingTime() != null) {
			samplingInfoEntity.setSamplingTime(samplingInfoPadEntity.getSamplingTime());
		}
		/** 受检单位编码*/
		if(samplingInfoPadEntity.getUnitFullcode() != null) {
			samplingInfoEntity.setUnitFullcode(samplingInfoPadEntity.getUnitFullcode());
		}
		/**打印数量 */
			samplingInfoEntity.setPrintCount(samplingInfoPadEntity.getPrintCount());
		/** 图片内容*/
		if(samplingInfoPadEntity.getImgContent()!=null){
			samplingInfoEntity.setImgContent(samplingInfoPadEntity.getImgContent());
		}
		/** 地区(导出用)*/
		if(StringUtils.isNotEmpty(samplingInfoPadEntity.getCityAndCountry())){
			samplingInfoEntity.setCityAndCountry(samplingInfoPadEntity.getCityAndCountry());
		}
		/** 例行监测类信息-- */
		if(samplingInfoPadEntity.getRoutinemonitoringList()!=null && samplingInfoPadEntity.getRoutinemonitoringList().size() > 0){
			samplingInfoEntity.setRoutinemonitoringList(samplingInfoPadEntity.getRoutinemonitoringList());
		}
		/** 普查类信息 **/
		if(samplingInfoPadEntity.getGeneralcheckEntity()!=null){
			samplingInfoEntity.setGeneralcheckEntity(samplingInfoPadEntity.getGeneralcheckEntity());
		}
		/** 监督抽查类信息 */
		if(samplingInfoPadEntity.getSuperviseCheckEntity()!=null){
			samplingInfoEntity.setSuperviseCheckEntity(samplingInfoPadEntity.getSuperviseCheckEntity());
		}
		/** 生鲜乳实体 */
		if(samplingInfoPadEntity.getNkyFreshMilkEntity()!=null){
			samplingInfoEntity.setNkyFreshMilkEntity(samplingInfoPadEntity.getNkyFreshMilkEntity());
		}
		/** 畜禽类信息 */
		if(samplingInfoPadEntity.getLivestockEntityList() != null && samplingInfoPadEntity.getLivestockEntityList().size() > 0){
			samplingInfoEntity.setLivestockEntityList(samplingInfoPadEntity.getLivestockEntityList());
		}
		return samplingInfoEntity;
	}

	
	/**
	 * 二维码验证
	 */
	@Override
	public String checkDcode(String dCode, String projectCode) {
		String message = "";
		// 验证条码为系统生成过的条码
		CriteriaQuery cq = new CriteriaQuery(TwoDimensionEntity.class);
		cq.eq("title", dCode);
		cq.eq("projectCode", projectCode);
		cq.add();
		List<TwoDimensionEntity> twoCode = systemService.getListByCriteriaQuery(cq, false);
		//List<TwoDimensionEntity> twoCode = systemService.findByProperty(TwoDimensionEntity.class, "title", dCode);
		if (twoCode.size() == 0) {
			message = "该条码不是系统生成的条码,请核实!";
		} else {
			int count = this.service.checkCode(dCode);
			if (count != 0) {
				message = "该条码已经使用,请核实!";
			}
		}
		return message;
	}
	
	@Override
	public String getWebContextPath(String type){
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String url = request.getScheme() + "://" + request.getServerName() 
				+ ":" + request.getServerPort() + request.getContextPath();
		if ("0".equals(type)) {
			return url;
		} else if ("1".equals(type)) {			
			return url +"/" + ResourceUtil.getConfigByName("uploadpath")+"/images";
		} else {
			return url;
		}	
	}
	
}

