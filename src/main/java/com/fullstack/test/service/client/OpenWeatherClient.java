package com.fullstack.test.service.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.fullstack.test.config.ApplicationConfig;
import com.fullstack.test.exception.http.BadRequestException;
import com.fullstack.test.response.OpenWeatherResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenWeatherClient {

	private final ApplicationConfig applicationConfig;
	private final RestTemplateBuilder restTemplateBuilder;
	
	public OpenWeatherResponse getTempByCityName(String cityName) {
		String url = applicationConfig.getOpenWeatherGetByCityName() + "&q=" + cityName + 
				"&appid=" + applicationConfig.getOpenWeatherKey();
		
		ResponseEntity<OpenWeatherResponse> weather = restTemplateBuilder.build().exchange(
				url,
				HttpMethod.GET,
				null,
				OpenWeatherResponse.class);
		
		return weather.getBody();
	}
	
	public OpenWeatherResponse getTempByLogLat(String log, String lat) {
		String url = applicationConfig.getOpenWeatherGetByGeoLoc() + lat + "&log=" + log + 
				"&appid=" + applicationConfig.getOpenWeatherKey();
		try {
			ResponseEntity<OpenWeatherResponse> weather = restTemplateBuilder.build().exchange(
					url,
					HttpMethod.GET,
					null,
					OpenWeatherResponse.class);
			return weather.getBody();
		} catch (HttpStatusCodeException e) {
			throw new BadRequestException();
		}
	}
}
