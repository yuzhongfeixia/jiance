package com.hippo.nky.service.impl.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.monitoring.NkyMonitoringPadEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.service.organization.OrganizationServiceI;

@Service("organizationService")
@Transactional
public class OrganizationServiceImpl extends CommonServiceImpl implements OrganizationServiceI {
	public static final String NAME_SPACE = "com.hippo.nky.entity.sample.SamplingInfoEntity.";
	
	public JSONObject getDatagrid(OrganizationEntity organization, DataGrid dataGrid){

		Map<String, Object> selCodition = new HashMap<String, Object>();
		setBeginAndEnd(dataGrid, selCodition);
		
		OrganizationEntity org = this.getEntity(OrganizationEntity.class, ResourceUtil.getSessionUserName().getTSDepart().getId());
		if (org != null) {// 管理部门登录，则为空
			selCodition.put("samplingOrgCode", org.getCode());
		}
		if (StringUtils.isNotEmpty(organization.getCode())) {
			selCodition.put("code", organization.getCode());
		}
		if (StringUtils.isNotEmpty(organization.getOgrname())) {
			selCodition.put("ogrname", organization.getOgrname());
		}
		if (StringUtils.isNotEmpty(organization.getContactstel())) {
			selCodition.put("contactstel", organization.getContactstel());
		}
		if (StringUtils.isNotEmpty(organization.getLeader())) {
			selCodition.put("leader", organization.getLeader());
		}

//		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
		Integer iCount = (Integer)this.getObjectByMyBatis(NAME_SPACE+"getOrganizationCount", selCodition);
		
		List<Map<String, Object>> mapList = this.findListByMyBatis(NAME_SPACE+"getOrganizationInfo", selCodition);
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("code")
				,new Db2Page("ogrname")
				,new Db2Page("leader")
				,new Db2Page("contacts")
				,new Db2Page("contactstel")
				,new Db2Page("rn")
		};
		
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList, iCount.intValue(),dataGrid.getsEcho() , db2Pages);
		return jObject;
	}
	
	// 设置查询的开始位置和结尾位置
	public void setBeginAndEnd(DataGrid dataGrid ,Map<String,Object> selCodition){
		int rows = dataGrid.getRows();
		int page = dataGrid.getPage();
		int beginIndex = (page-1)*rows;
		int endIndex = beginIndex+rows;
		selCodition.put("beginIndex", beginIndex);
		selCodition.put("endIndex", endIndex);	
	}

	/**
	 * 导出功能
	 * @param paramMap
	 * @returnexportExcel
	 */
	@Override
	public List<Map<String, Object>> exportExcelForOrganization(Map<String, Object> paramMap) { 
		String hql = " from OrganizationEntity where 1=1 ";
		if(paramMap.get("code") !=null && !"".equals(paramMap.get("code"))){
			hql += " and code = '" + String.valueOf(paramMap.get("code"))+"' ";
		}
		if(paramMap.get("ogrname") !=null && !"".equals(paramMap.get("ogrname"))){
			hql += " and ogrname like '%" + String.valueOf(paramMap.get("ogrname"))+"%' ";
		}
		if(paramMap.get("leader") !=null && !"".equals(paramMap.get("leader"))){
			hql += " and leader like '%" + String.valueOf(paramMap.get("leader"))+"%' ";
		}
		if(paramMap.get("contactstel") !=null && !"".equals(paramMap.get("contactstel"))){
			hql += " and contactstel like '%" + String.valueOf(paramMap.get("contactstel"))+"%' ";
		}
		
		//code,ogrname,msmtcertificate,inscertificate,leader,leadertel,contacts,contactstel,email,fax,zipcode,address
		List<OrganizationEntity> list = this.findHql(hql);

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for(int i=0;i<list.size();i++){
			OrganizationEntity organizationEntity = list.get(i);
			LinkedHashMap<String, Object> lhm = new LinkedHashMap<String, Object>();
			lhm.put("rn", i+1);
			lhm.put("code", organizationEntity.getCode());
			lhm.put("ogrname", organizationEntity.getOgrname());
			lhm.put("msmtcertificate", organizationEntity.getMsmtcertificate());
			lhm.put("inscertificate", organizationEntity.getInscertificate());
			lhm.put("leader", organizationEntity.getLeader());
			lhm.put("leadertel", organizationEntity.getLeadertel());
			lhm.put("contacts", organizationEntity.getContacts());
			lhm.put("contactstel", organizationEntity.getContactstel());
			lhm.put("email", organizationEntity.getEmail());
			lhm.put("fax", organizationEntity.getFax());
			lhm.put("zipcode", organizationEntity.getZipcode());
			lhm.put("address", organizationEntity.getAddress());
			mapList.add(lhm);
		}
		// 设置表头
		LinkedHashMap<String, Object> headerMap = new LinkedHashMap<String, Object>();
		
		headerMap.put("rn", "title#KV#序号#EM#width#KV#10");
		headerMap.put("code", "title#KV#机构代码#EM#width#KV#30");	
		headerMap.put("ogrname", "title#KV#机构名称#EM#width#KV#30");
		headerMap.put("msmtcertificate", "title#KV#计量认定证书#EM#width#KV#50");
		headerMap.put("inscertificate", "title#KV#机构考核证书#EM#width#KV#20");
		headerMap.put("leader", "title#KV#负责人#EM#width#KV#20");
		headerMap.put("leadertel", "title#KV#负责人电话#EM#width#KV#30");
		headerMap.put("contacts", "title#KV#联系人#EM#width#KV#30");
		headerMap.put("contactstel", "title#KV#联系人电话#EM#width#KV#30");
		headerMap.put("email", "title#KV#电子邮箱#EM#width#KV#30");
		headerMap.put("fax", "title#KV#传真#EM#width#KV#30");
		headerMap.put("zipcode", "title#KV#邮编#EM#width#KV#30");
		headerMap.put("address", "title#KV#地址#EM#width#KV#30");
		mapList.add(0, headerMap);
		
		return mapList;
	}

}