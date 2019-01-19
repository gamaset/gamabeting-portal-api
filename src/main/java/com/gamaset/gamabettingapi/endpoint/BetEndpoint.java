package com.gamaset.gamabettingapi.endpoint;

import java.io.IOException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.betfair.aping.entities.CompetitionResult;
import com.betfair.aping.exceptions.APINGException;
import com.gamaset.gamabettingapi.service.CompetitionService;

@RestController
public class BetEndpoint {

	@Autowired
	private CompetitionService competitionService;

	@GetMapping(value = "/v1/event-types/{eventTypeId}/competitions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<CompetitionResult> listEventTypes(
			@PathVariable(value = "eventTypeId") Long eventTypeId,
			@PathParam("onlyAvailable") boolean onlyAvailable) throws IOException, APINGException {
		return competitionService.listCompetitionsByEventTypeId(eventTypeId, onlyAvailable);
	}

	@GetMapping(value = "/v1/event-types/{eventTypeId}/coutries", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String listCountries(@PathVariable(value = "eventTypeId") Long eventTypeId)
			throws IOException, APINGException {
		return competitionService.listCountriesByEventTypeId(eventTypeId);
	}

}
