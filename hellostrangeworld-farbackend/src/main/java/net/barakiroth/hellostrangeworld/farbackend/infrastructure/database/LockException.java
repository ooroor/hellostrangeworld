package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

public class LockException extends RuntimeException {

  private static final long serialVersionUID = 5132755141860422391L;

  public LockException(final String message) {
    super(message);
  }
}
