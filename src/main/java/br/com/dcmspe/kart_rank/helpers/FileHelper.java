package br.com.dcmspe.kart_rank.helpers;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;
 
 public abstract class FileHelper {
 
 	
 	
 	
 	public static void convertTXTCSV(Path pathTxtFile, Path pathCSVFile) {
 	    try(
 	    	Stream<String> lines = Files.lines(pathTxtFile);
 	        PrintWriter pw = new PrintWriter(Files.newBufferedWriter(pathCSVFile, StandardOpenOption.CREATE_NEW))) {
 	        		lines.map((line) -> line.split("\\|")).
 	        			map((line) -> Stream.of(line).collect(Collectors.joining(","))).
 	        			forEach(pw::println);
 	    } catch (IOException e) {
 			e.printStackTrace();
 		}
 	}
 }