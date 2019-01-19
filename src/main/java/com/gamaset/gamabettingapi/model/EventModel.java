package com.gamaset.gamabettingapi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.betfair.aping.entities.Competition;
import com.betfair.aping.entities.Event;
import com.betfair.aping.entities.MarketCatalogue;

public class EventModel implements Comparable<EventModel> {
	
	private String id;
	private String name;
	private String timezone;
	private Date openDate;
	private Competition competition;
	private List<MarketModel> markets;
	
	public EventModel() {
		this.markets = new ArrayList<>();
	}
	
	@Override
	public int compareTo(EventModel o) {
		return getCompetition().getId().compareTo(o.getCompetition().getId());
	}
	
	public EventModel withEventDescription(Event event) {
		this.id = event.getId();
		this.name = event.getName();
		this.timezone = event.getTimezone();
		this.openDate = event.getOpenDate();
		return this;
	}
	

	public void addMarket(MarketCatalogue marketCatalogue) {
		markets.add(new MarketModel(marketCatalogue.getMarketId(), marketCatalogue.getMarketName()));
	}
	
	public EventModel withCompetition(Competition comp) {
		this.competition = comp;
		return this;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	
	public List<MarketModel> getMarkets() {
		return markets;
	}
	public void setMarkets(List<MarketModel> markets) {
		this.markets = markets;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@Override
	public String toString() {
		return "EventModel [id=" + id + ", name=" + name + ", timezone=" + timezone + ", openDate=" + openDate
				+ ", competition=" + competition + ", markets=" + markets + "]";
	}
	
}