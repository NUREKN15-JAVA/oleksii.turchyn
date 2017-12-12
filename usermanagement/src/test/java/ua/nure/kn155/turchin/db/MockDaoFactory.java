package ua.nure.kn155.turchin.db;

import com.mockobjects.dynamic.Mock;

import ua.nure.kn155.turchin.db.DaoFactory;
import ua.nure.kn155.turchin.db.UserDao;

public class MockDaoFactory extends DaoFactory {

  private Mock mockUserDao;

  public MockDaoFactory() {
    mockUserDao = new Mock(UserDao.class);
  }

  @Override
  public UserDao getUserDao() {
    return (UserDao) mockUserDao.proxy();
  }

  public Mock getMockUserDao() {
    return mockUserDao;
  }
}
