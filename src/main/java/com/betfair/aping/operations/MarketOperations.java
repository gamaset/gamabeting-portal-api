package com.betfair.aping.operations;

import static com.betfair.aping.enums.ApiNgOperation.LISTMARKETBOOK;
import static com.betfair.aping.enums.ApiNgOperation.LISTMARKETCATALOGUE;
import static com.betfair.aping.enums.ApiNgOperation.LISTMARKETTYPES;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.betfair.aping.entities.MarketBook;
import com.betfair.aping.entities.MarketCatalogue;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.entities.MarketTypeResult;
import com.betfair.aping.entities.PriceProjection;
import com.betfair.aping.enums.ApiNgOperation;
import com.betfair.aping.enums.MarketProjection;
import com.betfair.aping.enums.MarketSort;
import com.betfair.aping.enums.MatchProjection;
import com.betfair.aping.enums.OrderProjection;
import com.betfair.aping.exceptions.APINGException;
import com.betfair.aping.util.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MarketOperations implements ApiNgOperations {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private HttpUtil requester;

	public List<MarketTypeResult> listMarketTypes(MarketFilter marketFilter, String appKey, String ssoToken)
			throws APINGException, IOException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FILTER, marketFilter);
		params.put(LOCALE, locale);

		String result = makeRequest(LISTMARKETTYPES.getOperationName(), params, appKey, ssoToken);

		List<MarketTypeResult> results = mapper.readValue(result, new TypeReference<List<MarketTypeResult>>() {
		});

		return results;
	}

	public List<MarketCatalogue> listMarketCatalogue(MarketFilter marketFilter, String appKey, String ssoToken)
			throws APINGException, IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FILTER, marketFilter);
		params.put(LOCALE, locale);

        Set<MarketProjection> marketProjection = new HashSet<MarketProjection>();
        marketProjection.add(MarketProjection.COMPETITION);
        marketProjection.add(MarketProjection.EVENT);
//        marketProjection.add(MarketProjection.EVENT_TYPE);
//        marketProjection.add(MarketProjection.MARKET_DESCRIPTION);
        marketProjection.add(MarketProjection.RUNNER_DESCRIPTION);

		params.put(MARKET_PROJECTION, marketProjection);
		params.put(SORT, MarketSort.FIRST_TO_START);
		params.put(MAX_RESULT, 100);

		String result = makeRequest(LISTMARKETCATALOGUE.getOperationName(), params, appKey, ssoToken);

		List<MarketCatalogue> results = mapper.readValue(result, new TypeReference<List<MarketCatalogue>>() {
		});

		return results;
	}

	public List<MarketBook> listMarketBook(Set<String> marketIds, PriceProjection priceProjection, String appKey, String ssoToken)
			throws APINGException, IOException {
		
		System.out.println("Listando MarketBook por ID de Mercado: " + marketIds);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MARKET_IDS, marketIds);//REQUIRED
		params.put(LOCALE, locale);
		
        params.put(PRICE_PROJECTION, priceProjection);
        
        OrderProjection orderProjection = OrderProjection.EXECUTABLE;
        params.put(ORDER_PROJECTION, orderProjection);

        MatchProjection matchProjection = MatchProjection.ROLLED_UP_BY_AVG_PRICE;
        params.put(MATCH_PROJECTION, matchProjection);
        
//        String currencyCode = null;
//        params.put("currencyCode", currencyCode);

//		 boolean includeOverallPosition, 
//		 boolean partitionMatchedByStrategyRef, 
//		 Set<String> customerStrategyRefs, 
//		 Date matchedSince, 
//		 Set<BetId> betIds
		
		
		String result = makeRequest(LISTMARKETBOOK.getOperationName(), params, appKey, ssoToken);
		
		List<MarketBook> results = mapper.readValue(result, new TypeReference<List<MarketBook>>() {	});
		
		return results;
	}

	@Override
	public String makeRequest(String operation, Map<String, Object> params, String appKey, String ssoToken)
			throws APINGException, JsonProcessingException {

		String requestString = mapper.writeValueAsString(params);
		System.out.println(">> MarketOperations " +operation+ " Request: " + requestString);

		String response = requester.sendPostRequest(requestString, operation, appKey, ssoToken);
		if (Objects.nonNull(response)) {
			return response;
		} else {
			throw new APINGException();
		}
	}

}
