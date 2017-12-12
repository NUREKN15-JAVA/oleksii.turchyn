package ua.nure.kn155.turchin.db;

import static org.junit.Assert.fail;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn155.turchin.User;

class HsqldbUserDao implements UserDao {

  private static final String INSERT_QUERY =
      "INSERT INTO users (firstname,lastname,dateofbirth) VALUES (?,?,?)";
  private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
  private static final String UPDATE_QUERY =
      "UPDATE users SET firstname=?,lastname=?,dateofbirth=? WHERE id=?";
  private static final String FIND_QUERY = "SELECT * FROM users WHERE id = ?";
  private static final String FIND_ALL = "SELECT * FROM users";
  private static final String FIND_NAME_LASTNAME = "SELECT * FROM users WHERE users.firstname = ? AND lastname = ?";

  private ConnectionFactory connectionFactory;

  public HsqldbUserDao() {}

  public ConnectionFactory getConnectionFactory() {
    return connectionFactory;
  }

  public void setConnectionFactory(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  public HsqldbUserDao(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
  public User create(User user) throws DatabaseException {
    try {
      Connection connection = connectionFactory.createConnection();
      User resultUser = new User();
      PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
      statement.setString(1, user.getFirstName());
      statement.setString(2, user.getLastName());
      statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
      int n = statement.executeUpdate();
      if (n != 1) {
        throw new DatabaseException("Wrong number of inserted rows: " + n);
      }
      CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
      ResultSet keys = callableStatement.executeQuery();
      if (keys.next()) {
        resultUser = new User(user);
        resultUser.setId(new Long(keys.getLong(1)));
      }
      keys.close();
      callableStatement.close();
      statement.close();
      connection.close();
      return resultUser;
    } catch (DatabaseException e) {
      throw e;
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }

  }

  @Override
  public void update(User user) throws DatabaseException {
    try {
      Connection connection = connectionFactory.createConnection();
      PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
      statement.setString(1, user.getFirstName());
      statement.setString(2, user.getLastName());
      statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
      statement.setLong(4, user.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(User user) throws DatabaseException {
    try {
      Connection connection = connectionFactory.createConnection();
      PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, user.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public User find(Long id) throws DatabaseException {
    try {
      Connection connection = connectionFactory.createConnection();
      PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        if (resultSet.getLong(1) == id) {
          User user = new User();
          user.setId(resultSet.getLong(1));
          user.setFirstName(resultSet.getString(2));
          user.setLastName(resultSet.getString(3));
          user.setDateOfBirthd(resultSet.getDate(4));
          return user;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      fail("User with id was not find");
    }
    return null;
  }

  @Override
  public Collection<User> findAll() throws DatabaseException {
    Collection<User> result = new LinkedList<User>();
    try {
      Connection connection = connectionFactory.createConnection();
      ResultSet resultSet = connection.createStatement().executeQuery(FIND_ALL);
      while (resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setFirstName(resultSet.getString(2));
        user.setLastName(resultSet.getString(3));
        user.setDateOfBirthd(resultSet.getDate(4));
        result.add(user);
      }
    } catch (DatabaseException e) {
      throw e;
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }

    return result;
  }

  @Override
  public Collection<User> find(String firstName, String lastName) {
    Collection<User> users = new LinkedList<>();
    try {
      Connection connection = connectionFactory.createConnection();
      PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
      statement.setString(1, firstName);
      statement.setString(2, lastName);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
          User user = new User();
          user.setId(resultSet.getLong(1));
          user.setFirstName(resultSet.getString(2));
          user.setLastName(resultSet.getString(3));
          user.setDateOfBirthd(resultSet.getDate(4));
          users.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      fail("User with id was not find");
    } catch (DatabaseException e) {
      e.printStackTrace();
    }
    return users;
  }

}
