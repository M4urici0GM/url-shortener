package dev.mgbarbosa.urlshortner;

import dev.mgbarbosa.urlshortner.config.MongodbConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = UrlShortenerApplication.class)
@ActiveProfiles("tests")
@Import(MongodbConfiguration.class)
public class UrlShortenerTests {
	@Test
	public void contextLoads() {
	}
}

