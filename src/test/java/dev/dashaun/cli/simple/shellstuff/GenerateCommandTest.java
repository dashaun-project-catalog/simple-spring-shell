package dev.dashaun.cli.simple.shellstuff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.EnableCommand;
import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.shell.test.autoconfigure.ShellTest;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static dev.dashaun.cli.simple.shellstuff.GenerateCommand.*;
import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@ShellTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EnableCommand(GenerateCommand.class)
class GenerateCommandTest {

	@Autowired
	ShellTestClient client;

	@Test
	void interactiveTest() {
		ShellTestClient.InteractiveShellSession session = client.interactive().run();

		session.write(session.writeSequence().text(GENERATE_COMMAND).carriageReturn().build());
		await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(session.screen()).containsText(GENERATE_INFO);
		});
		assertFileExistsAndCleanup();
	}

	@Test
	void nonInteractiveTest() {
		ShellTestClient.NonInteractiveShellSession session = client.nonInterative(GENERATE_COMMAND).run();

		await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(session.screen()).containsText(GENERATE_INFO);
		});

		client.nonInterative(GENERATE_COMMAND).run();

		await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(session.screen()).containsText(GENERATE_INFO);
		});

		assertFileExistsAndCleanup();
	}

	void assertFileExistsAndCleanup() {
		File output = new File(DEFAULT_FILE_NAME);
		assertThat(output).exists().isFile().canRead().canWrite();
		boolean delete = output.delete();
		assertThat(delete).isTrue();
	}

}
