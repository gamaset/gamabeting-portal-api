package com.gamaset.gamabettingapi.model;

import java.util.ArrayList;
import java.util.List;

import com.betfair.aping.entities.Runner;

public class MarketModel {

	private String marketId;
	private String marketName;
	private List<Double> prices;
	
	public MarketModel(String marketId, String marketName) {
		this.marketId = marketId;
		this.marketName = marketName;
		this.prices = new ArrayList<>();
	}

	public MarketModel withPrices(List<Runner> runners) {
		for (Runner runner : runners) {
			this.prices.add(runner.getEx().getAvailableToBack().get(runner.getEx().getAvailableToBack().size() - 1).getPrice());
		}
		return this;
	}
	
	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public List<Double> getPrices() {
		return prices;
	}

	public void setPrices(List<Double> prices) {
		this.prices = prices;
	}

}
