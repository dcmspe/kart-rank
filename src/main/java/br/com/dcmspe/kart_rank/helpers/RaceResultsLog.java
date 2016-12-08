package br.com.dcmspe.kart_rank.helpers;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Duration;

import br.com.dcmspe.kart_rank.entities.Pilot;
import br.com.dcmspe.kart_rank.entities.Race;

public class RaceResultsLog {

	private final Map<Pilot, Duration> finishers;
	
	private final Map<Pilot, Duration> unfinishers;
	
	private final Race race;
	
	private static Logger log = LogManager.getLogger(RaceResultsLog.class.getName());
	
	public RaceResultsLog(Race race) {
		this.race = race;
		
		this.finishers = race.getFinishersByOrder();
		
		this.unfinishers = race.getUnfinishersByOrder();
		
	}
	
	public void generateLogForResults(){
		int position = 1;
		for (Pilot pilot : this.finishers.keySet()) {
			StringBuffer message = new StringBuffer();
			message.append("Posição: " + position + " ");
			message.append("Código do Piloto: " + pilot.getCode() + " ");
			message.append("Nome do Piloto: " + pilot.getName() + " ");
			message.append("Quantidade de Voltas: " + race.lapsFromPilot(pilot).size() + " ");
			message.append("Tempo Total de Prova: " + race.totalTimeFromPilot(pilot) + " ");
			log.trace(message);
			position++;
		}
		
		for (Pilot pilot : this.unfinishers.keySet()) {
			StringBuffer message = new StringBuffer();
			message.append("Posição: " + "*" + " ");
			message.append("Código do Piloto: " + pilot.getCode() + " ");
			message.append("Nome do Piloto: " + pilot.getName() + " ");
			message.append("Quantidade de Voltas: " + race.lapsFromPilot(pilot).size() + " ");
			message.append("Tempo Total de Prova: " + race.totalTimeFromPilot(pilot) + " ");
			log.trace(message);
		}
		
	}
	
	
	
	
}
