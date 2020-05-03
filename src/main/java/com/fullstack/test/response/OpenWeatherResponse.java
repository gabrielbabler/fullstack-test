package com.fullstack.test.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fullstack.test.dto.OpenWeatherMainDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenWeatherResponse {

	@JsonProperty("main")
	private OpenWeatherMainDTO main;
	
}
