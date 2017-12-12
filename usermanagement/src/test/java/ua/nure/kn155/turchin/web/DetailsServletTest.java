package ua.nure.kn155.turchin.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

public class DetailsServletTest extends MockServletTestCase {

  private static final String LASTNAME = "Freeman";
  private static final String FIRSTNAME = "Mr.";
  private final Date DATE = new Date();

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    createServlet(DetailsServlet.class);
  }

  @Test
  public void testDetails() {
    addRequestParameter("id", "1000");
    addRequestParameter("firstName", FIRSTNAME);
    addRequestParameter("lastName", LASTNAME);
    addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE));
    addRequestParameter("okButton", "Ok");
    doPost();
  }
}
