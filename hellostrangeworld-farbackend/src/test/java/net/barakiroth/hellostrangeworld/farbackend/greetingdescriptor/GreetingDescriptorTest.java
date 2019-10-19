package net.barakiroth.hellostrangeworld.farbackend.greetingdescriptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetingDescriptorTest {
		
	private static final Logger enteringTestHeaderLogger =
			LoggerFactory.getLogger("EnteringTestHeader");
	
	@Test
	public void should_return_a_given_description_unconditionally() {
		
		enteringTestHeaderLogger.debug(null);
		
		final GreetingDescriptor greetingDescriptor = new GreetingDescriptor();
		assertThat(greetingDescriptor.describeGreetee(), anyOf(is("\"strange\""), is("\"very strange\"")));
		greetingDescriptor.disconnect();
	}
	
	@Test
	public void should_not_throw_upon_two_subsequent_calls_without_intermediate_disconnect() {
		
		enteringTestHeaderLogger.debug(null);
		
		final GreetingDescriptor greetingDescriptor = new GreetingDescriptor();
		assertDoesNotThrow(() -> greetingDescriptor.describeGreetee());
		assertDoesNotThrow(() -> greetingDescriptor.describeGreetee());
		greetingDescriptor.disconnect();
	}
}