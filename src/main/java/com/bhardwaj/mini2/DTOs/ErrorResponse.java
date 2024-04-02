package com.bhardwaj.mini2.DTOs;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	private String message;
	private int code;
	private Date timestamp;
}
