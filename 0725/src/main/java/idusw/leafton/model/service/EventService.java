package idusw.leafton.model.service;

import idusw.leafton.model.DTO.EventDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {
    public List<EventDTO> getAll();
    EventDTO getEventById(Long eventId);

    EventDTO save(EventDTO eventDTO, MultipartFile mainImage, MultipartFile subImage, MultipartFile thumbImage) throws IOException;
}