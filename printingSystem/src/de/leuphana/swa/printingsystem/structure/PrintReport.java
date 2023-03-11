package de.leuphana.swa.printingsystem.structure;

import java.time.LocalDate;

public class PrintReport  {
	private String printDate;
	private String title;
	private String content;
	
	public PrintReport() {
		printDate = LocalDate.now().toString();
	}

	public String getPrintDate() {
		return printDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
