package Automation.LinkedIn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.record.cf.FontFormatting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.linkedIn.init.Common;
import com.linkedIn.init.SeleniumInit;
import com.linkedIn.loginPage.Index.LoginPageIndex;

/**
 * Hello world!
 * 
 */
public class App {

	public static void main(String[] args) throws FileNotFoundException {

	/*	FileInputStream file = new FileInputStream(new File(
				"I:\\Development\\LinkedIn\\XSLS\\LinkedIn_Test_Cases.xlsx"));
		try {

			int numOfRow = 2;

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheet("Sign In");

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				if (row.getRowNum() == numOfRow) {
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();

						if (cell.getColumnIndex() == 1)
							// Check the cell type and format accordingly
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								System.out.print(cell.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								System.out.print(cell.getStringCellValue());
								break;
							}
					}
				}
				// System.out.println("");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	*/
		
		
	
			
/*
	    		XSSFWorkbook workbook = new XSSFWorkbook();
	             
	    	        //Create a blank sheet
	    	        XSSFSheet sheet = workbook.createSheet("Data");
	    	        
	    	        
	    	        
	    	        sheet.createRow(0).createCell(0).setCellValue("Test Number");
	    	        System.out.println(1);
	    	       sheet.getRow(0).createCell(1).setCellValue("Test Name");
	    	        System.out.println(2);
	    	         sheet.getRow(0).createCell(2).setCellValue("Start Time");
	    	        System.out.println(3);
	    	        sheet.getRow(0).createCell(3).setCellValue("End Time");
	    	        System.out.println(4);
	    	        sheet.getRow(0).createCell(4).setCellValue("Total Execution Time");
	    	        
	    	        sheet.createRow(rownum).createCell(0).setCellValue(getTestDetails(LoginPageIndex.row_num, 0));
	    	        sheet.createRow(rownum).createCell(1).setCellValue(getTestDetails(LoginPageIndex.row_num, 1));
	    	        sheet.createRow(rownum).createCell(2).setCellValue(SeleniumInit.start_date);
	    	        sheet.createRow(rownum).createCell(3).setCellValue(SeleniumInit.end_date);
	    	        sheet.createRow(rownum).createCell(4).setCellValue(SeleniumInit.diff_date);
	    	        
	    	      
	    	      
	    	       
	    	        try
	    	        {
	    	            //Write the workbook in file system
	    	            FileOutputStream out = new FileOutputStream(new File("XSLS//Test.xlsx"));
	    	            workbook.write(out);
	    	            out.close();
	    	            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
	    	        }
	    	        catch (Exception e)
	    	        {
	    	            e.printStackTrace();
	    	        }
	    }*/
	
	
				
	}
}	
			
		
			
		

