package com.bhardwaj.mini2.sorting;

import java.util.List;
import java.util.stream.Collectors;

import com.bhardwaj.mini2.entities.UserEntity;

// case Name-Odd
public class SortingNameOdd implements SortingStrategyInterface {
	
	/* 
	 * SORTING RULE:
	 * USERS WITH 'ODD LENGTH NAME' COME FIRST. 
	 * NOTE: THIS METHOD DOES NOT SORT USERS BASED ON 'LENGTH OF NAME' WHEN TWO USERS HAVE SAME 'ODD/EVEN NAME LENGTH'
	 * THIS MEANS THAT USERS WITH 'ODD LENGTH NAME' STILL HAVE THE ORIGINAL RELATIVE ORDER[MAY BE UNSORTED] AND SO IS THE CASE WITH USERS OF 'EVEN LENGTH NAME'.
	*/
	@Override
	public List<UserEntity> sort(List<UserEntity> users) {
		List<UserEntity> sortedUsers= users.stream()
        .sorted((user1, user2)->{
        	boolean user1NameLengthOdd = (user1.getName().length()%2 != 0);
        	boolean user2NameLengthOdd = (user2.getName().length()%2 != 0);
        	
        	if(user1NameLengthOdd && !user2NameLengthOdd) {
        		return -1;
        	} else if(!user1NameLengthOdd && user2NameLengthOdd) {
        		return 1;
        	} else {
        		return 0;
        	}
        })
        .collect(Collectors.toList());
		return sortedUsers;
	}

}
