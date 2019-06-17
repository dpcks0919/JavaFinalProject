package edu.handong.csee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private static ArrayList<String> errorList = new ArrayList<String>();

	public static ArrayList<String> getErrorList() {
		return errorList;
	}

	public ArrayList<String> getData10(InputStream is, String fileName) {
		ArrayList<String> values = new ArrayList<String>();

		try {
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(is);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				String temp = "";
				int count = 0;
				try {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						// Check the cell type and format accordingly

						switch (cell.getCellType()) {
						case NUMERIC:
							count++;
							temp += "," + "\"" + cell.getNumericCellValue() + "\"";
							break;

						case STRING:
							// System.out.print(cell.getStringCellValue() );
							count++;
							temp += "," + "\"" + cell.getStringCellValue() + "\"";
							break;
						}
					}
					if (count != 7 && count != 0)
						throw new MissingDataException();
					if (!temp.isBlank()) {
						values.add(temp);
					}
				} catch (MissingDataException e) {
					if (!errorList.contains(fileName))
						errorList.add(fileName);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	public ArrayList<String> getData20(InputStream is, String fileName) {
		ArrayList<String> values = new ArrayList<String>();

		try {
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(is);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				String temp = "";
				int count = 0;
				try {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						// Check the cell type and format accordingly

						switch (cell.getCellType()) {
						case NUMERIC:
							count++;
							temp += "," + "\"" + cell.getNumericCellValue() + "\"";
							break;

						case STRING:
							// System.out.print(cell.getStringCellValue() );
							count++;
							temp += "," + "\"" + cell.getStringCellValue() + "\"";
							break;
						}
					}
					if (count != 5 && count != 0)
						throw new MissingDataException();
					if (!temp.isBlank()) {
						values.add(temp);
					}
				} catch (MissingDataException e) {
					if (!errorList.contains(fileName))
						errorList.add(fileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

}
