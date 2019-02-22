package com.gamaset.gamabettingapi.endpoint;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.betfair.aping.entities.MarketBook;
import com.betfair.aping.entities.MarketCatalogue;
import com.betfair.aping.entities.MarketTypeResult;
import com.betfair.aping.exceptions.APINGException;
import com.gamaset.gamabettingapi.service.MarketService;

@CrossOrigin(origins = "*")
@RestController
public class MarketEndpoint {

	@Autowired
	private MarketService marketService;

	@GetMapping(value = "/v1/events/{eventId}/market-types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<MarketTypeResult> listMarketTypesByEventId(@PathVariable(value = "eventId") Long eventId) throws IOException, APINGException {
		return marketService.listMarketTypesByEventId(eventId);
	}

	@GetMapping(value = "/v1/events/{eventId}/market-catalogues", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<MarketCatalogue> listMarketCatalogueByEventId(@PathVariable(value = "eventId") Long eventId) throws IOException, APINGException {
		return marketService.listMarketCatalogueByEventId(eventId);
	}

	@GetMapping(value = "/v1/market-catalogues/{marketId}/market-books", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<MarketBook> listMarketBookByMarketId(@PathVariable(value = "marketId") String marketId) throws IOException, APINGException {
		return marketService.listMarketBookByMarketId(marketId);
	}

}
