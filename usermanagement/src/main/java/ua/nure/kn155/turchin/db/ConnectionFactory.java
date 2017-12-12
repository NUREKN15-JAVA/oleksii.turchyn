package ua.nure.kn155.turchin.db;

import java.sql.Connection;

public interface ConnectionFactory {
  Connection createConnection() throws DatabaseException;
}
