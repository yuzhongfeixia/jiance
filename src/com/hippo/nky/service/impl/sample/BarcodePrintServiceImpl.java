package com.hippo.nky.service.impl.sample;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.sample.BarcodePrintEntity;
import com.hippo.nky.service.sample.BarcodePrintServiceI;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;

@Service("barcodePrintService")
@Transactional
public class BarcodePrintServiceImpl extends CommonServiceImpl implements
		BarcodePrintServiceI {

	@Override
	public JSONObject getDatagrid(BarcodePrintEntity barcodePrint,
			String orgid, DataGrid dataGrid) {

		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from ("
				+ "SELECT NMP.ID FROM NKY_MONITORING_PROJECT NMP\n"
				+ "INNER JOIN NKY_MONITORING_ORGANIZATION NMO ON(NMP.PROJECT_CODE = NMO.PROJECT_CODE)\n"
				+ "INNER JOIN NKY_ORGANIZATION_INFO NOI ON(NMO.ORG_CODE = NOI.CODE)\n"
				+ "LEFT JOIN (SELECT NMT.PROJECT_CODE,SUM(NMT.SAMPLING_COUNT) AS SUMCOUNT FROM NKY_MONITORING_TASK NMT WHERE NMT.ORG_CODE = '" + ResourceUtil.getSessionUserName().getOrganization().getCode()+"'"
				+ " GROUP BY NMT.PROJECT_CODE) MAC ON (NMP.PROJECT_CODE = MAC.PROJECT_CODE)\n"
				+ "LEFT JOIN NKY_BARCODE_PRINTING NBP ON (NMP.PROJECT_CODE = NBP.PROJECT_CODE)\n"
				+ "WHERE NOI.ID = '" + orgid + "'" 
				+ " AND NMP.STATE = 1 ORDER BY to_char(NMP.STARTTIME,'yyyy-MM-dd') DESC)";
		
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据
		String hql = "SELECT ROWNUM RN,t.* FROM (SELECT NMP.PROJECT_CODE,NMP.NAME,to_char(NMP.STARTTIME,'yyyy-MM-dd') || '至' || to_char(NMP.ENDTIME,'yyyy-MM-dd') AS starttime,NMP.ENDTIME,NVL(MAC.SUMCOUNT,0) AS SUMCOUNT,NVL(NBP.PRINT_COUNT,0) AS PRINT_COUNT,NBP.ID AS NBPID FROM NKY_MONITORING_PROJECT NMP\n"
				+ "INNER JOIN NKY_MONITORING_ORGANIZATION NMO ON(NMP.PROJECT_CODE = NMO.PROJECT_CODE)\n"
				+ "INNER JOIN NKY_ORGANIZATION_INFO NOI ON(NMO.ORG_CODE = NOI.CODE)\n"
				+ "LEFT JOIN (SELECT NMT.PROJECT_CODE,SUM(NMT.SAMPLING_COUNT) AS SUMCOUNT FROM NKY_MONITORING_TASK NMT WHERE NMT.ORG_CODE = '" + ResourceUtil.getSessionUserName().getOrganization().getCode()+"'"
				+ " GROUP BY NMT.PROJECT_CODE) MAC ON (NMP.PROJECT_CODE = MAC.PROJECT_CODE)\n"
				+ "LEFT JOIN NKY_BARCODE_PRINTING NBP ON (NMP.PROJECT_CODE = NBP.PROJECT_CODE)\n"
				+ "WHERE NOI.ID = '" + orgid + "'"
				+ " AND NMP.STATE = 1 ORDER BY to_char(NMP.STARTTIME,'yyyy-MM-dd') DESC) t";
		List<Map<String, Object>> mapList = findForJdbc(hql,
				dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("rn", "RN"),
				new Db2Page("name", "NAME"),
				new Db2Page("starttime", "STARTTIME"),
				new Db2Page("endtime", "ENDTIME"),
				new Db2Page("sumcount", "SUMCOUNT"),
				new Db2Page("projectCode", "PROJECT_CODE"),
				new Db2Page("printCount", "PRINT_COUNT"),
				new Db2Page("barcodeId", "NBPID")		
		};

		JSONObject jObject = DataUtils.getJsonDatagridEasyUI(mapList,
				iCount.intValue(), dataGrid.getsEcho(), db2Pages);
		return jObject;
	}

	@Override
	public Object findByCode(String dcode) {
		String hql = "SELECT DISTINCT NST.TITLE,NSI.D_CODE,TO_CHAR(NSI.SAMPLING_DATE,'yyyy-MM-dd') AS SAMPLING_DATE,NAC.CNAME\n" +
						"FROM NKY_SAMPLE_TWODIMENSION NST\n" + 
						"INNER JOIN NKY_SAMPLING_INFO nsi ON (nsi.d_code = nst.title)\n" + 
						"INNER JOIN V_NKY_NEWEST_AGR NAC ON (NSI.AGR_CODE = NAC.CODE)\n" +
						" WHERE NST.TITLE = ?";
		Map<String,Object> map  = this.findOneForJdbc(hql, new Object[]{dcode});
		return map;
	}

}