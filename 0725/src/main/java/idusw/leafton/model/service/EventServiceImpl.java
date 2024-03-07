package idusw.leafton.model.service;

import idusw.leafton.model.DTO.EventDTO;
import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.entity.Event;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

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
}