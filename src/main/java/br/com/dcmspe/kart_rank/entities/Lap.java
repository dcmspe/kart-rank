package br.com.dcmspe.kart_rank.entities;

import org.joda.time.LocalTime;
import org.joda.time.Period;

public class Lap{

	private final Integer lapNumber;

	private final Pilot pilot;

	private final LocalTime localTime;

	private final Period timeLap;

	private final Double averageTimeLap;

	public Lap(Integer lapNumber, Pilot pilot, LocalTime localTime, Period timeLap, Double averageTimeLap) {
		this.lapNumber = lapNumber;
		this.pilot = pilot;
		this.localTime = localTime;
		this.timeLap = timeLap;
		this.averageTimeLap = averageTimeLap;
	}

	public Pilot getPilot() {
		return pilot;
	}

	public LocalTime getLocalTime() {
		return localTime;
	}

	public Double getAverageTimeLap() {
		return averageTimeLap;
	}

	public Integer getLapNumber() {
		return lapNumber;
	}

	public Period getTimeLap() {
		return timeLap;
	}


}