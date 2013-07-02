package gov.dane.sige.restlet.dvp_xls.utils;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utility class for creating excel dynamic tables, using
 * {@link DivipolaSearchUtil} information.
 * 
 * @author mjlopezm
 */
public final class ExcelReportingUtil {
	private static final ExcelReportingUtil instance = new ExcelReportingUtil();

	public static ExcelReportingUtil getInstance() {
		return instance;
	}

	private void prepareCellDataRow(int[] indexs, HSSFRow row,
			JSONObject dataRow, JSONObject xlsparam,
			HashSet<Integer> colsForDeletion, HSSFCellStyle cellStyle)
			throws Exception {
		HSSFCell hssfCell = row.createCell(indexs[0]);
		hssfCell.setCellStyle(cellStyle);
		hssfCell.setCellValue(new HSSFRichTextString(dataRow.getString("cod").replaceAll("am", "").replaceAll("dstr", "")));
		HSSFCell hssfCell1 = row.createCell(indexs[1]);
		hssfCell1.setCellStyle(cellStyle);
		hssfCell1.setCellValue(new HSSFRichTextString(dataRow.getString("nom")));
		if (dataRow.has("depto")) {
			if (xlsparam.has("departamentos")) {
				HSSFCell cell0 = row.createCell(0);
				cell0.setCellStyle(cellStyle);
				cell0.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("departamentos").getString("cod")));
				HSSFCell cell1 = row.createCell(1);
				cell1.setCellStyle(cellStyle);
				cell1.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("departamentos").getString("nom")));
			}
			if (xlsparam.has("areasmetrop")) {
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("areasmetrop").getString("cod").replaceAll("am", "")));
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellStyle(cellStyle);
				cell3.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("areasmetrop").getString("nom")));
			} else {
				colsForDeletion.add(2);
				colsForDeletion.add(3);
			}
			if (xlsparam.has("distritos")) {
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellStyle(cellStyle);
				cell4.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("distritos").getString("cod").replaceAll("dstr", "")));
				HSSFCell cell5 = row.createCell(4);
				cell5.setCellStyle(cellStyle);
				cell5.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("distritos").getString("nom")));
			} else {
				colsForDeletion.add(4);
				colsForDeletion.add(5);
			}
		}
		if (dataRow.has("clase")) {
			HSSFCell hssfCell2 = row.createCell(indexs[2]);
			hssfCell2.setCellStyle(cellStyle);
			hssfCell2.setCellValue(new HSSFRichTextString(dataRow.getString("clase")));
			if (xlsparam.has("municipios")) {
				HSSFCell cell6 = row.createCell(6);
				cell6.setCellStyle(cellStyle);
				cell6.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("municipios").getString("cod")));
				HSSFCell cell7 = row.createCell(7);
				cell7.setCellStyle(cellStyle);
				cell7.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("municipios").getString("nom")));
			}
			if (xlsparam.has("departamentos")) {
				HSSFCell cell0 = row.createCell(0);
				cell0.setCellStyle(cellStyle);
				cell0.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("departamentos").getString("cod")));
				HSSFCell cell1 = row.createCell(1);
				cell1.setCellStyle(cellStyle);
				cell1.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("departamentos").getString("nom")));
			}
			if (xlsparam.has("areasmetrop")) {
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("areasmetrop").getString("cod").replaceAll("am", "")));
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellStyle(cellStyle);
				cell3.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("areasmetrop").getString("nom")));
			} else {
				colsForDeletion.add(2);
				colsForDeletion.add(3);
			}
			if (xlsparam.has("distritos")) {
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellStyle(cellStyle);
				cell4.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("distritos").getString("cod").replaceAll("dstr", "")));
				HSSFCell cell5 = row.createCell(4);
				cell5.setCellStyle(cellStyle);
				cell5.setCellValue(new HSSFRichTextString(xlsparam.getJSONObject("distritos").getString("nom")));
			} else {
				colsForDeletion.add(4);
				colsForDeletion.add(5);
			}
		}
	}

	protected JSONObject prepareColumnIndexes(int key) throws Exception {
		JSONObject idx = new JSONObject();
		idx.put("indexes", new JSONObject());
		idx.put("names", new JSONArray());
		switch (key) {
		case 1:
			idx.getJSONObject("indexes").put("departamentos",
					new int[] { 0, 1 });
			idx.getJSONArray("names").put("codigo departamento")
					.put("nombre departamento");
			break;
		case 2:
			idx.getJSONObject("indexes").put("areasmetrop", new int[] { 0, 1 });
			idx.getJSONArray("names").put("codigo area metropolitana")
					.put("nombre area metropolitana");
			break;
		case 3:
			idx.getJSONObject("indexes").put("distritos", new int[] { 0, 1 });
			idx.getJSONArray("names").put("codigo distrito")
					.put("nombre distrito");
			break;
		case 4:
			idx.getJSONObject("indexes").put("departamentos",
					new int[] { 0, 1 });
			idx.getJSONObject("indexes").put("areasmetrop", new int[] { 2, 3 });
			idx.getJSONObject("indexes").put("distritos", new int[] { 4, 5 });
			idx.getJSONObject("indexes").put("municipios", new int[] { 6, 7 });
			idx.getJSONArray("names").put("codigo departamento")
					.put("nombre departamento")
					.put("codigo area metropolitana")
					.put("nombre area metropolitana").put("codigo distrito")
					.put("nombre distrito").put("codigo municipio")
					.put("nombre municipio");
			break;
		case 5:
			idx.getJSONObject("indexes").put("departamentos",
					new int[] { 0, 1 });
			idx.getJSONObject("indexes").put("areasmetrop", new int[] { 2, 3 });
			idx.getJSONObject("indexes").put("distritos", new int[] { 4, 5 });
			idx.getJSONObject("indexes").put("municipios", new int[] { 6, 7 });
			idx.getJSONObject("indexes").put("cpoblados",
					new int[] { 8, 9, 10 });
			idx.getJSONArray("names").put("codigo departamento")
					.put("nombre departamento")
					.put("codigo area metropolitana")
					.put("nombre area metropolitana").put("codigo distrito")
					.put("nombre distrito").put("codigo municipio")
					.put("nombre municipio").put("codigo centro poblado")
					.put("nombre centro poblado").put("clase centro poblado");
			break;
		}
		return idx;
	}

	private HSSFCellStyle getHeaderColumnStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();

		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.ROSE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_MEDIUM);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_MEDIUM);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}

	private HSSFCellStyle getRowCellStyle(HSSFWorkbook xlsWb) {
		HSSFCellStyle style = xlsWb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}

	public void prepareReport(JSONObject xlsp, DivipolaSearchUtil searchUtil,
			ByteArrayOutputStream baos) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("divipola");
		JSONObject columnIndexes = prepareColumnIndexes(xlsp.getInt("level"));
		HSSFRow colsRow = sheet.createRow(0);

		final HSSFCellStyle headerColumnStyle = this.getHeaderColumnStyle(wb);
		final HSSFCellStyle cellStyle = this.getRowCellStyle(wb);

		final String listkey = xlsp.getString("listField");
		final JSONArray xlsdata = xlsp.getJSONArray(listkey);
		int rowIndex = 1;
		HashSet<Integer> colsForDeletion = new HashSet<Integer>();
		for (int i = 0; i < xlsdata.length(); i++) {
			final JSONObject dataRow = xlsdata.getJSONObject(i);
			HSSFRow row = sheet.createRow(rowIndex);
			final int[] indexs = (int[]) columnIndexes.getJSONObject("indexes").get(listkey);
			prepareCellDataRow(indexs, row, dataRow, xlsp, colsForDeletion, cellStyle);
			rowIndex++;
		}

		for (int i = 0; i < columnIndexes.getJSONArray("names").length(); i++) {
			String col = columnIndexes.getJSONArray("names").getString(i);
			boolean cellNotEmpty = true;
			if (!colsForDeletion.isEmpty()) {
				for (Integer cellIndex : colsForDeletion) {
					if (cellIndex.equals(i)) {
						cellNotEmpty = false;
						break;
					}
				}
			}
			if(cellNotEmpty){
				HSSFCell hssfcell = colsRow.createCell(i);
				hssfcell.setCellStyle(headerColumnStyle);
				hssfcell.setCellValue(new HSSFRichTextString(col));
			}
		}
		
		this.removeEmptyColumns(sheet, colsForDeletion);
		sheet.setZoom(3, 4);
		
		for (int i = 0; i < columnIndexes.getJSONArray("names").length(); i++) {
			sheet.autoSizeColumn(i);
		}

		wb.write(baos);
	}

	private void removeEmptyColumns(HSSFSheet sheet,
			HashSet<Integer> colsForDeletion) {
		boolean haveNulls = true;
		int deletedCells = 0;
		while (haveNulls) {
			HSSFRow hssfRow = sheet.getRow(sheet.getFirstRowNum());
			if (!hssfRow.equals(null)) {
				int cellCount = hssfRow.getLastCellNum();
				for (int cell = 0; cell < cellCount; cell++) {
					HSSFCell rcell = hssfRow.getCell(cell);
					if (rcell == null) {
						HSSFSheetUtil.deleteColumn(sheet, cell);
						deletedCells++;
						break;
					}
				}
				if (deletedCells == colsForDeletion.size()) {
					haveNulls = false;
				}
			}
		}
	}
}
