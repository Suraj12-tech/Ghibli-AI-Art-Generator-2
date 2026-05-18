package in.bushansirgur.ghbliapi.service;

import in.bushansirgur.ghbliapi.client.StabilityAIClient;
import in.bushansirgur.ghbliapi.dto.TextToImageRequest;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GhibliArtService {

	private final StabilityAIClient stabilityAIClient;

	@Value("${stability.api.key}")
	private String apiKey;

	public byte[] createGhibliArt(MultipartFile image, String prompt) {
		try {
			String finalPrompt = prompt + ", in the beautiful anime style of studio ghibli.";
			String engineId = "stable-diffusion-xl-1024-v1-0";
			String stylePreset = "anime";

			BufferedImage originalImage = ImageIO.read(image.getInputStream());
			BufferedImage resizedImage = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = resizedImage.createGraphics();
			graphics.drawImage(originalImage, 0, 0, 1024, 1024, null);
			graphics.dispose();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(resizedImage, "png", outputStream);
			byte[] imageBytes = outputStream.toByteArray();

			return stabilityAIClient.generateImageFromImage(
					"Bearer " + apiKey,
					engineId,
					imageBytes,
					finalPrompt,
					stylePreset);
		} catch (Exception ex) {
			throw new RuntimeException("Image processing failed", ex);
		}
	}

	public byte[] createGhibliArtFromText(String prompt, String style) {
		String finalPrompt = prompt + ", in the beautiful anime style of studio ghibli.";
		String engineId = "stable-diffusion-xl-1024-v1-0";
		String stylePreset = style == null || "general".equals(style) ? "anime" : style.replace("_", "-");
		TextToImageRequest requestPayload = new TextToImageRequest(finalPrompt, stylePreset);

		return stabilityAIClient.generateImageFromText("Bearer " + apiKey, engineId, requestPayload);
	}
}
