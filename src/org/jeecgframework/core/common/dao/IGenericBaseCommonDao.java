package org.jeecgframework.core.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.hibernate.Session;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.DBTable;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.springframework.dao.DataAccessException;


/**
 * 
 * 类描述：DAO层泛型基类接口
 * 
 * @author: jeecg
 * @date： 日期：2012-12-8 时间：下午05:37:33
 * @version 1.0
 */
public interface IGenericBaseCommonDao {
	/**
	 * 获取所有数据库表
	 * @return
	 */
	public List<DBTable> getAllDbTableName();
	public Integer getAllDbTableSize();
	public <T> void save(T entity);
	public <T> void batchSave(List<T> entitys);
	
	/**
	 * 批量保存数据(提交的最大行使用配置文件中的)
	 * @param entitys
	 */
	public <T> void batchSaveByMaxNum(List<T> entitys);
	
	/**
	 * 批量保存或更新数据
	 * @param <T>
	 * @param entitys 要持久化的临时实体对象集合
	 */
	public <T> void batchSaveOrUpdate(List<T> entitys);
	
	/**
	 * 批量保存数据，并在保存后用SQL去重
	 * 
	 * @param entitys <T>
	 * @param sql 用于去重的SQL语句
	 */
	public <T> void batchSaveThenDistinctBySQL(List<T> entitys, String sql);

	public <T> void saveOrUpdate(T entity);

	/**
	 * 删除实体
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 * @param entitie
	 */
	public <T> void delete(T entitie);

	/**
	 * 根据实体名称和主键获取实体
	 * 
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityName, Serializable id);

	/**
	 * 根据实体名字获取唯一记录
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value);

	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value);
	/**
	 * 加载全部实体
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> loadAll(final Class<T> entityClass);

	/**
	 * 根据实体名称和主键获取实体
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @return
	 */
	public <T> T getEntity(Class entityName, Serializable id);

	public <T> void deleteEntityById(Class entityName, Serializable id);

	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteAllEntitie(Collection<T> entities);

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void updateEntitie(T pojo);

	public <T> void updateEntityById(Class entityName, Serializable id);

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findByQueryString(String hql);

	/**
	 * 通过hql查询唯一对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> T singleResult(String hql);

	/**
	 * 根据sql更新
	 * 
	 * @param query
	 * @return
	 */
	public int updateBySqlString(String sql);

	/**
	 * 根据sql查找List
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findListbySql(String query);

	/**
	 * 通过属性称获取实体带排序
	 * 
	 * @param <T>
	 * @param clas
	 * @return
	 */
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc);

	/**
	 * 
	 * cq方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset);
	/**
	 * 通过cq获取全部实体
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq,Boolean ispage);
	/**
	 * 
	 * hqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final HqlQuery hqlQuery, final boolean needParameter);
	/**
	 * 
	 * sqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageListBySql(final HqlQuery hqlQuery, final boolean needParameter);
	public Session getSession();
	public List findByExample(final String entityName, final Object exampleEntity);
	/**
	 * 通过hql 查询语句查找HashMap对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public Map<Object,Object> getHashMapbyQuery(String query);
	/**
	 * 返回jquery datatables模型
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset);
	/**
	 * 返回easyui datagrid模型
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataGridReturn getDataGridReturn(final CriteriaQuery cq, final boolean isOffset);

	/**
	 * 执行SQL
	 */
	public Integer executeSql(String sql, List<Object> param);
	
	/**
	 * 执行SQL
	 */
	public Integer executeSql(String sql, Object... param);
	
	/**
	 * 执行SQL 使用:name占位符
	 */
	public Integer executeSql(String sql, Map<String, Object> param);
	
	
	/**
	 * 通过JDBC查找对象集合
	 * 使用指定的检索标准检索数据返回数据
	 */
	public List<Map<String, Object>> findForJdbc(String sql,Object... objs);
	
	
	/**
	 * 通过JDBC查找对象集合
	 * 使用指定的检索标准检索数据返回数据
	 */
	public Map<String, Object> findOneForJdbc(String sql,Object... objs);
	
	/**
	 * 通过JDBC查找对象集合,带分页
	 * 使用指定的检索标准检索数据并分页返回数据
	 */
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows);
	

	/**
	 * 通过JDBC查找对象集合,带分页
	 * 使用指定的检索标准检索数据并分页返回数据
	 */
	public <T> List<T> findObjForJdbc(String sql, int page, int rows,Class<T> clazz) ;
	
	
	/**
	 * 使用指定的检索标准检索数据并分页返回数据-采用预处理方式
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> findForJdbcParam(String  sql,  int page, int rows,Object... objs);
	
	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC
	 */
	public Long getCountForJdbc(String  sql) ;
	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC-采用预处理方式
	 * 
	 */
	public Long getCountForJdbcParam(String  sql,Object[] objs);


	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findHql(String hql, Object... param);
	
	 
	/** 
	 * 执行HQL语句操作更新
	 * @param hql
	 * @return  
	 */
	public Integer executeHql(String hql);

	/**
	 * 根据自定义hql查询列表
	 * 
	 */
	public DataGrid getDataGridByHql(DataGrid dataGrid, String hql ,Object [] param);
	
	/**  添加mybatis共通方法 开始 */
	/**
	 * 执行存储过程
	 * @param paramString
	 * @param paramMap
	 */
	public void callProcByMyBatis(String paramString, Map<String, Object> paramMap);

	/**
	 * 查询数据列表
	 * @param paramString
	 * @param paramObject
	 */
	public <T> List<T> findListByMyBatis(String paramString, Object paramObject);
	
	/**
	 * 查询数据列表带分页功能
	 * @param paramString
	 * @param paramObject
	 * @param rowBounds
	 */
	public <T> List<T> findListByMyBatis(String paramString, Object parameter, RowBounds rowBounds);

	/**
	 * 删除一条数据
	 * @param paramString
	 * @param paramObject
	 * @return
	 */
	public int deleteByMyBatis(String paramString, Object paramObject);

	/**
	 * 批量删除数据
	 * @param paramString
	 * @param paramList
	 * @return
	 */
	public int deleteBatchByMyBatis(String paramString, List<?> paramList);

	/**
	 * 得到一条数据
	 * @param paramString
	 * @param paramObject
	 * @return
	 */
	public <T> T getObjectByMyBatis(String paramString, Object paramObject);

	/**
	 * 插入数据
	 * @param paramString
	 * @param paramObject
	 * @return
	 */
	public int insertByMyBatis(String paramString, Object paramObject);

	/**
	 * 批量插入数据
	 * @param paramString
	 * @param paramList
	 * @return
	 */
	public int insertBatchByMyBatis(String paramString, List<?> paramList);

	/**
	 * 取得数据列表转化成map
	 * @param paramString1
	 * @param paramObject
	 * @param paramString2
	 * @return
	 */
	public <T> Map<?, T> selectMapByMyBatis(String paramString1, Object paramObject, String paramString2);

	/**
	 * 更新数据
	 * @param paramString
	 * @param paramObject
	 * @return
	 */
	public int updateByMyBatis(String paramString, Object paramObject);

	/**
	 * 批量更新数据
	 * @param paramString
	 * @param paramObject
	 * @return
	 */
	public int updateBatchByMyBatis(String paramString, List<?> paramList);
	
	/**  添加mybatis共通方法 结束 */
}
