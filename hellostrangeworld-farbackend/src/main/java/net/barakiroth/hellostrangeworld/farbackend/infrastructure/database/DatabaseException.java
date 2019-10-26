package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

public class DatabaseException  extends RuntimeException {

  private static final long serialVersionUID = 6369605649919724472L;

  DatabaseException(Exception cause) {
    super(cause);
  }
}