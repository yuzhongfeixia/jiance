package test;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.codegenerate.generate.onetomany.CodeGenerateOneToMany;
import org.jeecgframework.codegenerate.pojo.onetomany.CodeParamEntity;
import org.jeecgframework.codegenerate.pojo.onetomany.SubTableEntity;
import org.jeecgframework.codegenerate.util.def.JeecgKey;


/**
 * 代码生成器入口【一对多】
 *
 */
public class projectSet_JeecgOneToMainUtil {

	/**
	 * 一对多(父子表)数据模型，生成方法
	 * @param args
	 */
	public static void main(String[] args) {
		//第一步：设置主表
		CodeParamEntity codeParamEntityIn = new CodeParamEntity();
		codeParamEntityIn.setTableName("nky_monitoring_project");//主表[表名]
		codeParamEntityIn.setEntityName("MonitoringProject");	 //主表[实体名]
		codeParamEntityIn.setEntityPackage("monitoring");	 //主表[包名]
		codeParamEntityIn.setFtlDescription("检测项目数据");	 //主表[描述]
		
		//第二步：设置子表集合
		List<SubTableEntity> subTabParamIn = new ArrayList<SubTableEntity>();
		//[1].子表一
		SubTableEntity po = new SubTableEntity();
		po.setTableName("nky_monitoring_samplelink");//子表[表名]
		po.setEntityName("MonitoringSamplelink");	 //子表[实体名]
		po.setEntityPackage("monitoring");	 //子表[包]
		po.setFtlDescription("检测环节数据");		 //子表[描述]
		//子表[外键:与主表关联外键]
		//说明：这里面的外键是子表的外键字段,非主表和子表的对应关系
		//po.setForeignKeys(new String[]{"GORDER_ID","GO_ORDER_CODE"});
		po.setForeignKeys(new String[]{"PROJECT_CODE"});
		subTabParamIn.add(po);
		
		//[2].子表二
		SubTableEntity po2 = new SubTableEntity();
		po2.setTableName("nky_monitoring_area_count");		//子表[表名]
		po2.setEntityName("MonitoringAreaCount");			//子表[实体名]
		po2.setEntityPackage("monitoring"); 					//子表[包]
		po2.setFtlDescription("监测地区及数量数据");		//子表[描述]
		//子表[外键:与主表关联外键]
		//说明：这里面的外键是子表的外键字段,非主表和子表的对应关系
		//po2.setForeignKeys(new String[]{"GORDER_ID","GO_ORDER_CODE"});
		po2.setForeignKeys(new String[]{"PROJECT_CODE"});
		subTabParamIn.add(po2);
		
		//[3].子表三
		SubTableEntity po3 = new SubTableEntity();
		po3.setTableName("nky_monitoring_breed");		//子表[表名]
		po3.setEntityName("MonitoringBreed");			//子表[实体名]
		po3.setEntityPackage("monitoring"); 					//子表[包]
		po3.setFtlDescription("抽样品种数据");		//子表[描述]
		//子表[外键:与主表关联外键]
		po3.setForeignKeys(new String[]{"PROJECT_CODE"});
		subTabParamIn.add(po3);
		
		//[4].子表四
		SubTableEntity po4 = new SubTableEntity();
		po4.setTableName("nky_monitoring_dection_templet");		//子表[表名]
		po4.setEntityName("MonitoringDectionTemplet");			//子表[实体名]
		po4.setEntityPackage("monitoring"); 					//子表[包]
		po4.setFtlDescription("检测污染物模板数据");		//子表[描述]
		//子表[外键:与主表关联外键]
		po4.setForeignKeys(new String[]{"PROJECT_CODE"});
		subTabParamIn.add(po4);
		
		//[5].子表五
		SubTableEntity po5 = new SubTableEntity();
		po5.setTableName("nky_monitoring_organization");		//子表[表名]
		po5.setEntityName("MonitoringOrganization");			//子表[实体名]
		po5.setEntityPackage("monitoring"); 					//子表[包]
		po5.setFtlDescription("项目质检机构关系表");		//子表[描述]
		//子表[外键:与主表关联外键]
		po5.setForeignKeys(new String[]{"PROJECT_CODE"});
		subTabParamIn.add(po5);		
		
		//[6].子表五
		SubTableEntity po6 = new SubTableEntity();
		po6.setTableName("nky_monitoring_task");		//子表[表名]
		po6.setEntityName("MonitoringTask");			//子表[实体名]
		po6.setEntityPackage("monitoring"); 					//子表[包]
		po6.setFtlDescription("监测任务表");		//子表[描述]
		//子表[外键:与主表关联外键]
		po6.setForeignKeys(new String[]{"PROJECT_CODE"});
		subTabParamIn.add(po6);
		
		codeParamEntityIn.setSubTabParam(subTabParamIn);
		//第三步：一对多(父子表)数据模型,代码生成
		CodeGenerateOneToMany.oneToManyCreate(subTabParamIn, codeParamEntityIn);
	}
}
