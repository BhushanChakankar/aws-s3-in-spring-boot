package com.extwebtech.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

/**
 * Service class for handling file-related operations using AWS S3.
 */
@Service
public class AWSS3Service implements FileService {

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${AWS_BUCKET}")
	private String bucketName;

	@Value("${aws.folder.name}")
	private String folderName;

	/**
	 * Uploads a single file to AWS S3.
	 *
	 * @param file The file to be uploaded
	 * @return The public URL of the uploaded file
	 */
	@Override
	public String uploadFile(MultipartFile file) {
		if (file != null) {
			String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
			String key = folderName + "/" + UUID.randomUUID().toString() + "." + filenameExtension;
			ObjectMetadata metaData = new ObjectMetadata();
			metaData.setContentLength(file.getSize());
			metaData.setContentType(file.getContentType());
			try {
				amazonS3.putObject(bucketName, key, file.getInputStream(), metaData);
			} catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"An exception occurred while uploading the file");
			}
			String publicUrl = amazonS3.getUrl(bucketName, key).toString();
			return publicUrl;
		} else {
			return null;
		}
	}

	/**
	 * Deletes an image from AWS S3 based on the image path.
	 *
	 * @param imagePath The path of the image to be deleted
	 */
	public void deleteImage(String imagePath) {
		try {
			// Extract the key (object name) from the image path
			String key = extractKeyFromImagePath(imagePath);
			// Delete the object from S3 bucket
			amazonS3.deleteObject(bucketName, key);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error occured while deleting image");
		}
	}

	/**
	 * Extracts the key (object name) from the image path.
	 * 
	 * @param imagePath The image path from which to extract the key
	 * @return The key (object name) extracted from the image path
	 */
	private String extractKeyFromImagePath(String imagePath) {
		return imagePath.substring(imagePath.lastIndexOf('/') + 1);
	}

}
