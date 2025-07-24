package com.example.FleetManagement.Service;

import com.example.FleetManagement.Entity.Location;
import com.example.FleetManagement.Repository.LocationRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ReportService {

    @Autowired
    private LocationRepository locationRepository;

    public byte[] generatePdfReport() {
        List<Location> locations = locationRepository.findAll();

        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            // Add Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Location Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Add Table
            PdfPTable table = new PdfPTable(5); // Adjust column count as needed
            table.setWidthPercentage(100);

            // Table Header
            Stream.of("ID", "Latitude", "Longitude", "Timestamp", "Address").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                header.setPhrase(new Phrase(headerTitle));
                table.addCell(header);
            });

            // Table Rows
            for (Location loc : locations) {
                table.addCell(String.valueOf(loc.getId()));
                table.addCell(String.valueOf(loc.getLatitude()));
                table.addCell(String.valueOf(loc.getLongitude()));
                table.addCell(loc.getTimestamp().toString());
                table.addCell(loc.getAddress() != null ? loc.getAddress() : "N/A");
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

