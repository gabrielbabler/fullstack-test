package com.fullstack.test.service.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fullstack.test.config.ApplicationConfig;
import com.fullstack.test.response.SpotifyCategoryItemsResponse;
import com.fullstack.test.response.SpotifyPlaylistTracksResponse;
import com.fullstack.test.service.client.oauth.SpotifyOauth;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotifyClient {

	private final SpotifyOauth spotifyOauth;
	private final RestTemplateBuilder restTemplateBuilder;
	private final ApplicationConfig applicationConfig;
	
	public SpotifyCategoryItemsResponse getPlaylists(String category) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + spotifyOauth.getAccessToken());
		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		ResponseEntity<SpotifyCategoryItemsResponse> categories = restTemplateBuilder.build()
				.exchange(String.format(applicationConfig.getSpotifyCategoryPlaylistUrl(), category), 
						HttpMethod.GET, entity, SpotifyCategoryItemsResponse.class);
		
		return categories.getBody();
	}
	
	public SpotifyPlaylistTracksResponse getTracks(String playlistId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + spotifyOauth.getAccessToken());
		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		ResponseEntity<SpotifyPlaylistTracksResponse> tracks = restTemplateBuilder.build()
			.exchange(applicationConfig.getSpotifyPlaylistIdUrl() + playlistId + "?fields=tracks",
					HttpMethod.GET, entity, SpotifyPlaylistTracksResponse.class);
		
		return tracks.getBody();
	}
	
}
