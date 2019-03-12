package com.gamaset.gamabettingapi.service;

import static com.gamaset.gamabettingapi.utils.MarketFilterUtils.getPriceProjectionDefault;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betfair.aping.entities.MarketBook;
import com.betfair.aping.entities.MarketCatalogue;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.entities.MarketTypeResult;
import com.betfair.aping.exceptions.APINGException;
import com.betfair.aping.operations.MarketOperations;
import com.gamaset.gamabettingapi.repository.PortalCongifAuthRepository;
import com.gamaset.gamabettingapi.repository.entity.PortalConfigAuthModel;

@Service
public class MarketService {

	private String appKey;
	private String ssoToken;

	private MarketOperations marketOperation;
	private PortalCongifAuthRepository authRepository;
	
	@Autowired
	public MarketService(PortalCongifAuthRepository authRepository, MarketOperations marketOperation) {
		this.marketOperation = marketOperation;
		this.authRepository = authRepository;
		PortalConfigAuthModel portalConfigAuthModel = this.authRepository.findById(1L).get();
		this.appKey = portalConfigAuthModel.getAppKey();
		this.ssoToken = portalConfigAuthModel.getSsoToken();
	}

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
			Set<String> marketIds = new HashSet<>();
			marketIds.add(marketId);
			return marketOperation.listMarketBook(marketIds, getPriceProjectionDefault(), appKey, ssoToken);
		} catch (APINGException apiEx) {
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}

}
