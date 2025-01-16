package org.meetmybar.meetmybarapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class MeetMyBarApiApplicationTests {

	@Test
	void contextLoads() {
		// This will verify if the Spring context loads correctly
	}

}