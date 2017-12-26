package org.jeecgframework.tag.core.easyui;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
/**
 * 
 * @Title:AuthFilterTag
 * @description:列表按钮权限过滤
 * @author zty
 */
@SuppressWarnings("unchecked")
public class AuthFilterTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 列表容器的ID */
	protected String name;

	@Override
	public int doStartTag() throws JspException {
		boolean flag = true;
		List<String> nolist = (List<String>) super.pageContext.getRequest()
				.getAttribute("noauto_operationCodes");
		if (ResourceUtil.getSessionUserName().getUserName().equals("admin")
				|| !Globals.BUTTON_AUTHORITY_CHECK) {
		} else {
			if (nolist != null && nolist.size() > 0) {
				for (String s : nolist) {
					if (s.equals(name)) {
						flag = false;
						break;
					}
				}
			}
		}

		if (flag) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
		/**
		 * EVAL_BODY_INCLUDE 计算标签的body SKIP_BODY 不再计算标签的body
		 */
	}

	@Override
	public int doEndTag() throws JspException {
		/**
		 * EVAL_PAGE 标签结束时继续计算JSP页面其他的部分 SKIP_PAGE 标签结束时停止计算JSP页面其他的部分
		 */
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
