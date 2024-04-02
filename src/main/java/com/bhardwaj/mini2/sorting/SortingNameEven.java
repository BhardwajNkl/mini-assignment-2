package com.bhardwaj.mini2.sorting;

import java.util.List;
import java.util.stream.Collectors;

import com.bhardwaj.mini2.entities.UserEntity;

public class SortingNameEven implements SortingStrategyInterface {
	
	/* 
	 * SORTING RULE:
	 * USERS WITH 'EVEN LENGTH NAME' COME FIRST. 
	 * NOTE: THIS METHOD DOES NOT SORT USERS BASED ON 'LENGTH OF NAME' WHEN TWO USERS HAVE SAME 'EVEN/ODD NAME LENGTH'
	 * THIS MEANS THAT USERS WITH 'EVEN LENGTH NAME' STILL HAVE THE ORIGINAL RELATIVE ORDER[MAY BE UNSORTED] AND SO IS THE CASE WITH USERS OF 'ODD LENGTH NAME'.
	*/
	@Override
	public List<UserEntity> sort(List<UserEntity> users) {
		List<UserEntity> sortedUsers = users.stream()
        .sorted((user1, user2)->{
        	boolean user1NameLengthEven = (user1.getName().length()%2 == 0);
        	boolean user2NameLengthEven = (user2.getName().length()%2 == 0);
        	
        	if(user1NameLengthEven && !user2NameLengthEven) {
        		return -1;
        	} else if(!user1NameLengthEven && user2NameLengthEven) {
        		return 1;
        	} else {
        		return 0;
        	}
        })
        .collect(Collectors.toList());
		return sortedUsers;
	}

}
