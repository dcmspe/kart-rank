package br.com.dcmspe.kart_rank.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import br.com.dcmspe.kart_rank.entities.Pilot;
import br.com.dcmspe.kart_rank.entities.Race;

public class RaceTests {

	/*@Test
	public void should_return_a_list_of_pilots_that_will_participate_on_the_race(){
		Race race = new Race();
		ArrayList<Pilot> pilots = createPilots();
		race.insertPilots(pilots);
		
		assertTrue(race.getPilots().size() == 6);
	}*/
	
	private ArrayList<Pilot> createPilots(){
		
		Pilot pilot1 = new Pilot("038", "F.MASSA");
		Pilot pilot2 = new Pilot("033", "R.BARRICHELLO");
		Pilot pilot3 = new Pilot("002", "K.RAIKKONEN ");
		Pilot pilot4 = new Pilot("023", "M.WEBBER");
		Pilot pilot5 = new Pilot("015", "F.ALONSO");
		Pilot pilot6 = new Pilot("011", "S.VETTEL");
		
		ArrayList<Pilot> pilots = new ArrayList<Pilot>();
		pilots.add(pilot1);
		pilots.add(pilot2);
		pilots.add(pilot3);
		pilots.add(pilot4);
		pilots.add(pilot5);
		pilots.add(pilot6);
		
		return pilots;
	}
	 
	
}
