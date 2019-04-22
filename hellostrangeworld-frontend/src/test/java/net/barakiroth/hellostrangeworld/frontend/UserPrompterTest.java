package net.barakiroth.hellostrangeworld.frontend;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

public class UserPrompterTest {
	
	@Test
	public void should_return_input_when_standard_in_receives_input() {
		
		final String expectedGreetee = "universe";
		final ByteArrayInputStream in = new ByteArrayInputStream(expectedGreetee.getBytes());
		System.setIn(in);
		final UserPrompter userPrompter = new UserPrompter();
		final String actualGreetee = userPrompter.askWhomTheUserWantsToGreet();
		assertThat(actualGreetee, is(expectedGreetee));
	}
}