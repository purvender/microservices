package com.hooda.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p->p.path("/get")
						.filters(f->f.addRequestHeader("Myheader", "My uri"))
						.uri("http://httpbin.org:80"))	
				.route(p->p.path("/currency-exchange/**")
						.uri("lb://forex-exchange"))
				.route(p->p.path("/currency-conversion/**")
						.uri("lb://forex-conversion"))
				.route(p->p.path("/currency-conversion-feign/**")
						.uri("lb://forex-conversion"))
				.route(p->p.path("/currency-conversion-new/**")
						.filters(f->f.rewritePath("/currency-conversion-new/(?<segment>.*)", 
													"/currency-conversion-feign/${segment}"))
						.uri("lb://forex-conversion"))
				.build();
		
		/*
		 * Function<PredicateSpec, Buildable<Route>> routeFunction =
		 *  p->p.path("/get")
		 * .filters(f->f.addRequestHeader("Myheader", "My uri"))
		 * .uri("http://httpbin.org:80"); 
		 * 
		 * return builder.routes() .route(routeFunction).build();
		 */
	}
}
