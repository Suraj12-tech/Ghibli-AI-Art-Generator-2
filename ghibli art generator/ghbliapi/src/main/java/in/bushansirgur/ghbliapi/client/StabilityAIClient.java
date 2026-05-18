package in.bushansirgur.ghbliapi.client;

import in.bushansirgur.ghbliapi.config.FeignConfig;
import in.bushansirgur.ghbliapi.dto.TextToImageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;

@FeignClient(name = "stabilityAiClient", url = "${stability.api.base-url}", configuration = FeignConfig.class)
public interface StabilityAIClient {

	@PostMapping(value = "/v1/generation/{engine_id}/text-to-image", consumes = "application/json", headers = "Accept=image/png")
	byte[] generateImageFromText(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable("engine_id") String engineId,
			@RequestBody TextToImageRequest requestBody);

	@PostMapping(value = "/v1/generation/{engine_id}/image-to-image", consumes = "multipart/form-data", headers = "Accept=image/png")
	byte[] generateImageFromImage(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable("engine_id") String engineId,
			@RequestPart("init_image") byte[] initImage,
			@RequestPart("text_prompts[0][text]") String textPrompt,
			@RequestPart("style_preset") String stylePreset);
}
