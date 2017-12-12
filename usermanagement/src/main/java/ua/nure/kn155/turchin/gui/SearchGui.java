package ua.nure.kn155.turchin.gui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.agent.SearchAgent;
import ua.nure.kn155.turchin.agent.SearchException;

/**
 * Search window, connected to an agent. Displays the results of search with the
 * help of agent.
 * 
 * @author mak
 */
@SuppressWarnings("serial")
public class SearchGui extends JPanel implements ActionListener {

	private SearchAgent agent;

	private JPanel tablePanel;

	private JTable table;

	private MainFrame mainFrame;

	private JPanel detailsPanel;

	private JButton detailsButton;

	private JButton backButton;

	private JLabel agentLabel = new JLabel();

	/**
	 * @param agent Agent that will be used
	 * @param mainFrame Parent frame, where search window will be shown
	 */
	public SearchGui(SearchAgent agent, MainFrame mainFrame) {
		this.agent = agent;
		this.mainFrame = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getSearchPanel(), BorderLayout.NORTH);
		this.add(new JScrollPane(getTablePanel()), BorderLayout.CENTER);
		this.add(getDetailsPanel(), BorderLayout.SOUTH);
	}

	private JPanel getDetailsPanel() {
		if (detailsPanel == null) {
			detailsPanel = new JPanel();
			detailsPanel.add(getBackButton());
			detailsPanel.add(getDetailsButton());
			detailsPanel.add(agentLabel);
		}
		return detailsPanel;
	}

	private JButton getDetailsButton() {
		if (detailsButton == null) {
			detailsButton = new JButton();
			detailsButton.setActionCommand("details");
			detailsButton.setName("detailsButton");
			detailsButton.setText("Details");
			detailsButton.addActionListener(this);
		}
		return detailsButton;
	}

	private JButton getBackButton() {
		if (backButton == null) {
			backButton = new JButton();
			backButton.setText("Back"); //$NON-NLS-1$
			backButton.setName("backButton"); //$NON-NLS-1$
			backButton.setActionCommand("back"); //$NON-NLS-1$
			backButton.addActionListener(this);
		}
		return backButton;
	}

	private JPanel getTablePanel() {
		if (tablePanel == null) {
			tablePanel = new JPanel(new BorderLayout());
			tablePanel.add(getTable(), BorderLayout.CENTER);
		}
		return tablePanel;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable(new UserTableModel(new LinkedList<User>()));
		}
		return table;
	}

	private JPanel getSearchPanel() {
		return new SearchPanel(agent);
	}

	/**
	 * Adds {@code users} to the table. 
	 * @param users Collection of users to be added.
	 */
	public void addUsers(Collection<User> users) {
		System.out.println("addUsers : " + users);
		UserTableModel model = (UserTableModel) getTable().getModel();
		model.addUsers(users);
		this.repaint();
	}

	private void clearUsers() {
		System.out.println("clearUsers : ");
		UserTableModel model = (UserTableModel) getTable().getModel();
		model.clearUsers();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("details".equals(e.getActionCommand())) {
			User userToShow = getSelectedUser();
			if (userToShow != null) {
				mainFrame.showDetailsPanel(userToShow, "searchPanel");
			}
		} else if ("back".equals(e.getActionCommand())) {
			mainFrame.showBrowsPanel();
		}
	}

	private User getSelectedUser() {
		int row = getTable().getSelectedRow();
		if (row == -1) {
			return null;
		}
		return ((UserTableModel) getTable().getModel()).getUser(row);
	}
	/**
	 * Sets the name of the agent that found users.
	 * Created only for informative purposes.
	 * @param agentName
	 */
	public void setAgent(String agentName) {
		System.out.println("ping");
		System.out.println(agentName);
		agentLabel.setText("Users found for you by" + agentName);
	}

	public static void main(String[] args) {
		SearchGui gui = new SearchGui(null, null);
		gui.setVisible(true);
	}

	/**
	 * Panel with textfields for user's credential and button to start search.
	 * @author mak
	 */
	class SearchPanel extends JPanel implements ActionListener {

		SearchAgent agent;

		private JPanel fieldPanel;

		private JButton searchButton;

		private JTextField firstNameField;

		private JTextField lastNameField;

		public SearchPanel(SearchAgent agent) {
			this.agent = agent;
			initialize();
		}

		private void initialize() {
			this.setName("addPanel"); //$NON-NLS-1$
			this.setLayout(new BorderLayout());
			this.add(getFieldPanel(), BorderLayout.NORTH);

		}

		private JButton getSearchButton() {
			if (searchButton == null) {
				searchButton = new JButton();
				searchButton.setText("Search"); //$NON-NLS-1$
				searchButton.setName("okButton"); //$NON-NLS-1$
				searchButton.setActionCommand("ok"); //$NON-NLS-1$
				searchButton.addActionListener(this);
			}
			return searchButton;
		}

		private JPanel getFieldPanel() {
			if (fieldPanel == null) {
				fieldPanel = new JPanel();
				fieldPanel.setLayout(new GridLayout(2, 3));
				addLabeledField(fieldPanel, "FirstName", getFirstNameField()); //$NON-NLS-1$
				fieldPanel.add(getSearchButton());
				addLabeledField(fieldPanel, "LastName", getLastNameField()); //$NON-NLS-1$
			}
			return fieldPanel;
		}

		protected JTextField getLastNameField() {
			if (lastNameField == null) {
				lastNameField = new JTextField();
				lastNameField.setName("lastNameField"); //$NON-NLS-1$
			}
			return lastNameField;
		}

		private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
			JLabel label = new JLabel(labelText);
			label.setLabelFor(textField);
			panel.add(label);
			panel.add(textField);
		}

		protected JTextField getFirstNameField() {
			if (firstNameField == null) {
				firstNameField = new JTextField();
				firstNameField.setName("firstNameField"); //$NON-NLS-1$
			}
			return firstNameField;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("ok".equalsIgnoreCase(e.getActionCommand())) {
				String firstName = getFirstNameField().getText();
				String lastName = getLastNameField().getText();
				if (firstName != null && lastName != null) {
					try {
						clearUsers();
						agent.search(firstName, lastName);
					} catch (SearchException e1) {
						throw new RuntimeException(e1);
					}
					clearFields();
				}
			}
		}

		private void clearFields() {
			getFirstNameField().setText("");
			getLastNameField().setText("");
		}
	}
}
