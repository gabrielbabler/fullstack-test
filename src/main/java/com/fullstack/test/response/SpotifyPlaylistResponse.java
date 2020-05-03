package com.fullstack.test.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyPlaylistResponse {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
}
