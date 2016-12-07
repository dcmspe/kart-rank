package br.com.dcmspe.kart_rank.entities;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import br.com.dcmspe.kart_rank.helpers.CSVObject;

public class Race {

	/**
	 * List of pilots
	 */
	private final HashMap<String, Pilot> pilots;

	/**
	 * List of laps
	 */
	private final List<Lap> laps;

	/**
	 * Number of laps to complete a race
	 */
	private static int NUMBER_OF_LAPS_TO_COMPLETE_THE_RACE = 4;

	/**
	 * Constructor to create an empty race 
	 */
	public Race() {
		this.pilots = new LinkedHashMap<String, Pilot>();
		this.laps = new ArrayList<Lap>();
	}

	/**
	 * Create a race from CSVObject
	 * @param csvObjects
	 */
	public Race(List<CSVObject> csvObjects) {

		this.pilots = new LinkedHashMap<String, Pilot>();
		this.laps = new ArrayList<Lap>();
		this.createEntities(csvObjects);
	}

	/**
	 * Get all pilots from a race
	 * @return
	 */
	public HashMap<String, Pilot> getPilots() {
		return pilots;
	}

	/**
	 * Get all laps from a race
	 * @return
	 */
	public List<Lap> getLaps() {
		return laps;
	}

	private void createEntities(List<CSVObject> csvObjects) {
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
		
		Period lapTime = createPeriod(csvObject.getColumn5());
		Double averageTimeLap = Double.valueOf(csvObject.getColumn6());

		Lap lap = new Lap(lapNumber, pilot, localTime, lapTime, averageTimeLap);
		return lap;
	}
	
	private Period createPeriod(String stringPeriod){
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

	private void insertLap(Lap lap) {
		this.laps.add(lap);
	}

	private Pilot factoryPilot(CSVObject csvObject) {
		String pilotCode = csvObject.getColumn2();

		Pilot pilot = pilots.get(pilotCode);
		if (pilots.isEmpty()) {
			pilot = createPilot(csvObject);
			insertPilot(pilotCode, pilot);

		} else if (pilots.get(pilotCode) == null) {
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

	public Map<Pilot, List<Lap>> lapsFromPilots() {
		Map<Pilot, List<Lap>> lapsFromPilots = new HashMap<Pilot, List<Lap>>();

		for (Pilot pilot : this.pilots.values()) {
			List<Lap> lapsFromPilot = lapsFromPilot(pilot);
			lapsFromPilots.put(pilot, lapsFromPilot);

		}

		return lapsFromPilots;
	}

	public List<Lap> lapsFromPilot(Pilot pilot) {
		return this.laps.stream().filter(lap -> lap.getPilot().getCode().equals(pilot.getCode()))
				.collect(Collectors.toList());
	}

	public List<Pilot> getFinishers() {

		Map<Pilot, List<Lap>> lapsFromPilots = this.lapsFromPilots();
		List<Pilot> finishers = new ArrayList<Pilot>();

		for (Map.Entry<Pilot, List<Lap>> entry : lapsFromPilots.entrySet()) {
			if (entry.getValue().size() == Race.NUMBER_OF_LAPS_TO_COMPLETE_THE_RACE) {
				finishers.add(entry.getKey());
			}
		}
		return finishers;
	}

	public Map<Pilot, Duration> getFinishersByOrder() {
		List<Pilot> finishers = this.getFinishers();

		Map<Pilot, Duration> finishersByDuration = caculateTotalLapsFromEachPilot(finishers);

		Map<Pilot, Duration> finishersByOrder = orderFinishersCollection(finishersByDuration);

		return finishersByOrder;
	}

	private Map<Pilot, Duration> orderFinishersCollection(Map<Pilot, Duration> finishersByDuration) {
		
		
		Comparator<Entry<Pilot, Duration>> byValue = (entry1, entry2) -> entry1.getValue().compareTo(
		            entry2.getValue());
		
		Stream<Map.Entry<Pilot,Duration>> entries = finishersByDuration.entrySet().stream().sorted(byValue);
		
		return entries.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
	}

	private Map<Pilot, Duration> caculateTotalLapsFromEachPilot(List<Pilot> finishers) {
		Map<Pilot, Duration> finishersByDuration = new HashMap<Pilot, Duration>();

		for (Pilot pilot : finishers) {
			List<Lap> laps = this.lapsFromPilot(pilot);
			Duration total = Duration.ZERO;
			for (Lap lap : laps) {
				total.plus(lap.getTimeLap().toStandardDuration());
			}
			finishersByDuration.put(pilot, total);
		}
		return finishersByDuration;
	}

}