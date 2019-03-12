package com.gamaset.gamabettingapi.service;

import static com.gamaset.gamabettingapi.utils.MarketFilterUtils.buildWithEventTypes;
import static com.gamaset.gamabettingapi.utils.MarketFilterUtils.getFavoritesCompetitions;
import static com.gamaset.gamabettingapi.utils.MarketFilterUtils.getMarketTypeCodesDefault;
import static com.gamaset.gamabettingapi.utils.MarketFilterUtils.getTimeRangeDefault;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betfair.aping.entities.EventResult;
import com.betfair.aping.entities.EventTypeResult;
import com.betfair.aping.entities.MarketBook;
import com.betfair.aping.entities.MarketCatalogue;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.exceptions.APINGException;
import com.betfair.aping.operations.EventOperations;
import com.betfair.aping.operations.MarketOperations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamaset.gamabettingapi.model.EventGroupedByCompetitionModel;
import com.gamaset.gamabettingapi.model.EventModel;
import com.gamaset.gamabettingapi.repository.PortalCongifAuthRepository;
import com.gamaset.gamabettingapi.repository.entity.PortalConfigAuthModel;
import com.gamaset.gamabettingapi.utils.MarketFilterUtils;

@Service
public class EventService {

	private String appKey;
	private String ssoToken;
	private ObjectMapper mapper;
	private EventOperations eventOperation;
	private MarketOperations marketOperation;
	private PortalCongifAuthRepository authRepository;
	
	@Autowired
	public EventService(ObjectMapper mapper, EventOperations eventOperation, MarketOperations marketOperation, PortalCongifAuthRepository authRepository) {
		this.mapper = mapper;
		this.eventOperation = eventOperation;
		this.marketOperation = marketOperation;
		this.authRepository = authRepository;
		PortalConfigAuthModel portalConfigAuthModel = this.authRepository.findById(1L).get();
		this.appKey = portalConfigAuthModel.getAppKey();
		this.ssoToken = portalConfigAuthModel.getSsoToken();
	}

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

