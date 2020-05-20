package me.jingu.demostudyrestapi.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;

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
	
	//생성자가 하나만 있고 받아오는 파라미터가 빈으로 등록되있으면 @Autowired 생략 가능 (4.3부터)
	public EventController(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	@PostMapping
	public ResponseEntity createEvent(@RequestBody Event event) {
		Event newEvent = this.eventRepository.save(event);
		URI createdURi = linkTo(EventController.class)
				.slash(newEvent.getId())
				.toUri();
		event.setId(10);
		
		//TODO Location 헤더에 생성된 이벤트를 조회할 수 있는 URI에 담겨있는지 확인
		return ResponseEntity.created(createdURi).body(event);
	}
}
