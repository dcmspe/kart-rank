package br.com.dcmspe.kart_rank.tests;
 
 import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.model.EachTestNotifier;

import br.com.dcmspe.kart_rank.entities.Lap;
import br.com.dcmspe.kart_rank.entities.Pilot;
import br.com.dcmspe.kart_rank.entities.Race;
import br.com.dcmspe.kart_rank.helpers.CSVObject;
import br.com.dcmspe.kart_rank.helpers.FileHelper;
 
 public class RaceTests {
 
 	@Test
 	public void should_create_all_entities_from_a_csv_file(){
 		
 		List<CSVObject> csvObjects = FileHelper.readKartCSVFile();
 		Race race = new Race(csvObjects);
 		 		
 		Assert.assertSame(6, race.getPilots().size());
 		Assert.assertSame(23, race.getLaps().size());	
 	}
 	
 	@Test
 	public void should_get_laps_from_a_pilot(){
 		
 		List<CSVObject> csvObjects = FileHelper.readKartCSVFile();
 		Race race = new Race(csvObjects);
 		
 		List<Lap> lapsFromPilot = race.lapsFromPilot(race.getPilots().get("038"));
 		
 		Assert.assertTrue(lapsFromPilot.get(0).getPilot().getCode().equals("038"));
 		Assert.assertTrue(lapsFromPilot.get(1).getPilot().getCode().equals("038"));
 		Assert.assertTrue(lapsFromPilot.get(2).getPilot().getCode().equals("038"));
 	}
 	
 	@Test
 	public void should_get_laps_from_all_pilots(){
 		
 		List<CSVObject> csvObjects = FileHelper.readKartCSVFile();
 		Race race = new Race(csvObjects);
 		
 		Map<Pilot, List<Lap>> lapsFromPilots = race.listLapsFromPilots();
 		
 		for (Pilot pilot : race.getPilots().values()) {
 			List<Lap> laps = lapsFromPilots.get(pilot);
 			for (Lap lap : laps) {
 				Assert.assertTrue(lap.getPilot().getCode().equals(pilot.getCode()));
			}
		}
 		
 	}
 	
 	 
 	
 }