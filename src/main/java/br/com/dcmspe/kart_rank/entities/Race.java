package br.com.dcmspe.kart_rank.entities;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.Duration;

import br.com.dcmspe.kart_rank.helpers.CSVObject;
import br.com.dcmspe.kart_rank.helpers.RaceFactoryEntities;

public class Race {
	
	/**
	 * List of pilots
	 */
	public HashMap<String, Pilot> pilots;
	/**
	 * List of laps
	 */
	public List<Lap> laps;

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
		RaceFactoryEntities.createEntities(csvObjects, pilots, laps);
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

	public Map<Pilot, List<Lap>> lapsFromPilots() {
		Map<Pilot, List<Lap>> lapsFromPilots = new HashMap<Pilot, List<Lap>>();

		for (Pilot pilot : pilots.values()) {
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
	
	public List<Pilot> getUnfinishers() {

		Map<Pilot, List<Lap>> lapsFromPilots = this.lapsFromPilots();
		List<Pilot> unfinishers = new ArrayList<Pilot>();

		for (Map.Entry<Pilot, List<Lap>> entry : lapsFromPilots.entrySet()) {
			if (entry.getValue().size() < Race.NUMBER_OF_LAPS_TO_COMPLETE_THE_RACE) {
				unfinishers.add(entry.getKey());
			}
		}
		return unfinishers;
	}

	public Map<Pilot, Duration> getFinishersByOrder() {
		List<Pilot> finishers = this.getFinishers();

		Map<Pilot, Duration> finishersByDuration = caculateTotalLapsFromEachPilot(finishers);

		Map<Pilot, Duration> finishersByOrder = orderFinishersCollection(finishersByDuration);

		return finishersByOrder;
	}

	private Map<Pilot, Duration> orderFinishersCollection(Map<Pilot, Duration> unsorted) {
		
		Map<Pilot, Duration> result = new LinkedHashMap<>();
		
		unsorted.entrySet().stream()
				.sorted(Map.Entry.<Pilot, Duration>comparingByValue())
				.forEachOrdered(map -> result.put(map.getKey(), map.getValue()));
		
		return result;
	}

	private Map<Pilot, Duration> caculateTotalLapsFromEachPilot(List<Pilot> finishers) {
		Map<Pilot, Duration> finishersByDuration = new HashMap<Pilot, Duration>();

		for (Pilot pilot : finishers) {
			List<Lap> laps = this.lapsFromPilot(pilot);
			long total = 0;
			for (Lap lap : laps) {
				total = total + lap.getTimeLap().toStandardDuration().getMillis();
			}
			finishersByDuration.put(pilot, Duration.millis(total));
		}
		return finishersByDuration;
	}

	public Map<Pilot, Duration> getUnfinishersByOrder() {
		List<Pilot> unfinishers = this.getUnfinishers();

		Map<Pilot, Duration> unfinishersByDuration = caculateTotalLapsFromEachPilot(unfinishers);

		Map<Pilot, Duration> unfinishersByOrder = orderFinishersCollection(unfinishersByDuration);

		return unfinishersByOrder;
	}

	public long totalTimeFromPilot(Pilot pilot) {
		List<Lap> laps = this.lapsFromPilot(pilot);
		long totalRaceTime = 0;
		for (Lap lap : laps) {
			totalRaceTime = totalRaceTime + lap.getTimeLap().toStandardDuration().getMillis();
		}
		return totalRaceTime;
	}

	public List<Lap> bestLapFromEachPilot(){
		List<Lap> bestLapFromEachPilot = new LinkedList<>();
		System.out.println(this.pilots);
		for (Map.Entry<String, Pilot> entry : this.pilots.entrySet()) {
			List<Lap> laps = entry.getValue().getLaps();
			Lap bestLap = laps.stream()
            .min(new Comparator<Lap>() {
				@Override
				public int compare(Lap lap1, Lap lap2) {
					Duration durationLap1 = lap1.getTimeLap().toStandardDuration();
					Duration durationLap2 = lap2.getTimeLap().toStandardDuration();
					return durationLap1.compareTo(durationLap2);
				}
			}).get();
			bestLapFromEachPilot.add(bestLap);
		}
		
		return bestLapFromEachPilot;
	}
	
	public Map<String, Lap> bestLapFromEachPilotSortedByPilotCode(){
		
		Map<String, Lap> bestLapFromEachPilot = this.bestLapFromEachPilot().stream().collect(Collectors.toMap(lap -> lap.getPilot().getCode(), lap -> lap));
		
		Map<String, Lap> sortedBestLapFromEachPilot = new LinkedHashMap<>();
		bestLapFromEachPilot.entrySet().stream().sorted(Map.Entry.<String, Lap>comparingByKey())
        .forEachOrdered(entry -> sortedBestLapFromEachPilot.put(entry.getKey(), entry.getValue()));
		
		return sortedBestLapFromEachPilot;
	}
	
	
}