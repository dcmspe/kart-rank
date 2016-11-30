package br.com.dcmspe.kart_rank.tests;

import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import br.com.dcmspe.kart_rank.helpers.FileHelper;

public class HelperTests {

	@Test
	public void should_convert_txt_to_csv() {
		
		Path pathTXT = Paths.get("./files/kart-rank.txt");
		Path pathCSV = Paths.get("./files/kart-rank.csv");
		String fileName = String.valueOf(pathTXT.getFileName());
		if(fileName.equals("kart-rank.txt")){
			FileHelper.convertTXTCSV(pathTXT, pathCSV);
		}
	    fail();
	}

}


