package com.gamaset.gamabettingapi.model;

public class PriceMarket {

	private long selectionId;
	private String selectionName;
	private Double odd;

	public PriceMarket() {
		// TODO Auto-generated constructor stub
	}
	
	public long getSelectionId() {
		return selectionId;
	}

	public void setSelectionId(long selectionId) {
		this.selectionId = selectionId;
	}

	public String getSelectionName() {
		return selectionName;
	}

	public void setSelectionName(String selectionName) {
		this.selectionName = selectionName;
	}

	public Double getOdd() {
		return odd;
	}

	public void setOdd(Double odd) {
		this.odd = odd;
	}

}
