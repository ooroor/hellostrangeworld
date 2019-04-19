package net.barakiroth.hellostrangeworld.farbackend;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class GreetingDescriptorTest {
	
	@Test
	public void should_return_a_given_decription_unconditionally() {
		final GreetingDescriptor greetingDescriptor = new GreetingDescriptor();
		assertThat(greetingDescriptor.describeGreetee(), is("strange"));
		greetingDescriptor.disconnect();
	}
	
	@Test
	public void should_not_throw_upon_two_subsequent_calls_without_intermediate_disconnect() {
		final GreetingDescriptor greetingDescriptor = new GreetingDescriptor();
		assertDoesNotThrow(() -> greetingDescriptor.describeGreetee());
		assertDoesNotThrow(() -> greetingDescriptor.describeGreetee());
		greetingDescriptor.disconnect();
	}
}