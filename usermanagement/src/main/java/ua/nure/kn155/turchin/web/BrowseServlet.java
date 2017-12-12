package ua.nure.kn155.turchin.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.db.DaoFactory;
import ua.nure.kn155.turchin.db.DatabaseException;

public class BrowseServlet extends HttpServlet {

  @Override
  public void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getParameter("addButton") != null) {
      add(req, resp);
    } else if (req.getParameter("editButton") != null) {
      edit(req, resp);
    } else if (req.getParameter("deleteButton") != null) {
      delete(req, resp);
    } else if (req.getParameter("detailsButton") != null) {
      details(req, resp);
    }
    browse(req, resp);
  }

  private void details(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) {
      req.setAttribute("error", "You must select a user");
      req.getRequestDispatcher("/browse.jsp").forward(req, resp);
    }
    try {
      User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
      req.getSession().setAttribute("user", user);
    } catch (Exception e) {
      req.setAttribute("error", "ERROR: " + e.toString());
      req.getRequestDispatcher("/browse.jsp");
    }
    req.getRequestDispatcher("/details").forward(req, resp);
  }

  private void delete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) {
      req.setAttribute("error", "You must select a user for deleting");
      req.getRequestDispatcher("/browse.jsp").forward(req, resp);
    }
    try {
      User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
      req.getSession().setAttribute("user", user);
    } catch (Exception e) {
      req.setAttribute("error", "ERROR: " + e.toString());
      req.getRequestDispatcher("/browse.jsp");
      return;
    }
    req.getRequestDispatcher("/delete").forward(req, resp);
  }

  private void edit(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) {
      req.setAttribute("error", "You must select a user for editting");
      req.getRequestDispatcher("/browse.jsp").forward(req, resp);
    }
    try {
      User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
      req.getSession().setAttribute("user", user);
    } catch (Exception e) {
      req.setAttribute("error", "ERROR: " + e.toString());
      req.getRequestDispatcher("/browse.jsp").forward(req, resp);
      return;
    }
    req.getRequestDispatcher("/edit").forward(req, resp);
  }

  private void add(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getSession().setAttribute("user", null);
    req.getRequestDispatcher("/add").forward(req, resp);
  }

  private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
    Collection<User> users = null;
    try {
      users = DaoFactory.getInstance().getUserDao().findAll();
      req.getSession().setAttribute("users", users);
      req.getRequestDispatcher("/browse.jsp").forward(req, resp);
    } catch (DatabaseException | IOException e) {
      throw new ServletException(e);
    }

  }

}
