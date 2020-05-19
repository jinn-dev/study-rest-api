package me.jingu.demostudyrestapi.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest
class EventControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void createEvent() throws Exception {
		
		Event event = Event.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2020, 05, 10, 11, 30))
				.closeEnrollmentDateTime(LocalDateTime.of(2020, 05, 12, 11, 30))
				.beginEventDateTime(LocalDateTime.of(2020, 05, 13, 20, 12, 30))
				.closeEnrollmentDateTime(LocalDateTime.of(2020, 05, 20, 12, 30))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("강남역 DB2 스타쉽 팩토리")
				.build();
		
		mockMvc.perform(post("/api/events/")
				.content(objectMapper.writeValueAsString(event))
				.accept(MediaTypes.HAL_JSON))
		.andDo(print())	//어떤 요청과 어떤 응답 받았는지 확인 가능
		.andExpect(status().isCreated())
		.andExpect(jsonPath("id").exists()); //TODO id는 DB에 들어갈 때 자동 생성된 값으로 나오는지 확인
	}

}
