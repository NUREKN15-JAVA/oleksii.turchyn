package ua.nure.kn155.turchin.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn155.turchin.User;
import ua.nure.kn155.turchin.db.DaoFactory;
import ua.nure.kn155.turchin.db.DatabaseException;

/**
 * Behaviour that processes all incoming messages about searching for a user.
 * 
 * @author Yevhenii Baranov
 *
 */
@SuppressWarnings("serial")
public class RequestServer extends CyclicBehaviour {

  @Override
  public void action() {
    ACLMessage message = myAgent.receive();
    if (message != null) {
      switch (message.getPerformative()) {
        case ACLMessage.REQUEST:
          myAgent.send(createReply(message));
          break;

        case ACLMessage.INFORM:
          Collection<User> users = parseMessage(message);
          ((SearchAgent) myAgent).showUsers(users, message.getSender().getName());
          break;

        default:
          throw new IllegalStateException("Such message is not permitted for this agent");
      }
    } else {
      block();
    }
  }

  private Collection<User> parseMessage(ACLMessage message) {
    Collection<User> users = new LinkedList<>();
    String content = message.getContent();
    StringTokenizer usersTokenizer = new StringTokenizer(content, ";");
    while (usersTokenizer.hasMoreElements()) {
      StringTokenizer tokenizer = new StringTokenizer(usersTokenizer.nextToken(), ",");
      String id = tokenizer.nextToken();
      String firstName = tokenizer.nextToken();
      String lastName = tokenizer.nextToken();
      users.add(new User(new Long(id), firstName, lastName));
    }


    return users;
  }

  private ACLMessage createReply(ACLMessage message) {
    ACLMessage reply = message.createReply();
    String content = message.getContent();
    StringTokenizer tokenizer = new StringTokenizer(content, ",");
    String firstName = tokenizer.nextToken();
    String lastName = tokenizer.nextToken();
    Collection<User> users = null;
    try {
      users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
    } catch (DatabaseException e) {
      e.printStackTrace();
    }
    StringBuffer buffer = new StringBuffer();
    for (User user : users) {
      buffer.append(user.getId()).append(",");
      buffer.append(user.getFirstName()).append(",");
      buffer.append(user.getLastName()).append(";");
    }
    reply.setContent(buffer.toString());
    return reply;
  }

}
