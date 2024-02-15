package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.EventDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId")
    private Long eventId;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime registDate;
    @Column
    private String title;
    @Column
    private String content;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime endDate;
    @Column
    private String mainImage;
    @Column
    private String subImage;
    @Column
    private String thumbImage;
    public static Event toEventEntity(EventDTO eventDTO) {
        Event event = new Event();

        event.setEventId(eventDTO.getEventId());
        event.setRegistDate(eventDTO.getRegistDate());
        event.setTitle(eventDTO.getTitle());
        event.setContent(eventDTO.getContent());
        event.setEndDate(eventDTO.getEndDate());
        event.setMainImage(eventDTO.getMainImage());
        event.setSubImage(eventDTO.getSubImage());
        event.setThumbImage(eventDTO.getThumbImage());

        return event;
    }
}
