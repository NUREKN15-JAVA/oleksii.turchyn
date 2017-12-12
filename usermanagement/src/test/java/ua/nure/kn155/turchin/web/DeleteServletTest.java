package ua.nure.kn155.turchin.web;

import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.web.DeleteServlet;

public class DeleteServletTest extends MockServletTestCase {

  private static final long ID = 1000L;
  private static final String LASTNAME = "Wick";
  private static final String FIRSTNAME = "Jhon";
  private final Date DATE = new Date();

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    createServlet(DeleteServlet.class);
  }

  @Test
  public void testDelete() {
    User user = new User(ID, FIRSTNAME, LASTNAME, DATE);
    getMockUserDao().expect("delete", user);
    addRequestParameter("okButton", "Ok");
    doPost();
  }
}
