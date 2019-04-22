package net.barakiroth.hellostrangeworld.backend;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class GreetingPhraserTest {
	
	@Test
	public void should_return_a_given_phrase_unconditionally() {
		final GreetingPhraser greetingPhraser = new GreetingPhraser();
		assertThat(greetingPhraser.getGreetingPhrasePrefix(), is("Hello strange "));
		greetingPhraser.disconnect();
	}
	
	@Test
	public void should_not_throw_upon_two_subsequent_calls_without_intermediate_disconnect() {
		final GreetingPhraser greetingPhraser = new GreetingPhraser();
		assertDoesNotThrow(() -> greetingPhraser.getGreetingPhrasePrefix());
		assertDoesNotThrow(() -> greetingPhraser.getGreetingPhrasePrefix());
		greetingPhraser.disconnect();
	}
}
