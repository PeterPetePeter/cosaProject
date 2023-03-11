package de.leuphana.swa.printingsystem.behaviour.service;

import java.util.Set;

import de.leuphana.swa.printingsystem.structure.PrintReport;
import de.leuphana.swa.printingsystem.structure.Printable;

public interface PrintingCommandService  {

	void print(String title, String content) throws UnsupportedPrintFormatException;
	public boolean printReportCheck();

}