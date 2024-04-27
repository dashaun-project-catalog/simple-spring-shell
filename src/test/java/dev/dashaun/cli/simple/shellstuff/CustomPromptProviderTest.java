package dev.dashaun.cli.simple.shellstuff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.shell.test.autoconfigure.ShellTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@ShellTest
@ContextConfiguration(classes = { CustomPromptProvider.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomPromptProviderTest {

	@Autowired
	ShellTestClient client;

	@Test
	void promptShouldBeCustomized() {
		ShellTestClient.InteractiveShellSession session = client.interactive().run();

		await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(session.screen()).containsText(CustomPromptProvider.PROMPT);
		});
	}

}
