package com.bhardwaj.mini2.DTOs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NationalizeApiResponse {
	private List<Country> country;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class Country{
		private String country_id;
	}
	
}
