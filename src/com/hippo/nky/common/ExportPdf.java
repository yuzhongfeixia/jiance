package com.hippo.nky.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportPdf {
	/**
	 * 创建pdf文档
	 * 
	 * @param mapLst
	 * @param filePath
	 * 
	 * @param filename
	 */
	public static void createPdf(String fontTTF,
			List<Map<String, Object>> mapLst, String filePath) {
		Rectangle rect = new Rectangle(190,120);
		// 置设border型类 为box(四周都有)
		rect.setBorder(Rectangle.BOX);
		// 新建一 文个 档
		// 建一 文创 个 档,将rect作 的 式 入为预设 样 传,后面的10,10,10,10是文 的外 距档 边
		Document doc = new Document(rect, 10, 10, 10, 10);
		// /建立一 器个书写(Writer)与document象对关联
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(doc,
					new FileOutputStream(new File(filePath)));
			// 打 一 文开 个 档
			/**
			 * PdfWriter.HideMenubar:隐藏阅读程序的菜单
			 */
			pdfWriter.setViewerPreferences(PdfWriter.HideMenubar);

			doc.open();
			BaseFont basef = BaseFont.createFont(fontTTF, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			// 标题字体
			Font f16 = new Font(basef, 16, Font.BOLD, BaseColor.BLACK);
			// 正文字体
			Font f6 = new Font(basef, 6, Font.BOLD, BaseColor.BLACK);
			Font f8 = new Font(basef, 8, Font.BOLD, BaseColor.BLACK);
			Font f12 = new Font(basef, 12, Font.BOLD, BaseColor.BLACK);
			Font fontArr[] = new Font[] { f6, f8, f12, f16 };
			for (int i = 0; i < mapLst.size(); i++) {
				Map<String, Object> map = mapLst.get(i);
				// 判断行业
				String indName = getIndustryName(map.get("industryCode").toString());
				if (indName.indexOf("种植") != -1) {
					doc = createPlantTable(doc, fontArr, map);
				} else if (indName.indexOf("畜禽") != -1) {
					doc = createLivestockTable(doc, fontArr, map);
				} else {

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			doc.close();
		}
	}

	/**
	 * 数据字典
	 * 
	 * @param industryCode
	 * @return
	 */
	private static String getIndustryName(String industryCode) {
		String returnStr = "";
		List<TSType> types = TSTypegroup.allTypes.get("industry");
		if (types != null) {
			for (TSType type : types) {
				if ((type.getTypecode().equals(industryCode))) {
					returnStr = type.getTypename();
				}
			}
		}
		return returnStr;
	}

	/**
	 * 创建畜禽行业信息
	 * 
	 * @param doc
	 * @param fontArr
	 * @param map
	 * @throws DocumentException
	 */
	private static Document createLivestockTable(Document doc, Font[] fontArr,
			Map<String, Object> map) throws DocumentException {
		doc.add(createLivestockTopTable(fontArr, map));
		doc.newPage();

		doc.add(createLivestockBottomTable(fontArr, map));
		doc.newPage();
		return doc;
	}

	private static PdfPTable createLivestockTopTable(Font[] fontArr,
			Map<String, Object> map) {
		// 正样标签
		PdfPTable bottomTable = new PdfPTable(4);
		bottomTable.setWidthPercentage(110);
		
		PdfPCell rCell;
		rCell = new PdfPCell(new Phrase("样品名称："+ map.get("agrname"), fontArr[1]));
		rCell.setColspan(3);
		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("正样", fontArr[1]));
		rCell.setColspan(1);
		bottomTable.addCell(rCell);
		
		rCell = new PdfPCell(new Phrase("样品编号："+ map.get("labCode"), fontArr[1]));
		rCell.setColspan(2);
		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("原    号：" + (map.get("code") == null ? "" : map.get("code")), fontArr[1]));
		rCell.setColspan(2);
		bottomTable.addCell(rCell);
		rCell = new PdfPCell(new Phrase("到样日期：", fontArr[1]));
		rCell.setColspan(2);
		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("目    数：", fontArr[1]));
		rCell.setColspan(2);
		bottomTable.addCell(rCell);

		rCell = new PdfPCell();
		rCell.setColspan(4);
		
		rCell = new PdfPCell(new Phrase("状    态：", fontArr[1]));
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		bottomTable.addCell(rCell);
		rCell = new PdfPCell(new Phrase("未检□", fontArr[1]));
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		bottomTable.addCell(rCell);
		rCell = new PdfPCell(new Phrase("在检□", fontArr[1]));
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		bottomTable.addCell(rCell);
		rCell = new PdfPCell(new Phrase("已检□", fontArr[1]));
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		bottomTable.addCell(rCell);
		
		return bottomTable;
	}

	private static PdfPTable createLivestockBottomTable(Font[] fontArr,
			Map<String, Object> map) {
		// 副样标签
		PdfPTable bottomTable = new PdfPTable(4);
		bottomTable.setWidthPercentage(110);
		
		PdfPCell rCell;
		rCell = new PdfPCell(new Phrase("样品名称：" + map.get("agrname"), fontArr[1]));
		rCell.setColspan(3);
		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("副样", fontArr[1]));
		rCell.setColspan(1);
		bottomTable.addCell(rCell);
		
		rCell = new PdfPCell(new Phrase("样品编号：" + map.get("labCode"), fontArr[1]));
		rCell.setColspan(2);
		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("原    号：" + (map.get("code") == null ? "" : map.get("code")), fontArr[1]));
		rCell.setColspan(2);
		bottomTable.addCell(rCell);
		rCell = new PdfPCell(new Phrase("到样日期：", fontArr[1]));
		rCell.setColspan(2);
		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("目    数：", fontArr[1]));
		rCell.setColspan(2);
		bottomTable.addCell(rCell);

		rCell = new PdfPCell();
		rCell.setColspan(4);
		
		rCell = new PdfPCell(new Phrase("状    态：", fontArr[1]));
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		bottomTable.addCell(rCell);
		rCell = new PdfPCell(new Phrase("未检□", fontArr[1]));
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		bottomTable.addCell(rCell);
		rCell = new PdfPCell(new Phrase("在检□", fontArr[1]));
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		bottomTable.addCell(rCell);
		rCell = new PdfPCell(new Phrase("已检□", fontArr[1]));
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		bottomTable.addCell(rCell);
		
		return bottomTable;
	}

	/**
	 * 创建种植行业信息
	 * 
	 * @param doc
	 * 
	 * @param fontArr
	 * @param map
	 * 
	 * @return
	 * @throws DocumentException
	 */
	public static Document createPlantTable(Document doc, Font[] fontArr,
			Map<String, Object> map) throws DocumentException {
		// 参数只能为1248，分别代表上下左右
//		cell.disableBorderSide(1);
//		cell.disableBorderSide(2);
//		cell.disableBorderSide(4);
//		cell.disableBorderSide(8);
		doc.add(createPlantTopTable(fontArr, map));
		doc.newPage();
		doc.add(createPlantBottomTable(fontArr, map));
		doc.newPage();
		return doc;
	}

	public static PdfPTable createPlantTopTable(Font[] fontArr,
			Map<String, Object> map) throws DocumentException {
		// 样品标签
		PdfPTable topTable = new PdfPTable(4);
		topTable.setWidthPercentage(110);
		
		PdfPCell lCell;
		lCell = new PdfPCell(new Phrase("样品标签", fontArr[2]));
		lCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		lCell.setColspan(4);

		topTable.addCell(lCell);

		lCell = new PdfPCell(new Phrase("检测编码："+ map.get("labCode"), fontArr[1]));

		lCell.setColspan(4);
		topTable.addCell(lCell);

		lCell = new PdfPCell(new Phrase("样品名称："+ map.get("agrname"), fontArr[1]));
		lCell.setColspan(4);

		topTable.addCell(lCell);

		lCell = new PdfPCell(new Phrase("制样日期：", fontArr[1]));
		lCell.setColspan(4);

		topTable.addCell(lCell);

		lCell = new PdfPCell(new Phrase("样品状态：", fontArr[1]));
		lCell.setColspan(4);
		topTable.addCell(lCell);

		lCell = new PdfPCell();
		lCell.setRowspan(2);
		lCell.setColspan(4);
		topTable.addCell(lCell);
		
		PdfPCell dCell = new PdfPCell(new Phrase("待检", fontArr[1]));
		dCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		topTable.addCell(dCell);
		PdfPCell zCell = new PdfPCell(new Phrase("在检", fontArr[1]));
		zCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		topTable.addCell(zCell);
		PdfPCell jCell = new PdfPCell(new Phrase("检毕", fontArr[1]));
		jCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		topTable.addCell(jCell);
		PdfPCell tCell = new PdfPCell(new Phrase("退样", fontArr[1]));
		tCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		topTable.addCell(tCell);
		
		PdfPCell tempCell = new PdfPCell(new Phrase(" ", fontArr[1]));
		topTable.addCell(tempCell);
		topTable.addCell(tempCell);
		topTable.addCell(tempCell);
		topTable.addCell(tempCell);
		return topTable;
	}

	public static Element createPlantBottomTable(Font[] fontArr,
			Map<String, Object> map) throws DocumentException {
		// 留样标签
		PdfPTable bottomTable = new PdfPTable(4);
		bottomTable.setWidthPercentage(110);
		
		PdfPCell rCell;
		rCell = new PdfPCell(new Phrase("JSXJ/QCR24-13-2014", fontArr[1]));
		rCell.disableBorderSide(2);
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		rCell.setColspan(4);

		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("留样标签", fontArr[2]));
		rCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		rCell.setColspan(4);

		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("检测编码："+map.get("labCode"), fontArr[1]));
		rCell.setColspan(4);

		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("样品名称："+ map.get("agrname"), fontArr[1]));
		rCell.setColspan(4);

		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("制/留样人：", fontArr[1]));
		rCell.setColspan(2);

		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("重量/数量：", fontArr[1]));
		rCell.setColspan(2);

		bottomTable.addCell(rCell);
		rCell = new PdfPCell(new Phrase("日    期：", fontArr[1]));
		rCell.setColspan(2);

		bottomTable.addCell(rCell);

		rCell = new PdfPCell(new Phrase("留样有效期：", fontArr[1]));
		rCell.setColspan(2);
		bottomTable.addCell(rCell);

		return bottomTable;
	}

	public static void main(String[] args) throws DocumentException,
			IOException {
		ExportPdf.createPdf("C://Windows//Fonts//SIMYOU.TTF", null, "d:/abc.pdf");
	}
}
