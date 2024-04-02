package com.bhardwaj.mini2.sorting;

import java.util.List;

import com.bhardwaj.mini2.entities.UserEntity;

public class Sorter {
	private SortingStrategyInterface sortingStrategy;
	public Sorter(SortingStrategyInterface sortingStrategy) {
		this.sortingStrategy = sortingStrategy;
	}
	public SortingStrategyInterface getSortingStrategy() {
		return sortingStrategy;
	}
	public void setSortingStrategy(SortingStrategyInterface sortingStrategy) {
		this.sortingStrategy = sortingStrategy;
	}
	public List<UserEntity> doSort(List<UserEntity> users){
		return sortingStrategy.sort(users);
	}
}
