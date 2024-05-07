package com.greenhouse.service;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.greenhouse.exception.NotProcessedGreenhouseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;

@RequiredArgsConstructor
@Slf4j
@Service
public class ESPCondition {

	private final WebClient espGreenhouse;
	
//	@Scheduled(cron = "0 0 0 */3 * *")
	public void resetDevice() {
		log.info("reseting device...");
		espGreenhouse.get()
		.uri(uriBuilder -> uriBuilder.path("/reset").build()).httpRequest(httpRequest -> {
		    HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
		    reactorRequest.responseTimeout(Duration.ofSeconds(15));
		  })
		.retrieve()
		.onStatus(status -> status != HttpStatus.OK,
			status -> Mono.error(new NotProcessedGreenhouseException(
				"ESP reset")))
		.bodyToMono(String.class)
		.block();
	}
}
