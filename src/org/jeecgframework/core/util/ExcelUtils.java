package org.jeecgframework.core.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecgframework.core.annotation.excel.Excel;

/**
 * Excel工具类
 * 
 * @author XuDL
 */
public final class ExcelUtils {

	/** Excel导出单元格属性：title */
	public static final String EXCEL_TITLE = "title";

	/** Excel导出单元格属性：width */
	public static final String EXCEL_WIDTH = "width";

	/** Excel导出单元格宽度：最小值 */
	public static final int EXCEL_WIDTH_MIN = 10;

	/** Excel导出单元格宽度：最大值 */
	public static final int EXCEL_WIDTH_MAX = 100;

	/** Excel导出单元格WIDTH：auto */
	public static final String EXCEL_WIDTH_AUTO = "auto";

	/** Excel合并单元格：单元格地址 */
	public static final String MERGE_ADDRESS = "mergeAddress";

	/** Excel合并单元格：单元格第一行 */
	public static final String MERGE_FIRST_ROW = "mergeFirstRow";

	/** Excel合并单元格：单元格最后一行 */
	public static final String MERGE_LAST_ROW = "mergeLastRow";

	/** Excel合并单元格：单元格第一列 */
	public static final String MERGE_FIRST_COL = "mergeFirstCol";

	/** Excel合并单元格：单元格最后一列 */
	public static final String MERGE_LAST_COL = "mergeLastCol";

	/**
	 * 创建workbook（默认03版workbook）
	 * 
	 * @return
	 */
	public static Workbook createWorkBook() {
		// 默认创建03版Excel
		return create2003Book();
	}

	/**
	 * 创建03版workbook
	 * 
	 * @return
	 */
	public static Workbook create2003Book() {
		Workbook workBook = new HSSFWorkbook();
		return workBook;
	}

	/**
	 * 创建07版workbook
	 * 
	 * @return
	 */
	public static Workbook create2007Book() {
		Workbook workBook = new XSSFWorkbook();
		return workBook;
	}

	/**
	 * 创建一个sheet
	 * 
	 * @param workbook
	 *            工作簿
	 * @param sheetName
	 *            sheet的名
	 * @return
	 */
	public static Sheet createSheet(Workbook workbook, String sheetName) {
		Sheet workSheet = workbook.createSheet(sheetName);
		return workSheet;
	}

	/**
	 * 取得默认的单元格风格
	 * 
	 * @param workbook
	 *            工作簿
	 * @return
	 */
	public static CellStyle getDefaultCellStyle(Workbook workbook) {
		// 按照顺序写入表头
		CellStyle headCs = workbook.createCellStyle();

		// 设置边框
		headCs.setBorderTop(CellStyle.BORDER_THIN);
		headCs.setBorderLeft(CellStyle.BORDER_THIN);
		headCs.setBorderRight(CellStyle.BORDER_THIN);
		headCs.setBorderBottom(CellStyle.BORDER_THIN);

		return headCs;
	}

	/**
	 * 创建抬头
	 * 
	 * @param workSheet
	 *            sheet
	 * @param excelList
	 *            excelList
	 * @param headCs
	 *            抬头风格对象
	 * @return
	 */
	public static void createTitle(Sheet workSheet, List<Excel> excelList, CellStyle headCs, Workbook workBook) {
		if (null == excelList) {
			return;
		}
		// 创建头信息
		Row headRow = workSheet.createRow(0);

		for (int i = 0; i < excelList.size(); i++) {
			Cell headCell = headRow.createCell(i);
			String exportName = excelList.get(i).exportName();
			// 字符型
			headCell.setCellType(Cell.CELL_TYPE_STRING);
			headCell.setCellValue(exportName);
			// 设置表头样式
			headCell.setCellStyle(headCs);
			workSheet.setDefaultColumnStyle(i, headCs);
			// 设置excel的宽度 256=65280/255
			workSheet.setColumnWidth(i, 256 * excelList.get(i).exportFieldWidth());
		}
		
		// 表头设置粗体
        for (Iterator<Cell> cit = headRow.cellIterator(); cit.hasNext();) {
          Cell cell = cit.next();
          CellStyle boldStyle = getDefaultCellStyle(workBook);
          // 表头居中
          boldStyle.setAlignment(CellStyle.ALIGN_CENTER);
          Font font = workBook.createFont();
          font.setBoldweight(Font.BOLDWEIGHT_BOLD);
          boldStyle.setFont(font);
          cell.setCellStyle(boldStyle);
        }
	}

