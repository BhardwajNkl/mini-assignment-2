package com.bhardwaj.mini2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bhardwaj.mini2.DTOs.GenderizeApiResponse;
import com.bhardwaj.mini2.DTOs.NationalizeApiResponse;
import com.bhardwaj.mini2.DTOs.RandomUserApiResponse;

import reactor.core.publisher.Mono;

@Service
public class ExternalApiService {
	private WebClient webClient1;
	private WebClient webClient2;
	private WebClient webClient3;
	
	@Autowired
	public ExternalApiService(@Qualifier("webClient1") WebClient webClient1,
			@Qualifier("webClient2") WebClient webClient2,
			@Qualifier("webClient3") WebClient webClient3) {
		this.webClient1 = webClient1;
		this.webClient2 = webClient2;
		this.webClient3 = webClient3;
	}		
	public RandomUserApiResponse loadRandomUser() {
		Mono<RandomUserApiResponse> mono = webClient1.get()
				.retrieve().bodyToMono(RandomUserApiResponse.class);
		RandomUserApiResponse randomUser = mono.block();
		return randomUser;
	}
	
	public String getGenderFromGenderizeApi(String firstName) {
		Mono<GenderizeApiResponse> mono = webClient2.get()
				.uri(uriBuilder -> uriBuilder
						.queryParam("name", firstName)
						.build())
				.retrieve()
				.bodyToMono(GenderizeApiResponse.class);
		GenderizeApiResponse genderizeApiResponse = mono.block();
		return genderizeApiResponse.getGender();
	}
	
	public List<String> getNationalityFromNationalizeApi(String firstName) {
		Mono<NationalizeApiResponse> mono = webClient3.get()
				.uri(uriBuilder -> uriBuilder
						.queryParam("name", firstName)
						.build())
				.retrieve()
				.bodyToMono(NationalizeApiResponse.class);
		NationalizeApiResponse nationalizeApiResponse = mono.block();
		List<String> countries = nationalizeApiResponse.getCountry().stream()
				.map(country -> country.getCountry_id()).collect(Collectors.toList());
		return countries;
	}
}
