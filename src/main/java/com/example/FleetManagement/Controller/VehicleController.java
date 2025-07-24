package com.example.FleetManagement.Controller;

import com.example.FleetManagement.Entity.Vehicle;
import com.example.FleetManagement.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/fms/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Map<String, Object> request) {
        Vehicle vehicle = new Vehicle();
        
        vehicle.setVehicleNumber((String) request.get("vehicleNumber"));
        vehicle.setModel((String) request.get("model"));
        vehicle.setType((String) request.get("type"));
        vehicle.setStatus((String) request.get("status"));

        Long userId = null;
        if (request.get("userId") != null) {
            userId = Long.parseLong(request.get("userId").toString());
        } else if (request.get("user") != null) {
            Map<String, Object> userMap = (Map<String, Object>) request.get("user");
            if (userMap.get("id") != null) {
                userId = Long.parseLong(userMap.get("id").toString());
            }
        }

        Vehicle savedVehicle = vehicleService.createVehicle(vehicle, userId);
        return ResponseEntity.ok(savedVehicle);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Vehicle>> getVehicleById(@PathVariable Long id) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByUserId(@PathVariable Long userId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByUserId(userId);
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {

        Vehicle vehicle = new Vehicle();
        
        vehicle.setVehicleNumber((String) request.get("vehicleNumber"));
        vehicle.setModel((String) request.get("model"));
        vehicle.setType((String) request.get("type"));
        vehicle.setStatus((String) request.get("status"));

      
        Long userId = request.get("userId") != null ? Long.parseLong(request.get("userId").toString()) : null;

        Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicle, userId);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok("Vehicle deleted successfully.");
    }
}
