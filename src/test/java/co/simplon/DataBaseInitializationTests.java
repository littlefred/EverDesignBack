package co.simplon;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import co.simplon.tools.DataBaseInitialization;

public class DataBaseInitializationTests {
	DataBaseInitialization test = new DataBaseInitialization();

	// test to check when there is no file categories
	@Test
	public void noFileCategoriesTest() {
		assertEquals("Actually, no file to loading.", test.loadingDBCategories("src/main/resources/data/noFile"));
	}

	// test to check when the file categories is empty
	@Test
	public void emptyFileCategoriesTest() {
		assertEquals("Actually, the file is empty.", test.loadingDBCategories("src/main/resources/dataTest/emptyFile"));
	}

	// test to check when the file categories is not correct
	@Test
	public void badFileCategoriesTest() {
		assertEquals("the file is not in the correct format.",
				test.loadingDBCategories("src/main/resources/dataTest/badFormatFile"));
	}

	// test to check when there are empty data in the file categories
	@Test
	public void dataEmptyFileCategoriesTest() {
		assertEquals("the file have some empty data that required.",
				test.loadingDBCategories("src/main/resources/dataTest/dataEmptyFileCat"));
	}

	// test to check when there is no file compositions
	@Test
	public void noFileColorsTest() {
		assertEquals("Actually, no file to loading.", test.loadingDBColors("src/main/resources/data/noFile"));
	}

	// test to check when the file compositions is empty
	@Test
	public void emptyFileColorsTest() {
		assertEquals("Actually, the file is empty.", test.loadingDBColors("src/main/resources/dataTest/emptyFile"));
	}

	// test to check when the file compositions is not correct
	@Test
	public void badFileColorsTest() {
		assertEquals("the file is not in the correct format.",
				test.loadingDBColors("src/main/resources/dataTest/badFormatFile"));
	}

	// test to check when there are empty data in the file compositions
	@Test
	public void dataEmptyFileColorsTest() {
		assertEquals("the file have some empty data that required.",
				test.loadingDBColors("src/main/resources/dataTest/dataEmptyFileCompo"));
	}

	// test to check when there is no file items
	@Test
	public void noFileItemsTest() {
		assertEquals("Actually, no file to loading.", test.loadingDBItems("src/main/resources/data/noFile"));
	}

	// test to check when the file items is empty
	@Test
	public void emptyFileItemsTest() {
		assertEquals("Actually, the file is empty.", test.loadingDBItems("src/main/resources/dataTest/emptyFile"));
	}

	// test to check when the file items is not correct
	@Test
	public void badFileItemsTest() {
		assertEquals("the file is not in the correct format.",
				test.loadingDBItems("src/main/resources/dataTest/badFormatFile"));
	}

	// test to check when there are empty data in the file items
	@Test
	public void dataEmptyFileItemsTest() {
		assertEquals("the file have some empty data that required.",
				test.loadingDBColors("src/main/resources/dataTest/dataEmptyFileCompo"));
	}

	// test to check when there is no file users
	@Test
	public void noFileUsersTest() {
		assertEquals("Actually, no file to loading.", test.loadingDBUsers("src/main/resources/data/noFile"));
	}

	// test to check when the file users is empty
	@Test
	public void emptyFileUsersTest() {
		assertEquals("Actually, the file is empty.", test.loadingDBUsers("src/main/resources/dataTest/emptyFile"));
	}

	// test to check when the file user is not correct
	@Test
	public void badFileUsersTest() {
		assertEquals("the file is not in the correct format.",
				test.loadingDBUsers("src/main/resources/dataTest/badFormatFile"));
	}

	// test to check when there are empty data in the file compositions
	@Test
	public void dataEmptyUsersTest() {
		assertEquals("the file have some empty data that required.",
				test.loadingDBUsers("src/main/resources/dataTest/dataEmptyFileUsers"));
	}

}
