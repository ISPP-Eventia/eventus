package com.eventus.backend.services;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.ParticipationRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ParticipationService implements IParticipationService{

    private final ParticipationRepository partRepository;

    @Autowired
    public ParticipationService(ParticipationRepository partRepository) {
       this.partRepository = partRepository;
    }

    @Transactional
    public void saveParticipation(Event event, User user) throws DataAccessException {
        Participation participation=new Participation();
        participation.setTicket(RandomStringUtils.randomAlphanumeric(20)+user.getId()+event.getId());
        participation.setPrice(event.getPrice());
        participation.setEvent(event);
        participation.setUser(user);
        partRepository.save(participation);
    }
    
    public Participation createParticipationAndTicket(Event event, User user) throws DocumentException, IOException {
    	saveParticipation(event, user);
        Participation part = findByUserIdEqualsAndEventIdEquals(user.getId(), event.getId());
        if(part!=null) createTicketPDF(part);
    	return part;
    }

    public Participation findParticipationById(Long id) {
        return partRepository.findById(id).orElse(null);
    }

    public void deleteParticipation(Long id) {
        partRepository.deleteById(id);
    }

    public List<Participation> findParticipationByUserId(Long userId,Pageable p) {
        return partRepository.findByUserIdEquals(userId,p);
    }

    public List<Participation> findParticipationByEventId(Long eventId,Pageable p) {
        return partRepository.findByEventIdEquals(eventId,p);
    }

    public List<User> findUsersByEventId(Long eventId,Pageable p) {
        return partRepository.findUsersByEventId(eventId,p);
    }
    public List<Participation> findAllParticipation(Pageable p){
        return partRepository.findAll(p);
    }

    public Participation findByUserIdEqualsAndEventIdEquals(Long userId,Long evenId){
       return partRepository.findByUserIdEqualsAndEventIdEquals(userId,evenId).orElse(null);
    }
    
    public Document insertImageInPDF(Document d, String path) throws DocumentException, IOException {
        File file = ResourceUtils.getFile(path);

        Image img = Image.getInstance(file.getAbsolutePath());
        img.scalePercent(20);
        img.setAlignment(Element.ALIGN_CENTER);
        d.add(img);
    	return d;
    }
    
    public Document insertLineSeparetorinPDF(Document d, float width) throws DocumentException {
    	Chunk chunk;
    	LineSeparator separator = new LineSeparator();
        separator.setLineColor(new BaseColor(0, 63, 154));
        separator.setLineWidth(width);
        chunk = new Chunk(separator);
        d.add(chunk);
        separator = new LineSeparator();
        separator.setLineColor(new BaseColor(40, 113, 204));
        separator.setLineWidth(width);
        chunk = new Chunk(separator);
        d.add(chunk);
        separator = new LineSeparator();
        separator.setLineColor(new BaseColor(70, 143, 234));
        separator.setLineWidth(width);
        chunk = new Chunk(separator);
        d.add(chunk);
        separator = new LineSeparator();
        separator.setLineColor(new BaseColor(80, 153, 244));
        separator.setLineWidth(width);
        chunk = new Chunk(separator);
        d.add(chunk);
        separator = new LineSeparator();
        separator.setLineColor(new BaseColor(120, 193, 255));
        separator.setLineWidth(width);
        chunk = new Chunk(separator);
        d.add(chunk); 
    	return d;
    }
    
    public Document insertParagraphInPDF(Document d, String title, String text) throws DocumentException {
    	Paragraph p = new Paragraph();
        Font f = FontFactory.getFont(FontFactory.TIMES_BOLD, 15, new BaseColor(65, 64, 64));
    	Chunk chunk = new Chunk(title, f);
        p.add(chunk);
        if(title.equals("Identificador del ticket: ")) {
        	f = FontFactory.getFont(FontFactory.TIMES_BOLD, 15, new BaseColor(70, 143, 234));
        }
        chunk = new Chunk(text, f);
        p.add(chunk);
        p.setSpacingBefore(5);
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        d.add(p);
    	return d;
    }
    
    public Document insertHeaderInPDF(Document d, String title) throws DocumentException {
    	Paragraph p = new Paragraph();
        Font f = FontFactory.getFont(FontFactory.TIMES_BOLD, 23, new BaseColor(0, 63, 154));
    	Chunk chunk = new Chunk(title, f);
    	chunk.setUnderline(3,-5);
        p.add(chunk);
        p.setSpacingBefore(10);
        p.setSpacingAfter(10);
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        d.add(p);
    	return d;
    }
    
    public Document insertEventUsInPDF(Document d) throws DocumentException {
    	Paragraph p = new Paragraph();
    	Font f = FontFactory.getFont(FontFactory.TIMES_BOLD, 50, new BaseColor(0, 63, 154));
    	Chunk chunk = new Chunk("Event", f);
        p.add(chunk);
        f = FontFactory.getFont(FontFactory.TIMES_BOLD, 50, new BaseColor(120, 193, 255));
        chunk = new Chunk("Us", f);
        p.add(chunk);
        p.setSpacingBefore(35);
        p.setSpacingAfter(15);
        p.setAlignment(Element.ALIGN_CENTER);
        d.add(p);
    	return d;
    }
    
    public Document insertTitleInPDF(Document d, String title) throws DocumentException {
    	Paragraph p = new Paragraph();
    	Font f = FontFactory.getFont(FontFactory.TIMES_BOLD, 35, new BaseColor(65, 64, 64));
    	Chunk chunk = new Chunk(title, f);
        p.add(chunk);
        p.setSpacingBefore(35);
        p.setAlignment(Element.ALIGN_CENTER);
        d.add(p);
    	return d;
    }
    
    public byte[] createTicketPDF(Participation participation) throws DocumentException, IOException {
    	Document document = new Document();
    	ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        PdfWriter.getInstance(document,outputStream);
        document.open();
        
        //Formatters
        DateTimeFormatter date=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter hour=DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime startDate = participation.getEvent().getStartDate();
        LocalDateTime endDate = participation.getEvent().getEndDate();

        //Header
        document = insertImageInPDF(document, "classpath:com.eventus.backend.controllers/Logo.png");
        document = insertEventUsInPDF(document);
        document = insertLineSeparetorinPDF(document, 8);
        
        //Event title
        document = insertTitleInPDF(document, participation.getEvent().getTitle());
        
        //Event data
        document = insertHeaderInPDF(document, "Datos del evento");
        document = insertParagraphInPDF(document, "Descripción: ", participation.getEvent().getDescription());
        document = insertParagraphInPDF(document, "Fecha de inicio: ", startDate.format(date));
        document = insertParagraphInPDF(document, "Hora de inicio: ", startDate.format(hour));
        document = insertParagraphInPDF(document, "Fecha de fin: ", endDate.format(date));
        document = insertParagraphInPDF(document, "Hora de fin: ", endDate.format(hour));

        document = insertLineSeparetorinPDF(document, 3);                

        //Buy data
        document = insertHeaderInPDF(document, "Datos de compra");
        document = insertParagraphInPDF(document, "Nombre: ", participation.getUser().getFirstName());
        document = insertParagraphInPDF(document, "Apellidos: ", participation.getUser().getLastName());
        document = insertParagraphInPDF(document, "Fecha de compra: ", participation.getBuyDate().format(date));
        document = insertParagraphInPDF(document, "Precio: ", String.valueOf(participation.getPrice()));
        document = insertParagraphInPDF(document, "Identificador del ticket: ", String.valueOf(participation.getTicket()));

        document.close();
        
        return outputStream.toByteArray();
    }
}
