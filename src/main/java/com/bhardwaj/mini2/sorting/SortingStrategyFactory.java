package com.bhardwaj.mini2.sorting;

import org.springframework.stereotype.Component;

@Component
public class SortingStrategyFactory {
	public SortingStrategyInterface createSortingStrategy(String sortType, String sortOrder) {
		SortingStrategyInterface sortingStrategy;
		if(sortType.equalsIgnoreCase("Age") && sortOrder.equalsIgnoreCase("Even")) {
			sortingStrategy = new SortingAgeEven();
		} else if(sortType.equalsIgnoreCase("Age") && sortOrder.equalsIgnoreCase("Odd")) {
			sortingStrategy = new SortingAgeOdd();
		} else if(sortType.equalsIgnoreCase("Name") && sortOrder.equalsIgnoreCase("Even")) {
			sortingStrategy = new SortingNameEven();
		} else if(sortType.equalsIgnoreCase("Name") && sortOrder.equalsIgnoreCase("Odd")) {
			sortingStrategy = new SortingNameOdd();
		} else {
			// some exception
			return null;
		}
		
		return sortingStrategy;
	}
}
