package com.example.synchrony.controller;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;




@RestController
public class ImageController {

	  // Replace this with your Imgur API key
	  private static final String IMGUR_API_KEY = "YOUR_IMGUR_API_KEY";

	  // This map will store the IDs of the uploaded images,
	  // mapped to the URLs where they can be accessed
	  private Map<String, String> uploadedImageUrls = new HashMap<>();
	  List<String> imageUrlList =new ArrayList<String>();

	  // This endpoint will handle image uploads
	  @PostMapping("/upload")
	  public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException, ParseException {
		  
	    // Create the HTTP client
	    HttpClient httpClient = HttpClient.newBuilder().build();

	    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
	    
	    // Create the upload directory if it doesn't already exist
	    File uploadDir = new File(file.getContentType());
	    if (!uploadDir.exists()) {
	      uploadDir.mkdir();
	    }
	    
	    // Save the uploaded image to the upload directory
	    File uploadedImage = new File(file.getContentType() + "/" + fileName);
	    try {
	      file.transferTo(uploadedImage);
	    } catch (IOException e) {
	      // Handle the exception
	    }
	    
	    // Create the request
	    HttpRequest request = HttpRequest.newBuilder()
	        .POST((BodyPublisher) uploadedImage)
	        .uri(URI.create("https://api.imgur.com/3/image"))
	        .setHeader("Authorization", "Client-ID " + IMGUR_API_KEY)
	        .build();

	    // Send the request and get the response
	    HttpResponse<String> response = httpClient.send(request,
	        BodyHandlers.ofString());

	    // Parse the JSON response
	    JSONParser parser=new JSONParser(response.body());
	    JSONObject jsonResponse = (JSONObject)parser.parse();

	    // Get the ID and URL of the uploaded image from the response
	   
	    String imageId = jsonResponse.getJSONObject("data").getString("id");
	    String imageUrl = jsonResponse.getJSONObject("data").getString("link");

	    // Add the image ID and URL to the map
	    uploadedImageUrls.put(imageId, imageUrl);
	    
	    for( Map.Entry<String, String> e:uploadedImageUrls.entrySet()) {
			String  val=e.getValue().toString();
			  imageUrlList.add(val);
		  }

	    // Return the URL of the uploaded image
	    return imageUrl;
	  }

	  // This end point will return a list of the uploaded images
	  @GetMapping("/images")
	  public List<String> getUploadedImages(){
		  
		  //Get list of uploaded images
		  return imageUrlList;
	  }
	  
	// This endpoint will handle image deletions
	  @DeleteMapping("/images/{imageId}")
	  public void deleteImage(@PathVariable String imageId) throws ParseException, IOException, InterruptedException {
	    // Create the HTTP client
	    HttpClient httpClient = HttpClient.newBuilder()
	        .build();

	    // Create the request
	    HttpRequest request = HttpRequest.newBuilder()
	        .DELETE()
	        .uri(URI.create("https://api.imgur.com/3/image/" + imageId))
	        .setHeader("Authorization", "Client-ID " + IMGUR_API_KEY)
	        .build();

	    // Send the request and get the response
	    HttpResponse<String> response = httpClient.send(request,
	        BodyHandlers.ofString());

	    // Parse the response
	    JSONParser json = new JSONParser(response.body());
	    JSONObject jsonResponse = (JSONObject)json.parse();
	    if (!jsonResponse.getBoolean("success")) {
	      throw new RuntimeException("Failed to delete image: " + jsonResponse.getJSONObject("data").getString("error"));
	    }
	  }

}

