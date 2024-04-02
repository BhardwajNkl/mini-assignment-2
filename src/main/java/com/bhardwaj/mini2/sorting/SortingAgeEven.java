package com.bhardwaj.mini2.sorting;

import java.util.List;
import java.util.stream.Collectors;

import com.bhardwaj.mini2.entities.UserEntity;

public class SortingAgeEven implements SortingStrategyInterface{
	
	/* 
	 * SORTING RULE:
	 * USERS WITH EVEN AGE COME FIRST. 
	 * NOTE: THIS METHOD DOES NOT SORT USERS BASED ON AGE WHEN TWO USERS HAVE SAME EVEN/ODD AGES.
	 * THIS MEANS THAT USERS WITH EVEN AGES STILL HAVE THE ORIGINAL RELATIVE ORDER[MAY BE UNSORTED] AND SO IS THE CASE WITH USERS OF ODD AGES.
	*/
	@Override
	public List<UserEntity> sort(List<UserEntity> users) {	
		List<UserEntity> sortedUsers = users.stream()
                .sorted((user1, user2) -> {
                	boolean user1Even = (user1.getAge()%2 == 0);
                	boolean user2Even = (user2.getAge()%2 == 0);
                	
                	if(user1Even && !user2Even) {
                		return -1;
                	} else if(!user1Even && user2Even) {
                		return 1;
                	} else {
                		return 0;
                	}
                })
                .collect(Collectors.toList());		
		return sortedUsers;
	}
}


