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
		
		for (Pilot pilot : finishers) {
			System.out.println(pilot.getName());
		}
		
		Assert.assertTrue(finishers.contains(race.getPilots().get("033")));
		Assert.assertTrue(finishers.contains(race.getPilots().get("015")));
		Assert.assertTrue(finishers.contains(race.getPilots().get("002")));
		Assert.assertTrue(finishers.contains(race.getPilots().get("038")));
		Assert.assertTrue(finishers.contains(race.getPilots().get("023")));
		
 	}
	
	@Test 
	public void should_determine_the_winners_from_the_race(){
		Race race = createRace();
		
		Map<Pilot, Duration> finishersByOrder = race.getFinishersByOrder();
		
		Pilot[] winners = finishersByOrder.keySet().toArray(new Pilot[finishersByOrder.keySet().size()]);
		Assert.assertTrue(winners[0].getName().equals("F.MASSA"));
		Assert.assertTrue(winners[1].getName().equals("K.RAIKKONEN"));
		Assert.assertTrue(winners[2].getName().equals("R.BARRICHELLO"));
 	}
	
	private Race createRace() {
		List<CSVObject> csvObjects = FileHelper.readKartCSVFile();
 		Race race = new Race(csvObjects);
		return race;
	}
	
 }