package com.fullstack.test.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.test.response.SpotifyPlaylistResponse;
import com.fullstack.test.response.SpotifyTrackResponse;
import com.fullstack.test.service.TestService;
import com.fullstack.test.validator.TestValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class TestController {

	private final TestService testService;
	
	@GetMapping("/weather")
	public ResponseEntity<List<SpotifyPlaylistResponse>> getPlaylistByTemperature(
			@RequestParam(name = "city-name", required = false) String cityName,
			@RequestParam(name = "lat", required = false) Double lat,
			@RequestParam(name = "lon", required = false) Double lon) {
		
		TestValidator.validateUserEntry(cityName, lat, lon);

		return ResponseEntity.ok(testService.getPlaylistByLocation(cityName, lat, lon));
	}
	
	@GetMapping("/{playlist-id}")
	public ResponseEntity<List<SpotifyTrackResponse>> getTracksByPlaylistId(
			@PathVariable(name = "playlist-id") String playlistId) {
		return ResponseEntity.ok(testService.getTracksByPlaylistId(playlistId));
	}
}
