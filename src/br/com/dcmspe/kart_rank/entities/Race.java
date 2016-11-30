package br.com.dcmspe.kart_rank.entities;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Race {
	
	private final HashMap<String, Pilot> pilots;

	private final HashMap<Integer, Lap> laps; 
	
	public Race(){
		this.pilots = new LinkedHashMap<String, Pilot>();
		this.laps = new LinkedHashMap<Integer, Lap>();
	}
	
	public void insertPilot(Pilot pilot) {
		this.pilots.put(pilot.getCode(), pilot);
	}
	
	public void insertLap(Lap lap) {
		this.laps.put(lap.getLapNumber(), lap);
	}

	public HashMap<String, Pilot> getPilots() {
		return pilots;
	}

	public HashMap<Integer, Lap> getLaps() {
		return laps;
	}
	
}
