package ua.nure.kn155.turchin.db;

import java.util.Collection;

import ua.nure.kn155.turchin.User;

public interface UserDao {
  /**
   * 
   * Add {@link ua.nure.kn155.turchin.User} entity into DB
   * 
   * @param user all field of user must be field with exception of id, id must be null
   * @return {@link ua.nure.kn155.turchin.User} entity with populated primary key
   * @throws DatabaseExeption
   */
  public User create(User user) throws DatabaseException;

  /**
   * Update {@link ua.nure.kn155.turchin.User} entity in DB
   * 
   * @param {@link ua.nure.kn155.turchin.User} entity to be updated in DB
   * @throws DatabaseException
   */
  public void update(User user) throws DatabaseException;

  /**
   * Delete {@link ua.nure.kn155.turchin.User} entity from DB
   * 
   * @param user must be present in DB
   * @throws DatabaseException
   */

  public void delete(User user) throws DatabaseException;

  /**
   * Find an {@link ua.nure.kn155.turchin.User} entity in DB by id
   * 
   * @param id must be exist in db
   * @return an {@link ua.nure.kn155.turchin.User} entity from DB, or null if it doesn't exist
   * @throws DatabaseException
   */
  public User find(Long id) throws DatabaseException;

  /**
   * Find all {@link ua.nure.kn155.turchin.User} entities in DB
   * 
   * @return a collection containing objects User with copies all users from db otherwise return
   *         empty list
   * @throws DatabaseException
   */
  public Collection<User> findAll() throws DatabaseException;

  public void setConnectionFactory(ConnectionFactory connectionFactory);

  public Collection<User> find(String firstName, String lastName) throws DatabaseException;
}
