package ua.nure.kn155.turchin.agent;

import java.util.Collection;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.db.DaoFactory;
import ua.nure.kn155.turchin.db.DatabaseException;
import ua.nure.kn155.turchin.gui.MainFrame;


@SuppressWarnings("serial")
public class SearchAgent extends Agent {

	private AID[] aids;
	MainFrame gui;

	@Override
	protected void setup() {
		gui = new MainFrame(this);
		gui.setVisible(true);

		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setName("JADE-Searching");
		serviceDescription.setType("searching");

		DFAgentDescription description = new DFAgentDescription();
		description.setName(getAID());
		description.addServices(serviceDescription);

		try {
			DFService.register(this, description);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		System.out.println("Agent " + getAID().getName() + " has started");
		addBehaviour(new RequestServer());
		addBehaviour(new TickerBehaviour(this, 60000) {

			@Override
			protected void onTick() {
				DFAgentDescription agentDescription = new DFAgentDescription();
				ServiceDescription serviceDescription = new ServiceDescription();
				serviceDescription.setType("searching");
				agentDescription.addServices(serviceDescription);
				DFAgentDescription[] descriptions;
				try {
					descriptions = DFService.search(myAgent, agentDescription);
				} catch (FIPAException e) {
					e.printStackTrace();
					descriptions = new DFAgentDescription[0];
				}
				aids = new AID[descriptions.length];
				for (int i = 0; i < descriptions.length; i++) {
					aids[i] = descriptions[i].getName();
				}

			}
		});
	}

	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		gui.setVisible(false);
		gui.dispose();
		System.out.println("Agent " + getAID().getName() + "terminated");
	}
	
	
	public void search(String firstName, String lastName) throws SearchException {
		try {
			Collection<User> users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
			if (users.size() > 0) {
				showUsers(users, this.getName());
			} else {
				addBehaviour(new SearchRequestBehaviour(aids, firstName, lastName));
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
			throw new SearchException();
		}

	}
	public void showUsers(Collection<User> users, String string) {
		/*((SearchGui) gui.getSearchPanel()).setAgent(string);
		((SearchGui) gui.getSearchPanel()).addUsers(users);*/
	}

}
