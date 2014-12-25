package com.linkedIn.loginPage.Index;

import org.testng.annotations.Test;

import com.linkedIn.init.Common;
import com.linkedIn.init.SeleniumInit;

public class LoginPageIndex extends SeleniumInit {

	Common common = new Common(driver);
	
	static int logcase = 1;
	
	public static int row_num = 0;
	
	
	@Test
	public void verifyRegisterPage(){
		
		row_num = 7;
		
		common.logcase(common.getTestDetails(row_num, 0) + ": " + common.getTestDetails(row_num, 1));
		
		loginPageIndexPage.verify_Register_Page();
		
		common.testExcutionDetails();
	}
	
	@Test
	public void verifyLabelinPage(){
		
		row_num = 8;
		
		common.logcase(common.getTestDetails(row_num, 0) + ": " + common.getTestDetails(row_num, 1) );
		
		loginPageIndexPage.verify_Label_in_Page();
		
		common.testExcutionDetails();
	}
	
	@Test
	public void verifyMendatoryFields(){
		
		row_num = 9;
		
		common.logcase(common.getTestDetails(row_num, 0) + ": " + common.getTestDetails(row_num, 1) );
		
		loginPageIndexPage.verify_Mendatory_Fields();
		
		common.testExcutionDetails();
		
	}
	
	@Test
	public void verifyJoinnowButton(){
		
		row_num = 10;
		
		common.logcase(common.getTestDetails(row_num, 0) + ": " + common.getTestDetails(row_num, 1) );
		
		loginPageIndexPage.verify_JoinNow_Button();
		
		common.testExcutionDetails();
		
	}
	
	@Test
	public void clickJoinnowButton(){
		
		row_num = 11;
		
		common.logcase(common.getTestDetails(row_num, 0) + ": " + common.getTestDetails(row_num, 1) );
		
		loginPageIndexPage.click_JoinNow_Button();
		
		common.testExcutionDetails();
		
	}
	
	@Test
	public void textboxEditFunctionality(){
		
		row_num = 13;
		
		common.logcase(common.getTestDetails(row_num, 0) + ": " + common.getTestDetails(row_num, 1) );
		
		loginPageIndexPage.textbox_Edit_Functionality();
		
		common.testExcutionDetails();
		
	}
	
	
	

}
