package com.hippo.nky.service.impl.standard;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.system.service.SystemService;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippo.nky.entity.standard.NkyPortalIntroductionsEntity;
import com.hippo.nky.service.standard.NkyPortalIntroductionsServiceI;

@Service("nkyPortalIntroductionsService")
@Transactional
public class NkyPortalIntroductionsServiceImpl extends CommonServiceImpl implements NkyPortalIntroductionsServiceI {

	@Autowired
	private SystemService systemService;
	public void getTreeJson(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		CriteriaQuery cq = new CriteriaQuery(NkyPortalIntroductionsEntity.class);
		cq.isNull("pid");
		cq.add();
		String JsonTemp = "[";
		List<NkyPortalIntroductionsEntity> entityList = systemService.getListByCriteriaQuery(cq, false);
		for (int i = 0; i < entityList.size(); i++) {
			NkyPortalIntroductionsEntity entity = entityList.get(i);
			if (i > 0)
				JsonTemp += ",";
			if (entity.getPid() == null) {
				JsonTemp += "{\"id\":\"" + entity.getId() + "\",\"pid\":\"\",\"text\":\""
						+ entity.getName() + "\"";
			} else {
				JsonTemp += "{\"id\":\"" + entity.getId() + "\",\"pid\":\"" + entity.getPid() + "\",\"text\":\""
						+ entity.getName() + "\"";
			}
			List<NkyPortalIntroductionsEntity> list = systemService.findByProperty(NkyPortalIntroductionsEntity.class,
					"pid", entity.getId());
			if (list.size() > 0) {
				JsonTemp += ",\"state\":\"closed\",\"children\":[";
				int j;
				for (j = 0; j < list.size(); j++) {
					NkyPortalIntroductionsEntity e = list.get(j);
					if (j > 0)
						JsonTemp += ",";
					
						JsonTemp += "{ \"id\":\"" + e.getId() + "\",\"pid\":\"" + e.getPid() + "\",\"text\":\""
								+ e.getName()+ "\"";
						
					//
					List<NkyPortalIntroductionsEntity> list3 = systemService.findByProperty(NkyPortalIntroductionsEntity.class,
							"pid", e.getId());
					if (list3.size() > 0) {
						JsonTemp += ",\"state\":\"closed\",\"children\":[";
						int k;
						for (k = 0; k < list3.size(); k++) {
							NkyPortalIntroductionsEntity en = list3.get(k);
							if (k > 0)
								JsonTemp += ",";
							
								JsonTemp += "{ \"id\":\"" + en.getId() + "\",\"pid\":\"" + en.getPid() + "\",\"text\":\""
										+ en.getName()+ "\"";
								JsonTemp += "}";
						}
						
						if (k == list3.size())
							JsonTemp += "]";
					}
					//
					JsonTemp += "}";
				}
				
				if (j == list.size())
					JsonTemp += "]";
			}
			JsonTemp += "}";

		}
		JsonTemp += "]";
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-cache");
		try {
			PrintWriter pw=response.getWriter();
			pw.write(JsonTemp.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@Override
	public void getTreeSon(HttpServletRequest request, HttpServletResponse response, int introductionleavel) {
		CriteriaQuery cq = new CriteriaQuery(NkyPortalIntroductionsEntity.class);
		cq.eq("introductionleavel", introductionleavel);
		cq.add();
		String JsonTemp = "[";
		List<NkyPortalIntroductionsEntity> entityList = systemService.getListByCriteriaQuery(cq, false);
		for (int i = 0; i < entityList.size(); i++) {
			NkyPortalIntroductionsEntity entity = entityList.get(i);
			if (i > 0)
				JsonTemp += ",";
				JsonTemp += "{\"id\":\"" + entity.getId() + "\",\"pid\":\"\",\"text\":\""
						+ entity.getName() + "\"}";
		}
		JsonTemp += "]";
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter pw=response.getWriter();
			pw.write(JsonTemp.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}