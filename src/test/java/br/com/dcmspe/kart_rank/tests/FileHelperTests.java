package br.com.dcmspe.kart_rank.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.dcmspe.kart_rank.helpers.CSVObject;
import br.com.dcmspe.kart_rank.helpers.FileHelper;

public class FileHelperTests {

	@Test
	public void should_return_a_list_from_a_given_csv_file() {
		
		List<CSVObject> objects = FileHelper.readKartCSVFile();
		
		Assert.assertNotNull(objects);
		Assert.assertSame(23, objects.size());
	}

}


