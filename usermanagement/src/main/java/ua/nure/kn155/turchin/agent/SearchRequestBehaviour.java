package ua.nure.kn155.turchin.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;



public class SearchRequestBehaviour extends Behaviour {

  private String lastName;
  private String firstName;
  private AID[] aids;

  @Override
  public void action() {
    ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
    message.setContent(firstName + "," + lastName);
    for (AID aid : aids) {
      message.addReceiver(aid);
    }
    myAgent.send(message);
  }

  public SearchRequestBehaviour(AID[] aids, String firstName, String lastName) {
    this.aids = aids;
    this.firstName = firstName;
    this.lastName = lastName;

  }

  @Override
  public boolean done() {
    return true;
  }

}