	public List<EventGroupedByCompetitionModel> listEventsByFavoriteCompetitions(Long eventTypeId) throws APINGException, IOException {
		List<EventModel> events = new ArrayList<>();
		try {
			MarketFilter marketFilter = new MarketFilter();
			marketFilter.setEventTypeIds(buildWithEventTypes(eventTypeId));
			marketFilter.setCompetitionIds(getFavoritesCompetitions());
			marketFilter.setMarketTypeCodes(getMarketTypeCodesDefault());
			marketFilter.setMarketStartTime(getTimeRangeDefault());
			System.out.println(">> Competitions: " + mapper.writeValueAsString(getFavoritesCompetitions()));
			
			// LISTANDO EVENTOS
			List<EventResult> listEvents = new ArrayList<>();
			List<EventResult> listEventsAux = eventOperation.listEvents(marketFilter, appKey, ssoToken);
			if(listEventsAux.size() > 40) {
				Collections.shuffle(listEventsAux);
				for (int i = 0; i < 40; i++) {listEvents.add(listEventsAux.get(i));}
			}else {
				listEvents.addAll(listEventsAux);
			}
			
			for (EventResult eventResult : listEvents) {
				EventModel eModel = new EventModel();
				eModel.withEventDescription(eventResult.getEvent());
				events.add(eModel);
			}
			
			// FILTRANDO MERCADOS
			marketFilter.setEventIds(listEvents.stream().map(e -> e.getEvent().getId()).collect(Collectors.toSet()));
			List<MarketCatalogue> listMarketCatalogue = marketOperation.listMarketCatalogue(marketFilter, appKey, ssoToken);
			for (EventModel event : events) {
				for (MarketCatalogue marketCatalogue : listMarketCatalogue) {
					if(marketCatalogue.getEvent().getId().equalsIgnoreCase(event.getId())) {
						event.addMarket(marketCatalogue);
						event.withCompetition(marketCatalogue.getCompetition());
					}
				}
			}

			// FILTRANDO ODDS POR MERCADO
			Set<String> marketIds = listMarketCatalogue.stream().map(e -> e.getMarketId()).collect(Collectors.toSet());
			List<MarketBook> listMarketBook = marketOperation.listMarketBook(marketIds, MarketFilterUtils.getPriceProjectionDefault(), appKey, ssoToken);
			for (EventModel event : events) {
				for (MarketBook marketBook : listMarketBook) {
					if(marketBook.getMarketId().equalsIgnoreCase(event.getMarkets().get(0).getMarketId())){
						event.getMarkets().get(0).withPrices(marketBook.getRunners(), listMarketCatalogue);
						break;
					}
				}
			}
			
		} catch (APINGException apiEx) {// RETURN STATUS_CODE=400
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
		
		Collections.sort(events);
		
		List<EventGroupedByCompetitionModel> response = new ArrayList<>();
		Set<String> competitionIds = events.stream().map(e -> e.getCompetition().getId()).collect(Collectors.toSet());

		for (String competitionId : competitionIds) {
			response.add(new EventGroupedByCompetitionModel(getEventByCompetitionId(competitionId, events)));
		}
		
		return response;
	}

	private List<EventModel> getEventByCompetitionId(String competitionId, List<EventModel> events) {
		List<EventModel> eventResponse = new ArrayList<>();
		for (EventModel event : events) {
			if(event.getCompetition().getId().equalsIgnoreCase(competitionId)) {
				eventResponse.add(event);
			}
		}
		return eventResponse;
	}
		
		
//	public List<EventMarketResult> listResumeEvents(Long eventTypeId) throws APINGException, IOException {
//		try {
//			List<EventMarketResult> events = new ArrayList<>();
//
//			MarketFilter marketFilter = new MarketFilter();
//
//			Set<String> eventTypeIds = new HashSet<>();
//			eventTypeIds.add(eventTypeId.toString());
//			marketFilter.setEventTypeIds(eventTypeIds);
//
//			Set<String> marketTypeCodes = new HashSet<>();
//			marketTypeCodes.add("MATCH_ODDS");
//			marketFilter.setMarketTypeCodes(marketTypeCodes);
//
//			Set<String> competitionIds = new HashSet<>();
//			competitionIds.add("10932509");
//			competitionIds.add("81");
//			competitionIds.add("117");
//			competitionIds.add("59");
//			marketFilter.setCompetitionIds(competitionIds);
//			TimeRange timeRange = new TimeRange();
//			timeRange.setFrom(Date.from(Instant.now()));
//			timeRange.setTo(Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//			marketFilter.setMarketStartTime(timeRange);
//			
//			System.out.println(">> [listEventTypes] Get all Event Types");
//			List<EventResult> listEvents = eventOperation.listEvents(marketFilter, appKey, ssoToken);
//			System.out.println(">> listEvents: " + listEvents.size());
//
//			for (EventResult eventResult : listEvents) {
//				Set<String> eventIds = new HashSet<>();
//				eventIds.add(eventResult.getEvent().getId());
//				marketFilter.setEventIds(eventIds);
//
//				EventMarketResult eventMarket = new EventMarketResult();
//				
//				Optional<MarketCatalogue> marketCatalogue = marketOperation.listMarketCatalogue(marketFilter, appKey, ssoToken).stream().findFirst();
//				eventMarket.withEvent(eventResult.getEvent());
//				eventMarket.withMarketDescription(marketCatalogue.isPresent() ? marketCatalogue.get() : new MarketCatalogue());
//
//				Set<String> marketIds = new HashSet<>();
//				marketIds.add(eventResult.getEvent().getId());
//				Optional<MarketBook> marketBookOptional = marketOperation.listMarketBook(marketIds, appKey, ssoToken).stream().findFirst();
//				eventMarket.withMarketBook(marketBookOptional.isPresent() : marketBookOptional.get() : Collections.emptyList());
//				events.add(eventMarket);
//			}
//
//			return events;
//		} catch (APINGException apiEx) {
//			throw apiEx;
//		} catch (IOException e) {
//			throw e;
//		}
//
//	}

	public List<EventResult> listEventsByCompetitionId(Long eventTypeId, Long competitionId)
			throws APINGException, IOException {
		try {
			MarketFilter marketFilter = new MarketFilter();

			Set<String> eventTypeIds = new HashSet<>();
			eventTypeIds.add(eventTypeId.toString());
			marketFilter.setEventTypeIds(eventTypeIds);

			Set<String> competitionIds = new HashSet<>();
			competitionIds.add(competitionId.toString());
			marketFilter.setCompetitionIds(competitionIds);

			return eventOperation.listEvents(marketFilter, appKey, ssoToken);
		} catch (APINGException apiEx) {
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}

}
