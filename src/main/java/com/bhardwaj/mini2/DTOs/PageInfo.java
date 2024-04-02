package com.bhardwaj.mini2.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PageInfo {
	private long total;
	private boolean hasPreviousPage;
	private boolean hasNextPage;
	
	public PageInfo(long totalUsers, int limit, int offset) {
		this.total = totalUsers;
		this.hasPreviousPage = computeHasPreviousPage(offset);
		this.hasNextPage = compputeHasNextPage(totalUsers, limit, offset);
	}
	
	private boolean computeHasPreviousPage(int offset) {
		return offset > 0;
	}
	private boolean compputeHasNextPage(long totalUsers, int limit, int offset) {
		return ((offset+limit)<totalUsers);
	}
}
