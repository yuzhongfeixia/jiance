package com.hippo.nky.service.impl.standard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.standard.JudgeStandardEntity;
import com.hippo.nky.service.standard.JudgeStandardServiceI;

@Service("judgeStandardService")
@Transactional
public class JudgeStandardServiceImpl extends CommonServiceImpl implements JudgeStandardServiceI {
	//@Override
	public JSONObject getDatagrid3(JudgeStandardEntity pageObj, DataGrid dataGrid) {
		String sqlWhere = getSqlWhere(pageObj);
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from (SELECT JUDGE.ID,JUDGE.UNITS,JUDGE.VALUEFROM,JUDGE.STIPULATE,POLL.POPCNAME,AGR.CNAME" +
						"  FROM NKY_JUDGE_STANDARD JUDGE\n" + 
						" INNER JOIN NKY_AGR_CATEGORY AGR ON AGR.ID = JUDGE.AGRID\n" + 
						" INNER JOIN NKY_POLL_PRODUCTS POLL ON POLL.ID = JUDGE.POLLID\n" + 
						"  LEFT JOIN NKY_STANDARD_VERSION NSV ON (NSV.ID = JUDGE.VID)\n" + 
						" WHERE NSV.ID = '"+pageObj.getVid()+"' " +
						")t";
		
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " where" + sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		String sql = "select t.* from (SELECT JUDGE.ID,\n" +
						"       JUDGE.LID,\n" + 
						"       JUDGE.VALUE,\n" + 
						"       JUDGE.UNITS,\n" + 
						"       JUDGE.VALUEFROM,\n" + 
						"       JUDGE.STIPULATE,\n" + 
						"       AGR.CNAME,\n" + 
						"       POLL.POPCNAME,\n" + 
						"       POLL.CAS,\n" +
						"       NSV.CNAME AS VNAME\n" + 
						"  FROM NKY_JUDGE_STANDARD JUDGE\n" + 
						" INNER JOIN NKY_AGR_CATEGORY AGR ON AGR.ID = JUDGE.AGRID\n" + 
						" INNER JOIN NKY_POLL_PRODUCTS POLL ON POLL.ID = JUDGE.POLLID\n" + 
						"  LEFT JOIN NKY_STANDARD_VERSION NSV ON (NSV.ID = JUDGE.VID)\n" + 
						" WHERE NSV.ID = '"+pageObj.getVid()+"' " +
						")t";

		if (!sqlWhere.isEmpty()) {
			sql += " where" + sqlWhere;
		}
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id","ID")
				,new Db2Page("lid","LID")
				,new Db2Page("agrname","CNAME")
				,new Db2Page("pollname","POPCNAME")
				,new Db2Page("cas","CAS")
				,new Db2Page("value","VALUE")
				,new Db2Page("units","UNITS")
				,new Db2Page("valuefrom","VALUEFROM")
				,new Db2Page("stipulate","STIPULATE")
				,new Db2Page("vname","VNAME")
		};
		Map<String,String> dataDicMap = new HashMap<String, String>();
		dataDicMap.put("units","unit");
		dataDicMap.put("valuefrom","valuefrom");
		dataDicMap.put("stipulate","stipulate");
		
		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList,iCount.intValue(),dataGrid.getsEcho(),db2Pages,dataDicMap);
		return jObject;
		// end of 方式3 ========================================= */
	}
	
	// 拼查询条件（where语句）
	String getSqlWhere(JudgeStandardEntity pageObj) {
		// 拼出条件语句
		String sqlWhere = "";
		if (StringUtil.isNotEmpty(pageObj.getAgrname())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " t.CNAME like '%" + pageObj.getAgrname() + "%'";
		}
		if (StringUtil.isNotEmpty(pageObj.getPollname())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " t.POPCNAME like '%" + pageObj.getPollname() + "%'";
		}
		if (StringUtil.isNotEmpty(pageObj.getUnits())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " t.units = " + pageObj.getUnits() + "";
		}
		if (StringUtil.isNotEmpty(pageObj.getValuefrom())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " t.valuefrom = " + pageObj.getValuefrom() + "";
		}
		if (StringUtil.isNotEmpty(pageObj.getStipulate())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " t.stipulate = " + pageObj.getStipulate() + "";
		}
		return sqlWhere;
	}
	
	// 数据变换的统一接口
	interface IMyDataExchanger {
		public Object exchange(Object value);
	}
	
	// 性别的数据变换实体
	class MyDataExchangerSex implements IMyDataExchanger {
		public Object exchange(Object value) {
			if (value == null) {
				return "";
			} else if (value.equals("0")) {
				return "男";
			} else {
				return "女";
			}
		}
	}
}