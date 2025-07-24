package com.example.FleetManagement.Service;
import com.example.FleetManagement.Entity.Location;
import com.example.FleetManagement.Entity.Vehicle;
import com.example.FleetManagement.Repository.LocationRepository;
import com.example.FleetManagement.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private GeocodingService geocodingService;

    public Location createLocation(Location location, Long vehicleId) {
        if (vehicleId != null) {
            Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
            vehicle.ifPresent(location::setVehicle);
        }
        try {
            String address = geocodingService.getAddressFromCoordinates(
                location.getLatitude(), location.getLongitude()
            );
            location.setAddress(address);
        } catch (Exception e) {
            location.setAddress("Unable to fetch address");
        }

        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public List<Location> getLocationsByVehicleId(Long vehicleId) {
        return locationRepository.findByVehicleId(vehicleId);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
