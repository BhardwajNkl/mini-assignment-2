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
public class RandomUserApiResponse {
	private List<Result> results;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class Result{
		private Name name;
		private String gender;
		private DOB dob;
		private String nat;
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		@NoArgsConstructor
		@AllArgsConstructor
		@Getter
		@Setter
		public static class Name{
			private String first;
			private String last;
		}
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		@NoArgsConstructor
		@AllArgsConstructor
		@Getter
		@Setter
		public static class DOB{
			private int age;
		}
	}
}
