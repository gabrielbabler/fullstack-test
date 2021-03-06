package com.fullstack.test.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fullstack.test.dto.SpotifyPlaylistDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyCategoryItemsResponse {
	
	@JsonProperty("playlists")
	private SpotifyPlaylistDTO playlists;
	
}
