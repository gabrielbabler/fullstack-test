package com.fullstack.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationConfig {
	
	@Value("${spotify.credentials.client-id}")
	private String spotifyClientId;
	
	@Value("${spotify.credentials.client-secret}")
	private String spotifyClientSecret;
	
	@Value("${spotify.token.url}")
	private String spotifyTokenUrl;
	
	@Value("${spotify.category-playlist.url}")
	private String spotifyCategoryPlaylistUrl;
	
	@Value("${spotify.playlist-id.url}")
	private String spotifyPlaylistIdUrl;
	
	@Value("${openweather.get.city-name}")
	private String openWeatherGetByCityName;
	
	@Value("${openweather.get.geo-loc}")
	private String openWeatherGetByGeoLoc;
	
	@Value("${openweather.key}")
	private String openWeatherKey;
	
}
