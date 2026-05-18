package in.bushansirgur.ghbliapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TextToImageRequest {

	@JsonProperty("text_prompts")
	private List<TextPrompt> textPrompts;

	@JsonProperty("cfg_scale")
	private double cfgScale = 7.0;

	private int height = 1024;
	private int width = 1024;
	private int samples = 1;
	private int steps = 30;

	@JsonProperty("style_preset")
	private String stylePreset;

	public TextToImageRequest(String prompt, String stylePreset) {
		this.textPrompts = List.of(new TextPrompt(prompt));
		this.stylePreset = stylePreset;
	}

	@Data
	@NoArgsConstructor
	public static class TextPrompt {

		private String text;

		public TextPrompt(String text) {
			this.text = text;
		}
	}
}
