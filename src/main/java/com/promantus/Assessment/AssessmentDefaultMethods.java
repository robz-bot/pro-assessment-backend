package com.promantus.Assessment;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

public final class AssessmentDefaultMethods {

	public static final void cleanSheet(Sheet sheet) {
		int numberOfRows = sheet.getPhysicalNumberOfRows();

		if (numberOfRows > 0) {
			for (int i = 3; i <= sheet.getLastRowNum(); i++) {
				if (sheet.getRow(i) != null) {
					sheet.removeRow(sheet.getRow(i));
				} else {
					System.out.println("Info: clean sheet='" + sheet.getSheetName() + "' ... skip line: " + i);
				}
			}
		} else {
			System.out.println("Info: clean sheet='" + sheet.getSheetName() + "' ... is empty");
		}
	}

	//Replace spl chars 
	// eg., + replace with \\+ 
	// to search the keywords with regex 
	public static final String replaceSplCharKeyword(String keyword) {
		List<String> temp = new ArrayList<>();
		temp.add("(");
		temp.add(")");
		temp.add("+");
		temp.add("$");
		temp.add("^");
		temp.add("{");
		temp.add("}");
		temp.add("?");
		temp.add(">");
		temp.add("<");
		temp.add(".");
		temp.add("[");
		temp.add("]");
		for (String temp1 : temp) {
			if (keyword.contains(temp1)) {
				keyword = keyword.replace(temp1, "\\" + temp1);
			}
		}

		return keyword;
	}

}
