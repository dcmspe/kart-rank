package br.com.dcmspe.kart_rank.helpers;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import br.com.dcmspe.kart_rank.entities.Lap;
import br.com.dcmspe.kart_rank.entities.Pilot;

public abstract class RaceFactoryEntities {
	
	
	
	public static void createEntities(List<CSVObject> csvObjects, Map<String, Pilot> pilots, List<Lap> laps) {
		for (CSVObject csvObject : csvObjects) {

			Pilot pilot = factoryPilot(csvObject, pilots);

			factoryLap(csvObject, pilot, laps);
		}

	}
	
	private static void factoryLap(CSVObject csvObject, Pilot pilot, List<Lap> laps) {
		Lap lap = generateLap(csvObject, pilot);

		laps.add(lap);
	}
	
	private static Pilot factoryPilot(CSVObject csvObject, Map<String, Pilot> pilots) {
		String pilotCode = csvObject.getColumn2();

		Pilot pilot = pilots.get(pilotCode);
		if (pilots.isEmpty()) {
			pilot = createPilot(csvObject);
			pilots.put(pilotCode, pilot);
		} else if (pilots.get(pilotCode) == null) {
			pilot = createPilot(csvObject);
			pilots.put(pilotCode, pilot);
		}
		return pilot;
	}
	
	private static Pilot createPilot(CSVObject csvObject) {
		return new Pilot(csvObject.getColumn2(), csvObject.getColumn3());
	}
	
	private static Lap generateLap(CSVObject csvObject, Pilot pilot) {
		LocalTime localTime = LocalTime.parse(csvObject.getColumn1());
		
		Integer lapNumber = new Integer(csvObject.getColumn4());
		
		Period lapTime = createPeriod(csvObject.getColumn5());
		Double averageTimeLap = Double.valueOf(csvObject.getColumn6());

		Lap lap = new Lap(lapNumber, pilot, localTime, lapTime, averageTimeLap);
		return lap;
	}
	
	private static Period createPeriod(String stringPeriod){
		PeriodFormatter formatter = new PeriodFormatterBuilder()
				.appendHours()
				.appendSeparator(":")
				.appendMinutes()
				.appendSeparator(":")
				.appendSeconds()
				.appendSeparator(".")
				.appendMillis().toFormatter();
		return formatter.parsePeriod(stringPeriod);
	}
}