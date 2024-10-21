package driverFactory;

import org.openqa.selenium.WebDriver;

import commonfunction.FuntionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	WebDriver driver;
	String inputpath ="./FileInput/Controller.xlsx";
	String outputpath ="./FileOutput/HybridResults.xlsx";
	String TCSheet ="MasterTestCases";
	public void startTest() throws Throwable
	{
		String Module_Status="";
		String Module_New ="";
		//create reference obejct for accessing excel methods
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		for(int i=1;i<=xl.rowCount(TCSheet);i++)
	{
		if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("y"))
		{
			String TCModule = xl.getCellData(TCSheet, i, 1);
			for(int j=1;j<=xl.rowCount(TCModule);j++) {
				String Description = xl.getCellData(TCModule, j, 0);
				String ObjectType = xl.getCellData(TCModule, j, 1);
				String Ltype = xl.getCellData(TCModule, j, 2);
				String Lvalue = xl.getCellData(TCModule, j, 3);
				String Testdata = xl.getCellData(TCModule, j, 4);
				try {
					if(ObjectType.equalsIgnoreCase("startbrowser"))
					{
						driver = FuntionLibrary.startBrowser();
					}
					if(ObjectType.equalsIgnoreCase("openurl")) {
						FuntionLibrary.openUrl();
					}
					if(ObjectType.equalsIgnoreCase("waitforelement")) {
						FuntionLibrary.waitForElement(Ltype, Lvalue, Testdata);
					}
					if(ObjectType.equalsIgnoreCase("typeaction")) {
						FuntionLibrary.typeAction(Ltype, Lvalue, Testdata);
					}
					if(ObjectType.equalsIgnoreCase("clickaction")) {
						FuntionLibrary.clickAction(Ltype, Lvalue);
					}
					if(ObjectType.equalsIgnoreCase("validateTitle")) {
						FuntionLibrary.validateTitle(Testdata);
					}
					if(ObjectType.equalsIgnoreCase("closebrowser")) {
						FuntionLibrary.closeBrowser();
					}
					xl.setCellData(TCModule, j, 5, "pass", outputpath);
					Module_Status="true";
				} catch (Exception e) {
					xl.setCellData(TCModule, j, 5, "fail", outputpath);
					Module_New="false";
				}
				if(Module_Status.equalsIgnoreCase("True"))
				{
					//write as pass into TCsheet in Status cell
					xl.setCellData(TCSheet, i, 3, "Pass", outputpath);
				}
				
			}
			if(Module_New.equalsIgnoreCase("False"))
			{
				//write as Fail into TCsheet in Status cell
				xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
			}
		}
		else
		{
			xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
		}
	}
	
}

}
