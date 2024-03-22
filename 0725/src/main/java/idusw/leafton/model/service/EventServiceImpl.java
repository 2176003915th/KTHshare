package idusw.leafton.model.service;

import idusw.leafton.model.DTO.EventDTO;
import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.FileSave;
import idusw.leafton.model.entity.Event;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final FileSave fileSave;

    //event를 list로 모두 가져오는 메서드
    @Override
    public List<EventDTO> getAll(){
        List<Event> eventList = eventRepository.findAll();
        List<EventDTO> eventDTOList = new ArrayList<>();

        for(Event event : eventList) {
            eventDTOList.add(EventDTO.toEventDTO(event));
        }

        return eventDTOList;
    }

    @Override
    public EventDTO getEventById(Long eventId){
        Optional<Event> opEvent = eventRepository.findById(eventId);
        if(opEvent.isPresent()) {
            return EventDTO.toEventDTO(opEvent.get());
        } else {
            throw new IllegalArgumentException("Invalid event Id: " + eventId);
        }
    }

    @Override
    public EventDTO save(EventDTO eventDTO, MultipartFile mainImage, MultipartFile subImage, MultipartFile thumbImage) throws IOException {
        String mainPath = fileSave.getDefaultPath() + "event/main/";
        String subPath = fileSave.getDefaultPath() + "event/sub";
        String thumbPath = fileSave.getDefaultPath() + "event/thumb/";

        if (!mainImage.isEmpty()) {
            String Filename = fileSave.saveFileAndRename(mainImage, mainPath);
            eventDTO.setMainImage("/home/passion/images/event/main/" + Filename); // 파일의 저장된 이름을 설정
        }
        if (!subImage.isEmpty()) {
            String Filename = fileSave.saveFileAndRename(subImage, subPath);
            eventDTO.setSubImage("/home/passion/images/event/sub/" + Filename); // 파일의 저장된 이름을 설정 // /event/main/아아.png
        }
        if (!thumbImage.isEmpty()) {
            String Filename = fileSave.saveFileAndRename(thumbImage, thumbPath);
            eventDTO.setThumbImage("/home/passion/images/event/thumb/" + Filename); // 파일의 저장된 이름을 설정
        }

        Event event = Event.toEventEntity(eventDTO); // entity에 넣기위하여 변경
        Event result = eventRepository.save(event); // 레파지토리에서 save(insert)한 결과
        return EventDTO.toEventDTO(result); //결과를 dto에 저장
    }
}