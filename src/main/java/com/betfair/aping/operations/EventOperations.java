package com.betfair.aping.operations;

import static com.betfair.aping.enums.ApiNgOperation.LISTEVENTS;
import static com.betfair.aping.enums.ApiNgOperation.LISTEVENTTYPES;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.betfair.aping.entities.EventResult;
import com.betfair.aping.entities.EventTypeResult;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.enums.MarketSort;
import com.betfair.aping.exceptions.APINGException;
import com.betfair.aping.util.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EventOperations implements ApiNgOperations {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private HttpUtil requester;

	public List<EventTypeResult> listEventTypes(MarketFilter marketFilter, String appKey, String ssoToken)
			throws APINGException, IOException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FILTER, marketFilter);
		params.put(LOCALE, locale);

		String result = makeRequest(LISTEVENTTYPES.getOperationName(), params, appKey, ssoToken);

		List<EventTypeResult> events = mapper.readValue(result, new TypeReference<List<EventTypeResult>>() {
		});

		return events;
	}

	public List<EventResult> listEvents(MarketFilter marketFilter, String appKey, String ssoToken)
			throws APINGException, IOException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FILTER, marketFilter);
		params.put(LOCALE, locale);
		params.put(SORT, MarketSort.FIRST_TO_START);
//		params.put(MAX_RESULT, 10);

		String result = makeRequest(LISTEVENTS.getOperationName(), params, appKey, ssoToken);

		List<EventResult> events = mapper.readValue(result, new TypeReference<List<EventResult>>() {
		});

		return events;
	}

	@Override
	public String makeRequest(String operation, Map<String, Object> params, String appKey, String ssoToken)
			throws APINGException, JsonProcessingException {

		String requestString = mapper.writeValueAsString(params);
		System.out.println(">> EventOperations Request: " + requestString);

		String response = requester.sendPostRequest(requestString, operation, appKey, ssoToken);
		if (Objects.nonNull(response)) {
			return response;
		} else {
			throw new APINGException();
		}
	}

}
