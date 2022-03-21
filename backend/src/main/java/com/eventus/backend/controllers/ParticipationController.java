package com.eventus.backend.controllers;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.UserService;
import java.util.List;
import java.util.Map;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ParticipationController {
  private final ParticipationService participationService;

  private final UserService userService;

  private final EventService eventService;

  @Autowired
  public ParticipationController(
    ParticipationService participationService,
    UserService userService,
    EventService eventService
  ) {
    this.participationService = participationService;
    this.userService = userService;
    this.eventService = eventService;
  }

  @GetMapping("/participations")
  @ResponseStatus(HttpStatus.OK)
  public List<Participation> getParticipations(
    @RequestParam(defaultValue = "0") Integer numPag
  ) {
    return this.participationService.findAllParticipation(
        PageRequest.of(numPag, 20)
      );
  }

  @GetMapping("/participations/{id}")
  public ResponseEntity<Participation> getParticipationById(
    @PathVariable Long id
  ) {
    Participation participation =
      this.participationService.findParticipationById(id);
    if (participation != null) {
      return ResponseEntity.ok(participation);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/events/{eventId}/participants")
  public ResponseEntity<List<User>> getUsersByEvent(
    @PathVariable Long eventId,
    @RequestParam(defaultValue = "0") Integer numPag
  ) {
    return ResponseEntity.ok(
      this.participationService.findUsersByEventId(
          eventId,
          PageRequest.of(numPag, 20)
        )
    );
  }

  @GetMapping("/users/{userId}/participations")
  public ResponseEntity<List<Participation>> getParticipationsByUser(
    @PathVariable Long userId,
    @RequestParam(defaultValue = "0") Integer numPag
  ) {
    return ResponseEntity.ok(
      this.participationService.findParticipationByUserId(
          userId,
          PageRequest.of(numPag, 20)
        )
    );
  }

  @GetMapping("/events/{eventId}/participations")
  public ResponseEntity<List<Participation>> getParticipationsByEvent(
    @PathVariable Long eventId,
    @RequestParam(defaultValue = "0") Integer numPag
  ) {
    return ResponseEntity.ok(
      this.participationService.findParticipationByEventId(
          eventId,
          PageRequest.of(numPag, 20)
        )
    );
  }

  @PostMapping("/participations")
  public ResponseEntity<String> createParticipation(
    @RequestBody Map<String, String> p
  ) {
    try {
      Event event = this.eventService.findById(Long.valueOf(p.get("eventId")));
      User user = this.userService.findUserById(1L);

      if (user != null && event != null) {
        Participation participation =
          this.participationService.findByUserIdEqualsAndEventIdEquals(
              user.getId(),
              event.getId()
            );

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

                //Formatters
                DateTimeFormatter date=DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter hour=DateTimeFormatter.ofPattern("HH:mm");
                LocalDateTime startDate = participation.getEvent().getStartDate();
                LocalDateTime endDate = participation.getEvent().getEndDate();

                //Image
                document = this.participationService.insertImageInPDF(document, "src/main/resources/com.eventus.backend.controllers/Logo.png");

                //EventUs
                document = this.participationService.insertEventUsInPDF(document);

                document = this.participationService.insertLineSeparetorinPDF(document, 8);

                //Tittle
                document = this.participationService.insertTitleInPDF(document, participation.getEvent().getTitle());

                //Header event data
                document = this.participationService.insertHeaderInPDF(document, "Datos del evento");

                //Description
                document = this.participationService.insertParagraphInPDF(document, "Descripción: ", participation.getEvent().getDescription());

                //Start date
                document = this.participationService.insertParagraphInPDF(document, "Fecha de inicio: ", startDate.format(date));

                //Start hour
                document = this.participationService.insertParagraphInPDF(document, "Hora de inicio: ", startDate.format(hour));

                //End date
                document = this.participationService.insertParagraphInPDF(document, "Fecha de fin: ", endDate.format(date));

                //End hour
                document = this.participationService.insertParagraphInPDF(document, "Hora de fin: ", endDate.format(hour));

                document = this.participationService.insertLineSeparetorinPDF(document, 3);

                //Header buy data
                document = this.participationService.insertHeaderInPDF(document, "Datos de compra");

                //First name
                document = this.participationService.insertParagraphInPDF(document, "Nombre: ", participation.getUser().getFirstName());

                //Last name
                document = this.participationService.insertParagraphInPDF(document, "Apellidos: ", participation.getUser().getLastName());

                //Buy date
                document = this.participationService.insertParagraphInPDF(document, "Fecha de compra: ", participation.getBuyDate().format(date));

                //Price
                document = this.participationService.insertParagraphInPDF(document, "Precio: ", String.valueOf(participation.getPrice()));

                //Ticket id
                document = this.participationService.insertParagraphInPDF(document, "Identificador del ticket: ", String.valueOf(participation.getTicket()));

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
        } else {
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }
}
