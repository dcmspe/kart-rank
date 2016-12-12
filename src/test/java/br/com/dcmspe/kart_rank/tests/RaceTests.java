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
import br.com.dcmspe.kart_rank.helpers.CSVObject;
import br.com.dcmspe.kart_rank.helpers.FileHelper;
import br.com.dcmspe.kart_rank.helpers.FormaterHelper;
import br.com.dcmspe.kart_rank.helpers.RaceResultsLog;
 
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
		
		RaceResultsLog results = new RaceResultsLog(race);
		
		results.generateLogForResults();
		
		Path path = Paths.get("logs/race_results.log");
		
		Assert.assertTrue(path.toFile().exists());
		Assert.assertTrue(FileHelper.countNumberLines(path.toString()) >= 6);
		
 	}
	
	@Test
	public void shoud_retrieve_the_best_lap_from_each_pilot(){
		Race race = createRace();
		
		Map<String, Lap> bestLapFromEachPilotSorted = race.bestLapFromEachPilotSortedByPilotCode();
		
		String bestTilePilot1 = FormaterHelper.longPeriodToString(bestLapFromEachPilotSorted.get("011").getTimeLap().toStandardDuration().getMillis());
		String bestTilePilot2 = FormaterHelper.longPeriodToString(bestLapFromEachPilotSorted.get("033").getTimeLap().toStandardDuration().getMillis());
		String bestTilePilot3 = FormaterHelper.longPeriodToString(bestLapFromEachPilotSorted.get("023").getTimeLap().toStandardDuration().getMillis());
		String bestTilePilot4 = FormaterHelper.longPeriodToString(bestLapFromEachPilotSorted.get("002").getTimeLap().toStandardDuration().getMillis());
		String bestTilePilot5 = FormaterHelper.longPeriodToString(bestLapFromEachPilotSorted.get("015").getTimeLap().toStandardDuration().getMillis());
		String bestTilePilot6 = FormaterHelper.longPeriodToString(bestLapFromEachPilotSorted.get("038").getTimeLap().toStandardDuration().getMillis());
		
		
		Assert.assertTrue(bestTilePilot1.equals("1:18.097"));
		Assert.assertTrue(bestTilePilot2.equals("1:3.716"));
		Assert.assertTrue(bestTilePilot3.equals("1:4.216"));
		Assert.assertTrue(bestTilePilot4.equals("1:3.076"));
		Assert.assertTrue(bestTilePilot5.equals("1:7.011"));
		Assert.assertTrue(bestTilePilot6.equals("1:2.769"));
	}
	
	
	private Race createRace() {
		List<CSVObject> csvObjects = FileHelper.readKartCSVFile();
 		Race race = new Race(csvObjects);
		return race;
	}
	
 }