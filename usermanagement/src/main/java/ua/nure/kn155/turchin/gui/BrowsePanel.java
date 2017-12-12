package ua.nure.kn155.turchin.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.db.DatabaseException;
import ua.nure.kn155.turchin.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {

  MainFrame parentFrame;
  private JPanel buttonPanel;
  private JScrollPane tablePanel;
  private JButton detailsButton;
  private JButton deleteButton;
  private JButton editButton;
  private JButton addButton;
  private JTable userTable;
  private UserTableModel model = null;
  private final String message = "Are you sure you want delete this user ?";

  public BrowsePanel(MainFrame frame) {
    parentFrame = frame;
    initialize();
  }

  private void initialize() {
    this.setName("browsePanel");
    this.setLayout(new BorderLayout());
    this.add(getTablePanel(), BorderLayout.CENTER);
    this.add(getButtonPanel(), BorderLayout.SOUTH);
  }

  private JButton getDetailsButton() {
    if (detailsButton == null) {
      detailsButton = new JButton();
      detailsButton.setText(Messages.getString("user_management.BrowsePanel1"));
      detailsButton.setName("detailsButton");
      detailsButton.addActionListener(this);
      detailsButton.setActionCommand("details");
    }
    return detailsButton;
  }

  private JButton getDeleteButton() {
    if (deleteButton == null) {
      deleteButton = new JButton();
      deleteButton.setName("deleteButton");
      deleteButton.setText(Messages.getString("user_management.BrowsePanel2"));
      deleteButton.addActionListener(this);
      deleteButton.setActionCommand("delete");
    }
    return deleteButton;
  }

  private JButton getEditButton() {
    if (editButton == null) {
      editButton = new JButton();
      editButton.setName("editButton");
      editButton.setText(Messages.getString("user_management.BrowsePanel3"));
      editButton.addActionListener(this);
      editButton.setActionCommand("edit");
    }
    return editButton;
  }

  private JButton getAddButton() {
    if (addButton == null) {
      addButton = new JButton();
      addButton.setText(Messages.getString("user_management.BrowsePanel4"));
      addButton.setName("addButton");
      addButton.setActionCommand("add");
      addButton.addActionListener(this);
    }
    return addButton;
  }

  private JPanel getButtonPanel() {
    if (buttonPanel == null) {
      buttonPanel = new JPanel();
      buttonPanel.add(getAddButton(), null);
      buttonPanel.add(getEditButton(), null);
      buttonPanel.add(getDeleteButton(), null);
      buttonPanel.add(getDetailsButton(), null);
    }
    return buttonPanel;
  }

  private JScrollPane getTablePanel() {
    if (tablePanel == null) {
      tablePanel = new JScrollPane(getUserTable());
    }
    return tablePanel;
  }

  private JTable getUserTable() {
    if (userTable == null) {
      userTable = new JTable();
      userTable.setName("userTable");
      // userTable.setModel(new UserTableModel(new ArrayList<>()));
    }
    return userTable;
  }

  public void initTable() {
    try {
      model = new UserTableModel(parentFrame.getDao().findAll());
    } catch (Exception e) {
      model = new UserTableModel(new ArrayList<User>());
      JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    getUserTable().setModel(model);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if ("add".equalsIgnoreCase(e.getActionCommand())) { 
      this.setVisible(false);
      parentFrame.showAddPanel();
    }
    if ("edit".equalsIgnoreCase(e.getActionCommand())) { 
      this.setVisible(false);
      parentFrame.showEditPanel();
    }
    if ("delete".equalsIgnoreCase(e.getActionCommand())) { 

      int result = JOptionPane.showConfirmDialog(parentFrame, message, "Delete confirm",
          JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
      if (result == JOptionPane.YES_OPTION) {
        try {
          parentFrame.getDao().delete(getSelectedUser());

          getUserTable().setModel(new UserTableModel(parentFrame.getDao().findAll()));
        } catch (DatabaseException e1) {
          JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    if ("details".equalsIgnoreCase(e.getActionCommand())) {
      this.setVisible(false);
      parentFrame.showDetailsPanel();
    }
  }

  /**
   * Find selected user in the userTable
   * 
   * @return user from UserTable
   */
  public User getSelectedUser() {
    int selectedRow = getUserTable().getSelectedRow();
    if (selectedRow == -1)
      return null;
    try {
      User user = parentFrame.getDao().find((Long) getUserTable().getValueAt(selectedRow, 0));
      return user;
    } catch (DatabaseException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return null;
  }

}
