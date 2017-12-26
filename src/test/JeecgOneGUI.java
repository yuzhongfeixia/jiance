package test;

import org.jeecgframework.codegenerate.window.CodeWindow;


/**
 * 【单表模型】代码生成器入口
 *
 */
public class JeecgOneGUI  extends CodeWindow{
	
	public JeecgOneGUI(){
		super.setTitle("联信智源代码生成器");
	}
	
	public static void main(String[] args) {
//		CodeWindow  codeWindow = new CodeWindow();
		
		new JeecgOneGUI().pack();
	}
}
