package com.eventus.backend.controllers;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.UserService;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Line;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
public class ParticipationController {

    private final ParticipationService participationService;

    private final UserService userService;

    private final EventService eventService;

    @Autowired
    public ParticipationController(ParticipationService participationService,UserService userService,EventService eventService) {
        this.participationService = participationService;
        this.userService = userService;
        this.eventService=eventService;
    }

    @GetMapping("/participations")
    @ResponseStatus(HttpStatus.OK)
    public List<Participation> getParticipations(@RequestParam(defaultValue = "0") Integer numPag) {
        return this.participationService.findAllParticipation(PageRequest.of(numPag,20));
    }

    @GetMapping("/participations/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Long id) {
        Participation participation=this.participationService.findParticipationById(id);
        if(participation!=null){
            return ResponseEntity.ok(participation);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/event/{eventId}/participants")
    public ResponseEntity<List<User>> getUsersByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag) {
       return ResponseEntity.ok(this.participationService.findUsersByEventId(eventId, PageRequest.of(numPag,20)));
    }

    @GetMapping("/user/{userId}/participations")
    public ResponseEntity<List<Participation>>  getParticipationsByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.participationService.findParticipationByUserId(userId,PageRequest.of(numPag,20)));
    }

    @GetMapping("/event/{eventId}/participations")
    public ResponseEntity<List<Participation>> getParticipationsByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.participationService.findParticipationByEventId(eventId, PageRequest.of(numPag,20)));
    }

    @PostMapping("/participations")
    public ResponseEntity<String> createParticipation(@RequestBody Map<String, String> p) {
        try {
            Event event = this.eventService.findById(Long.valueOf(p.get("eventId")));
            User user = this.userService.findUserById(1L);

            if (user != null && event != null) {
                Participation participation = this.participationService.findByUserIdEqualsAndEventIdEquals(user.getId(), event.getId());

                if (participation != null) {
                    return ResponseEntity.badRequest().build();
                }

                this.participationService.saveParticipation(event, user);
            } else {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }


    }

    @DeleteMapping("/participations/{id}")
    public ResponseEntity<String> deleteParticipation(@PathVariable Long id) {
        try {
            this.participationService.deleteParticipation(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/participation/{id}/ticket")
    public ResponseEntity<byte[]> generateTicket(@PathVariable Long id){
        ResponseEntity<byte[]> response = null;
        Participation participation=this.participationService.findParticipationById(id);
        if(participation!=null){
            Document document=new Document();
            try {
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                PdfWriter.getInstance(document,outputStream);
                document.open();

                //Image
                Path path = Paths.get("src/main/resources/com.eventus.backend.controllers/Logo.png");
                Image img = Image.getInstance(path.toAbsolutePath().toString());
                img.scalePercent(20);
                img.setAlignment(Element.ALIGN_CENTER);
                document.add(img);

                //Tittle
                Paragraph paragraph=new Paragraph();
                Font font = FontFactory.getFont(FontFactory.TIMES_BOLD, 50, new BaseColor(28, 69, 135));
                Chunk chunk = new Chunk("Event", font);
                paragraph.add(chunk);
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 50, new BaseColor(47, 112, 213));
                chunk = new Chunk("Us", font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(35);
                paragraph.setSpacingAfter(10);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);


                LineSeparator separator = new LineSeparator();
                separator.setLineColor(new BaseColor(245, 125, 198));
                separator.setLineWidth(8);
                chunk = new Chunk(separator);
                document.add(chunk);
                separator = new LineSeparator();
                separator.setLineColor(new BaseColor(47, 112, 213));
                separator.setLineWidth(8);
                chunk = new Chunk(separator);
                document.add(chunk);
                separator = new LineSeparator();
                separator.setLineColor(new BaseColor(80, 182, 107));
                separator.setLineWidth(8);
                chunk = new Chunk(separator);
                document.add(chunk);
                separator = new LineSeparator();
                separator.setLineColor(new BaseColor(254, 164, 70));
                separator.setLineWidth(8);
                chunk = new Chunk(separator);
                document.add(chunk);

                //Tittle
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 35, new BaseColor(65, 64, 64));
                chunk = new Chunk(participation.getEvent().getTitle(), font);
                paragraph=new Paragraph();
                paragraph.add(chunk);
                paragraph.setSpacingBefore(35);
                paragraph.setSpacingAfter(10);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);

                //Event data
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 25, new BaseColor(47, 112, 213));
                chunk = new Chunk("Datos del evento", font);
                chunk.setUnderline(3,-5);
                paragraph.add(chunk);
                paragraph.setSpacingAfter(10);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);


                //Description
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Descripción: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(participation.getEvent().getDescription(), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                //Formatters
                DateTimeFormatter date=DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter hour=DateTimeFormatter.ofPattern("HH:mm");

                //Start date
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Fecha de inicio: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(participation.getEvent().getStartDate().format(date), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                //Start hour
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Hora de inicio: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(participation.getEvent().getStartDate().format(hour), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                //End date
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Fecha de fin: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(participation.getEvent().getEndDate().format(date), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                //End hour
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Hora de fin: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(participation.getEvent().getEndDate().format(hour), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setSpacingAfter(15);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                separator = new LineSeparator();
                separator.setLineColor(new BaseColor(245, 125, 198));
                separator.setLineWidth(3);
                chunk = new Chunk(separator);
                document.add(chunk);
                separator = new LineSeparator();
                separator.setLineColor(new BaseColor(47, 112, 213));
                separator.setLineWidth(3);
                chunk = new Chunk(separator);
                document.add(chunk);
                separator = new LineSeparator();
                separator.setLineColor(new BaseColor(80, 182, 107));
                separator.setLineWidth(3);
                chunk = new Chunk(separator);
                document.add(chunk);
                separator = new LineSeparator();
                separator.setLineColor(new BaseColor(254, 164, 70));
                separator.setLineWidth(3);
                chunk = new Chunk(separator);
                document.add(chunk);

                //Buy data
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 25, new BaseColor(47, 112, 213));
                chunk = new Chunk("Datos de compra", font);
                chunk.setUnderline(3,-5);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(15);
                paragraph.setSpacingAfter(10);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                //First name
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Nombre: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(participation.getUser().getFirstName(), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                //Last name
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Apellidos: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(participation.getUser().getLastName(), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                //Buy date
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Fecha de compra: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(participation.getBuyDate().format(date), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                //Price
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Precio: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(String.valueOf(participation.getPrice()), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                //Ticket id
                paragraph=new Paragraph();
                font = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, new BaseColor(65, 64, 64));
                chunk = new Chunk("Id de ticket: ", font);
                paragraph.add(chunk);
                chunk = new Chunk(String.valueOf(participation.getTicket()), font);
                paragraph.add(chunk);
                paragraph.setSpacingBefore(5);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paragraph);

                document.close();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                String filename = "ticket.pdf";
                headers.setContentDispositionFormData(filename, filename);
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                response = new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

            } catch (DocumentException | IOException e) {
                response = ResponseEntity.badRequest().build();
            }
        }else{
            response = ResponseEntity.badRequest().build();
        }


        return response;
    }
}
