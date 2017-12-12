package ua.nure.kn155.turchin.db;

public class DatabaseException extends Exception {
  public DatabaseException(Throwable e) {
    super(e);
  }

  public DatabaseException(String string) {
    super(string);
  }

  public DatabaseException() {
    super();
  }
}
