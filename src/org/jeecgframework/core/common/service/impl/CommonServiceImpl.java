package org.jeecgframework.core.common.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.pojo.base.TSDepart;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.hibernate.Session;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.DBTable;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ConverterUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.monitoring.MonitoringProjectEntity;
import com.hippo.nky.entity.organization.OrganizationEntity;
import com.hippo.nky.entity.system.SysAreaCodeEntity;


@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {
	public ICommonDao commonDao = null;
	public static final String NAME_SPACE = "com.hippo.nky.entity.common.CommonEntity.";
	/**
	 * 获取所有数据库表
	 * @return
	 */
	public List<DBTable> getAllDbTableName()
	{
		return commonDao.getAllDbTableName();
	}
	public Integer getAllDbTableSize() {
		return commonDao.getAllDbTableSize();
	}
	@Resource
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public <T> void save(T entity) {
		ConverterUtil.setPrimaryKeyPolicy(entity, this);
		commonDao.save(entity);
	}
	
	/**
	 * 批量保存数据(提交的最大行使用配置文件中的)
	 * @param entitys
	 */
	public <T> void batchSaveByMaxNum(List<T> entitys){
		ConverterUtil.setPrimaryKeyPolicyList(entitys, this);
		commonDao.batchSaveByMaxNum(entitys);
	}

	@Override
	public <T> void saveOrUpdate(T entity) {
		ConverterUtil.setPrimaryKeyPolicy(entity, this);
		commonDao.saveOrUpdate(entity);

	}

	@Override
	public <T> void delete(T entity) {
		commonDao.delete(entity);

	}

	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteAllEntitie(Collection<T> entities) {
		commonDao.deleteAllEntitie(entities);
	}

	/**
	 * 根据实体名获取对象
	 */
	public <T> T get(Class<T> class1, Serializable id) {
		return commonDao.get(class1, id);
	}

	/**
	 * 根据实体名返回全部对象
	 * 
	 * @param <T>
	 * @param hql
	 * @param size
	 * @return
	 */
	public <T> List<T> getList(Class clas) {
		return commonDao.loadAll(clas);
	}

	/**
	 * 根据实体名获取对象
	 */
	public <T> T getEntity(Class entityName, Serializable id) {
		return commonDao.getEntity(entityName, id);
	}

	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
		return commonDao.findUniqueByProperty(entityClass, propertyName, value);
	}

	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {

		return commonDao.findByProperty(entityClass, propertyName, value);
	}

	/**
	 * 加载全部实体
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> loadAll(final Class<T> entityClass) {
		return commonDao.loadAll(entityClass);
	}
	
	public <T> T singleResult(String hql)
	{
		return commonDao.singleResult(hql);
	}

	/**
	 * 删除实体主键ID删除对象
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		commonDao.deleteEntityById(entityName, id);
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void updateEntitie(T pojo) {
		commonDao.updateEntitie(pojo);

	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findByQueryString(String hql) {
		return commonDao.findByQueryString(hql);
	}

	/**
	 * 根据sql更新
	 * 
	 * @param query
	 * @return
	 */
	public int updateBySqlString(String sql) {
		return commonDao.updateBySqlString(sql);
	}

	/**
	 * 根据sql查找List
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findListbySql(String query) {
		return commonDao.findListbySql(query);
	}

	/**
	 * 通过属性称获取实体带排序
	 * 
	 * @param <T>
	 * @param clas
	 * @return
	 */
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc) {
		return commonDao.findByPropertyisOrder(entityClass, propertyName, value, isAsc);
	}

	/**
	 * 
	 * cq方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getPageList(cq, isOffset);
	}

	/**
	 * 返回DataTableReturn模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getDataTableReturn(cq, isOffset);
	}

	/**
	 * 返回easyui datagrid模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataGridReturn getDataGridReturn(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getDataGridReturn(cq, isOffset);
	}

	/**
	 * 
	 * hqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final HqlQuery hqlQuery, final boolean needParameter) {
		return commonDao.getPageList(hqlQuery, needParameter);
	}

	/**
	 * 
	 * sqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageListBySql(final HqlQuery hqlQuery, final boolean isToEntity) {
		return commonDao.getPageListBySql(hqlQuery, isToEntity);
	}

	public Session getSession()

	{
		return commonDao.getSession();
	}

	public List findByExample(final String entityName, final Object exampleEntity) {
		return commonDao.findByExample(entityName, exampleEntity);
	}

	/**
	 * 通过cq获取全部实体
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq,Boolean ispage) {
		return commonDao.getListByCriteriaQuery(cq,ispage);
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 */
	public <T> T  uploadFile(UploadFile uploadFile) {
		return commonDao.uploadFile(uploadFile);
	}
	
	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param type 附件类型
	 */
	public <T> T  uploadFile(UploadFile uploadFile,String type) {
		return commonDao.uploadFile(uploadFile, type);
	}

	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile)

	{
		return commonDao.viewOrDownloadFile(uploadFile);
	}


	/**
	 * 生成XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 * @return
	 */
	public HttpServletResponse createXml(ImportFile importFile) {
		return commonDao.createXml(importFile);
	}

	/**
	 * 解析XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 */
	public void parserXml(String fileName) {
		commonDao.parserXml(fileName);
	}

	public List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree) {
		return commonDao.comTree(all, comboTree);
	}

	/**
	 * 根据模型生成JSON
	 * 
	 * @param all
	 *            全部对象
	 * @param in
	 *            已拥有的对象
	 * @param comboBox
	 *            模型
	 * @return
	 */
	public List<ComboTree> ComboTree(List all,ComboTreeModel comboTreeModel,List in) {
		return commonDao.ComboTree(all,comboTreeModel,in);
	}
	/**
	 * 构建树形数据表
	 */
	public List<TreeGrid> treegrid(List all, TreeGridModel treeGridModel) {
		return commonDao.treegrid(all, treeGridModel);
	}

	/**
	 * 获取自动完成列表
	 * 
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getAutoList(Autocomplete autocomplete) {
		StringBuffer sb = new StringBuffer("");
		for(String searchField:autocomplete.getSearchField().split(",")){
			sb.append("  or "+searchField+" like '%"+autocomplete.getTrem() + "%' ");
		}
		String hql = "from " + autocomplete.getEntityName() + " where 1!=1 " + sb.toString();
		return commonDao.getSession().createQuery(hql).setFirstResult(autocomplete.getCurPage()-1).setMaxResults(autocomplete.getMaxRows()).list();
	}
	
	
	@Override
	public Integer executeSql(String sql, List<Object> param) {
		return commonDao.executeSql(sql, param);
	}
	@Override
	public Integer executeSql(String sql, Object... param) {
		return commonDao.executeSql(sql, param);
	}
	
	@Override
	public Integer executeSql(String sql, Map<String, Object> param) {
		return commonDao.executeSql(sql, param);
	}
	
	@Override
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		return commonDao.findForJdbc(sql, page,rows);
	}
	@Override
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return commonDao.findForJdbc(sql, objs);
	}
	@Override
	public List<Map<String, Object>> findForJdbcParam(String sql, int page,
			int rows, Object... objs) {
		return commonDao.findForJdbcParam(sql, page, rows, objs);
	}
	@Override
	public <T> List<T> findObjForJdbc(String sql, int page, int rows,
			Class<T> clazz) {
		return commonDao.findObjForJdbc(sql, page, rows, clazz);
	}
	@Override
	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		return commonDao.findOneForJdbc(sql, objs);
	}
	@Override
	public Long getCountForJdbc(String sql) {
		return commonDao.getCountForJdbc(sql);
	}
	@Override
	public Long getCountForJdbcParam(String sql, Object[] objs) {
		return commonDao.getCountForJdbc(sql);
	}
	@Override
	public <T> void batchSave(List<T> entitys) {
		ConverterUtil.setPrimaryKeyPolicyList(entitys, this);
		this.commonDao.batchSave(entitys);
	}
	@Override
	public <T> void batchSaveOrUpdate(List<T> entitys) {
		ConverterUtil.setPrimaryKeyPolicyList(entitys, this);
		this.commonDao.batchSaveOrUpdate(entitys);
	}
	/**
	 * 批量保存数据，并在保存后用SQL去重
	 * 
	 * @param entitys <T>
	 * @param sql 用于去重的SQL语句
	 */
	@Override
	public <T> void batchSaveThenDistinctBySQL(List<T> entitys, String sql) {
		ConverterUtil.setPrimaryKeyPolicyList(entitys, this);
		this.commonDao.batchSaveThenDistinctBySQL(entitys, sql);
	}
	
	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findHql(String hql, Object... param) {
		return this.commonDao.findHql(hql, param);
	}
	
	/**
	 * 取得oracle生成的uid
	 * 
	 * @return uid
	 */
	@Override
	public String getUidFromOracle() {
		// TODO Auto-generated method stub
		Map<String,Object> map = this.findOneForJdbc("select substr(sys_guid(), 1, 32) as uuid from dual", null);
		
		if(map!=null){
			return map.get("uuid").toString();
		}
		return null;
	}
	
	@Override
	public void callProcByMyBatis(String nameSpace, Map<String, Object> paramMap){
		commonDao.callProcByMyBatis(nameSpace, paramMap);
	}

	@Override
	public <T> List<T> findListByMyBatis(String nameSpace, Object paramObject){
		return commonDao.findListByMyBatis(nameSpace, paramObject);
	}
	@Override
	public <T> List<T> findListByMyBatis(String nameSpace, Object paramObject,DataGrid dataGrid){
		if(dataGrid.getRows() > 0){
			Integer beginIndex = Integer.parseInt(dataGrid.getAoDataMap().get("iDisplayStart"));
			Integer endIndex = dataGrid.getRows();
			dataGrid.setTotal(findListByMyBatis(nameSpace, paramObject).size());
			return commonDao.findListByMyBatis(nameSpace, paramObject, new RowBounds(beginIndex,endIndex));
			
		}else{
			List<T> list = findListByMyBatis(nameSpace, paramObject);
			dataGrid.setTotal(list.size());
			return list;
		}
	}

	@Override 
	public int deleteByMyBatis(String nameSpace, Object paramObject){
		return commonDao.deleteByMyBatis(nameSpace, paramObject);
	}

	@Override
	public int deleteBatchByMyBatis(String nameSpace, List<?> paramList){
		return commonDao.deleteBatchByMyBatis(nameSpace, paramList);
	}	

	@Override
	public <T> T getObjectByMyBatis(String nameSpace, Object paramObject){
		return commonDao.getObjectByMyBatis(nameSpace, paramObject);
	}
	
	@Override
	public int insertByMyBatis(String nameSpace, Object paramObject){
		return commonDao.insertByMyBatis(nameSpace, paramObject);
	}

	@Override
	public int insertBatchByMyBatis(String nameSpace, List<?> paramList){
		return commonDao.insertBatchByMyBatis(nameSpace, paramList);
	}

	@Override
	public <T> Map<?, T> selectMapByMyBatis(String nameSpace, Object paramObject, String paramString){
		return commonDao.selectMapByMyBatis(nameSpace, paramObject, paramString);
	}

	@Override
	public int updateByMyBatis(String nameSpace, Object paramObject){
		return commonDao.updateByMyBatis(nameSpace, paramObject);
	}

	@Override
	public int updateBatchByMyBatis(String nameSpace, List<?> paramList){
		return commonDao.updateBatchByMyBatis(nameSpace, paramList);
	}
	
	/**
	 * 从DB取得UUID
	 * 
	 * @param num
	 *            取得数量
	 * @return num条GUID
	 */
	@Override
	public List<String> getGUID(int num) {
		List<String> rltList = new ArrayList<String>();
		String sql = "select unintinfo as GUID from TABLE(get_ALL_ID(" + num + "))";
		List<Map<String, Object>> idMapList = findForJdbc(sql);
		for (Map<String, Object> idMap : idMapList) {
			for (String idKey : idMap.keySet()) {
				rltList.add((String)idMap.get(idKey));
			}
		}
		return rltList;
	}
	
	
	/**
	 * 取得行政区划数据
	 * 
	 * @return uid
	 */
	@Override
	public List<SysAreaCodeEntity> getSysAreaCodeData(String code,boolean flg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("flg", flg);
		return this.findListByMyBatis(NAME_SPACE+"getSysAreaCodeData", map);
	}
	
	/**
	 * 取得行政区划数据行
	 * 
	 * @param code 区域代码
	 * @return String 采用xxx#KV#yyy#EM#xxx#KV#yyy的拼接形式
	 */
	@Override
	public String getSysAreaForString(String code){
		List<SysAreaCodeEntity> sysAreaList = getSysAreaCodeData(code,false);
		StringBuffer result = new StringBuffer();
		// 采用xxx#KV#yyy#EM#xxx#KV#yyy的拼接形式
		for (SysAreaCodeEntity sysAreaCodeEntity : sysAreaList) {
			result.append(sysAreaCodeEntity.getCode());
			result.append(ConverterUtil.SEPARATOR_KEY_VALUE);
			result.append(sysAreaCodeEntity.getAreaname());
			result.append(ConverterUtil.SEPARATOR_ELEMENT);
		}
		return result.toString();
	}
	
	/**
	 * 唯一性校验
	 * @param tableName 表名
	 * @param id 校验数据的id
	 * @param constraint 约束条件
	 * @return 结果(y,n)
	 */
	public AjaxJson uniquenessCheck(String tableName ,String id,String constraint){
		AjaxJson j = new AjaxJson();
		j.setMsg(null);
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("tableName", tableName);
		if(StringUtils.isNotEmpty(id) && id.indexOf("#KV#") > 0){
			String[] ids = id.split("#KV#");
			modelMap.put("idKey", ids[0]);
			if(ids.length == 2){
				modelMap.put("idValue", ids[1]);	
			}else{
				modelMap.put("idValue", "");				
			}
		}else{
			modelMap.put("id", id);
		}
		
		if(constraint!=null){
			//modelMap.put("constraintKey", ConverterUtil.stringToMap(constraint).keySet());
			modelMap.put("constraint", ConverterUtil.stringToMap(constraint));
		}
		int resCount = ConverterUtil.toInteger(this.getObjectByMyBatis(NAME_SPACE+"uniquenessCheck", modelMap));
		if(resCount == 0){
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
			j.setMsg("已存在!");
		}
		return j;
	}
	
	/**
	 * 根据登录的用户取得查看数据的权限
	 * @return
	 */
	@Override
	public void setUserDataPriv(String proCode, Map<String, Object> selCodition) {
		String deparid = ResourceUtil.getSessionUserName().getTSDepart().getId();
		// 取得用户类型
		String userType = ResourceUtil.getSessionUserName().getUsertype();
		
		// 管理部门
		if (StringUtils.equals(userType, "0")) {
			selCodition.put("gl", deparid);   //管理部门ID
		// 质检机构
		} else {
			OrganizationEntity org = this.getEntity(OrganizationEntity.class, deparid);

			CriteriaQuery cq = new CriteriaQuery(MonitoringProjectEntity.class);
			cq.eq("projectCode", proCode);
			cq.eq("leadunit", org.getCode());
			cq.add();
			List<MonitoringProjectEntity> projectList =  this.getListByCriteriaQuery(cq, false); 
			if (projectList != null && projectList.size() > 0) {
				selCodition.put("qt", org.getCode()); //牵头单位code
			} else {
				selCodition.put("pt", org.getCode()); //普通质检机构code
			}
			
		}
	}
}
