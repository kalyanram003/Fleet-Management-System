package com.example.FleetManagement.Controller;

import com.example.FleetManagement.Service.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fms/geocode")
public class GeocodingController {

    @Autowired
    private GeocodingService geocodingService;

    @GetMapping
    public String getAddress(@RequestParam double latitude, @RequestParam double longitude) {
        return geocodingService.getAddressFromCoordinates(latitude, longitude);
    }
}

