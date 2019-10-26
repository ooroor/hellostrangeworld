package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

public class UnknownDatabaseBrandException extends RuntimeException {
  
  private static final long serialVersionUID = 7876621158574579617L;
  
  final String dbBrand;
  
  public UnknownDatabaseBrandException(final String dbBrand) {
    this.dbBrand = dbBrand;
  }
  
  @Override
  public String getMessage() {
    return "Database unknownb: " +  this.dbBrand;
  }
}
