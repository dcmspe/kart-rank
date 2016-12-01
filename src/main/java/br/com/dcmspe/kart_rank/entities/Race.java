package br.com.dcmspe.kart_rank.entities;
 
 import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
 			
 			String pilotCode = csvObject.getColumn2();
 			Pilot pilot = pilots.get(pilotCode);
 			if(pilots.isEmpty()){
 				this.pilots.put(pilotCode, pilot);
 			}else if(pilots.get(pilotCode) == null){
 				pilots.put(pilotCode, pilot);
 			}
 			
 			LocalTime localTime = LocalTime.parse(csvObject.getColumn1()); 
 			Integer lapNumber = new Integer(csvObject.getColumn4());
 			LocalTime timeLap = LocalTime.parse(csvObject.getColumn5());
 			Double averageTimeLap = Double.valueOf(csvObject.getColumn6());
 			
 			Lap lap = new Lap(lapNumber, pilot, localTime, timeLap, averageTimeLap);
 			laps.add(lap);
		}
 		
 	}
 	
 }