package in.bushansirgur.ghbliapi.controller;

import in.bushansirgur.ghbliapi.dto.TextGenerationRequestDTO;
import in.bushansirgur.ghbliapi.service.GhibliArtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GenerationController {

	// Service Object
	private final GhibliArtService ghibliArtService;


//	 * Image + Prompt
	@PostMapping("/generate")
	public ResponseEntity<byte[]> generateImage(

			@RequestParam("image") MultipartFile image,

			@RequestParam("prompt") String prompt
	) {

		byte[] generatedImage = ghibliArtService.createGhibliArt(image, prompt);

		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(generatedImage);
	}

//	 * Text Prompt
	@PostMapping("/generate-from-text")
	public ResponseEntity<byte[]> generateFromText(

			@RequestBody TextGenerationRequestDTO request
	) {

		byte[] generatedImage =
				ghibliArtService.createGhibliArtFromText(
						request.getPrompt(),
						request.getStyle()
				);

		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(generatedImage);
	}
}