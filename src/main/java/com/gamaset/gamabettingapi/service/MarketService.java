package com.gamaset.gamabettingapi.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betfair.aping.entities.MarketBook;
import com.betfair.aping.entities.MarketCatalogue;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.entities.MarketTypeResult;
import com.betfair.aping.exceptions.APINGException;
import com.betfair.aping.operations.MarketOperations;

@Service
public class MarketService {

	@Value("${betfair.security.appKey}")
	private String appKey;
	@Value("${betfair.security.ssoToken}")
	private String ssoToken;

	@Autowired
	private MarketOperations marketOperation;

	public List<MarketTypeResult> listMarketTypesByEventId(Long eventId) throws APINGException, IOException {
		try {
			Set<String> eventIds = new HashSet<>();
			eventIds.add(eventId.toString());

			MarketFilter marketFilter = new MarketFilter();
			marketFilter.setEventIds(eventIds);

			return marketOperation.listMarketTypes(marketFilter, appKey, ssoToken);
		} catch (APINGException apiEx) {
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}

	public List<MarketCatalogue> listMarketCatalogueByEventId(Long eventId) throws APINGException, IOException {
		try {
			Set<String> eventIds = new HashSet<>();
			eventIds.add(eventId.toString());

			MarketFilter marketFilter = new MarketFilter();
			marketFilter.setEventIds(eventIds);

			return marketOperation.listMarketCatalogue(marketFilter, appKey, ssoToken);
		} catch (APINGException apiEx) {
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}

	public List<MarketBook> listMarketBookByMarketId(String marketId) throws APINGException, IOException {
		try {
			
			return marketOperation.listMarketBook(Arrays.asList(marketId), appKey, ssoToken);
		} catch (APINGException apiEx) {
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}

}
