package co.simplon.tools;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Component
public class DataBaseInitialization implements ApplicationListener<ContextRefreshedEvent> {
	private final static char CSV_SEPARATOR = ';';

	@Inject
	private UsersInitialisationTools usersTools;
	@Inject
	private CategoriesInitialisationTools categoriesTools;
	@Inject
	private ColorsInitialisationTools colorsTools;
	@Inject
	private ItemsInitialisationTools itemsTools;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		System.out.println("loading Database :");
		System.out.println("==> file of Categories");
		System.out.println(loadingDBCategories("src/main/resources/data/Categories"));
		System.out.println("==> file of Colors");
		System.out.println(loadingDBColors("src/main/resources/data/Colors"));
		System.out.println("==> file of items");
		System.out.println(loadingDBItems("src/main/resources/data/Items"));
		System.out.println("==> file of users");
		System.out.println(loadingDBUsers("src/main/resources/data/Users.csv"));
	}

	/**
	 * method to load items since a file
	 * 
	 * @param fileLocation
	 * @return
	 */
	public final String loadingDBItems(String fileLocation) {
		try {
			List<String[]> file = readFile(fileLocation); // file reading
			if (file.isEmpty()) {
				return "Actually, the file is empty.";
			} else {
				if (correctFormat(file, 10)) { // control correct format
					int[] exceptionNullColumns = { 6 };
					if (noEmptyValueInFile(file, exceptionNullColumns)) { // control no empty data return
						return this.itemsTools.insertionFileItems(file); // file processing
					} else {
						return "the file have some empty data that required.";
					}
				} else {
					return "the file is not in the correct format.";
				}
			}
		} catch (FileNotFoundException e) {
			return "Actually, no file to loading.";
		} catch (IOException e) {
			e.printStackTrace();
			return "Internal error is happening";
		}
	}

	/**
	 * method to load colors since a file
	 * 
	 * @param fileLocation
	 * @return
	 */
	public final String loadingDBColors(String fileLocation) {
		try {
			List<String[]> file = readFile(fileLocation); // file reading
			if (file.isEmpty()) {
				return "Actually, the file is empty.";
			} else {
				if (correctFormat(file, 3)) { // control correct format
					if (noEmptyValueInFile(file)) { // control no empty data
						return this.colorsTools.insertionFileColors(file); // file processing
					} else {
						return "the file have some empty data that required.";
					}
				} else {
					return "the file is not in the correct format.";
				}
			}
		} catch (FileNotFoundException e) {
			return "Actually, no file to loading.";
		} catch (IOException e) {
			e.printStackTrace();
			return "Internal error is happening";
		}
	}

	/**
	 * method to load categories since a file
	 * 
	 * @param fileLocation
	 * @return
	 */
	public final String loadingDBCategories(String fileLocation) {
		try {
			List<String[]> file = readFile(fileLocation); // file reading
			if (file.isEmpty()) {
				return "Actually, the file is empty.";
			} else {
				if (correctFormat(file, 2)) { // control correct format
					if (noEmptyValueInFile(file)) { // control no empty data
						return this.categoriesTools.InsertionFileCategories(file); // file processing
					} else {
						return "the file have some empty data that required.";
					}
				} else {
					return "the file is not in the correct format.";
				}
			}
		} catch (FileNotFoundException e) {
			return "Actually, no file to loading.";
		} catch (IOException e) {
			e.printStackTrace();
			return "Internal error is happening";
		}
	}

	/**
	 * method to load users tests since users file
	 * 
	 * @param fileLocation
	 * @return result
	 */
	public final String loadingDBUsers(String fileLocation) {
		try {
			List<String[]> file = readFile(fileLocation); // file reading
			if (file.isEmpty()) {
				return "Actually, the file is empty.";
			} else {
				if (correctFormat(file, 12)) { // control correct format
					if (noEmptyValueInFile(file)) { // control no empty data
						return this.usersTools.InsertionFileUser(file); // file processing
					} else {
						return "the file have some empty data that required.";
					}
				} else {
					return "the file is not in the correct format.";
				}
			}
		} catch (FileNotFoundException e) {
			return "Actually, no file to loading.";
		} catch (IOException e) {
			e.printStackTrace();
			return "Internal error is happening";
		}
	}

	/**
	 * method to check that file exists and if it's ok, read it and return content
	 * 
	 * @param fileLocation
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private final List<String[]> readFile(String fileLocation) throws FileNotFoundException, IOException {
		// Constructor of reader of file with the separator
		CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileLocation)).withCSVParser(csvParser).build()) {
			List<String[]> list = reader.readAll(); // including the informations of file in a List of data
			return list;
		}
	}

	/**
	 * method to check that file have a correct number of informations (correct
	 * number of columns)
	 * 
	 * @param file
	 * @return
	 */
	private final boolean correctFormat(List<String[]> file, int nbColumns) {
		for (String[] fileLineCheck : file) {
			if (fileLineCheck.length != nbColumns) {
				return false;
			}
		}
		return true;
	}

	/**
	 * method to check that ALL columns are not empty
	 * 
	 * @param file
	 * @return
	 */
	private final boolean noEmptyValueInFile(List<String[]> file) {
		for (String[] fileLineCheck : file) {
			for (String s : fileLineCheck) {
				if (s.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * method to check that all columns are not empty except the integer array
	 * specified
	 * 
	 * @param file
	 * @param columnsFileNullAuthorized
	 * @return
	 */
	private final boolean noEmptyValueInFile(List<String[]> file, int[] columnsFileNullAuthorized) {
		for (String[] fileLineCheck : file) {
			for (int i = 0; i < fileLineCheck.length; i++) {
				for (int j : columnsFileNullAuthorized) {
					if (i != j) {
						if (fileLineCheck[i].isEmpty()) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

}
