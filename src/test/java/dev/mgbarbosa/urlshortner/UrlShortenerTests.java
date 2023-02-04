package dev.mgbarbosa.urlshortner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureDataMongo
@DirtiesContext
@SpringBootTest(classes = UrlShortenerApplication.class)
@ActiveProfiles("test")
public class UrlShortenerTests {
	@Test
	public void contextLoads() {
	}
}

