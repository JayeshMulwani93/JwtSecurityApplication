package com.solutions.security.repository.cache.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTToken {

	@Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MDE4MzcyOTAsImV4cCI6MTYwMTgzNzM1MCwic3ViIjoiamF5ZXNoLm11bHdhbmk5M0B5YWhvby5pbiIsInJvbGUiOiJST0xFX1VTRVIiLCJjdXN0b21lcklkIjoxfQ.rFysJY5E0jpEXzWUVtzi5lb3yr3JiRj1nHUCyqdDl1k")
	private String jwt;

	private Date expirationTimeStamp;
}
