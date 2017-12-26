package com.hippo.nky.controller.monitoring;

import java.util.List;

import org.jeecgframework.core.util.ConverterUtil;

import com.hippo.nky.entity.monitoring.MonitoringBreedEntity;
import com.hippo.nky.service.sample.SamplingInfoServiceI;

public class SaveBreedThread extends Thread {
	private SamplingInfoServiceI samplingInfoService;
	private String projectCode;
	public SaveBreedThread(String projectCode, SamplingInfoServiceI samplingInfoService){
		this.projectCode = projectCode;
		this.samplingInfoService = samplingInfoService;
	}
	
	public void run() {
		List<MonitoringBreedEntity> breedList = samplingInfoService.getMonitoringBreedForProject(projectCode);

		for (MonitoringBreedEntity mb : breedList) {
			mb.setId(ConverterUtil.getUUID());
			mb.setProjectCode(projectCode);
//			ProjectBreedEntity pb = new ProjectBreedEntity();
//			pb.setAgrCode(mb.getAgrCode());
//			pb.setAgrName(mb.getAgrName());
//			pb.setProjectCode(projectCode);
			samplingInfoService.saveProjectBreed(mb);
		}

	}
}
