package com.extwebtech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.extwebtech.bean.ApiResponse;
import com.extwebtech.bean.Data;
import com.extwebtech.repository.DataRepository;
import com.extwebtech.util.AWSS3Service;

@Service
public class DataService {

	@Autowired
	DataRepository dataRepository;

	@Autowired
	private AWSS3Service awsS3Service;

	public ApiResponse saveData(Data data, MultipartFile file) {
		try {
			String videoUrl = awsS3Service.uploadFile(file);
			data.setVideoUrl(videoUrl);
			Data savedData = dataRepository.save(data);
			ApiResponse response = new ApiResponse();
			response.setStatus(true);
			response.setStatusCode("200");
			response.setMessage("Data saved successfully");
			response.setData(savedData);
			return response;
		} catch (Exception e) {
			ApiResponse response = new ApiResponse();
			response.setStatus(false);
			response.setStatusCode("500");
			response.setMessage("Failed to save data: " + e.getMessage());
			return response;
		}
	}

	public ApiResponse getDataById(int id) {
		Optional<Data> optionalData = dataRepository.findById(id);
		if (optionalData.isPresent()) {
			ApiResponse response = new ApiResponse();
			response.setStatus(true);
			response.setStatusCode("200");
			response.setMessage("Data found");
			response.setData(optionalData.get());
			return response;
		} else {
			ApiResponse response = new ApiResponse();
			response.setStatus(false);
			response.setStatusCode("404");
			response.setMessage("Data not found");
			return response;
		}
	}

	public ApiResponse getAllData() {
		List<Data> dataList = dataRepository.findAll();
		ApiResponse response = new ApiResponse();
		response.setStatus(true);
		response.setStatusCode("200");
		response.setMessage("List of all data");
		response.setData(dataList);
		return response;
	}

	public ApiResponse deleteDataById(int id) {
		try {
			dataRepository.deleteById(id);
			ApiResponse response = new ApiResponse();
			response.setStatus(true);
			response.setStatusCode("200");
			response.setMessage("Data deleted successfully");
			return response;
		} catch (Exception e) {
			ApiResponse response = new ApiResponse();
			response.setStatus(false);
			response.setStatusCode("500");
			response.setMessage("Failed to delete data: " + e.getMessage());
			return response;
		}
	}

}
