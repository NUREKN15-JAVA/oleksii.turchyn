package ua.nure.kn155.turchin.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.db.ConnectionFactory;
import ua.nure.kn155.turchin.db.DatabaseException;
import ua.nure.kn155.turchin.db.UserDao;

public class MockUserDao implements UserDao {

  private Map<Long, User> users = new HashMap<>();
  private long id = 0;

  @Override
  public User create(User user) throws DatabaseException {
    Long currentId = new Long(++id);
    user.setId(currentId);
    users.put(currentId, user);
    return user;
  }

  @Override
  public void update(User user) throws DatabaseException {
    Long currentId = user.getId();
    users.remove(currentId);
    users.put(currentId, user);
  }

  @Override
  public void delete(User user) throws DatabaseException {
    Long currentId = user.getId();
    users.remove(currentId);
  }

  @Override
  public User find(Long id) throws DatabaseException {
    return users.get(id);
  }

  @Override
  public Collection<User> findAll() throws DatabaseException {
    return users.values();
  }

  @Override
  public void setConnectionFactory(ConnectionFactory connectionFactory) {}

  @Override
  public Collection<User> find(String firstName, String lastName) throws DatabaseException {
    throw new UnsupportedOperationException();
  }

}
