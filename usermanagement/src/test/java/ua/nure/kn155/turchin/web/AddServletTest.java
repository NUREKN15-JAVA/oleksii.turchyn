package ua.nure.kn155.turchin.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.web.AddServlet;

public class AddServletTest extends MockServletTestCase {

  private final String LASTNAME = "Duck";
  private final String FIRSTNAME = "Donald";
  private final Date DATE = new Date();

  private final User USER = new User(FIRSTNAME, LASTNAME, DATE);
  private final User ADDED_USER = new User(1000L, FIRSTNAME, LASTNAME, DATE);

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    createServlet(AddServlet.class);
  }
  
  @Test
  public void testAdd() {
    getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
    addRequestParameter("firstName", FIRSTNAME);
    addRequestParameter("lastName", LASTNAME);
    addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE));
    addRequestParameter("okButton", "Ok");
    doPost();
  }

  @Test
  public void testAddEmptyFirstName() {
    getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
    addRequestParameter("lastName", LASTNAME);
    addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
    addRequestParameter("okButton", "Ok");
    doPost();
    String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
    assertNotNull(errorMessage);
  }

  @Test
  public void testAddEmptyLastName() {
    getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
    addRequestParameter("firstName", FIRSTNAME);
    addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
    addRequestParameter("okButton", "Ok");
    doPost();
    String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
    assertNotNull(errorMessage);
  }


  @Test
  public void testAddEmptyDateOfBirth() {
    getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
    addRequestParameter("firstName", FIRSTNAME);
    addRequestParameter("lastName", LASTNAME);
    addRequestParameter("dateOfBirth", "");
    addRequestParameter("okButton", "Ok");
    doPost();
    String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
    assertNotNull(errorMessage);
  }

  @Test
  public void testAddInvalidDate() {
    getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
    addRequestParameter("firstName", FIRSTNAME);
    addRequestParameter("lastName", LASTNAME);
    addRequestParameter("dateOfBirth", "123");
    addRequestParameter("okButton", "Ok");
    doPost();
    String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
    assertNotNull(errorMessage);
  }
}
