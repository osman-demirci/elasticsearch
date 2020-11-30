package io.od.example.elastic.config;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

import io.od.example.elastic.common.EntityParams;

/**
 * 
 * @author osman.demirci
 *
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableReactiveElasticsearchRepositories(basePackages = {
	"io.od.example.elastic.repository"
})
public class AppContextConfig {

	private final static Logger logger = LoggerFactory.getLogger(AppContextConfig.class);
	public final static String elasticsearchHostEnvVariableKey = "ELASTICSEARCH_HOST";

	/********************************************
	 * Bean Names
	 ********************************************/
	public final static String elasticsearchClientBeanName = "elasticsearchClient";
	public final static String elasticsearchTemplateBeanName = "elasticsearchTemplate";

	/********************************************
	 * Context Beans
	 ********************************************/
	@Autowired
	@Bean(elasticsearchClientBeanName)
	public ReactiveElasticsearchClient elasticsearchClient() {

		logger.info(elasticsearchClientBeanName + " => " + LocalDateTime.now());

		final String elasticsearchHost = Optional.ofNullable(System.getenv().get(elasticsearchHostEnvVariableKey))
			.orElse("localhost");
		final Integer elasticsearchPort = 9200;

		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
			.connectedTo(elasticsearchHost + ":" + elasticsearchPort)
			.withConnectTimeout(EntityParams.defaultConnectionTimeout)
			.withWebClientConfigurer(webClient -> {
				ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
					.codecs(configurer -> configurer.defaultCodecs()
						.maxInMemorySize(-1))
					.build();
				return webClient.mutate().exchangeStrategies(exchangeStrategies).build();
			})
			.build();

		return ReactiveRestClients.create(clientConfiguration);
	}

	@Bean(elasticsearchTemplateBeanName)
	public ReactiveElasticsearchTemplate elasticsearchTemplate() {

		logger.info(elasticsearchTemplateBeanName + " => " + LocalDateTime.now());

		DefaultConversionService conversionService = new DefaultConversionService();
		// conversionService.addConverter(GeoConverters.GeoJsonGeometryCollectionToMapConverter.INSTANCE);
		// conversionService.addConverter(GeoConverters.MapToGeoJsonGeometryCollectionConverter.INSTANCE);
		SimpleElasticsearchMappingContext elasticsearchMappingContext = new SimpleElasticsearchMappingContext();
		ElasticsearchConverter elasticsearchConverter = new MappingElasticsearchConverter(elasticsearchMappingContext, conversionService);

		ReactiveElasticsearchTemplate elasticsearchTemplate = new ReactiveElasticsearchTemplate(elasticsearchClient(), elasticsearchConverter);
		return elasticsearchTemplate;
	}

}
