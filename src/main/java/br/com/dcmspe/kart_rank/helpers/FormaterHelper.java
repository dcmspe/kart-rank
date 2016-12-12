package br.com.dcmspe.kart_rank.helpers;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public abstract class FormaterHelper {

	public static String longPeriodToString(long longPeriod){
		Duration duration = Duration.millis(longPeriod);
		PeriodFormatter formatter = new PeriodFormatterBuilder()
							.appendHours()
							.appendSeparator(":")
							.appendMinutes()
							.appendSeparator(":")
							.appendSecondsWithMillis()
							.toFormatter();
		
		
		return formatter.print(duration.toPeriod());
	}
	
	public static Period createPeriod(String stringPeriod){
		PeriodFormatter formatter = new PeriodFormatterBuilder()
				.appendHours()
				.appendSeparator(":")
				.appendMinutes()
				.appendSeparator(":")
				.appendSeconds()
				.appendSeparator(".")
				.appendMillis().toFormatter();
		return formatter.parsePeriod(stringPeriod);
	}
}
