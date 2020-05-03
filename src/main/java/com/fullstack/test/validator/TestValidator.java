package com.fullstack.test.validator;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.test.exception.http.BadRequestException;
import com.fullstack.test.response.ErrorResponse;

public class TestValidator {
	
	public static void validateUserEntry(String cityName, String lat, String log) {
		if(StringUtils.isEmpty(cityName) && (StringUtils.isEmpty(lat) && StringUtils.isEmpty(log))) {
			throw new BadRequestException(ErrorResponse.builder()
					.code("400.001")
					.message("Query params city-name or log and lat is required.")
					.build());
		} else if (StringUtils.isEmpty(lat) && !StringUtils.isEmpty(log)) {
			throw new BadRequestException(ErrorResponse.builder()
					.code("400.001")
					.message("Query param lat is required.")
					.build());
		} else if (!StringUtils.isEmpty(lat) && StringUtils.isEmpty(log)) {
			throw new BadRequestException(ErrorResponse.builder()
					.code("400.001")
					.message("Query param log is required.")
					.build());
		}
	}
}
