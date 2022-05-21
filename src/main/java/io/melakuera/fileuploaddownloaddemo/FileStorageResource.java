package io.melakuera.fileuploaddownloaddemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/files")
public class FileStorageResource {
	
	private final FileStorageService fileStorageService;

	@Autowired
	public FileStorageResource(FileStorageService fileStorageService) {
		super();
		this.fileStorageService = fileStorageService;
	}
	
	@PostMapping("/upload")
	ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		return fileStorageService.saveFile(file);
	}
	
	@GetMapping
	ResponseEntity<?> getFiles() {
		return fileStorageService.loadAll();
	}
	
	@GetMapping("/{fileName}")
	ResponseEntity<?> getFile(@PathVariable String fileName) {
		return fileStorageService.loadFile(fileName);
	}
	
	
}