	/**
	 * 创建抬头
	 * 
	 * @param workSheet
	 *            sheet
	 * @param titleList
	 *            抬头map
	 * @param headCs
	 *            抬头风格对象
	 * @return
	 */
	public static void createTitle(Workbook workbook, Sheet workSheet, int rowNum, Map<String, Object> columnsAttrMap) {
		if (null == columnsAttrMap) {
			return;
		}

		// 创建头信息
		Row headRow = workSheet.createRow(rowNum);
		int cellIndex = 0;

		for (String key : columnsAttrMap.keySet()) {
			CellStyle headCs = getDefaultCellStyle(workbook);
			Cell headCell = headRow.createCell(cellIndex);
			// 取得单元格属性
			Map<String, Object> columAttrMap = ConverterUtil
					.stringToMap(ConverterUtil.toString(columnsAttrMap.get(key)));
			// 字符型
			headCell.setCellType(Cell.CELL_TYPE_STRING);
			// 设置title
			headCell.setCellValue(ConverterUtil.toString(columAttrMap.get(EXCEL_TITLE)));
			// 设置excel的宽度
			workSheet.setColumnWidth(cellIndex, getCellWidth(columAttrMap));
			// 设置需要设置合并的单元格
			boolean hasMerge = setMergeCells(workSheet, columAttrMap);
			// 如果有合并行为则需要居中
			if (hasMerge) {
				CellStyle mergeStyle = getDefaultCellStyle(workbook);
				// 水平居中
				mergeStyle.setAlignment(CellStyle.ALIGN_CENTER);
				// 设置表头样式
				headCell.setCellStyle(mergeStyle);
			} else {
				// 设置表头样式
				headCell.setCellStyle(headCs);
			}

			// 设置整体风格
			workSheet.setDefaultColumnStyle(cellIndex, headCs);
			cellIndex++;
		}
		
        // 表头设置粗体
        for (Iterator<Cell> cit = headRow.cellIterator(); cit.hasNext();) {
          Cell cell = cit.next();
          CellStyle boldStyle = getDefaultCellStyle(workbook);
          // 表头居中
          boldStyle.setAlignment(CellStyle.ALIGN_CENTER);
          Font font = workbook.createFont();
          font.setBoldweight(Font.BOLDWEIGHT_BOLD);
          boldStyle.setFont(font);
          cell.setCellStyle(boldStyle);
        }
	}

	/**
	 * 创建抬头
	 * 
	 * @param workSheet
	 *            sheet
	 * @param titleList
	 *            抬头map
	 * @return
	 */
	public static void createTitle(Workbook workbook, Sheet workSheet, Map<String, Object> columnsAttrMap) {
		createTitle(workbook, workSheet, 0, columnsAttrMap);
	}

	/**
	 * 设置合并单元格
	 * <p>
	 * 默认取得columAttrMap中mergeAddress(B1:B4)</br>
	 * 如果没有mergeAddress,则需要取四个参数mergeFirstRow
	 * ，mergeLastRow，mergeFirstCol，mergeLastCol</br> 如果这四个参数任意一个有没有设置，则不会进行设置
	 * 
	 * @param workSheet
	 * @param columAttrMap
	 */
	public static boolean setMergeCells(Sheet workSheet, Map<String, Object> columAttrMap) {
		if (columAttrMap == null) {
			return false;
		}
		CellRangeAddress cRangeAddress = null;
		// 取得文字地址
		String mergeAddress = ConverterUtil.toString(columAttrMap.get(MERGE_ADDRESS));
		if (ConverterUtil.isNotEmpty(mergeAddress)) {
			cRangeAddress = CellRangeAddress.valueOf(mergeAddress);
		} else {
			// 取得绝对地址
			String mergeFirstRow = ConverterUtil.toString(columAttrMap.get(MERGE_FIRST_ROW));
			String mergeLastRow = ConverterUtil.toString(columAttrMap.get(MERGE_LAST_ROW));
			String mergeFirstCol = ConverterUtil.toString(columAttrMap.get(MERGE_FIRST_COL));
			String mergeLastCol = ConverterUtil.toString(columAttrMap.get(MERGE_LAST_COL));
			if (ConverterUtil.isEmpty(mergeFirstRow) || ConverterUtil.isEmpty(mergeLastRow)
					|| ConverterUtil.isEmpty(mergeFirstCol) || ConverterUtil.isEmpty(mergeLastCol)) {
				return false;
			}
			int firstRow = ConverterUtil.toInteger(mergeFirstRow);
			int lastRow = ConverterUtil.toInteger(mergeLastRow);
			int firstCol = ConverterUtil.toInteger(mergeFirstCol);
			int lastCol = ConverterUtil.toInteger(mergeLastCol);
			cRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		}
		// 合并单元格
		workSheet.addMergedRegion(cRangeAddress);

		return true;
	}

