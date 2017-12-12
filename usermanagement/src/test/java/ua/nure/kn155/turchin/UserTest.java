package ua.nure.kn155.turchin;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import ua.nure.kn155.turchin.User;

public class UserTest extends TestCase {

  private User user;
  private Date dateOfBirthd;
  private Date futureDateOfBirthd;
  private final int DAY_OF_BIRTHD = 9;
  private final int YEAR_OF_BIRTHD = 1998;
  private final int CURRENT_YEAR = 2017;
  private final int FUTURE_YEAR = 2100;


  @Before
  public void setUp() throws Exception {
    super.setUp();
    user = new User();
    Calendar calendar = Calendar.getInstance();
    calendar.set(YEAR_OF_BIRTHD, Calendar.OCTOBER, DAY_OF_BIRTHD);
    dateOfBirthd = calendar.getTime();
    calendar.set(FUTURE_YEAR, Calendar.JANUARY, DAY_OF_BIRTHD);
    futureDateOfBirthd = calendar.getTime();
  }

  @Test
  public void testGetFullName() {
    user.setFirstName("Arthas");
    user.setLastName("Menethil");
    assertEquals("Menethil, Arthas", user.getFullName());
  }

  @Test
  public void testGetFullNameWithoutFirstName() {
    user.setFirstName("Arthas");
    try {
      user.getFullName();
      fail("IllegalStateException expecting");
    } catch (IllegalStateException e) {
      // TODO: handle exception
    }
  }

  @Test
  public void testGetFullNameWithoutSecondName() {
    user.setLastName("Menethil");
    try {
      user.getFullName();
      fail("IllegalStateException expecting");
    } catch (IllegalStateException e) {
    }
  }

  @Test
  public void testGetAge() {
    user.setDateOfBirthd(dateOfBirthd);
    assertEquals(CURRENT_YEAR - YEAR_OF_BIRTHD, user.getAge());
  }

  @Test
  public void testEmptyAge() {
    try {
      user.getAge();
      fail("IllegalStateException expecting");
    } catch (IllegalStateException e) {
    }
  }

  @Test
  public void testGetNonebornAge() {
    user.setDateOfBirthd(futureDateOfBirthd);
    try {
      user.getAge();
      fail("IllegalStateException expecting");
    } catch (IllegalStateException e) {
    }
  }
}
