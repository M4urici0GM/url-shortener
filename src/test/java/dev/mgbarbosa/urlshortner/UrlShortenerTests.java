package dev.mgbarbosa.urlshortner;

import dev.mgbarbosa.urlshortner.repositories.interfaces.CachingShortedUrlRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.CachingUserRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.SecurityCachingRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.ShortedUrlRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = UrlShortenerApplication.class)
@ActiveProfiles("test")
public class UrlShortenerTests {

	@MockBean
	private CachingShortedUrlRepository repository;

	@MockBean
	private SecurityCachingRepository securityCachingRepository;

	@MockBean
	private CachingUserRepository cachingUserRepository;

	@MockBean
	private ShortedUrlRepository shortedUrlRepository;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void contextLoads() {
	}
}

