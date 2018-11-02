package com.gamaset.gamabettingapi.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betfair.aping.entities.EventResult;
import com.betfair.aping.entities.EventTypeResult;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.exceptions.APINGException;
import com.betfair.aping.operations.EventOperations;

@Service
public class EventService {

	@Value("${betfair.security.appKey}")
	private String appKey;
	@Value("${betfair.security.ssoToken}")
	private String ssoToken;

	@Autowired
	private EventOperations eventOperation;

	public List<EventTypeResult> listEventTypes() throws APINGException, IOException {

		try {
			MarketFilter marketFilter = new MarketFilter();
			return eventOperation.listEventTypes(marketFilter, appKey, ssoToken);
		} catch (APINGException apiEx) {// RETURN STATUS_CODE=400
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}

	public List<EventResult> listEvents(Long eventTypeId) throws APINGException, IOException {
		try {
			Set<String> eventTypeIds = new HashSet<>();
			eventTypeIds.add(eventTypeId.toString());

			MarketFilter marketFilter = new MarketFilter();
			marketFilter.setEventTypeIds(eventTypeIds);

			return eventOperation.listEvents(marketFilter, appKey, ssoToken);
		} catch (APINGException apiEx) {
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}

	}
	
	public List<EventResult> listEventsByCompetitionId(Long competitionId) throws APINGException, IOException {
		try {
			Set<String> competitionIds = new HashSet<>();
			competitionIds.add(competitionId.toString());

			MarketFilter marketFilter = new MarketFilter();
			marketFilter.setCompetitionIds(competitionIds);

			return eventOperation.listEvents(marketFilter, appKey, ssoToken);
		} catch (APINGException apiEx) {
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}


}
