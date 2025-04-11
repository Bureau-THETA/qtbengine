package org.msh.quantb.services.calc;
/**
 * Period between two dates
 * @author alexk
 *
 */

import java.time.LocalDate;

import org.msh.quantb.model.mvp.ModelFactory;
import org.msh.quantb.services.io.MonthUIAdapter;

public class Period implements Comparable<Period>{
	private MonthUIAdapter from = MonthUIAdapter.of(LocalDate.now());
	private MonthUIAdapter to = MonthUIAdapter.of(LocalDate.now());
	
	/**
	 * To fit bean spec
	 */
	public Period() {
		super();
	}

	public MonthUIAdapter getFrom() {
		return from;
	}

	public void setFrom(MonthUIAdapter from) {
		this.from = from;
	}

	public MonthUIAdapter getTo() {
		return to;
	}

	public void setTo(MonthUIAdapter to) {
		this.to = to;
	}
	/**
	 * Create a new period
	 * @param _from
	 * @param _to
	 * @return
	 */
	public static Period of(MonthUIAdapter _from, MonthUIAdapter _to) {
		Period ret = new Period();
		ret.setFrom(_from.incrementClone(ModelFactory.of(""), 0));
		ret.setTo(_to.incrementClone(ModelFactory.of(""), 0));
		return ret;
	}

	@Override
	public int compareTo(Period other) {
		return this.getFrom().compareTo(other.getFrom());
	}

	@Override
	public String toString() {
		return "Period [from=" + from + ", to=" + to + "]";
	}
	
}
