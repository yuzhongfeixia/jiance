package org.jeecgframework.core.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.hippo.nky.common.constants.Constants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class WordUtils {
	/**
	 * 创建一个检测报告-种植Word文件
	 * 
	 * @param templete
	 *            模板路径
	 * @param wordData
	 *            数据对象
	 * @param fOut
	 *            输出流
	 * @throws IOException
	 *             IO异常
	 * @throws TemplateException
	 *             加载模板异常
	 */
	public static <T> void createDetectionReportPlant(T wordData, OutputStream fOut) throws IOException, TemplateException {
		// 创建检测报告-种植
		createDoc(Constants.EXPORT_WORD_TEMPLETE_PLANT, wordData, fOut);
	}

	/**
	 * 创建一个检测报告-畜禽Word文件
	 * 
	 * @param templete
	 *            模板路径
	 * @param wordData
	 *            数据对象
	 * @param fOut
	 *            输出流
	 * @throws IOException
	 *             IO异常
	 * @throws TemplateException
	 *             加载模板异常
	 */
	public static <T> void createDetectionReportLivestock(T wordData, OutputStream fOut) throws IOException, TemplateException {
		// 创建检测报告-畜禽
		createDoc(Constants.EXPORT_WORD_TEMPLETE_LIVESTOCK, wordData, fOut);
	}

	/**
	 * 创建一个Word文件
	 * 
	 * @param templete
	 *            模板路径
	 * @param wordData
	 *            数据对象
	 * @param fOut
	 *            输出流
	 * @throws IOException
	 *             IO异常
	 * @throws TemplateException
	 *             加载模板异常
	 */
	public static <T> void createWordFile(String templete, T wordData, OutputStream fOut) throws IOException,
			TemplateException {
		
		if (templete.contains("detectionReportPlant")) {
			// 检测报告-种植
			createDetectionReportPlant(wordData, fOut);
		} else if (templete.contains("detectionReportLivestock")) {
			// 创建检测报告-畜禽
			createDetectionReportLivestock(wordData, fOut);
		} else {
			// 其他导出
			createDoc(templete, wordData, fOut);
		}
	}

	/**
	 * 创建一个Word文件
	 * 
	 * @param templete
	 *            模板路径
	 * @param wordData
	 *            数据对象
	 * @param fOut
	 *            输出流
	 * @throws IOException
	 *             IO异常
	 * @throws TemplateException
	 *             加载模板异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> void createDoc(String templete, T wordData, OutputStream fOut) throws IOException, TemplateException {
		// word暂时只支持模板导出
		if (ConverterUtil.isEmpty(templete)) {
			return;
		}
		String temPath = "";
		String temName = "";
		if (templete.indexOf("/") >= 0) {
			temPath = templete.substring(0, templete.lastIndexOf("/"));
			temName = templete.substring(templete.lastIndexOf("/") + 1);
		} else if (templete.indexOf("\\") >= 0) {
			temPath = templete.substring(0, templete.lastIndexOf("\\"));
			temName = templete.substring(templete.lastIndexOf("\\") + 1);
		} else {
			// 如果不是路径 则返回
			return;
		}

		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		// 设置模板路径
		configuration.setClassForTemplateLoading(WordUtils.class, temPath);
		Template t = null;
		// 装载的模板
		t = configuration.getTemplate(temName);
		Writer out = null;
		// 设置编码格式(使用main调用时,可以不写,但是如果是web请求导出时导出后word文档就会打不开,并且抛出XML文件异常)
		OutputStreamWriter oWriter = new OutputStreamWriter(fOut, "UTF-8");
		out = new BufferedWriter(oWriter);
		if(wordData instanceof Map){
			((Map<String, Object>) wordData).put("createDate", ConverterUtil.toDate(DataUtils.getDate(), ConverterUtil.FORMATE_DATE_TIME_24H));
		}
		// 模板替换写入
		t.process(wordData, out);
	}
}
