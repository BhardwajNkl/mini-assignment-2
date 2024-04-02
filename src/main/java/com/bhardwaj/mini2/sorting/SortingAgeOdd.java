package com.bhardwaj.mini2.sorting;

import java.util.List;
import java.util.stream.Collectors;

import com.bhardwaj.mini2.entities.UserEntity;

public class SortingAgeOdd implements SortingStrategyInterface{
	
	/* 
	 * SORTING RULE:
	 * USERS WITH ODD AGE COME FIRST. 
	 * NOTE: THIS METHOD DOES NOT SORT USERS BASED ON AGE WHEN TWO USERS HAVE SAME ODD/EVEN AGES.
	 * THIS MEANS THAT USERS WITH ODD AGES STILL HAVE THE ORIGINAL RELATIVE ORDER[MAY BE UNSORTED] AND SO IS THE CASE WITH USERS OF EVEN AGES.
	*/
	@Override
	public List<UserEntity> sort(List<UserEntity> users) {
		List<UserEntity> sortedUsers = users.stream()
                .sorted((user1, user2)->{
                	
                	boolean user1Odd = (user1.getAge()%2 != 0);
                	boolean user2Odd = (user2.getAge()%2 != 0);
                	
                	if(user1Odd && !user2Odd) {
                		return -1;
                	} else if(!user1Odd && user2Odd) {
                		return 1;
                	} else {
                		return 0;
                	}
                })
                .collect(Collectors.toList());	
		return sortedUsers;
	}
}
