package com.example.FleetManagement.Service;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAddressFromCoordinates(double latitude, double longitude) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://maps.googleapis.com/maps/api/geocode/json")
                .queryParam("latlng", latitude + "," + longitude)
                .queryParam("key", apiKey)
                .build()
                .toUri();

        Map response = restTemplate.getForObject(uri, Map.class);
        if (response != null && response.containsKey("results")) {
            var results = (java.util.List<Map<String, Object>>) response.get("results");
            if (!results.isEmpty()) {
                return (String) results.get(0).get("formatted_address");
            }
        }
        return "Address not found";
    }
}

