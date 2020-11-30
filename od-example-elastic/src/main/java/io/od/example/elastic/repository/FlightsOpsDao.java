package io.od.example.elastic.repository;

import java.io.Serializable;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import io.od.example.elastic.common.IntervalDatesProperty;
import io.od.example.elastic.config.AppContextConfig;
import io.od.example.elastic.entity.Flight;
import io.od.example.elastic.entity.Flight.FieldNames;
import reactor.core.publisher.Flux;

/**
 * 
 * @author osman.demirci
 *
 */
@Repository
public class FlightsOpsDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier(AppContextConfig.elasticsearchTemplateBeanName)
	private ReactiveElasticsearchTemplate elasticsearchTemplate;

	private Flux<String> aggregateAndExportKeys(NativeSearchQuery query) {
		return elasticsearchTemplate.aggregate(query, Flight.class)
			.map((agg) -> (ParsedStringTerms) agg)
			.flatMap((term) -> Flux.fromIterable(term.getBuckets()))
			.map((bucket) -> bucket.getKeyAsString());
	}

	/**
	 * 
	 * @param fetchSize
	 * @return
	 */
	public Flux<String> findCarriersByTopUses(Integer fetchSize) {

		final String carrierTermsQKey = FieldNames.carrier + "Terms";

		NativeSearchQuery query = new NativeSearchQueryBuilder()
			.withQuery(QueryBuilders.termQuery(FieldNames.cancelled, false))
			.addAggregation(AggregationBuilders.terms(carrierTermsQKey)
				.field(FieldNames.carrier)
				.size(fetchSize)
				.order(BucketOrder.count(false)))
			.build();

		return aggregateAndExportKeys(query);
	}

	/**
	 * 
	 * @param fetchSize
	 * @param delayedIntervalProp
	 * @return
	 */
	public Flux<String> findAirportsByDelayedFlights(Integer fetchSize, IntervalDatesProperty delayedIntervalProp) {

		final String originAirportTermsQKey = FieldNames.originAirportId + "Terms";
		final String flightDelayMinSumQKey = FieldNames.flightDelayMin + "Sum";
		final String ticketPriceAvgQKey = FieldNames.originAirportId + "Avg";

		NativeSearchQuery query = new NativeSearchQueryBuilder()
			.withQuery(QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery(FieldNames.cancelled, false))
				.must(QueryBuilders.termQuery(FieldNames.flightDelay, true))
				.filter(QueryBuilders.rangeQuery(FieldNames.timestamp)
					.gte(delayedIntervalProp.getStartDate())
					.lt(delayedIntervalProp.getEndDate())))
			.addAggregation(AggregationBuilders.terms(originAirportTermsQKey)
				.field(FieldNames.originAirportId)
				.size(fetchSize)
				.order(List.of(BucketOrder.aggregation(flightDelayMinSumQKey, false), BucketOrder.aggregation(ticketPriceAvgQKey, true)))
				.subAggregation(AggregationBuilders.sum(flightDelayMinSumQKey)
					.field(FieldNames.flightDelayMin))
				.subAggregation(AggregationBuilders.avg(ticketPriceAvgQKey)
					.field(FieldNames.avgTicketPrice)))
			.build();

		return aggregateAndExportKeys(query);
	}

	/**
	 * 
	 * @param maxAvgTicketPrice
	 * @param destCityName
	 * @return
	 */
	public Flux<String> findNonDelayedFlights(Double maxAvgTicketPrice, String destCityName) {

		NativeSearchQuery query = new NativeSearchQueryBuilder()
			.withQuery(QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery(FieldNames.cancelled, false))
				.must(QueryBuilders.termQuery(FieldNames.flightDelay, false))
				.must(QueryBuilders.matchQuery(FieldNames.destCityName, destCityName))
				.filter(QueryBuilders.rangeQuery(FieldNames.avgTicketPrice)
					.lte(maxAvgTicketPrice)))
			// .withFields(FieldNames.flightNum, FieldNames.dest)
			.build();

		return elasticsearchTemplate.search(query, Flight.class)
			.flatMap((searchHit) -> Flux.just(searchHit.getContent()))
			.map((content) -> {
				return new StringBuilder()
					.append(content.getFlightNum())
					.append(" - ")
					.append(content.getDest())
					.toString();
			});

	}

}
