package com.gamaset.gamabettingapi.model;

import java.util.List;

import com.betfair.aping.entities.Competition;

public class EventGroupedByCompetitionModel {

	private Competition competition;
	private List<EventModel> events;

	public EventGroupedByCompetitionModel(List<EventModel> eventByCompetitionId) {
		this.events = eventByCompetitionId;
	}

	public Competition getCompetition() {
		if (events.isEmpty()) {
			return null;
		}
		return events.get(0).getCompetition();
	}

	public List<EventModel> getEvents() {
		return events;
	}

	public void setEvents(List<EventModel> events) {
		this.events = events;
	}

}