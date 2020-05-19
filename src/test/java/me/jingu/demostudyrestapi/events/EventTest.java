package me.jingu.demostudyrestapi.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EventTest {

	@Test
	public void test() {
		Event event = Event.builder()
				.name("REST API Study")
				.description("RES API development with Spring")
				.build();

		assertThat(event).isNotNull();
	}
	
	@Test
	public void javaBean() {
		//Given
		String name = "Event";
		String description = "Spring";
		
		//When
		Event event = new Event();
		event.setName(name);
		event.setDescription("Spring");
		
		//Then
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(description);
	}
}
