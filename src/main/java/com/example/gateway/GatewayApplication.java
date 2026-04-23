package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("microservice1",
						r -> r.path("/api/auth/**")
								.filters(f -> f.dedupeResponseHeader(
										"Access-Control-Allow-Origin Access-Control-Allow-Credentials", "RETAIN_FIRST"))
								.uri("lb://microservice1"))

				.route("transplant-service",
						r -> r.path(
										"/api/transplants/**",
										"/api/biopsies/**",
										"/api/complications/**",
										"/api/hla/**",
										"/api/drugs/**",
										"/api/rejections/**",
										"/api/surveillance/**")
								.filters(f -> f.dedupeResponseHeader(
										"Access-Control-Allow-Origin Access-Control-Allow-Credentials", "RETAIN_FIRST"))
								.uri("lb://transplant-service"))

				.route("microservice4",
						r -> r.path("/api/schedules", "/api/schedules/**")
								.filters(f -> f.dedupeResponseHeader(
										"Access-Control-Allow-Origin Access-Control-Allow-Credentials", "RETAIN_FIRST"))
								.uri("lb://microservice4"))

				.route("lab-results-service",
						r -> r.path("/api/lab-reports", "/api/lab-reports/**")
								.filters(f -> f.dedupeResponseHeader(
										"Access-Control-Allow-Origin Access-Control-Allow-Credentials", "RETAIN_FIRST"))
								.uri("lb://lab-results-service"))

				.route("medical-media-service",
						r -> r.path("/api/documents-medicaux", "/api/documents-medicaux/**",
										"/api/images-medicales", "/api/images-medicales/**",
										"/api/intelligence", "/api/intelligence/**")
								.filters(f -> f.dedupeResponseHeader(
										"Access-Control-Allow-Origin Access-Control-Allow-Credentials", "RETAIN_FIRST"))
								.uri("lb://MEDICAL-MEDIA-SERVICE"))

				.route("microservice2",
						r -> r.path("/api/**")
								.filters(f -> f.dedupeResponseHeader(
										"Access-Control-Allow-Origin Access-Control-Allow-Credentials", "RETAIN_FIRST"))
								.uri("lb://microservice2"))
					.build();
	}
}
