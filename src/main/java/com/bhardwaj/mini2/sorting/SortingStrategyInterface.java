package com.bhardwaj.mini2.sorting;

import java.util.List;

import com.bhardwaj.mini2.entities.UserEntity;
@FunctionalInterface
public interface SortingStrategyInterface {
	List<UserEntity> sort(List<UserEntity> users);
}
