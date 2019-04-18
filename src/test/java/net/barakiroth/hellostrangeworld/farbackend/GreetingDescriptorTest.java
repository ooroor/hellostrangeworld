package net.barakiroth.hellostrangeworld.farbackend;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.jupiter.api.Test;

public class GreetingDescriptorTest {
	
	@Test
	public void should_return_a_given_decription_unconditionally() {
		final GreetingDescriptor greetingDescriptor = new GreetingDescriptor();
		assertThat(greetingDescriptor.describeGreetee(), is("strange"));
	}
}