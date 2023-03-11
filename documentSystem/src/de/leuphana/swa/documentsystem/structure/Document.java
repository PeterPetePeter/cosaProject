package de.leuphana.swa.documentsystem.structure;

public class Document {
	private static Integer lastId = 0;

	private Integer id;

	private String titel;
	private String text;

	public Document(String titel) {
		this.titel = titel;
		id = ++lastId;
	}

	public Integer getId() {
		return id;
	}

	public String getTitel() {
		return titel;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
