package com.hippo.nky.page.monitoring;

import java.util.ArrayList;
import java.util.List;

import com.hippo.nky.entity.monitoring.MonitoringTaskDetailsEntity;

/**   
 * @Title: Entity
 * @Description: 检测方案数据
 * @author nky
 * @date 2013-10-23 15:54:32
 * @version V1.0   
 *
 */
public class MonitoringTaskDetailsPage implements java.io.Serializable {
	/**保存-检测项目数据*/
	private List<MonitoringTaskDetailsEntity> monitoringTaskDetailsList = new ArrayList<MonitoringTaskDetailsEntity>();

	public List<MonitoringTaskDetailsEntity> getMonitoringTaskDetailsList() {
		return monitoringTaskDetailsList;
	}

	public void setMonitoringTaskDetailsList(
			List<MonitoringTaskDetailsEntity> monitoringTaskDetailsList) {
		this.monitoringTaskDetailsList = monitoringTaskDetailsList;
	}
	

}
