package dev.dashaun.cli.simple.shellstuff;

import org.jline.terminal.Terminal;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.context.InteractionMode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Command
public class GenerateCommand {

	public static final String GENERATE_COMMAND = "generate";

	public static final String GENERATE_INFO = "Generating file: ";

	public static final String DEFAULT_FILE_NAME = "simple-spring-shell.txt";

	private final Terminal terminal;

	public GenerateCommand(Terminal terminal) {
		this.terminal = terminal;
	}

	@Command(command = GENERATE_COMMAND, description = "Generates a file", interactionMode = InteractionMode.ALL)
	void generate(@Option(description = "file path to use for generation",
			defaultValue = DEFAULT_FILE_NAME) String newFilePath) {
		terminal.writer().println(GENERATE_INFO + newFilePath);
		terminal.writer().flush();
		try {
			File file = new File(newFilePath);
			FileWriter fileWriter = new FileWriter(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("This is a file created by a simple Spring Shell application.");
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
