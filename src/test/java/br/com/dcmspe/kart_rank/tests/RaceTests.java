package br.com.dcmspe.kart_rank.tests;
 

import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;

import br.com.dcmspe.kart_rank.entities.Lap;
import br.com.dcmspe.kart_rank.entities.Pilot;
import br.com.dcmspe.kart_rank.entities.Race;
import br.com.dcmspe.kart_rank.helpers.CSVObject;
import br.com.dcmspe.kart_rank.helpers.FileHelper;
 
 public class RaceTests {
 
 	@Test
 	public void should_create_all_entities_from_a_csv_file(){
 		
 		Race race = createRace();
 		 		
 		Assert.assertSame(6, race.getPilots().size());
 		Assert.assertSame(23, race.getLaps().size());	
 	}
 	
 	@Test
 	public void should_get_laps_from_a_pilot(){
 		
 		Race race = createRace();
 		
 		List<Lap> lapsFromPilot = race.lapsFromPilot(race.getPilots().get("038"));
 		
 		Assert.assertTrue(lapsFromPilot.get(0).getPilot().getCode().equals("038"));
 		Assert.assertTrue(lapsFromPilot.get(1).getPilot().getCode().equals("038"));
 		Assert.assertTrue(lapsFromPilot.get(2).getPilot().getCode().equals("038"));
 	}
 	
 	@Test
 	public void should_get_laps_from_all_pilots(){
 		
 		Race race = createRace();
 		
 		Map<Pilot, List<Lap>> lapsFromPilots = race.lapsFromPilots();
 		
 		for (Pilot pilot : race.getPilots().values()) {
 			List<Lap> laps = lapsFromPilots.get(pilot);
 			for (Lap lap : laps) {
 				Assert.assertTrue(lap.getPilot().getCode().equals(pilot.getCode()));
			}
		}
 		
 	}
 	
	@Test 
	public void should_determine_wich_pilots_finished_the_race(){
		Race race = createRace();
		
		List<Pilot> finishers = race.getFinishers();
		
		Assert.assertTrue(finishers.get(0).getName().equals("M.WEBBER"));
		Assert.assertTrue(finishers.get(1).getName().equals("F.MASSA"));
		Assert.assertTrue(finishers.get(2).getName().equals("F.ALONSO"));
		Assert.assertTrue(finishers.get(3).getName().equals("R.BARRICHELLO"));
		Assert.assertTrue(finishers.get(4).getName().equals("K.RAIKKONEN"));
		
 	}
	
	@Test 
	public void should_determine_the_position_from_whose_finished_the_race(){
		Race race = createRace();
		
		List<Pilot> finishers = race.getFinishers();
		
		for (Pilot pilot : finishers) {
			System.out.println("Code: " + pilot.getCode() + " Name: " + pilot.getName());
		}
		
		Map<Pilot, Duration> finishersByOrder = race.getFinishersByOrder();
		
		for (Map.Entry<Pilot, Duration> map : finishersByOrder.entrySet()) {
			System.out.println("Duration: " + map.getValue() +  " Code: " + map.getKey().getCode() + " Name: " + map.getKey().getName());
		}
		
		
		Assert.fail();
		
 	}
	
	private Race createRace() {
		List<CSVObject> csvObjects = FileHelper.readKartCSVFile();
 		Race race = new Race(csvObjects);
		return race;
	}
	
 }