	/**
	 * 取得cell的宽度
	 * 
	 * @param columAttrMap
	 *            列属性的Map
	 * @return cell的宽度
	 */
	public static int getCellWidth(Map<String, Object> columAttrMap) {
		Object width = null;
		if (null != columAttrMap) {
			width = columAttrMap.get(EXCEL_WIDTH);
		} else {
			// 默认宽度20
			width = 20;
		}

		int titleLength = EXCEL_WIDTH_MIN;
		if (EXCEL_WIDTH_AUTO.equalsIgnoreCase(ConverterUtil.toString(width))) {
			// 取得title名
			String title = ConverterUtil.toString(columAttrMap.get(EXCEL_TITLE));
			titleLength = title.length() * 2;

		} else {
			titleLength = ConverterUtil.toInteger(width, 10);
		}

		if (titleLength < EXCEL_WIDTH_MIN) {
			titleLength = EXCEL_WIDTH_MIN;
		} else if (titleLength > EXCEL_WIDTH_MAX) {
			titleLength = EXCEL_WIDTH_MAX;
		}
		return 256 * titleLength;
	}

	/**
	 * 创建整个sheet
	 * 
	 * 抬头和数据在一起的list
	 * 
	 * @param workSheet
	 * @param titleAndBodyList
	 */
	public static void setSheetValues(Workbook workbook, Sheet workSheet, List<Map<String, Object>> titleAndBodyList) {

		for (int i = 0; i < titleAndBodyList.size(); i++) {
			if (0 == i) {
				// 设置抬头
				createTitle(workbook, workSheet, titleAndBodyList.get(i));
			} else {
				// 设置一行
				createRow(workbook, workSheet, i, titleAndBodyList.get(i));
			}
		}
	}

	/**
	 * 创建整个sheet
	 * 
	 * 抬头和数据分开的list
	 * 
	 * @param workSheet
	 *            sheet对象
	 * @param titMapList
	 *            抬头mapList
	 * @param bodyList
	 *            数据mapList
	 * 
	 */
	public static void setSheetValues(Workbook workbook, Sheet workSheet, List<Map<String, Object>> titMapList,
			List<Map<String, Object>> bodyList) {
		int rowIndex = 0;
		// 补全抬头
		titMapList = getFullTitle(titMapList);
		for (int i = 0; i < titMapList.size(); i++) {
			// 设置抬头
			createTitle(workbook, workSheet, i, titMapList.get(i));
			rowIndex++;
		}

		for (int y = 0; y < bodyList.size(); y++) {
			// 设置一行
			createRow(workbook, workSheet, rowIndex, bodyList.get(y));
			rowIndex++;
		}
	}

	/**
	 * 补全抬头
	 * 
	 * @param titMapList
	 *            抬头list
	 * @return 抬头list
	 */
	public static List<Map<String, Object>> getFullTitle(List<Map<String, Object>> titMapList) {
		// 如果只有一行抬头则返回
		if (1 >= titMapList.size()) {
			return titMapList;
		}

		List<Map<String, Object>> resutlList = new ArrayList<Map<String, Object>>();

		List<String> keyList = new ArrayList<String>();
		// 抬头key的list
		Map<String, String> titleKeyMap = new HashMap<String, String>();

		// 取得全部的title的KEY
		for (Map<String, Object> titleMap : titMapList) {

			for (String titleKey : titleMap.keySet()) {

				titleKeyMap.put(titleKey, "");
			}
		}
		// 添加到list中进行排序
		keyList.addAll(titleKeyMap.keySet());
		Collections.sort(keyList, new Comparator<String>() {
			@Override
			public int compare(String title1, String title2) {
				String[] title1Strs = title1.split("_");
				Integer t1Index = 0;
				if (title1Strs.length > 1) {
					t1Index = ConverterUtil.toInteger(title1Strs[1]);
				}
				String[] title2Strs = title2.split("_");
				Integer t2Index = 0;
				if (title2Strs.length > 1) {
					t2Index = ConverterUtil.toInteger(title2Strs[1]);
				}
				return t1Index.compareTo(t2Index);
			}
		});

		// 填充补全不够长的抬头，如果没有该列则设置成空
		for (int y = 0; y < titMapList.size(); y++) {
			// 使用链表使顺序不错乱
			Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
			for (String titleKey : keyList) {
				Object titleValue = titMapList.get(y).get(titleKey);
				tempMap.put(titleKey, null == titleValue ? "" : titleValue);
			}
			resutlList.add(tempMap);
		}

		// 返回新的list
		return resutlList;
	}

