package dev.dashaun.cli.simple.shellstuff;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
class CustomPromptProvider implements PromptProvider {

	public static final String PROMPT = "simple-spring-shell:>";

	@Override
	public AttributedString getPrompt() {
		return new AttributedString(PROMPT, AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
	}

}