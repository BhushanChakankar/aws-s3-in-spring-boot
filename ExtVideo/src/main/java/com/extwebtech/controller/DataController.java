package com.extwebtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.extwebtech.bean.ApiResponse;
import com.extwebtech.bean.Data;
import com.extwebtech.service.DataService;

@RestController
@RequestMapping("/data")
@CrossOrigin("*")
public class DataController {

	@Autowired
	DataService dataService;

	@PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ApiResponse uploadData(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
			@RequestParam("duration") double duration, @RequestParam("userId") int userId,
			@RequestParam("repeatCount") int repeatCount, @RequestParam("fileType") String fileType) {
		Data data = new Data();
		data.setTitle(title);
		data.setDuration(duration);
		data.setUserId(userId);
		data.setRepeatCount(repeatCount);
		data.setFileType(fileType);
		data.setIsAlive(true);
		return dataService.saveData(data, file);

	}

	@GetMapping("/{id}")
	public ApiResponse getDataById(@PathVariable("id") int id) {
		return dataService.getDataById(id);

	}

	@GetMapping("/all")
	public ApiResponse getAllData() {
		return dataService.getAllData();
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteDataById(@PathVariable("id") int id) {
		return dataService.deleteDataById(id);

	}

}
