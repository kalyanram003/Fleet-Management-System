package com.example.FleetManagement.Controller;

import com.example.FleetManagement.Entity.Location;
import com.example.FleetManagement.Service.LocationService;
import com.example.FleetManagement.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/fms/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Map<String, Object> request) {
        Location location = new Location();

        location.setLatitude(Double.parseDouble(request.get("latitude").toString()));
        location.setLongitude(Double.parseDouble(request.get("longitude").toString()));

        String timestampStr = request.get("timestamp").toString();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime timestamp = LocalDateTime.parse(timestampStr, formatter);
        location.setTimestamp(timestamp);

        Long vehicleId = null;
        if (request.get("vehicleId") != null) {
            vehicleId = Long.parseLong(request.get("vehicleId").toString());
        } else if (request.get("vehicle") != null) {
            Map<String, Object> vehicleMap = (Map<String, Object>) request.get("vehicle");
            if (vehicleMap.get("id") != null) {
                vehicleId = Long.parseLong(vehicleMap.get("id").toString());
            }
        }

        Location savedLocation = locationService.createLocation(location, vehicleId);
        return ResponseEntity.ok(savedLocation);
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Location>> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<Location>> getLocationsByVehicleId(@PathVariable Long vehicleId) {
        List<Location> locations = locationService.getLocationsByVehicleId(vehicleId);
        return ResponseEntity.ok(locations);
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/report/pdf")
    public ResponseEntity<byte[]> downloadPdfReport() {
        byte[] pdfContent = reportService.generatePdfReport();
        if (pdfContent == null) {
            return ResponseEntity.internalServerError().body(null);
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=location_report.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdfContent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok("Location deleted successfully.");
    }
}
