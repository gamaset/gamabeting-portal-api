package com.gamaset.gamabettingapi.model;

import java.util.ArrayList;
import java.util.List;

public class CompetitionModel {

	private int id;
	private String name;
	private boolean favorite;
	
	public CompetitionModel(int id, String name, boolean favorite) {
		this.id = id;
		this.name = name;
		this.favorite = favorite;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	public static List<CompetitionModel> getCompetitionsAvailable(){
		List<CompetitionModel> competitions = new ArrayList<>();
		competitions.add(new CompetitionModel(228, "UEFA Liga dos Campeões", true));
		competitions.add(new CompetitionModel(7129730, "Inglaterra - Championship", true));
		competitions.add(new CompetitionModel(10932509, "Inglaterra - Premier League", true));
		competitions.add(new CompetitionModel(81, "Itália – Série A", true));
		competitions.add(new CompetitionModel(83, "Itália - Serie B", true));
		competitions.add(new CompetitionModel(99, "Portugal - Primeira liga", true));
		competitions.add(new CompetitionModel(117, "Espanha - La Liga", true));
		competitions.add(new CompetitionModel(55, "França - Ligue 1", true));
		competitions.add(new CompetitionModel(42886, "Inglaterra - FA Trophy", false));
		competitions.add(new CompetitionModel(105, "Escócia - Premiership", false));
		competitions.add(new CompetitionModel(2005, "UEFA Liga Europa", false));
		competitions.add(new CompetitionModel(30558, "Copa de Inglaterra", false));
		competitions.add(new CompetitionModel(11, "Holanda - Eerste Divisie", false));
		competitions.add(new CompetitionModel(11463202, "Itália - Serie C", false));
		competitions.add(new CompetitionModel(5627174, "México - Liga MX", false));
		competitions.add(new CompetitionModel(119, "Espanha - Segunda Divisão", false));
		competitions.add(new CompetitionModel(57, "França - Ligue 2", false));
		competitions.add(new CompetitionModel(67387, "Argentina - Primera Division", false));
//		competitions.add(new CompetitionModel(, "", false));
		return competitions;
	}
}
