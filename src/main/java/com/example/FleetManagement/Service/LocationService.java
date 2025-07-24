package com.example.FleetManagement.Service;


import com.example.FleetManagement.Entity.Location;

import java.util.List;
import java.util.Optional;


public interface LocationService {

    Location createLocation(Location location, Long vehicleId);

    List<Location> getAllLocations();

    Optional<Location> getLocationById(Long id);

    List<Location> getLocationsByVehicleId(Long vehicleId);

    void deleteLocation(Long id);
}
