package br.com.dcmspe.kart_rank.tests;
 
 import java.util.List;

import org.junit.Assert;
import org.junit.Test;

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
 		
 		for (Pilot pilot : race.getPilots().values()) {
			System.out.println(pilot.getName());
		}
 		
 		Assert.fail();
 	}
 	
 	 
 	
 }