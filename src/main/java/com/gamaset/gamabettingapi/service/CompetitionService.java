package com.gamaset.gamabettingapi.service;

import static com.gamaset.gamabettingapi.utils.MarketFilterUtils.getAvailableCompetitions;
import static com.gamaset.gamabettingapi.utils.MarketFilterUtils.getCountriesDefault;
import static com.gamaset.gamabettingapi.utils.MarketFilterUtils.getMarketTypeCodesDefault;
import static com.gamaset.gamabettingapi.utils.MarketFilterUtils.getTimeRangeDefault;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betfair.aping.entities.CompetitionResult;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.exceptions.APINGException;
import com.betfair.aping.operations.CompetitionOperations;
import com.gamaset.gamabettingapi.model.CountryModel;
import com.gamaset.gamabettingapi.repository.PortalCongifAuthRepository;
import com.gamaset.gamabettingapi.repository.entity.PortalConfigAuthModel;

@Service
public class CompetitionService {

	private String appKey;
	private String ssoToken;
	private CompetitionOperations competitionOperation;
	private PortalCongifAuthRepository authRepository;
	
	@Autowired
	public CompetitionService(PortalCongifAuthRepository authRepository, CompetitionOperations competitionOperation) {
		this.competitionOperation = competitionOperation;
		this.authRepository = authRepository;
		PortalConfigAuthModel portalConfigAuthModel = this.authRepository.findById(1L).get();
		this.appKey = portalConfigAuthModel.getAppKey();
		this.ssoToken = portalConfigAuthModel.getSsoToken();
	}

	public List<CompetitionResult> listCompetitionsByEventTypeId(Long eventTypeId, boolean onlyAvailable) throws APINGException, IOException {
		try {

			MarketFilter marketFilter = new MarketFilter();

			Set<String> eventTypeIds = new HashSet<>();
			eventTypeIds.add(eventTypeId.toString());
			marketFilter.setEventTypeIds(eventTypeIds);
			marketFilter.setMarketTypeCodes(getMarketTypeCodesDefault());
			marketFilter.setMarketStartTime(getTimeRangeDefault());
			marketFilter.setMarketCountries(getCountriesDefault());
			
			if(onlyAvailable) {
				marketFilter.setCompetitionIds(getAvailableCompetitions());
			}

			return competitionOperation.listCompetitions(marketFilter, appKey, ssoToken);
		} catch (APINGException apiEx) {// RETURN STATUS_CODE=400
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}

	public List<CountryModel> listCountriesByEventTypeId(Long eventTypeId) throws APINGException, IOException {
		try {

			MarketFilter marketFilter = new MarketFilter();

			Set<String> eventTypeIds = new HashSet<>();
			eventTypeIds.add(eventTypeId.toString());
			marketFilter.setEventTypeIds(eventTypeIds);

			marketFilter.setMarketTypeCodes(getMarketTypeCodesDefault());

			return competitionOperation.listCountries(marketFilter, appKey, ssoToken);
		} catch (APINGException apiEx) {// RETURN STATUS_CODE=400
			throw apiEx;
		} catch (IOException e) {
			throw e;
		}
	}

}
