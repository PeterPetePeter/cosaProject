package de.leuphana.swa.printingsystem.structure;


public class PrintJob {
	private PrintJobState printJobState;
	private Printable printable;
	
	public PrintJob(Printable printable) {
		this.printable = printable;
		printJobState = new CreatedPrintJobState();
	}
	
	public void changePrintJobState(PrintJobAction printJobAction) {
		printJobState = printJobState.changePrintJobState(printJobAction);
	}
}
