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

	
	@PostMapping
	public ResponseEntity createEvent(@RequestBody Event event) {
		URI createdURi = linkTo(EventController.class).slash("{id}").toUri();
		event.setId(10);
		
		//TODO Location 헤더에 생성된 이벤트를 조회할 수 있는 URI에 담겨있는지 확인
		return ResponseEntity.created(createdURi).build();
	}
}
