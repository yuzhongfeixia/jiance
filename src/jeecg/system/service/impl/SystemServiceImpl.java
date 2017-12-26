package jeecg.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jeecg.system.pojo.base.TSFunction;
import jeecg.system.pojo.base.TSIcon;
import jeecg.system.pojo.base.TSLog;
import jeecg.system.pojo.base.TSOnline;
import jeecg.system.pojo.base.TSRole;
import jeecg.system.pojo.base.TSRoleFunction;
import jeecg.system.pojo.base.TSRoleUser;
import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;
import jeecg.system.pojo.base.TSUser;
import jeecg.system.service.SystemService;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.system.SysAreaCodeEntity;

@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
	public TSUser checkUserExits(TSUser user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}

	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short loglevel, Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOperatetime(DataUtils.gettimestamp());
		log.setTSUser(ResourceUtil.getSessionUserName());
		commonDao.save(log);
	}

	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup) {
		TSType actType = commonDao.findUniqueByProperty(TSType.class, "typecode", typecode);
		if (actType == null) {
			actType = new TSType();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTSTypegroup(tsTypegroup);
			commonDao.save(actType);
		}
		return actType;

	}

	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSTypegroup getTypeGroup(String typegroupcode, String typgroupename) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupcode);
		if (tsTypegroup == null) {
			tsTypegroup = new TSTypegroup();
			tsTypegroup.setTypegroupcode(typegroupcode);
			tsTypegroup.setTypegroupname(typgroupename);
			commonDao.save(tsTypegroup);
		}
		return tsTypegroup;
	}

	@Override
	public TSTypegroup getTypeGroupByCode(String typegroupCode) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupCode);
		return tsTypegroup;
	}

	@Override
	public void initAllTypeGroups() {
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
			TSTypegroup.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);
		}
	}

	@Override
	public void refleshTypesCach(TSType type) {
		TSTypegroup tsTypegroup = type.getTSTypegroup();
		TSTypegroup typeGroupEntity = this.commonDao.get(TSTypegroup.class, tsTypegroup.getId());
		List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
		TSTypegroup.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
	}

	@Override
	public void refleshTypeGroupCach() {
		TSTypegroup.allTypeGroups.clear();
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
		}
	}
	
	@Override
	public void initCustomDataDic() {
		//行政区发自定义数据字典
		List<SysAreaCodeEntity> sysAreas = this.commonDao.loadAll(SysAreaCodeEntity.class);
		for (SysAreaCodeEntity sysArea : sysAreas) {
			SysAreaCodeEntity.allSysAreas.put(sysArea.getCode().toLowerCase(), sysArea);
		}
		//(其他自定义数组字典)
		
		
	}

	@Override
	public void refleshCustomDataDic() {
		//行政区发自定义数据字典
		SysAreaCodeEntity.allSysAreas.clear();
		List<SysAreaCodeEntity> sysAreas = this.commonDao.loadAll(SysAreaCodeEntity.class);
		for (SysAreaCodeEntity sysArea : sysAreas) {
			SysAreaCodeEntity.allSysAreas.put(sysArea.getAreaname().toLowerCase(), sysArea);
		}
		//(其他自定义数组字典)
	}



	// ----------------------------------------------------------------
	// ----------------------------------------------------------------

	@Override
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getOperation()) {
				String[] operationArry = tsRoleFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	@Override
	public Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (null != tsRoleFunction.getOperation()) {
					String[] operationArry = tsRoleFunction.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		}
		return operationCodes;
	}

	// ----------------------------------------------------------------
	// ----------------------------------------------------------------
	@Override
	public void flushRoleFunciton(String id, TSFunction newFunction) {
		TSFunction functionEntity = this.getEntity(TSFunction.class, id);
//		if (functionEntity.getTSIcon() == null || !StringUtil.isNotEmpty(functionEntity.getTSIcon().getId())) {
//			return;
//		}
		if (functionEntity.getIconId() == null || !StringUtil.isNotEmpty(functionEntity.getIconId())) {
			return;
		}
//		TSIcon oldIcon = this.getEntity(TSIcon.class, functionEntity.getIconId());
//		if (!oldIcon.getIconClas().equals(newFunction.getTSIcon().getIconClas())) {
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			TSUser user = ResourceUtil.getSessionUserName();
			List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				session.removeAttribute(role.getId());
			}
//		}
	}

	// ----------------------------------------------------------------
	// ----------------------------------------------------------------
	
	/**
	 * 添加在线人员信息
	 */
	@Override
	public void addOnline(String ipString, TSUser user) {
		TSOnline online = null;
		
		String hql = "from TSOnline t where" + " t.ip='" + ipString +"' and t.loginname='" + user.getUserName()+"'";
		online = commonDao.singleResult(hql);
		
		if(online==null){
			online = new TSOnline();
		}
		online.setIp(ipString);
		online.setLogindatetime(DataUtils.gettimestamp());
		online.setLoginname(user.getUserName());
		commonDao.saveOrUpdate(online);
	}

	/**
	 * 删除在线人员信息
	 */
	@Override
	public void deleteOnline(String ipString, TSUser user) {
		TSOnline online = null;
		
		String hql = "from TSOnline t where" + " t.ip='" + ipString +"' and t.loginname='" + user.getUserName()+"'";
		online = commonDao.singleResult(hql);
		
		if(online!=null){
			commonDao.delete(online);
		}
	}

	// ----------------------------------------------------------------
	// ----------------------------------------------------------------

	/**
	 * 取得导出Excel的数据
	 * 
	 * @param sqlId
	 *            SQL的ID
	 * @param params
	 *            查询参数
	 * @return 数据list
	 */
	@Override
	public <T> List<T> findExportList(String sqlId, Map<String, Object> params) {
		String NAME_SPACE = "com.hippo.nky.common.ExprotSQL.";
		 return this.findListByMyBatis(NAME_SPACE + sqlId, params);
	}
	
	/**
	 * 根据菜单名称取得菜单对象
	 * 
	 * @param functionName
	 *            菜单名称
	 * @return 菜单对象
	 */
	@Override
	public <T> List<T> getFunctionByName(String functionName) {
		return this.findByQueryString("from TSFunction t where t.functionName='" + functionName + "'");
	}
}
