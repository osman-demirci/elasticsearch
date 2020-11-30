package io.od.example.elastic.entity;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import io.od.example.elastic.common.DocumentEntity;

/**
 * 
 * @author osman.demirci
 *
 */
@Document(indexName = Flight.INDEX_NAME)
public class Flight extends DocumentEntity {

	private static final long serialVersionUID = 1L;

	public final static String INDEX_NAME = "kibana_sample_data_flights";

	public static interface FieldNames {
		final static String id = "id";
		final static String flightNum = "FlightNum";
		final static String origin = "Origin";
		final static String originLocation = "OriginLocation";
		final static String destLocation = "DestLocation";
		final static String flightDelay = "FlightDelay";
		final static String distanceMiles = "DistanceMiles";
		final static String flightTimeMin = "FlightTimeMin";
		final static String originWeather = "OriginWeather";
		final static String dayOfWeek = "dayOfWeek";
		final static String avgTicketPrice = "AvgTicketPrice";
		final static String carrier = "Carrier";
		final static String flightDelayMin = "FlightDelayMin";
		final static String originRegion = "OriginRegion";
		final static String destAirportId = "DestAirportID";
		final static String flightDelayType = "FlightDelayType";
		final static String dest = "Dest";
		final static String flightTimeHour = "FlightTimeHour";
		final static String cancelled = "Cancelled";
		final static String distanceKilometers = "DistanceKilometers";
		final static String originCityName = "OriginCityName";
		final static String destWeather = "DestWeather";
		final static String originCountry = "OriginCountry";
		final static String destCountry = "DestCountry";
		final static String destRegion = "DestRegion";
		final static String destCityName = "DestCityName";
		final static String originAirportId = "OriginAirportID";
		final static String timestamp = "timestamp";
	}

	@Field(name = FieldNames.flightNum, type = FieldType.Keyword)
	private String flightNum;
	@Field(name = FieldNames.origin, type = FieldType.Keyword)
	private String origin;
	@Field(name = FieldNames.originLocation, type = FieldType.Object)
	private Map<String, String> originLocation;
	@Field(name = FieldNames.destLocation, type = FieldType.Object)
	private Map<String, String> destLocation;
	@Field(name = FieldNames.flightDelay, type = FieldType.Boolean)
	private Boolean flightDelay;
	@Field(name = FieldNames.distanceMiles, type = FieldType.Float)
	private Double distanceMiles;
	@Field(name = FieldNames.flightTimeMin, type = FieldType.Float)
	private Double flightTimeMin;
	@Field(name = FieldNames.originWeather, type = FieldType.Keyword)
	private String originWeather;
	@Field(name = FieldNames.dayOfWeek, type = FieldType.Integer)
	private Integer dayOfWeek;
	@Field(name = FieldNames.avgTicketPrice, type = FieldType.Float)
	private Double avgTicketPrice;
	@Field(name = FieldNames.carrier, type = FieldType.Keyword)
	private String carrier;
	@Field(name = FieldNames.flightDelayMin, type = FieldType.Integer)
	private Integer flightDelayMin;
	@Field(name = FieldNames.originRegion, type = FieldType.Keyword)
	private String originRegion;
	@Field(name = FieldNames.destAirportId, type = FieldType.Keyword)
	private String destAirportId;
	@Field(name = FieldNames.flightDelayType, type = FieldType.Keyword)
	private String flightDelayType;
	@Field(name = FieldNames.dest, type = FieldType.Keyword)
	private String dest;
	@Field(name = FieldNames.flightTimeHour, type = FieldType.Keyword)
	private String flightTimeHour;
	@Field(name = FieldNames.cancelled, type = FieldType.Boolean)
	private Boolean cancelled;
	@Field(name = FieldNames.distanceKilometers, type = FieldType.Float)
	private Double distanceKilometers;
	@Field(name = FieldNames.originCityName, type = FieldType.Keyword)
	private String originCityName;
	@Field(name = FieldNames.destWeather, type = FieldType.Keyword)
	private String destWeather;
	@Field(name = FieldNames.originCountry, type = FieldType.Keyword)
	private String originCountry;
	@Field(name = FieldNames.destCountry, type = FieldType.Keyword)
	private String destCountry;
	@Field(name = FieldNames.destRegion, type = FieldType.Keyword)
	private String destRegion;
	@Field(name = FieldNames.destCityName, type = FieldType.Keyword)
	private String destCityName;
	@Field(name = FieldNames.originAirportId, type = FieldType.Keyword)
	private String originAirportId;

