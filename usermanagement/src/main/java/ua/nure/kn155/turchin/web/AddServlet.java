package ua.nure.kn155.turchin.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.db.DaoFactory;
import ua.nure.kn155.turchin.db.DatabaseException;


public class AddServlet extends EditServlet {
  @Override
  protected void processUser(User user) {
    try {
      DaoFactory.getInstance().getUserDao().create(user);
    } catch (DatabaseException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void showPage(HttpServletRequest req, HttpServletResponse resp) {
    try {
      req.getRequestDispatcher("/add.jsp").forward(req, resp);
      return;
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
  }
}
