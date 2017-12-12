package ua.nure.kn155.turchin.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.web.EditServlet;

public class EditServletTest extends MockServletTestCase {

  private static final long ID = 1000L;
  private static final String LASTNAME = "Abrams";
  private static final String FIRSTNAME = "JJ";
  private final Date DATE = new Date();

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    createServlet(EditServlet.class);
  }

  @Test
  public void testEdit() {
    User user = new User(ID, FIRSTNAME, LASTNAME, DATE);
    getMockUserDao().expect("update", user);
    addRequestParameter("firstName", FIRSTNAME);
    addRequestParameter("lastName", LASTNAME);
    addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE));
    addRequestParameter("okButton", "Ok");
    doPost();
  }

  @Test
  public void testEditEmptyFirstName() {
    addRequestParameter("lastName", LASTNAME);
    addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE));
    addRequestParameter("okButton", "Ok");
    doPost();
    String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
    assertNotNull("The session didn't return an error message", errorMessage);
  }

  @Test
  public void testEditEmptyLastName() {
    addRequestParameter("firstName", FIRSTNAME);
    addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE));
    addRequestParameter("okButton", "Ok");
    doPost();
    String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
    assertNotNull("The session didn't return an error message", errorMessage);
  }

  @Test
  public void testEditEmptyDateOfBirth() {
    addRequestParameter("firstName", FIRSTNAME);
    addRequestParameter("lastName", LASTNAME);
    addRequestParameter("okButton", "Ok");
    doPost();
    String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
    assertNotNull("The session didn't return an error message", errorMessage);
  }

  @Test
  public void testEditInvalidDate() {
    addRequestParameter("firstName", FIRSTNAME);
    addRequestParameter("lastName", LASTNAME);
    addRequestParameter("dateOfBirth", "123");
    addRequestParameter("okButton", "Ok");
    doPost();
    String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
    assertNotNull("Could not find error message in session scope", errorMessage);
  }
}
