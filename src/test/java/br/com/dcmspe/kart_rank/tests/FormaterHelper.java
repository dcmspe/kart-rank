package br.com.dcmspe.kart_rank.tests;

import org.joda.time.Duration;
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
}
