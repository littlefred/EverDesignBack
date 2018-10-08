package co.simplon.services;

import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageServices {
	/**
	 * method ton initialise folders to store images of categories and items
	 * @return
	 */
	public boolean init();
	
	/**
	 * method to upload picture(s) to a place
	 * @param files
	 * @param place
	 * @return
	 */
	public Map<String, Boolean> store(MultipartFile[] files, String place);
	
	/**
	 * method to read a file since a place & name
	 * @param filename
	 * @param place
	 * @return
	 */
	public Resource loadFile(String filename, String place);
	
	// public void deleteAll();

}
