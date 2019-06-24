package com.borsuki.app.musicrecommendationsystem.externalAPI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
public class SpotifyAPI {


    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${spotify.loginURL}")
    private String spotifyLoginURL;
    @Value("${spotify.artistURL}")
    private String spotifyArtistURL;
    @Value("${spotify.searchURL}")
    private String spotifySearchURL;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    ObjectMapper objectMapper;

    @Autowired
    public SpotifyAPI(RestTemplate restTemplate, HttpHeaders httpHeaders, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.objectMapper = objectMapper;
    }

    public String getAccessToken() throws IOException {
        HttpEntity request = getHttpEntity();
        ResponseEntity<String> responseEntity = restTemplate.exchange(spotifyLoginURL, HttpMethod.POST, request, String.class);
        return objectMapper.readTree(responseEntity.getBody()).path("access_token").toString().replace("\"", "");
    }

    public String getArtist(String artistId, String accessToken) throws IOException {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> request = new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(spotifyArtistURL + artistId, HttpMethod.GET, request, String.class);
        return objectMapper.readTree(responseEntity.getBody()).path("name").toString();
    }

    public String searchItem(String item, String accessToken) throws IOException {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(spotifySearchURL)
                .queryParam("q", item)
                .queryParam("type", "artist")
                .queryParam("market", "US")
                .queryParam("limit", "1")
                .queryParam("offset", "0");
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        JsonNode jsonNode = objectMapper.readTree(response.getBody()).path("artists").path("items");
        return jsonNode.get(0).path("id").asText();
    }

    public Map<String, String> getRelated(String artistId, String accessToken) throws IOException {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> request = new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(spotifyArtistURL + artistId + "/related-artists", HttpMethod.GET, request, String.class);
        Map<String, String> result = new LinkedHashMap<>();
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody()).path("artists");
        jsonNode.forEach(e -> result.put(e.path("id").asText(), e.path("name").asText()));

        return result;
    }

    private String getBase64(String clientId, String clientSecret) {
        return Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
    }


    private HttpEntity getHttpEntity() {
        httpHeaders.set("Authorization", "Basic " + getBase64(clientId, clientSecret));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        return new HttpEntity<>(map, httpHeaders);
    }
}
