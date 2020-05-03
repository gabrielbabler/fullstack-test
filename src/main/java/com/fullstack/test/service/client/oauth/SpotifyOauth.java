package com.fullstack.test.service.client.oauth;

import java.util.Base64;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fullstack.test.config.ApplicationConfig;
import com.fullstack.test.response.SpotifyOauthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotifyOauth {
	
	private final ApplicationConfig applicationConfig;
	private final RestTemplateBuilder restTemplateBuilder;
	
	public String getAccessToken() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(
				(applicationConfig.getSpotifyClientId()+":"+applicationConfig.getSpotifyClientSecret())
				.getBytes()));
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		
		String body = "grant_type=client_credentials";
		
		HttpEntity<?> entity = new HttpEntity<>(body, headers);
		
		ResponseEntity<SpotifyOauthResponse> spotifyOauth = restTemplateBuilder.build().postForEntity(
				applicationConfig.getSpotifyTokenUrl(), entity, SpotifyOauthResponse.class);
		
		return spotifyOauth.getBody().getToken();
	}
}
