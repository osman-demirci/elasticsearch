package io.od.example.elastic;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.od.example.elastic.common.IntervalDatesProperty;
import io.od.example.elastic.repository.FlightsOpsDao;

@SpringBootTest
class OdExampleElasticApplicationTests {

	private final static Logger logger = LoggerFactory.getLogger(OdExampleElasticApplicationTests.class);

	@Autowired
	private FlightsOpsDao flightsOpsDao;

	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	void findCarriersByTopUsesTest() {

		flightsOpsDao.findCarriersByTopUses(5)
			.doOnNext(logger::debug)
			.blockLast();
	}

	@Test
	@Order(2)
	void findAirportsByDelayedFlightsTest() {

		ZoneId zoneId = ZoneId.systemDefault();

		ZonedDateTime delayedIntervalStartDate = ZonedDateTime.of(2019, 6, 1, 0, 0, 0, 0, zoneId);
		ZonedDateTime delayedIntervalEndDate = ZonedDateTime.of(2020, 12, 1, 0, 0, 0, 0, zoneId);
		IntervalDatesProperty delayedIntervalProp = IntervalDatesProperty.of(delayedIntervalStartDate, delayedIntervalEndDate);
		flightsOpsDao.findAirportsByDelayedFlights(3, delayedIntervalProp)
			.doOnNext(logger::debug)
			.blockLast();

	}

	@Test
	@Order(3)
	void findNonDelayedFlightsTest() {

		flightsOpsDao.findNonDelayedFlights(500.0, "Venice")
			.doOnNext(logger::debug)
			.blockLast();
		
	}

}
