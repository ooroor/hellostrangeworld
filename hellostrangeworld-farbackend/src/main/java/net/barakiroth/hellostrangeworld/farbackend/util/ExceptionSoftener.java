package net.barakiroth.hellostrangeworld.farbackend.util;

/**
 * Ref.: https://medium.com/@johnmcclean/dysfunctional-programming-in-java-5-no-exceptions-5f37ac594323
 * Ref.: https://stackoverflow.com/questions/32682405/throwing-checked-exception-vs-throwing-a-wrapped-runtimeexception
 */
public class ExceptionSoftener {

  @SuppressWarnings("unchecked")
  public static <T extends Throwable> T uncheck(final Throwable throwable) throws T {
    throw (T) throwable;
  }
}

