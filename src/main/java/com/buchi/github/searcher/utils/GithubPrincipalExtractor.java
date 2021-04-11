package com.buchi.github.searcher.utils;

import java.security.Principal;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GithubPrincipalExtractor{

	public static String retrieveUserLoginDetails(Principal principal) {
		if(principal == null) {
			return null;
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String principalJsonString = objectMapper.writeValueAsString(principal);
			JSONObject principalDetailJson = new JSONObject(principalJsonString);
			JSONObject principalJson = principalDetailJson.getJSONObject("principal");
			JSONObject attributes = principalJson.has("attributes") ? principalJson.getJSONObject("attributes") : null;
			if(attributes != null && attributes.has("login")) {
				return attributes.getString("login");
			}

		} catch (JsonProcessingException exception) {
			log.error("Could not retrieve user details from principal object!", exception);
		}

		return null;
	}

}
