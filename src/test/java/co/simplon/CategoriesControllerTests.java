package co.simplon;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import co.simplon.services.CategoriesServices;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CategoriesControllerTests {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	CategoriesServices categoriesServices;
	
	@Test
	public void getCategories() throws Exception {
		when(this.categoriesServices.findAll()).thenReturn(new TreeSet<>());
		this.mockMvc.perform(get("/api/categories")).andExpect(status().isOk());
	}

}
