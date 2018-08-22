package co.simplon;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringIntegrationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getCategories() {
		List<?> categories = this.restTemplate.getForObject("/api/categories", List.class);
		assertThat(categories).isNotNull();
	}

}
