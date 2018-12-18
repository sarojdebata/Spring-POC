package com.spring.fileHandling.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController 
{

	 	   @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		   public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException
	 	   {
		      File convertFile = new File(file.getOriginalFilename());
		      System.out.println("File name "+ convertFile);
		      convertFile.createNewFile();
		      FileOutputStream fout = new FileOutputStream(convertFile);
		      fout.write(file.getBytes());
		      fout.close();
		      return "File is upload successfully";
		   }
	 
	 	  @RequestMapping(value = "/download", method = RequestMethod.GET) 
	 	   public ResponseEntity<InputStreamResource> downloadFile() throws IOException  {
	 	      String filename = "D:\\myFile.txt";
	 	      File file = new File(filename);
	 	      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	 	      HttpHeaders headers = new HttpHeaders();
	 	      
	 	      headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
	 	      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	 	      headers.add("Pragma", "no-cache");
	 	      headers.add("Expires", "0");
	 	      
	 	      ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
	 	      
	 	      return responseEntity;
	 	   }
}
