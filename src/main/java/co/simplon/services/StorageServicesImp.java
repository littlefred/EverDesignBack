package co.simplon.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServicesImp implements StorageServices {
	private final Path rootLocationCategories = Paths.get("images/categories");
	private final Path rootLocationItems = Paths.get("images/items");
	private final Path rootLocationColors = Paths.get("images/colors");

	/**
	 * method ton initialise folders to store images of categories and items
	 */
	@Override
	public boolean init() {
		boolean result = false;
		try {
			if (!Files.exists(Paths.get("images"))) {
				Files.createDirectory(Paths.get("images"));
			}
			if (!Files.exists(this.rootLocationCategories)) {
				Files.createDirectory(rootLocationCategories);
			}
			if (!Files.exists(this.rootLocationItems)) {
				Files.createDirectory(rootLocationItems);
			}
			if (!Files.exists(this.rootLocationColors)) {
				Files.createDirectory(rootLocationColors);
			}
			result = true;
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
		return result;
	}

	/**
	 * method to save a list of files
	 */
	@Override
	public Map<String, Boolean> store(MultipartFile[] files, String place) {
		Map<String, Boolean> map = new TreeMap<>();
		Path finalPlace = null;
		if (place.equals("item")) {
			finalPlace = this.rootLocationItems;
		}
		if (place.equals("category")) {
			finalPlace = this.rootLocationCategories;
		}
		if (place.equals("color")) {
			finalPlace = this.rootLocationColors;
		}
		for (MultipartFile file : files) {
			boolean control = true;
			if (file.getSize() > 2000000) {
				map.put(file.getOriginalFilename(), false);
				control = false;
			}
			if (control && !file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/jpg")) {
				map.put(file.getOriginalFilename(), false);
				control = false;
			}
			if (control) {
				try {
					Files.copy(file.getInputStream(), finalPlace.resolve(file.getOriginalFilename()));
					map.put(file.getOriginalFilename(), true);
				} catch (Exception e) {
					map.put(file.getOriginalFilename(), false);
					e.printStackTrace();
					System.err.println("erreur sur l'enregistrement du fichier : " + file.getOriginalFilename());
				}
			}
		}
		return map;
	}

	/**
	 * method to read a file
	 */
	@Override
	public Resource loadFile(String filename, String place) {
		Path finalPlace = null;
		if (place.equals("item")) {
			finalPlace = this.rootLocationItems;
		}
		if (place.equals("category")) {
			finalPlace = this.rootLocationCategories;
		}
		if (place.equals("color")) {
			finalPlace = this.rootLocationColors;
		}
		try {
			Path file = finalPlace.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	// @Override
	/*
	 * public void deleteAll() {
	 * FileSystemUtils.deleteRecursively(rootLocationItems.toFile()); }
	 */

}
