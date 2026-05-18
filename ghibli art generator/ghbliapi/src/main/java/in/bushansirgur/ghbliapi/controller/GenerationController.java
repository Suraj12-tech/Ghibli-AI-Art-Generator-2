package in.bushansirgur.ghbliapi.controller;

import in.bushansirgur.ghbliapi.dto.TextGenerationRequestDTO;
import in.bushansirgur.ghbliapi.service.GhibliArtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RequiredArgsConstructor
public class GenerationController {

	private static final Logger logger = LoggerFactory.getLogger(GenerationController.class);

	private final GhibliArtService ghibliArtService;

	@PostMapping(value = "/generate", produces = "image/png")
	public ResponseEntity<byte[]> generateGhibliArt(@RequestParam("image") MultipartFile image,
			@RequestParam("prompt") String prompt) {
		try {
			if (image.isEmpty()) {
				logger.warn("Image file is empty");
				return ResponseEntity.badRequest().body(null);
			}

			logger.info("Generating Ghibli art from image with prompt: {}", prompt);
			byte[] imageBytes = ghibliArtService.createGhibliArt(image, prompt);
			return imageResponse(imageBytes);
		} catch (Exception ex) {
			logger.error("Error while generating Ghibli art from image", ex);
			return ResponseEntity.internalServerError().body(null);
		}
	}

	@PostMapping(value = "/generate-from-text", produces = "image/png")
	public ResponseEntity<byte[]> generateGhibliArtFromText(@RequestBody TextGenerationRequestDTO requestDTO) {
		try {
			if (requestDTO.getPrompt() == null || requestDTO.getPrompt().isEmpty()) {
				logger.warn("Prompt is empty");
				return ResponseEntity.badRequest().body(null);
			}

			logger.info("Generating Ghibli art from text with prompt: {}", requestDTO.getPrompt());
			byte[] imageBytes = ghibliArtService.createGhibliArtFromText(requestDTO.getPrompt(), requestDTO.getStyle());
			return imageResponse(imageBytes);
		} catch (Exception ex) {
			logger.error("Error while generating Ghibli art from text", ex);
			return ResponseEntity.internalServerError().body(null);
		}
	}

	private ResponseEntity<byte[]> imageResponse(byte[] imageBytes) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ghibli.png")
				.contentType(MediaType.IMAGE_PNG)
				.body(imageBytes);
	}
}
