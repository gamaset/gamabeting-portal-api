package com.betfair.aping.operations;

import static com.betfair.aping.enums.ApiNgOperation.LISTCOMPETITIONS;
import static com.betfair.aping.enums.ApiNgOperation.LISTCOUNTRIES;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.betfair.aping.entities.CompetitionResult;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.exceptions.APINGException;
import com.betfair.aping.util.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamaset.gamabettingapi.model.CountryModel;

@Component
public class CompetitionOperations implements ApiNgOperations {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private HttpUtil requester;

	public List<CompetitionResult> listCompetitions(MarketFilter marketFilter, String appKey, String ssoToken)
			throws APINGException, IOException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FILTER, marketFilter);
		params.put(LOCALE, locale);

		System.out.println(">> [listCompetition] Get Competitions");

		String result = makeRequest(LISTCOMPETITIONS.getOperationName(), params, appKey, ssoToken);

		List<CompetitionResult> competitions = mapper.readValue(result, new TypeReference<List<CompetitionResult>>() {});
		
		return competitions;
	}

	public List<CountryModel> listCountries(MarketFilter marketFilter, String appKey, String ssoToken) throws APINGException, IOException {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(FILTER, marketFilter);
			params.put(LOCALE, locale);

			System.out.println(">> [listCompetition] Get Competitions");

			String result = makeRequest(LISTCOUNTRIES.getOperationName(), params, appKey, ssoToken);
			List<CountryModel> countries = mapper.readValue(result, new TypeReference<List<CountryModel>>() {});

			return countries;
	}

	@Override
	public String makeRequest(String operation, Map<String, Object> params, String appKey, String ssoToken)
			throws APINGException, JsonProcessingException {

		String requestString = mapper.writeValueAsString(params);
		System.out.println(">> Request: " + requestString);

		String response = requester.sendPostRequest(requestString, operation, appKey, ssoToken);
		if (Objects.nonNull(response)) {
			return response;
		} else {
			throw new APINGException();
		}
	}



}
