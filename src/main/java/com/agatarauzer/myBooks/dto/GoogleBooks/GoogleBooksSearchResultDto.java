package com.agatarauzer.myBooks.dto.GoogleBooks;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor 
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBooksSearchResultDto {
	
	@JsonProperty("items")
    private List<GoogleBookBasicDto> books;

    @JsonProperty("totalItems")
    private int totalResults;
}


