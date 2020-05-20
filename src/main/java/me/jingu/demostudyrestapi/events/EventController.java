package me.jingu.demostudyrestapi.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

	private final EventRepository eventRepository;

	private final ModelMapper modelMapper;

	// 생성자가 하나만 있고 받아오는 파라미터가 빈으로 등록되있으면 @Autowired 생략 가능 (4.3부터)
	public EventController(EventRepository eventRepository, ModelMapper modelMapper) {
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
	}

	@PostMapping
	public ResponseEntity createEvent(@RequestBody EventDto eventDto) {
		// 입력값은 event 대신 eventDto로 받자.
		// 1. builder를 사용해서 eventDto -> event로 옮기자.
		// 2. 이걸 생략할 수 있는 방법 -> 모델 맵퍼 -> 리플렉션 발생으로 속도 저하 발생 가능성
		/*
		 * Event event = Event.builder() .name(eventDto.getName())
		 * .description(eventDto.getDescription())..
		 */
		Event event = modelMapper.map(eventDto, Event.class);
		Event newEvent = this.eventRepository.save(event);
		
		URI createdURi = linkTo(EventController.class).slash(newEvent.getId()).toUri();

		// TODO Location 헤더에 생성된 이벤트를 조회할 수 있는 URI에 담겨있는지 확인
		return ResponseEntity.created(createdURi).body(event);
	}
}
