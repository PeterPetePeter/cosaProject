package de.leuphana.cosa.pricingsystem.behaviour.service;

public interface PricingSystemCommandService {

	String NORMAL_TARIF = "Normal";
	String GUENSTIGER_REISEN_TARIF = "Guenstiger";
	String SCHNAEPPCHEN_TARIF = "Schnappi";

	void  getPrice(String tarif, float length) throws Exception;
	public void getTarifs();
	public float calcPrice(float len);
}