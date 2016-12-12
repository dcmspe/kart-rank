package br.com.dcmspe.kart_rank.entities;

import java.util.ArrayList;
import java.util.List;

public class Pilot {
 	
 	private final String code;
 	
 	private final String name;
 	
 	private final List<Lap> laps;
 	
 	public String getName() {
 		return name;
 	}

	public Pilot(String code, String name) {
		super();
		this.code = code;
		this.name = name;
		this.laps = new ArrayList<Lap>();
	}

	public String getCode() {
		return code;
	}

	public List<Lap> getLaps() {
		return laps;
	}
	
	public void addLap(Lap lap) {
		laps.add(lap);
	}
	
 }