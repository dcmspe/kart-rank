package br.com.dcmspe.kart_rank.tests;
 

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;

import br.com.dcmspe.kart_rank.entities.Lap;
import br.com.dcmspe.kart_rank.entities.Pilot;
import br.com.dcmspe.kart_rank.entities.Race;
import br.com.dcmspe.kart_rank.entities.RaceResults;
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
	
	@Test
	public void should_prove_that_vettel_did_not_finished_the_race(){
		Race race = createRace();
		
		List<Pilot> finishers = race.getFinishers();
		
		Assert.assertFalse(finishers.contains(race.getPilots().get("011")));
 	}
	
	@Test 
	public void should_write_a_logger_file_with_results_of_race(){
		Race race = createRace();
		
		RaceResults results = new RaceResults(race);
		
		results.generateLogForResults();
		
		Path path = Paths.get("logs/race_results.log");
		
		Assert.assertTrue(path.toFile().exists());
		Assert.assertTrue(FileHelper.countNumberLines(path.toString()) >= 6);
		
 	}
	
	
	
	private Race createRace() {
		List<CSVObject> csvObjects = FileHelper.readKartCSVFile();
 		Race race = new Race(csvObjects);
		return race;
	}
	
 }