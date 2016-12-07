package br.com.dcmspe.kart_rank.entities;
 
 import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.dcmspe.kart_rank.helpers.CSVObject;
 
 public class Race {
 	
 	private final HashMap<String, Pilot> pilots;
 
 	private final List<Lap> laps; 
 	
 	public Race(){
 		this.pilots = new LinkedHashMap<String, Pilot>();
 		this.laps = new ArrayList<Lap>();
 	}
 	
 	public Race(List<CSVObject> csvObjects) {
 		
 		this.pilots = new LinkedHashMap<String, Pilot>();
 		this.laps = new ArrayList<Lap>();
		this.createEntities(csvObjects);
	}

 	
 	public HashMap<String, Pilot> getPilots() {
 		return pilots;
 	}
 
 	public List<Lap> getLaps() {
 		return laps;
 	}
 	
 	private void createEntities(List<CSVObject> csvObjects){
 		for (CSVObject csvObject : csvObjects) {
 			
 			Pilot pilot = factoryPilot(csvObject);
 			
 			factoryLap(csvObject, pilot);
		}
 		
 	}

	private void factoryLap(CSVObject csvObject, Pilot pilot) {
		Lap lap = generateLap(csvObject, pilot);
		
		this.insertLap(lap);
	}

	private Lap generateLap(CSVObject csvObject, Pilot pilot) {
		LocalTime localTime = LocalTime.parse(csvObject.getColumn1()); 
		Integer lapNumber = new Integer(csvObject.getColumn4());
		LocalTime timeLap = LocalTime.parse(csvObject.getColumn5());
		Double averageTimeLap = Double.valueOf(csvObject.getColumn6());
		
		Lap lap = new Lap(lapNumber, pilot, localTime, timeLap, averageTimeLap);
		return lap;
	}

	private void insertLap(Lap lap) {
		this.laps.add(lap);
	}

	private Pilot factoryPilot(CSVObject csvObject) {
		String pilotCode = csvObject.getColumn2();
		
		Pilot pilot = pilots.get(pilotCode);
		if(pilots.isEmpty()){
			pilot = createPilot(csvObject);
			insertPilot(pilotCode, pilot);
			
		}else if(pilots.get(pilotCode) == null){
			pilot = createPilot(csvObject);
			insertPilot(pilotCode, pilot);
		}
		return pilot;
	}

	private void insertPilot(String pilotCode, Pilot pilot) {
		this.pilots.put(pilotCode, pilot);
	}

	private Pilot createPilot(CSVObject csvObject) {
		return new Pilot(csvObject.getColumn2(), csvObject.getColumn3());
	}
 	

	public Map<Pilot, List<Lap>> listLapsFromPilots() {
		Map<Pilot, List<Lap>> lapsFromPilots = new HashMap<Pilot, List<Lap>>();
		
		for (Pilot pilot : this.pilots.values()) {
			List<Lap> lapsFromPilot =  lapsFromPilot(pilot);
			lapsFromPilots.put(pilot, lapsFromPilot);
			
		}
		
		return lapsFromPilots;
	}

	public List<Lap> lapsFromPilot(Pilot pilot) {
		return this.laps.stream().filter(
				lap -> lap.getPilot().getCode().equals(pilot.getCode())).collect(Collectors.toList());
	}
 	
 }