	/**
	 * 创建除title外的数据，bodylist每行包含行属性的map
	 * 
	 * @param workSheet
	 *            sheet
	 * @param bodyList
	 *            body数据
	 */
	public static void createBody(Workbook workbook, Sheet workSheet, List<Map<String, Object>> bodyList) {
		// 默认第一行是title
		int startNum = 1;
		for (int i = 0; i < bodyList.size(); i++) {
			createRow(workbook, workSheet, (startNum + i), bodyList.get(i));
		}
	}

	/**
	 * 创建除title外的数据，bodylist每行包含行属性的map
	 * 
	 * @param workSheet
	 *            sheet
	 * @param bodyList
	 *            body数据
	 */
	public static void createBody(Workbook workbook, Sheet workSheet, int startNum, List<Map<String, Object>> bodyList) {
		if (0 >= startNum) {
			// 默认第一行是title
			startNum = 1;
		}
		for (int i = 0; i < bodyList.size(); i++) {
			createRow(workbook, workSheet, (startNum + i), bodyList.get(i));
		}
	}

	/**
	 * 创建除title外的数据，bodylist对应columnsAttrMapList中每一行属性map
	 * 
	 * @param workSheet
	 *            sheet
	 * @param bodyList
	 *            body数据
	 */
	public static void createBody(Workbook workbook, Sheet workSheet, int startNum, List<Map<String, Object>> bodyList,
			List<Map<String, Object>> columnsAttrMapList) {
		if (0 >= startNum) {
			// 默认第一行是title
			startNum = 1;
		}
		for (int i = 0; i < bodyList.size(); i++) {
			createRow(workbook, workSheet, (startNum + i), bodyList.get(i));
		}
	}

	/**
	 * 创建除title外的数据，bodylist对应columnsAttrMapList中每一行属性map
	 * 
	 * @param workSheet
	 *            sheet
	 * @param bodyList
	 *            body数据
	 */
	public static <T> void createBody(Workbook workbook, Sheet workSheet, Map<String, String> excelGetMethodMap,
			List<Excel> excelList, List<T> entityList) throws Exception {
		// 默认第一行是title
		int startNum = 1;
		// 循环一行数据
		for (T entity : entityList) {
			createRow(workbook, workSheet, (startNum++), excelGetMethodMap, excelList, entity);
		}
	}

	/**
	 * 创建除title外的数据，bodylist对应columnsAttrMapList中每一行属性map
	 * 
	 * @param workSheet
	 *            sheet
	 * @param bodyList
	 *            body数据
	 */
	public static <T> void createBody(Workbook workbook, Sheet workSheet, int startNum,
			Map<String, String> excelGetMethodMap, List<Excel> excelList, List<T> entityList) throws Exception {
		if (0 >= startNum) {
			// 默认第一行是title
			startNum = 1;
		}
		// 循环一行数据
		for (T entity : entityList) {
			createRow(workbook, workSheet, (startNum++), excelGetMethodMap, excelList, entity);
		}
	}

	/**
	 * 创建一行
	 * 
	 * @param workSheet
	 *            sheet
	 * @param rowNum
	 *            行号
	 * @param excelGetMethodMap
	 *            导出名与对应属性的get方法名的Map
	 * @param excelList
	 *            entity中对应的Excel Annotation
	 * @param entity
	 *            一行数据
	 * @throws Exception
	 */
	public static <T> void createRow(Workbook workbook, Sheet workSheet, int rowNum,
			Map<String, String> excelGetMethodMap, List<Excel> excelList, T entity) throws Exception {
		if (null == entity) {
			return;
		}

		// 创建一行
		Row workRow = null;
		workRow = workSheet.createRow(rowNum);

		for (int i = 0; i < excelList.size(); i++) {
			String exportName = excelList.get(i).exportName();
			String getMethodName = excelGetMethodMap.get(exportName);
			// 取得get方法
			Method getMethod = entity.getClass().getMethod(getMethodName);
			// 如果没有get方法则返回
			if (null == getMethod) {
				return;
			}
			Object value = getMethod.invoke(entity);
			Cell workCell = workRow.createCell(i);
			setCellVal(workCell, value);
		}
	}

