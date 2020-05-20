package me.jingu.demostudyrestapi.events;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest //Web관련 bean만 등록하고 Repository는 등록 안될 수 있음 -> @MockBean으로 따로 선언
class EventControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	//Mockito를 사용해서 mock 객체를 만들고 빈으로 등록해준다.
	//(주의)기존빈을 해당 테스트빈이 대체한다.
	@MockBean
	EventRepository eventRepository;
	
	
	@Test
	public void createEvent() throws Exception {
		
		Event event = Event.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2020, 05, 10, 11, 30))
				.closeEnrollmentDateTime(LocalDateTime.of(2020, 05, 12, 11, 30))
				.beginEventDateTime(LocalDateTime.of(2020, 05, 13, 20, 12, 30))
				.endEventDateTime(LocalDateTime.of(2020, 05, 20, 12, 30))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("강남역 DB2 스타쉽 팩토리")
				.build();
		
		event.setId(10);
		Mockito.when(eventRepository.save(event)).thenReturn(event);
		
		
		
		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event)))
		.andDo(print())	//어떤 요청과 어떤 응답 받았는지 확인 가능
		.andExpect(status().isCreated())
		.andExpect(jsonPath("id").exists()) //TODO id는 DB에 들어갈 때 자동 생성된 값으로 나오는지 확인
		.andExpect(header().exists(HttpHeaders.LOCATION))
		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
		;
	}

}
