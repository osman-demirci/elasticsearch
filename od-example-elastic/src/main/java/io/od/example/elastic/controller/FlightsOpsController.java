package io.od.example.elastic.controller;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.od.example.elastic.common.IntervalDatesProperty;
import io.od.example.elastic.repository.FlightsOpsDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author osman.demirci
 *
 */
@RestController
@RequestMapping("/flights")
public class FlightsOpsController implements DisposableBean, Serializable {

	private static final long serialVersionUID = 1L;
	private transient final static Logger logger = LoggerFactory.getLogger(FlightsOpsController.class);

	/********************************************
	 * Spring Dependencies
	 ********************************************/
	@Autowired
	private FlightsOpsDao flightsOpsDao;

	@RequestMapping(value = "/studyCase", method = {
		RequestMethod.GET
	}, produces = {
		MediaType.APPLICATION_JSON_VALUE
	})
	public Mono<Map<String, List<String>>> solveStudyCase() {

		// Study Case A
		Flux<String> carriersByTopUses = flightsOpsDao.findCarriersByTopUses(5);

		// Study Case B
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime delayedIntervalStartDate = ZonedDateTime.of(2019, 6, 1, 0, 0, 0, 0, zoneId);
		// Record not found at : 2019-06-01 - 2019-08-31 !!!!!!!!!!!!
		ZonedDateTime delayedIntervalEndDate = ZonedDateTime.of(2020, 12, 1, 0, 0, 0, 0, zoneId);
		IntervalDatesProperty delayedIntervalProp = IntervalDatesProperty.of(delayedIntervalStartDate, delayedIntervalEndDate);
		Flux<String> airportsByDelayedFlights = flightsOpsDao.findAirportsByDelayedFlights(3, delayedIntervalProp);

		// Study Case C
		Flux<String> nonDelayedFlights = flightsOpsDao.findNonDelayedFlights(500.0, "Venice");

		return Flux.concat(
			convertToMap("studyCaseA", carriersByTopUses),
			convertToMap("studyCaseB", airportsByDelayedFlights),
			convertToMap("studyCaseC", nonDelayedFlights))
			.collect(Collectors.toSet())
			.flatMap((studyCases) -> {
				Map<String, List<String>> resultMap = new HashMap<>(studyCases.size());
				studyCases.forEach(resultMap::putAll);
				return Mono.just(resultMap);
			})
			.onErrorResume((error) -> Mono.error(error))
			.doOnError((error) -> {
				// Do nothing
			});
	}

	private <T> Mono<Map<String, List<T>>> convertToMap(String mapKey, Flux<T> source) {
		return source.collect(Collectors.toList())
			.flatMap((contents) -> {
				return Mono.just(Map.of(mapKey, contents));
			});
	}

	/**
	 * Exception Handler
	 * 
	 * @param e
	 */
	@ResponseStatus(value = HttpStatus.GONE, reason = "An error occurred")
	@ExceptionHandler(Throwable.class)
	public void onException(Exception e) {
		logger.error("An error occurred", e);
	}

	@Override
	public void destroy() throws Exception {

		logger.info(this.getClass().getName() + " has destroyed.");
	}

}
