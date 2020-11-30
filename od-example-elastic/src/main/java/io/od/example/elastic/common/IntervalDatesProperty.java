package io.od.example.elastic.common;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * 
 * @author osman.demirci
 *
 */
public final class IntervalDatesProperty implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ZonedDateTime startDate;
	private final ZonedDateTime endDate;

	private IntervalDatesProperty(ZonedDateTime startDate, ZonedDateTime endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public static IntervalDatesProperty of(ZonedDateTime startDate, ZonedDateTime endDate) {
		Objects.requireNonNull(startDate);
		Objects.requireNonNull(endDate);
		return new IntervalDatesProperty(startDate, endDate);
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public ZonedDateTime getEndDate() {
		return endDate;
	}

	@Override
	public String toString() {
		return this.startDate + " - " + this.endDate;
	}

}
