package tflow.com.yzs.flow.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.alibaba.fastjson.JSON;

import tflow.com.yzs.flow.common.annotation.ExcelField;

public class ExportExcel {
	
	private static final Class<?>[] METHOD_PARAMS = new Class[] {};
	
	private static final Object[] INVOKE_OBJECT = new Object[] {};
	
	private Map<String, Map<String, String>> jsonMap = new HashMap<String, Map<String,String>>();

	private SXSSFWorkbook wb;	
	/**
	 * excel下所有表单
	 * key:表单下标 从0开始
	 * sheet:key对象的表单对象
	 */
	private Map<Integer, Sheet> allSheet = new HashMap<Integer, Sheet>();
	
	/**
	 * 每个表单对应需要填充的数据类型解析
	 * key:表单下标 从0开始
	 * List<Object[]> :key对象需要填充的数据对象的解析参数
	 */
	private Map<Integer, List<Object[]>> allType = new HashMap<Integer, List<Object[]>>();
	
	/**
	 * 每个表单填充了数据的行数
	 */
	private Map<Integer, Integer> dataRows = new HashMap<Integer, Integer>();
	/**
	 * 表示是第几张表单
	 */
	private int sheetIndex = -1;
	/**
	 * 当前行号
	 */
	private int rowNum;
	
	/**
	 * 样式列表
	 */
	private Map<String, CellStyle> styles;
	
	public ExportExcel() {
		wb = new SXSSFWorkbook();
		styles = defaultStyles(wb);
	}
	
	/**
	 * 设置数据格式
	 * @param styles
	 * @return
	 */
	public ExportExcel setStyles(Map<String, CellStyle> styles) {
		this.styles = styles;
		return this;
	}
	
	public ExportExcel createSheet(String sheetName, Class<?> type) {
		return createSheet(sheetName, sheetName, type);
	}
	
	/**
	 * 创建表单 
	 * @param sheetName 表单名
	 * @param titleName 表单里面填充数据的总标题名称
	 * @param type
	 * @return
	 */
	public ExportExcel createSheet(String sheetName, String titleName, Class<?> type) {
		rowNum = 0;
		sheetIndex ++;
		allSheet.put(sheetIndex, formatSheet(sheetName, titleName, type));
		return this;
	}
	/**
	 * 格式化表单
	 * @param sheetName
	 * @param titleName
	 * @param type
	 * @return
	 */
	private Sheet formatSheet(String sheetName, String titleName, Class<?> type) {
		SXSSFSheet sheet = wb.createSheet(sheetName);
		List<String> headerList = new ArrayList<String>();
		//解析数据类型
		parse(headerList, type);
		//渲染标题
		if (titleName != null && !"".equals(titleName)) {
			Row titleRow = sheet.createRow(rowNum ++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(titleName);
			//合并单元格
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));

		}
		// 添加字段对应的标题
		Row headerRow = sheet.createRow(rowNum++);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			cell.setCellValue(headerList.get(i));
			sheet.trackAllColumnsForAutoSizing();
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {  
			int colWidth = sheet.getColumnWidth(i) * 2;
	        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
		return sheet;
	}
	
	private void parse(List<String> headerList, Class<?> type) {
		List<Object[]> list = new ArrayList<Object[]>();
		Field[] fields = type.getDeclaredFields();
		for (Field f: fields) {
			ExcelField annotation = f.getAnnotation(ExcelField.class);
			if (annotation != null) {
				list.add(new Object[] {f, annotation});
			}
		}
		//排序
		Collections.sort(list, new Comparator<Object[]>() {

			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[1]).sort()).compareTo(
						new Integer(((ExcelField)o2[1]).sort()));
			}
		});
		
		for (Object[] objs:list) {
			headerList.add(((ExcelField)objs[1]).title());
		}
		allType.put(sheetIndex, list);
	}
	
	private String getMethodName(Field f) {
		String name = f.getName();		
		return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	/**
	 * 给指定表单填充数据
	 * @param dataList
	 * @return
	 */
	public <E> ExportExcel addData(List<E> dataList) {
		int num = getRowNum();
		Sheet sheet = getSheet();
		for(E e: dataList) {
			Row row = sheet.createRow(num ++);
			int colunm = 0;
			for(Object[] objs: getTypeList()) {
				Object val = null;
				Field f = (Field)objs[0];
				try {
					Method method = e.getClass().getMethod(getMethodName(f), METHOD_PARAMS);
					method.setAccessible(true);
					val = method.invoke(e, INVOKE_OBJECT);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				ExcelField ex = (ExcelField)objs[1];
				createCell(row, ex, colunm ++ , val, f);
			}
		}
		dataRows.put(sheetIndex, num);
		return this;
	}
	
	/**
	 * 创建单元格
	 * @param row
	 * @param align
	 * @param column
	 * @param val
	 * @return
	 */
	private Cell createCell(Row row, ExcelField ex, int column, Object val, Field f) {
		Cell cell = row.createCell(column);
		int align = ex.align();
		CellStyle style = styles.get("data"+(align>=1&&align<=3?align:""));
		try {
			if (val == null){
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue(getValue(val.toString(), f.getName(), ex.jsonStr()));
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				DataFormat format = wb.createDataFormat();
	            style.setDataFormat(format.getFormat(ex.datePattern()));
				cell.setCellValue((Date) val);
			}
		} catch (Exception e) {
			e.printStackTrace();
			cell.setCellValue(val.toString());
		}
		cell.setCellStyle(style);
		return cell;
	}
	/**
	 * 创建默认样式
	 * @param wb
	 * @return
	 */
	private Map<String, CellStyle> defaultStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBold(true);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.RIGHT);
		styles.put("data3", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		return styles;
	}
	
	private Sheet getSheet() {
		return allSheet.get(sheetIndex);
	}
	
	private List<Object[]> getTypeList() {
		return allType.get(sheetIndex);
	}
	
	private Integer getRowNum() {
		return dataRows.get(sheetIndex) == null ? rowNum:dataRows.get(sheetIndex);
	}
	
	private String getValue(String key, String fieldName, String jsonStr) {
		if (jsonStr != null && !"".equals(jsonStr)) {
			Map<String, String> map = jsonMap.get(fieldName);
			if (map == null) {
				map = JSON.parseObject(jsonStr, Map.class);
				jsonMap.put(fieldName, map);
			}
			key = map.get(key);
		}
		return key;
	}
	
	/**
	 * 输出数据流
	 * @param os 输出数据流
	 */
	public ExportExcel write(OutputStream os) throws IOException{
		wb.write(os);
		return this;
	}
	
	/**
	 * 输出到客户端
	 * @param fileName 输出文件名
	 */
	public ExportExcel write(HttpServletResponse response, String fileName) throws IOException{
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		write(response.getOutputStream());
		return this;
	}
	
	/**
	 * 输出到文件
	 * @param fileName 输出文件名
	 */
	public ExportExcel writeFile(String name) throws FileNotFoundException, IOException{
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		return this;
	}
	
	/**
	 * 清理临时文件
	 */
	public ExportExcel dispose(){
		wb.dispose();
		return this;
	}

}
