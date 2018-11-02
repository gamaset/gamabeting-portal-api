package com.betfair.aping.operations;

import static com.betfair.aping.enums.ApiNgOperation.LISTCOMPETITIONS;

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
		System.out.println(String.format(">> params(%s) appKey(%s) ssoId(%s)", params, appKey, ssoToken));

		String result = makeRequest(LISTCOMPETITIONS.getOperationName(), params, appKey, ssoToken);

		List<CompetitionResult> competitions = mapper.readValue(result, new TypeReference<List<CompetitionResult>>() {
		});

		return competitions;
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
