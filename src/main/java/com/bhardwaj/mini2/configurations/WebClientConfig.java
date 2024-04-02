package com.bhardwaj.mini2.configurations;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
	@Value("${endpoint-url1}")
	private String endpointUrl1;
	@Value("${endpoint-url2}")
	private String endpointUrl2;
	@Value("${endpoint-url3}")
	private String endpointUrl3;
	@Value("${connection-timeout1}")
	private int connectionTimeout1;
	@Value("${connection-timeout2}")
	private int connectionTimeout2;
	@Value("${connection-timeout3}")
	private int connectionTimeout3;
	@Value("${read-timeout1}")
	private int readTimeout1;
	@Value("${read-timeout2}")
	private int readTimeout2;
	@Value("${read-timeout3}")
	private int readTimeout3;
	@Value("${write-timeout1}")
	private int writeTimeout1;
	@Value("${write-timeout2}")
	private int writeTimeout2;
	@Value("${write-timeout3}")
	private int writeTimeout3;
		
	@Bean
	public WebClient webClient1() {
		return WebClient.builder()
		.baseUrl(endpointUrl1)
		.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16*1024*1024))
		.defaultHeader("Content-Type", "application/json")
		.defaultHeader("Accept", "application/json")
		.clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout1)
				.doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(readTimeout1, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(writeTimeout1, TimeUnit.MILLISECONDS)))
				))
		.build();
	}
	
	@Bean
	public WebClient webClient2() {
		return WebClient.builder()
		.baseUrl(endpointUrl2)
		.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16*1024*1024))
		.defaultHeader("Content-Type", "application/json")
		.defaultHeader("Accept", "application/json")
		.clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout2)
				.doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(readTimeout2, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(writeTimeout2, TimeUnit.MILLISECONDS)))
				))
		.build();
	}
	
	@Bean
	public WebClient webClient3() {
		return WebClient.builder()
		.baseUrl(endpointUrl3)
		.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16*1024*1024))
		.defaultHeader("Content-Type", "application/json")
		.defaultHeader("Accept", "application/json")
		.clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout3)
				.doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(readTimeout3, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(writeTimeout3, TimeUnit.MILLISECONDS)))
				))
		.build();
	}	
	
}