	/**
	 * 创建一行
	 * 
	 * @param workSheet
	 *            sheet
	 * @param rowNum
	 *            行号
	 * @param rowMap
	 *            一行的数据
	 */
	public static void createRow(Workbook workbook, Sheet workSheet, int rowNum, Map<String, Object> rowMap) {
		if (null == rowMap) {
			return;
		}
		// 创建一行
		Row bodyRow = workSheet.createRow(rowNum);

		int cellIndex = 0;
		for (String bodyString : rowMap.keySet()) {
			Cell cell = bodyRow.createCell(cellIndex);
			Object cellVal = null;
			String bodyValue = ConverterUtil.toString(rowMap.get(bodyString));
			if (ConverterUtil.isNotEmpty(bodyValue) && bodyValue.contains(ConverterUtil.SEPARATOR_KEY_VALUE)) {
				Map<String, Object> columAttrMap = ConverterUtil.stringToMap(bodyValue);
				if (null != columAttrMap) {
					// 取得单元格的值
					cellVal = columAttrMap.get(ConverterUtil.EXCEL_TITLE);
					// 设置需要设置合并的单元格
					setMergeCells(workSheet, columAttrMap);
					CellStyle mergeStyle = getDefaultCellStyle(workbook);
					// 是否包含对齐方式
                    String align = ConverterUtil.toString(columAttrMap.get(ConverterUtil.EXCEL_ALIGN));
                    if(ConverterUtil.isNotEmpty(align)){
                      if("center".equals(align)){
                        // 水平居中
                        mergeStyle.setAlignment(CellStyle.ALIGN_CENTER);
                      }
                      if("left".equals(align)){
                        // 左对齐
                        mergeStyle.setAlignment(CellStyle.ALIGN_LEFT);
                      }
                      if("right".equals(align)){
                        // 右对齐
                        mergeStyle.setAlignment(CellStyle.ALIGN_RIGHT);
                      }
                    }
					// 是否包含颜色
                    String color = ConverterUtil.toString(columAttrMap.get(ConverterUtil.EXCEL_COLOR));
                    if(ConverterUtil.isNotEmpty(color)){
                      // 支持红色字体
                      if("red".equals(color)){
                        Font font = workbook.createFont();
                        font.setColor(HSSFColor.RED.index);
                        mergeStyle.setFont(font);
                      }
                    }
					cell.setCellStyle(mergeStyle);
				}
			} else {
				// 取得单元格的值
				cellVal = rowMap.get(bodyString);
			}
			// 设置单元格的值
			setCellVal(cell, cellVal);

			cellIndex++;
		}
	}

	/**
	 * 设置导出数据到Cell中
	 * 
	 * @param workBook
	 *            工作簿
	 * @param cell
	 *            单元格
	 * @param obj
	 *            值
	 * @throws Exception
	 *             异常
	 */
	public static void setCellVal(Cell cell, Object obj) {
		if (obj instanceof String) {
			// 字符型
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(ConverterUtil.toString(obj));
		} else if (obj instanceof Date) {
			// 日期型
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(ConverterUtil.toString(obj));
		} else if (obj instanceof Boolean) {
			// 布尔型
			cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
			cell.setCellValue(ConverterUtil.toBoolean(obj));
		} else if (obj instanceof Integer) {
			// 整型
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(ConverterUtil.toInteger(obj));
		} else if (obj instanceof Long) {
			// 长整型
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(ConverterUtil.toLong(obj));
		} else if (obj instanceof Double) {
			// 浮点型
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(ConverterUtil.toDouble(obj));
		} else if (obj instanceof BigDecimal) {
			// BigDecimal型
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			// excel的方法中没有BigDecimal
			cell.setCellValue(ConverterUtil.toDouble(obj));
		} else {
			// 其它都按字符型设置
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(ConverterUtil.toString(obj));
		}
	}
}