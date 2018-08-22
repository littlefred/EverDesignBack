package co.simplon;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import co.simplon.tools.DataBaseInitialization;

public class DataBaseInitializationTests {
	DataBaseInitialization test = new DataBaseInitialization();

	// test to check when there is no file categories
	@Test
	public void noFileCategoriesTest() {
		assertEquals("Actually, no file to loading categories.",
				test.loadingDBCategories("src/main/resources/data/noFile"));
	}

	// test to check when the file categories is empty
	@Test
	public void emptyFileCategoriesTest() {
		assertEquals("Actually, the file of categories is empty.",
				test.loadingDBCategories("src/main/resources/dataTest/emptyFile"));
	}

	// test to check when the file categories is not correct
	@Test
	public void badFileCategoriesTest() {
		assertEquals("the file of categories is not in the correct format.",
				test.loadingDBCategories("src/main/resources/dataTest/badFormatFile"));
	}

	// test to check when there are empty data in the file categories
	@Test
	public void dataEmptyFileCategoriesTest() {
		assertEquals("the file of categories have some empty data.",
				test.loadingDBCategories("src/main/resources/dataTest/dataEmptyFileCat"));
	}

	// test to check when there is no file compositions
	@Test
	public void noFileCompositionsTest() {
		assertEquals("Actually, no file to loading compositions.",
				test.loadingDBCompositions("src/main/resources/data/noFile"));
	}

	// test to check when the file compositions is empty
	@Test
	public void emptyFileCompositionsTest() {
		assertEquals("Actually, the file of compositions is empty.",
				test.loadingDBCompositions("src/main/resources/dataTest/emptyFile"));
	}

	// test to check when the file compositions is not correct
	@Test
	public void badFileCompositionsTest() {
		assertEquals("the file of compositions is not in the correct format.",
				test.loadingDBCompositions("src/main/resources/dataTest/badFormatFile"));
	}

	// test to check when there are empty data in the file compositions
	@Test
	public void dataEmptyFileCompositionsTest() {
		assertEquals("the file of compositions have some empty data.",
				test.loadingDBCompositions("src/main/resources/dataTest/dataEmptyFileCompo"));
	}

	// test to check when there is no file items
	@Test
	public void noFileItemsTest() {
		assertEquals("Actually, no file to loading items.", test.loadingDBItems("src/main/resources/data/noFile"));
	}

	// test to check when the file items is empty
	@Test
	public void emptyFileItemsTest() {
		assertEquals("Actually, the file of items is empty.",
				test.loadingDBItems("src/main/resources/dataTest/emptyFile"));
	}

	// test to check when the file items is not correct
	@Test
	public void badFileItemsTest() {
		assertEquals("the file of items is not in the correct format.",
				test.loadingDBItems("src/main/resources/dataTest/badFormatFile"));
	}

}
