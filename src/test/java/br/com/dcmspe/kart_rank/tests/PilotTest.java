package br.com.dcmspe.kart_rank.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.dcmspe.kart_rank.entities.Pilot;

public class PilotTest {
 
 	@Test
 	public void pilot_should_have_a_code_and_a_name() {
 		Pilot pilot = new Pilot("121", "Rubinho pe de chinelo");
 		
 		
 		assertNotEquals(pilot.getCode(),null);
 		assertNotEquals(pilot.getName(),null);
 		assertTrue(pilot.getName().equals("Rubinho pe de chinelo"));
 		assertTrue(pilot.getCode().equals("121"));
 	}
 
 }