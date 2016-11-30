package br.com.dcmspe.kart_rank.helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHelper {

	
	
	
	public void convertTXTCSV(Path pathTxtFile, Path pathCSVFile) {
	    Path path = Paths.get("path", "to", "folder");
	    Path txt = path.resolve("myFile.txt");
	    Path csv = path.resolve("myFile.csv");
	    try(
	    	Stream<String> lines = Files.lines(pathTxtFile);
	        PrintWriter pw = new PrintWriter(Files.newBufferedWriter(csv, StandardOpenOption.CREATE_NEW))) {
	        		lines.map((line) -> line.split("\\|")).
	        			map((line) -> Stream.of(line).collect(Collectors.joining(","))).
	        			forEach(pw::println);
	    } catch (IOException e) {
			e.printStackTrace();
		}
	}
}
