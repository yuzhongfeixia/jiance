package org.jeecgframework.tag.core.easyui;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;

/**
 * 
 * @Title:AuthFilterTag
 * @description:Tab按钮权限过滤
 * @author zty
 */
@SuppressWarnings("unchecked")
public class AuthTabFilterTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 列表容器的ID */
	protected String name;

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;

	}

	protected Object end() {
		StringBuilder out = new StringBuilder();
		getAuthFilter(out);
		return out;
	}

	/**
	 * 获取隐藏按钮的JS代码
	 * 
	 * @param out
	 */
	protected void getAuthFilter(StringBuilder out) {
		out.append("<script type=\"text/javascript\">");
		out.append("$(document).ready(function(){");
		List<String> nolist = (List<String>) super.pageContext.getRequest()
				.getAttribute("noauto_operationCodes");

		if (ResourceUtil.getSessionUserName().getUserName().equals("admin")
				|| !Globals.BUTTON_AUTHORITY_CHECK) {
		} else {
			if (nolist != null && nolist.size() > 0) {
				for (String s : nolist) {
					if (s.equals(name)) {
						if (name.indexOf("province") != -1) {
							out.append("$(\"#" + name + "\").parent().remove();$(\"#tab_1\").find('li:eq(0)').attr('class','active'); $(\"#tab_1_1\").remove();$(\"#tab_1_c\").find('div:eq(0)').attr('class','tab-pane active');");
						} else if (name.indexOf("city") != -1) {
							out.append("$(\"#" + name + "\").parent().remove();$(\"#tab_1\").find('li:eq(0)').attr('class','active'); $(\"#tab_1_2\").remove();$(\"#tab_1_c\").find('div:eq(0)').attr('class','tab-pane active');");
						} else if (name.indexOf("county") != -1) {
							out.append("$(\"#" + name + "\").parent().remove();$(\"#tab_1\").find('li:eq(0)').attr('class','active'); $(\"#tab_1_3\").remove();$(\"#tab_1_c\").find('div:eq(0)').attr('class','tab-pane active');");
						}
						break;
					}
				}
			}
		}
		out.append("});");
		out.append("</script>");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
