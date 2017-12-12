package ua.nure.kn155.turchin.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.db.DaoFactory;
import ua.nure.kn155.turchin.db.DatabaseException;

public class EditServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.service(req, resp);
    if (req.getParameter("okButton") != null) {
      doOk(req, resp);
    } else if (req.getParameter("cancelButton") != null) {
      doCancel(req, resp);
    } else {
      showPage(req, resp);
    }
  }

  protected void showPage(HttpServletRequest req, HttpServletResponse resp) {
    try {
      req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }

  }

  private void doCancel(HttpServletRequest req, HttpServletResponse resp) {
    try {
      req.getRequestDispatcher("/browse.jsp").forward(req, resp);
    } catch (IOException | ServletException e) {
      e.printStackTrace();
    }

  }

  private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
    User user = null;
    try {
      user = getUser(req);
    } catch (Exception e1) {
      req.setAttribute("error", e1.getMessage());
      showPage(req, resp);
      return;
    }

    processUser(user);

    try {
      req.getRequestDispatcher("/browse").forward(req, resp);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
  }

  private User getUser(HttpServletRequest req) {
    User user = new User();
    String idStr = req.getParameter("id");
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String dateStr = req.getParameter("dateOfBirth");

    try {
      if (firstName == null)
        throw new ValidationException("Empty first name");
    } catch (ValidationException e1) {
      e1.printStackTrace();
    }
    try {
      if (lastName == null)
        throw new ValidationException("Empty last name");
    } catch (ValidationException e1) {
      e1.printStackTrace();
    }
    try {
      if (dateStr == null)
        throw new ValidationException("Empty date");
    } catch (ValidationException e1) {
      e1.printStackTrace();
    }
    if (idStr != null) {
      user.setId(new Long(idStr));
    }

    user.setFirstName(firstName);
    user.setLastName(lastName);
    try {
      user.setDateOfBirthd(DateFormat.getDateInstance().parse(dateStr));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return user;
  }

  protected void processUser(User user) {
    try {
      DaoFactory.getInstance().getUserDao().update(user);
    } catch (DatabaseException e) {
      e.printStackTrace();
      return;
    }

  }

}
