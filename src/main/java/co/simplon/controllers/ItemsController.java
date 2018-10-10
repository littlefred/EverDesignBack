package co.simplon.controllers;

import java.util.List;
import java.util.Map;

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

import co.simplon.models.Colors;
import co.simplon.models.Items;
import co.simplon.services.ColorsServices;
import co.simplon.services.ItemsServices;
import co.simplon.services.StorageServices;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = { "http://localhost:4200", "http://192.168.1.194:4200" })
public class ItemsController {
	@Inject
	private ItemsServices itemsServices;
	@Inject
	private ColorsServices colorsServices;
	@Autowired
	StorageServices storageService;

	/**
	 * method to find all items
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Items>> findAll() {
		List<Items> finalList = itemsServices.findAll();
		if (finalList.isEmpty()) {
			return new ResponseEntity<List<Items>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Items>>(finalList, HttpStatus.OK);
		}
	}

	/**
	 * method to get the stock of an item
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/stock/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Integer> getStock(@PathVariable("id") Long id) {
		int stockItem = itemsServices.stock(id);
		return new ResponseEntity<Integer>(stockItem, HttpStatus.OK);
	}
	
	/**
	 * method to upload image(s) of new item(s)
	 * @param files
	 * @return
	 */
	@PostMapping("/upload")
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> uploadFilesItems(@RequestParam("files") MultipartFile[] files) {
		Map<String, Boolean> result = this.storageService.store(files, "item");
		if(result.isEmpty()) {
			return new ResponseEntity<Map<String, Boolean>>(HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			return new ResponseEntity<Map<String, Boolean>>(result, HttpStatus.OK);
		}
	}
	
	/**
	 * method to upload only one image
	 * @param files
	 * @return
	 */
	@PostMapping("/uploadColors")
	@ResponseBody
	public ResponseEntity<Boolean> uploadFilesItems(@RequestParam("files") MultipartFile files) {
		boolean result = this.storageService.storeOneFile(files, "color");
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
	public ResponseEntity<List<Items>>  saveItems(@RequestBody Items[] body) {
		List<Items> result = this.itemsServices.save(body);
		if (result.isEmpty()) {
			return new ResponseEntity<List<Items>>(HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<List<Items>>(result, HttpStatus.OK);
		}
	}
	
	/**
	 * method to save new item(s) after have load their images
	 * @param body
	 */
	@PostMapping("/newColors")
	@ResponseBody
	public ResponseEntity<Colors>  saveColors(@RequestBody Colors body) {
		Colors result = this.colorsServices.save(body);
		if (result == null) {
			return new ResponseEntity<Colors>(HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<Colors>(result, HttpStatus.OK);
		}
	}
	
	/**
	 * method to have access at items pictures
	 * @param fileName
	 * @return
	 */
	@GetMapping("/loading/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) {
		Resource file = storageService.loadFile(fileName, "item");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	/**
	 * method to have access at colors pictures
	 * @param fileName
	 * @return
	 */
	@GetMapping("/colorsloading/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> getColorFile(@PathVariable("fileName") String fileName) {
		Resource file = storageService.loadFile(fileName, "color");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
