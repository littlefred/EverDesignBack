package co.simplon.controllers;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.simplon.models.Categories;
import co.simplon.models.Colors;
import co.simplon.services.CategoriesServices;
import co.simplon.services.ColorsServices;
import co.simplon.services.StorageServices;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.194:4200"})
// @CrossOrigin("${origin.allowed}")
public class CategoriesController {
	@Inject
	private CategoriesServices categoriesServices;
	@Inject
	private ColorsServices colorsServices;
	@Autowired
	StorageServices storageService;
	
	/**
	 *  method to find all categories
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Set<Categories>> findAll() {
		Set<Categories> finalList = categoriesServices.findAll();
		if (finalList.isEmpty()) {
			return new ResponseEntity<Set<Categories>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Set<Categories>>(finalList, HttpStatus.OK);
		}
	}
	
	/**
	 * method to find all colors
	 * @return
	 */
	@GetMapping(value = "/colors")
	@ResponseBody
	public ResponseEntity<Set<Colors>> findAllColors() {
		Set<Colors> finalList = colorsServices.findAll();
		if (finalList.isEmpty()) {
			return new ResponseEntity<Set<Colors>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Set<Colors>>(finalList, HttpStatus.OK);
		}
	}
	
	/**
	 * method to have access at categories pictures
	 * @param fileName
	 * @return
	 */
	@GetMapping("/loading/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) {
		Resource file = storageService.loadFile(fileName, "category");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	/**
	 * method to upload image(s) of new item(s)
	 * @param files
	 * @return
	 */
	@PostMapping("/upload")
	@ResponseBody
	public ResponseEntity<Boolean> uploadFilesItems(@RequestParam("files") MultipartFile files) {
		boolean result = this.storageService.storeOneFile(files, "category");
		if(!result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		}
	}
	
	/**
	 * method to save new item(s) after have load their images
	 * @param body
	 */
	@PostMapping("/new")
	@ResponseBody
	public ResponseEntity<Categories>  saveItems(@RequestBody Categories body) {
		Categories result = this.categoriesServices.save(body);
		if (result == null) {
			return new ResponseEntity<Categories>(HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<Categories>(result, HttpStatus.OK);
		}
	}

}
