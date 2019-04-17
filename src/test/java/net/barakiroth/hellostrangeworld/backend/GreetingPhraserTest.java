package net.barakiroth.hellostrangeworld.backend;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GreetingPhraserTest {
	
	@Test
	public void should_return_a_given_phrase_unconditionally() {
		final GreetingPhraser greetingPhraser = new GreetingPhraser();
		assertThat(greetingPhraser.getGreetingPhrasePrefix(), is("Hello strange "));
	}
}
