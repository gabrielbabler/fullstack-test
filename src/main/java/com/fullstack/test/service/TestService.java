package com.fullstack.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fullstack.test.response.OpenWeatherResponse;
import com.fullstack.test.response.SpotifyCategoryItemsResponse;
import com.fullstack.test.response.SpotifyPlaylistResponse;
import com.fullstack.test.response.SpotifyPlaylistTracksResponse;
import com.fullstack.test.response.SpotifyTrackResponse;
import com.fullstack.test.service.client.OpenWeatherClient;
import com.fullstack.test.service.client.SpotifyClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {
	
	private final SpotifyClient spotifyClient;
	private final OpenWeatherClient openWeatherClient;
	
	public List<SpotifyTrackResponse> getTracksByPlaylistId(String playlistId) {
		SpotifyPlaylistTracksResponse tracks = spotifyClient.getTracks(playlistId);
		return tracksToResponse(tracks);
	}
	
	public List<SpotifyPlaylistResponse> getPlaylistByLocation(String cityName, Double lat, Double lon) {
		if(cityName != null) {
			OpenWeatherResponse tempByCityName = openWeatherClient.getTempByCityName(cityName);
			String categoryByTemp = getSuggestionByTemp(tempByCityName.getMain().getTemp());
			
			return playlistToResponse(spotifyClient.getPlaylists(categoryByTemp));
		} else {
			OpenWeatherResponse tempByLogLat = openWeatherClient.getTempByLogLat(lon, lat);
			String categoryByTemp = getSuggestionByTemp(tempByLogLat.getMain().getTemp());
			
			return playlistToResponse(spotifyClient.getPlaylists(categoryByTemp));
		}
	}
	
	/**
	 * Method to return a category based on temperature
	 * @param integer temperature
	 * @return string category
	 */
	private String getSuggestionByTemp(Double temperature) {
		String category = null;

		if(temperature > 30) {
			category = "party";
		} else if (temperature >= 15 && temperature <= 30) {
			category = "pop";
		} else if (temperature >= 10 && temperature <= 14) {
			category = "rock";
		} else {
			category = "classical";
		}
		return category;
	}
	
	
	private List<SpotifyTrackResponse> tracksToResponse(SpotifyPlaylistTracksResponse tracks) {
		List<SpotifyTrackResponse> tracksToResponse = new ArrayList<>();
		
		tracks.getTracks().getItems().forEach(i -> {
			tracksToResponse.add(SpotifyTrackResponse.builder()
					.track(i.getTrack().getName())
					.build());
		});
		return tracksToResponse;
	}
	
	/**
	 * Method to convert a SpotifyCategoryItemsResponse to a List of SpotifyPlaylistResponse
	 * @param items - SpotifyCategoryItemsResponse
	 * @return List of SpotifyPlaylistResponse
	 */
	private List<SpotifyPlaylistResponse> playlistToResponse(SpotifyCategoryItemsResponse items){
		List<SpotifyPlaylistResponse> playlistToResponse = new ArrayList<>();
		
		items.getPlaylists().getItems().forEach(i -> {
			playlistToResponse.add(SpotifyPlaylistResponse.builder()
					.id(i.getId())
					.name(i.getName())
					.build());
		});
		return playlistToResponse;
	}
}
