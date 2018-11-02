package com.gamaset.gamabettingapi.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betfair.aping.entities.CompetitionResult;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.exceptions.APINGException;
import com.betfair.aping.operations.CompetitionOperations;

@Service
public class CompetitionService {

	@Value("${betfair.security.appKey}")
	private String appKey;
	@Value("${betfair.security.ssoToken}")
	private String ssoToken;

	@Autowired
	private CompetitionOperations competitionOperation;

	public List<CompetitionResult> listCompetitionsByEventTypeId(Long eventTypeId) throws APINGException, IOException {
		try {
			
			Set<String> eventTypeIds = new HashSet<>();
			eventTypeIds.add(eventTypeId.toString());
			
			MarketFilter marketFilter = new MarketFilter();
			marketFilter.setEventTypeIds(eventTypeIds);
			
			return competitionOperation.listCompetitions(marketFilter, appKey, ssoToken);
		} catch (APINGException apiEx) {// RETURN STATUS_CODE=400
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}

}
