package com.hippo.nky.service.impl.monitoring;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.monitoring.NkyMonitoringSiteEntity;
import com.hippo.nky.entity.system.SysAreaCodeEntity;
import com.hippo.nky.service.monitoring.NkyMonitoringSiteServiceI;

@Service("nkyMonitoringSiteService")
@Transactional
public class NkyMonitoringSiteServiceImpl extends CommonServiceImpl implements NkyMonitoringSiteServiceI {
	
	/**
	 * 取得sqlite数据库连接
	 * @return
	 */
	private java.sql.Connection getConn() {
		java.sql.Connection connection = null;
		// 取得系统绝对路径
		String syspath = ResourceUtil.getSysPath();
		// 取得文件上传根目录
	    String uploadbasepath = ResourceUtil.getConfigByName("uploadpath")+"\\files";
	    // 模板文件路径
	    String templateFilePath = syspath + uploadbasepath +"/Produce_QS_DB.db";
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:/" + templateFilePath);
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			
		}
		return connection;
	}
	
	/**
	 * 保存数据到数据库模板文件
	 * @param entity
	 */
	@Override
	public void saveDataToTemplate(NkyMonitoringSiteEntity entity) {
		java.sql.Connection connection = getConn();
		PreparedStatement pstm = null;
		String sql = "insert into TB_MONITORING_SITE ('id','code','name','legalPerson','zipcode','address','contact','areacode','areacode2'," +
				"'monitoringLink','enterprise','scale','unit','longitude','latitude','contactPerson','fax','townandstreet','scaleType','scaleUnit')" +
	    		"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, entity.getId());
			pstm.setString(2, entity.getCode());
			pstm.setString(3, entity.getName());
			pstm.setString(4, entity.getLegalPerson());
			pstm.setString(5, entity.getZipcode());
			pstm.setString(6, entity.getAddress());
			pstm.setString(7, entity.getContact());
			pstm.setString(8, entity.getAreacode());
			pstm.setString(9, entity.getAreacode2());
			pstm.setString(10, entity.getMonitoringLink());
			pstm.setString(11, entity.getEnterprise());
			pstm.setString(12, entity.getScale());
			pstm.setString(13, entity.getUnit());
			pstm.setString(14, entity.getLongitude());
			pstm.setString(15, entity.getLatitude());
			pstm.setString(16, entity.getContactPerson());
			pstm.setString(17, entity.getFax());
			pstm.setString(18, entity.getTownandstreet());
			pstm.setString(19, entity.getScaletype());
			pstm.setString(20, entity.getScaleunit());
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 更新数据到数据库模板文件
	 * @param entity
	 */
	@Override
	public void updateDataForTemplate(NkyMonitoringSiteEntity entity) {
		java.sql.Connection connection = getConn();
		PreparedStatement pstm = null;
		String sql = "update TB_MONITORING_SITE set code=?,name=?,legalPerson=?,zipcode=?,address=?,contact=?,areacode=?,areacode2=?," +
				"monitoringLink=?,enterprise=?,scale=?,unit=?,longitude=?,latitude=?,contactPerson=?,fax=?,townandstreet=?,scaleType=?,scaleUnit=?" +
				"where id=?";
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, entity.getCode());
			pstm.setString(2, entity.getName());
			pstm.setString(3, entity.getLegalPerson());
			pstm.setString(4, entity.getZipcode());
			pstm.setString(5, entity.getAddress());
			pstm.setString(6, entity.getContact());
			pstm.setString(7, entity.getAreacode());
			pstm.setString(8, entity.getAreacode2());
			pstm.setString(9, entity.getMonitoringLink());
			pstm.setString(10, entity.getEnterprise());
			pstm.setString(11, entity.getScale());
			pstm.setString(12, entity.getUnit());
			pstm.setString(13, entity.getLongitude());
			pstm.setString(14, entity.getLatitude());
			pstm.setString(15, entity.getContactPerson());
			pstm.setString(16, entity.getFax());
			pstm.setString(17, entity.getTownandstreet());
			pstm.setString(18, entity.getScaletype());
			pstm.setString(19, entity.getScaleunit());
			pstm.setString(20, entity.getId());
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除数据到数据库模板文件
	 * @param entity
	 */
	@Override
	public void deleteDataForTemplate(NkyMonitoringSiteEntity entity) {
		java.sql.Connection connection = getConn();
		PreparedStatement pstm = null;
		String sql = "delete from TB_MONITORING_SITE where id=?";
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, entity.getId());
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 导出功能
	 * @param paramMap
	 * @returnexportExcel
	 */
	@Override
	public List<Map<String, Object>> exportExcelForMonitoringSite(Map<String, Object> paramMap) { 
		String hql = " from NkyMonitoringSiteEntity where 1=1 ";
		if(paramMap.get("code") !=null && !"".equals(paramMap.get("code"))){
			hql += " and code = '" + String.valueOf(paramMap.get("code"))+"' ";
		}
		if(paramMap.get("name") !=null && !"".equals(paramMap.get("name"))){
			hql += " and name like '%" + String.valueOf(paramMap.get("name"))+"%' ";
		}
		if(paramMap.get("areacode") !=null && !"".equals(paramMap.get("areacode"))){
			hql += " and areacode = '" + String.valueOf(paramMap.get("areacode"))+"' ";
		}
		if(paramMap.get("areacode2") !=null && !"".equals(paramMap.get("areacode2"))){
			hql += " and areacode2 = '" + String.valueOf(paramMap.get("areacode2"))+"' ";
		}
		if(paramMap.get("monitoringLink") !=null && !"".equals(paramMap.get("monitoringLink"))){
			hql += " and monitoringLink = '" + String.valueOf(paramMap.get("monitoringLink"))+"' ";
		}
		
		List<NkyMonitoringSiteEntity> list = this.findHql(hql);
		List<SysAreaCodeEntity> acList = this.findHql("from SysAreaCodeEntity ");

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		//受检单位代码、受检单位名称、法定代表人或负责人、邮编、详细地址、联系电话、所属区域、监测环节、企业性质、企业规模、主管单位
		for(int i=0;i<list.size();i++){
			NkyMonitoringSiteEntity monitoringSiteEntity = list.get(i);
			LinkedHashMap<String, Object> lhm = new LinkedHashMap<String, Object>();
			lhm.put("rn", i+1);
			lhm.put("code", monitoringSiteEntity.getCode());
			lhm.put("name", monitoringSiteEntity.getName());
			lhm.put("legalPerson", monitoringSiteEntity.getLegalPerson());
			lhm.put("zipcode", monitoringSiteEntity.getZipcode());
			lhm.put("address", monitoringSiteEntity.getAddress());
			lhm.put("contact", monitoringSiteEntity.getContact());
			lhm.put("areacode", getAreaCodeName(acList,monitoringSiteEntity.getAreacode())+getAreaCodeName(acList,monitoringSiteEntity.getAreacode2()));
			lhm.put("monitoringLink", getMonitoringLinkName(monitoringSiteEntity.getMonitoringLink()));
			lhm.put("enterprise", monitoringSiteEntity.getEnterprise());
			lhm.put("scale", monitoringSiteEntity.getScale());
			lhm.put("unit", monitoringSiteEntity.getUnit());
			mapList.add(lhm);
		}
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		//受检单位代码、受检单位名称、法定代表人或负责人、邮编、详细地址、联系电话、所属区域、监测环节、企业性质、企业规模、主管单位
		headerMap.put("rn", "title#KV#序号#EM#width#KV#10");
		headerMap.put("code", "title#KV#受检单位代码#EM#width#KV#30");	
		headerMap.put("name", "title#KV#受检单位名称#EM#width#KV#30");
		headerMap.put("legalPerson", "title#KV#法定代表人或负责人#EM#width#KV#20");
		headerMap.put("zipcode", "title#KV#邮编#EM#width#KV#20");
		headerMap.put("address", "title#KV#详细地址#EM#width#KV#50");
		headerMap.put("contact", "title#KV#联系电话#EM#width#KV#30");
		headerMap.put("areacode", "title#KV#所属区域#EM#width#KV#30");
		headerMap.put("monitoringLink", "title#KV#监测环节#EM#width#KV#30");
		headerMap.put("enterprise", "title#KV#企业性质#EM#width#KV#30");
		headerMap.put("scale", "title#KV#企业规模#EM#width#KV#30");
		headerMap.put("unit", "title#KV#主管单位#EM#width#KV#30");

		mapList.add(0, headerMap);
		
		return mapList;
	}
	
	/**
	 * 数据字典
	 * 
	 * @param industryCode
	 * @return
	 */
	private static String getMonitoringLinkName(String code) {
		String returnStr = "";
		List<TSType> types = TSTypegroup.allTypes.get("allmonlink");
		if (types != null) {
			for (TSType type : types) {
				if ((type.getTypecode().equals(code))) {
					returnStr = type.getTypename();
				}
			}
		}
		return returnStr;
	}
	
	/**
	 * 地区兑换
	 * 
	 * @param industryCode
	 * @return
	 */
	private static String getAreaCodeName(List<SysAreaCodeEntity> arcaCodeList,String code) {
		String returnStr = "";
		if (code != null && !"".equals(code)) {
			for (SysAreaCodeEntity entity : arcaCodeList) {
				if ((entity.getCode().equals(code))) {
					returnStr = entity.getAreaname();
				}
			}
		}
		return returnStr;
	}
}