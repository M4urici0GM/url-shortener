package dev.mgbarbosa.urlshortner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class AccessingDataMongodbApplicationTests {

	@Autowired
	private RedisTemplate<?, ?> redisTemplate;

	@Test
	void contextLoads() {
	}

}
