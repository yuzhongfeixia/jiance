package com.hippo.nky.common;

import javax.servlet.http.HttpServletRequest;

public class MyMultipartResolver extends org.springframework.web.multipart.commons.CommonsMultipartResolver {

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		String requestPath = request.getRequestURI().substring(request.getContextPath().length());// 去掉项目路径
		if (requestPath.equals("/kindeditor/file_upload.do")) {
			return false;
		} else if (requestPath.equals("/kindeditor/file_manager_json.do")) {
			return false;
		} else {
			return super.isMultipart(request);
		}

	}
}
