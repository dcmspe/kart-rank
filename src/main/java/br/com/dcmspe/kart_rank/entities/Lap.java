package br.com.dcmspe.kart_rank.entities;
 
 import java.time.LocalTime;
 public class Lap {
 	
 	
 	private final Integer lapNumber;
 	
 	private final Pilot pilot;
 	
 	private final LocalTime localTime;
 	
 	private final LocalTime timeLap;
 	
 	private final Double averageTimeLap;
 	
 	public Lap(Integer lapNumber, Pilot pilot, LocalTime localTime, LocalTime timeLap, Double averageTimeLap) {
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
 
 	public LocalTime getTimeLap() {
 		return timeLap;
 	}
 	
 }