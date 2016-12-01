package br.com.dcmspe.kart_rank.helpers;
 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
 
 public abstract class FileHelper {
 
 	 public static final String PATH_TO_CSV_FILE = "./files/kart-rank.csv";
	 
	 public static List<CSVObject> readKartCSVFile() {
 		List<CSVObject> csvObjects = new ArrayList<CSVObject>();
 		try {
			FileReader fileReader = new FileReader(PATH_TO_CSV_FILE);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(fileReader);
			for (CSVRecord csvRecord : records) {
				CSVObject csvObject = new CSVObject();
				csvObject.setColumn1(csvRecord.get(0));
				csvObject.setColumn2(csvRecord.get(1));
				csvObject.setColumn3(csvRecord.get(2));
				csvObject.setColumn4(csvRecord.get(3));
				csvObject.setColumn5(csvRecord.get(4));
				csvObject.setColumn6(csvRecord.get(5));
				csvObjects.add(csvObject);
			}
			
			return csvObjects;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
 		return csvObjects;
 	}
 }