package com.gamaset.gamabettingapi.endpoint;

import java.io.IOException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.betfair.aping.entities.CompetitionResult;
import com.betfair.aping.exceptions.APINGException;
import com.gamaset.gamabettingapi.model.CountryModel;
import com.gamaset.gamabettingapi.service.CompetitionService;

@CrossOrigin(origins = "*")
@RestController
public class CompetitionEndpoint {

	@Autowired
	private CompetitionService competitionService;

	@GetMapping(value = "/v1/event-types/{eventTypeId}/competitions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<CompetitionResult> listEventTypes(
			@PathVariable(value = "eventTypeId") Long eventTypeId,
			@PathParam("onlyAvailable") boolean onlyAvailable) throws IOException, APINGException {
		List<CompetitionResult> listCompetitionsByEventTypeId = competitionService.listCompetitionsByEventTypeId(eventTypeId, onlyAvailable);
//		listCompetitionsByEventTypeId.forEach(c -> {
//			System.out.println(String.format("list.add(new CompetitionModel(%sL, \"%s\", \"%s\"));", c.getCompetition().getId(), c.getCompetition().getName(), c.getCompetitionRegion()));
//		});
		return competitionService.listCompetitionsByEventTypeId(eventTypeId, onlyAvailable);
	}

	@GetMapping(value = "/v1/event-types/{eventTypeId}/coutries", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<CountryModel> listCountries(@PathVariable(value = "eventTypeId") Long eventTypeId)
			throws IOException, APINGException {
		List<CountryModel> listCountriesByEventTypeId = competitionService.listCountriesByEventTypeId(eventTypeId);
		return listCountriesByEventTypeId;
	}

}
