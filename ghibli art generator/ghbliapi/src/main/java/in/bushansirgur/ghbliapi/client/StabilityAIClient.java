package in.bushansirgur.ghbliapi.client;

import in.bushansirgur.ghbliapi.config.FeignConfig;
import in.bushansirgur.ghbliapi.dto.TextToImageRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;



@FeignClient(
		name = "stabilityAiClient",
		url = "${stability.api.base-url}",
		configuration = FeignConfig.class
)
public interface StabilityAIClient {


//	 * Text Prompt se Image Generate Karna


	@PostMapping(
			value = "/v1/generation/{engine_id}/text-to-image",
			consumes = "application/json",
			headers = "Accept=image/png"
	)
	byte[] generateImageFromText(

			// API Key
			@RequestHeader("Authorization")
			String authorization,

			// Engine Name
			@PathVariable("engine_id")
			String engineId,

			// Request Body
			@RequestBody
			TextToImageRequest request
	);




//	 * Image + Prompt se New Image Generate Karna


	@PostMapping(
			value = "/v1/generation/{engine_id}/image-to-image",
			consumes = "multipart/form-data",
			headers = "Accept=image/png"
	)
	byte[] generateImageFromImage(

			// API Key
			@RequestHeader("Authorization")
			String authorization,


			@PathVariable("engine_id")
			String engineId,


			@RequestPart("init_image")
			byte[] image,


			@RequestPart("text_prompts[0][text]")
			String prompt,


			@RequestPart("style_preset")
			String style
	);
}