	@Field(name = FieldNames.timestamp, type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestamp;

	public Flight() {
		super();
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Map<String, String> getOriginLocation() {
		return originLocation;
	}

	public void setOriginLocation(Map<String, String> originLocation) {
		this.originLocation = originLocation;
	}

	public Map<String, String> getDestLocation() {
		return destLocation;
	}

	public void setDestLocation(Map<String, String> destLocation) {
		this.destLocation = destLocation;
	}

	public Boolean getFlightDelay() {
		return flightDelay;
	}

	public void setFlightDelay(Boolean flightDelay) {
		this.flightDelay = flightDelay;
	}

	public Double getDistanceMiles() {
		return distanceMiles;
	}

	public void setDistanceMiles(Double distanceMiles) {
		this.distanceMiles = distanceMiles;
	}

	public Double getFlightTimeMin() {
		return flightTimeMin;
	}

	public void setFlightTimeMin(Double flightTimeMin) {
		this.flightTimeMin = flightTimeMin;
	}

	public String getOriginWeather() {
		return originWeather;
	}

	public void setOriginWeather(String originWeather) {
		this.originWeather = originWeather;
	}

	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Double getAvgTicketPrice() {
		return avgTicketPrice;
	}

	public void setAvgTicketPrice(Double avgTicketPrice) {
		this.avgTicketPrice = avgTicketPrice;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public Integer getFlightDelayMin() {
		return flightDelayMin;
	}

	public void setFlightDelayMin(Integer flightDelayMin) {
		this.flightDelayMin = flightDelayMin;
	}

	public String getOriginRegion() {
		return originRegion;
	}

	public void setOriginRegion(String originRegion) {
		this.originRegion = originRegion;
	}

	public String getDestAirportId() {
		return destAirportId;
	}

	public void setDestAirportId(String destAirportId) {
		this.destAirportId = destAirportId;
	}

	public String getFlightDelayType() {
		return flightDelayType;
	}

	public void setFlightDelayType(String flightDelayType) {
		this.flightDelayType = flightDelayType;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getFlightTimeHour() {
		return flightTimeHour;
	}

	public void setFlightTimeHour(String flightTimeHour) {
		this.flightTimeHour = flightTimeHour;
	}

	public Boolean getCancelled() {
		return cancelled;
	}

	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Double getDistanceKilometers() {
		return distanceKilometers;
	}

	public void setDistanceKilometers(Double distanceKilometers) {
		this.distanceKilometers = distanceKilometers;
	}

	public String getOriginCityName() {
		return originCityName;
	}

	public void setOriginCityName(String originCityName) {
		this.originCityName = originCityName;
	}

	public String getDestWeather() {
		return destWeather;
	}

	public void setDestWeather(String destWeather) {
		this.destWeather = destWeather;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public String getDestCountry() {
		return destCountry;
	}

	public void setDestCountry(String destCountry) {
		this.destCountry = destCountry;
	}

	public String getDestRegion() {
		return destRegion;
	}

	public void setDestRegion(String destRegion) {
		this.destRegion = destRegion;
	}

	public String getDestCityName() {
		return destCityName;
	}

	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}

	public String getOriginAirportId() {
		return originAirportId;
	}

	public void setOriginAirportId(String originAirportId) {
		this.originAirportId = originAirportId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
