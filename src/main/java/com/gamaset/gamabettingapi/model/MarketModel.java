package com.gamaset.gamabettingapi.model;

import java.util.ArrayList;
import java.util.List;

import com.betfair.aping.entities.MarketCatalogue;
import com.betfair.aping.entities.Runner;

public class MarketModel {

	private String marketId;
	private String marketName;
	private List<PriceMarket> prices;

	public MarketModel(String marketId, String marketName) {
		this.marketId = marketId;
		this.marketName = marketName;
		this.prices = new ArrayList<>();
	}

	public MarketModel withPrices(List<Runner> runners, List<MarketCatalogue> listMarketCatalogue) {
		for (Runner runner : runners) {
			listMarketCatalogue.forEach(mc -> {
				if (mc.getMarketId().equals(marketId)) {
					mc.getRunners().forEach(rn -> {
						if (runner.getSelectionId().equals(rn.getSelectionId())) {
							PriceMarket pm = new PriceMarket();
							pm.setOdd(runner.getEx().getAvailableToBack()
									.get(runner.getEx().getAvailableToBack().size() - 1).getPrice());
							pm.setSelectionId(rn.getSelectionId());
							pm.setSelectionName(rn.getRunnerName());
							this.prices.add(pm);
						}
					});
				}
			});
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

	public List<PriceMarket> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceMarket> prices) {
		this.prices = prices;
	}

}
