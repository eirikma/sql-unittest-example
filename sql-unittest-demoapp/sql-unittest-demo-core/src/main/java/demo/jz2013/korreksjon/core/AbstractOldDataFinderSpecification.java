package demo.jz2013.korreksjon.core;

import java.sql.Date;


public abstract class AbstractOldDataFinderSpecification /*implements ObjectSpecification, FinderSpecification*/ {

	private static int CUTOFF_YEARS = 10;



	/**
	 * Returns the date to be considered a cut-off time: objects older than that date will be elected for removal.
	 * The cut-off date being calculated as follows:
	 * (today's day of the month)/(today's month of the year)/(today's year - 10 years).
	 *
	 * @return the calculated cut-off date
	 */
	public Date getCutOffDate(){
//		Date today = SystemClock.today();
//		int cutOffYear = (today.yearNo() - CUTOFF_YEARS);
		Date cutOffDate = null;
//        cutOffDate = Date.from(cutOffYear, today.monthNo(), today.dayNo());
        return cutOffDate;
	}
}
