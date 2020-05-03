package com.fullstack.test.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fullstack.test.dto.SpotifyTracksDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyPlaylistTracksResponse {
	
	@JsonProperty("tracks")
	private SpotifyTracksDTO tracks;
	
}
