package com.gamaset.gamabettingapi.endpoint;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.betfair.aping.entities.EventResult;
import com.betfair.aping.entities.EventTypeResult;
import com.betfair.aping.exceptions.APINGException;
import com.gamaset.gamabettingapi.service.EventService;

@RestController
public class EventEndpoint {

	@Autowired
	private EventService eventService;

	@GetMapping(value = "/v1/event-types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<EventTypeResult> listEventTypes() throws IOException, APINGException {
		return eventService.listEventTypes();
	}

	@GetMapping(value = "/v1/event-types/{eventTypeId}/events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<EventResult> listEventsByEventTypeId(@PathVariable(value = "eventTypeId") Long eventTypeId)
			throws IOException, APINGException {

		return eventService.listEvents(eventTypeId);
	}

	@GetMapping(value = "/v1/competitions/{competitionId}/events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<EventResult> listEventsByCompetitionId(@PathVariable(value = "competitionId") Long competitionId)
			throws IOException, APINGException {

		return eventService.listEventsByCompetitionId(competitionId);
	}
	
}
