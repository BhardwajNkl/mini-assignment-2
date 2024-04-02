package com.bhardwaj.mini2.DTOs;

import java.util.List;

import com.bhardwaj.mini2.entities.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetApiSuccessResponse {
	private List<UserEntity> data;
	private PageInfo pageInfo;
}
