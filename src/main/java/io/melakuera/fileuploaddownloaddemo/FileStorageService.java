package io.melakuera.fileuploaddownloaddemo;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

	private final Path root = Paths.get("uploads");

	@PostConstruct
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			throw new RuntimeException("Не удалось создать директорию. Ошибка: " + e.getMessage());
		}
	}

	public ResponseEntity<?> saveFile(MultipartFile file) {
		try {
			String fileCode = RandomStringUtils.random(10, true, true);
//			Files.copy(
//					file.getInputStream(),
//					root.resolve(fileCode+"-"+file.getOriginalFilename()),
//					StandardCopyOption.REPLACE_EXISTING
//			);
			file.transferTo(root.resolve(fileCode+"-"+file.getOriginalFilename()));

			Attachment attachment = new Attachment();
			attachment.setFileName(file.getOriginalFilename());
			attachment.setUrl(MvcUriComponentsBuilder.fromMethodName(
					FileStorageResource.class,
					"getFile", file.getOriginalFilename()).build().toString());

			return ResponseEntity.status(HttpStatus.CREATED).body(attachment);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body("Не удалось сохранить файлы. Ошибка: " + e.getMessage());
		}
	}

	public ResponseEntity<?> loadFile(String fileName) {
		try {
			Path file = root.resolve(fileName);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment: filename=\"" + resource.getFilename() + "\"").body(resource);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не удалось прочесть файл!");
			}
		} catch (MalformedURLException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: " + e.getMessage());
		}
	}

	public ResponseEntity<?> loadAll() {
		try {
			List<Attachment> files = Files.walk(root, 1).filter(path -> !path.equals(root))
					.map(root::relativize).map(path -> {

						String fileName = path.getFileName().toString() ;
						String downloadUrl = MvcUriComponentsBuilder.fromMethodName(
								FileStorageResource.class, "getFile", path.getFileName().toString()).build().toString();

						return new Attachment(fileName, downloadUrl);

					}).collect(Collectors.toList());

			return ResponseEntity.ok().body(files);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Не удалось прочесть все файлы");
		}
	}

	@PreDestroy
	public void destroy() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}
}
