package com.fullstack.test.validator;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.test.exception.http.BadRequestException;
import com.fullstack.test.response.ErrorResponse;

public class TestValidator {
	
	public static void validateUserEntry(String cityName, Double lat, Double lon) {
		if(StringUtils.isEmpty(cityName) && lat == null && lon == null) {
			throw new BadRequestException(ErrorResponse.builder()
					.code("400.001")
					.message("Query params city-name or lon and lat is required.")
					.build());
		} else if (lat == null && lon != null) {
			throw new BadRequestException(ErrorResponse.builder()
					.code("400.001")
					.message("Query param lat is required.")
					.build());
		} else if (lat != null && lon == null) {
			throw new BadRequestException(ErrorResponse.builder()
					.code("400.001")
					.message("Query param lon is required.")
					.build());
		}
	}
}
