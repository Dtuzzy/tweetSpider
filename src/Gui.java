import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.logging.Logger;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import javax.swing.*;
/**
 * This is an example class that shows how to exploit the Twitter4J java library to interact with Twitter
 * 
 * Twitter4j: http://twitter4j.org/en/index.html
 * Download (version 4.0.1):http://twitter4j.org/archive/twitter4j-4.0.1.zip
 * JavaDoc: http://twitter4j.org/javadoc/index.html
 * Example code of Twitter4j: http://twitter4j.org/en/code-examples.html
 * 
 * @author Francesco Ronzano
 *
 */
public class Gui {

	private static Logger logger = Logger.getLogger(Gui.class.getName());
	
	public static void main(String[] args) {
		
		JFrame frm  = new JFrame();
		Container cont  = new Container();
		//JPanel pane2 = new JPanel();
				//pane2.setBounds(0,32,1200,570);
				final JTextArea ta = new JTextArea();
				ta.setLineWrap(true);
				ta.setBackground(Color.PINK);
				JScrollPane js = new JScrollPane(ta);
				js.setBounds(0,32,1180,520);
				
				final JTextArea ta2 = new JTextArea();
				ta2.setBackground(Color.PINK);
				ta2.setLineWrap(true);
				JScrollPane js2 = new JScrollPane(ta2);
				js2.setBounds(0,290,1180,250);
				//pane2.add(js);
				
		JPanel pane = new JPanel();
		pane.setBounds(0,0,1180,30);
		pane.setLayout(new GridLayout());
		final JTextField tff = new JTextField();
		final JButton  butt = new JButton("Search");
		butt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				scrapeTweets();
			}

			private void scrapeTweets() {
				// 1) Instantiate a Twitter Factory
				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true).setJSONStoreEnabled(true);
				TwitterFactory tf = new TwitterFactory(cb.build());
				
				// 2) Instantiate a new Twitter client
				// Go to https://dev.twitter.com/ to register a new Twitter App and get credentials
				Twitter twitter = tf.getInstance();
				AccessToken accessToken = new AccessToken("180912654-w2oGG1eJ0UclBpBgd9dRVXtmNzMI8eym0SoA0TEA", "UQrIWhsEzx3obCstCUs9eCWjnRzFmdGZozQOcMIW8dm2e");
				twitter.setOAuthConsumer("qpK0XXox197NpCqBuY8kLoCZg", "b3Q4GGF35O58Mju5L4XmfBrZynlrN7jNT4o2QFKCs1oTExpFzG");
				twitter.setOAuthAccessToken(accessToken);
				
				// Task1: search for all the tweets with the keywords: football world cup
			    // Reference JavaDoc http://twitter4j.org/javadoc/twitter4j/Query.html to customize the query
				//System.out.println("*******************************************************************************");
				System.out.println("***** TASK 1: search for all the tweets with the keywords: football world cup");
				//ta.setText("********************************************************************************\n");
				//ta.setText("***** TASK 1: search for all the tweets with the keywords: football world cup\n");
				String queryString = tff.getText();
				Query query = new Query(queryString);
				query.count(100); // sets the number of tweets to return per page, up to a max of 100
			    QueryResult result;
				try {
					result = twitter.search(query);
					Integer countTw = 1;
					System.out.println("Query result for " + queryString + ":");
					for (Status status : result.getTweets()) {
				        System.out.println(countTw++ + " > @" + status.getUser().getScreenName() + " (" + status.getCreatedAt().toString() + ") : " + status.getText() + "\n");
				        ta.append(countTw++ + " > @" + status.getUser().getScreenName() + " (" + status.getCreatedAt().toString() + ") : " + status.getText() + "\n");
					}
				} catch (TwitterException e) {
					logger.info("Exception while searching for tweets by a query string: " + e.getMessage());
					e.printStackTrace();
				}
			    
				
				// Task2: get all the tweets of a user (by user id) with paging control
				// The user ID of the NewYorkTimes Twitter account is: 807095l
				System.out.println("********************************************************************************");
				System.out.println("***** TASK 2: get all the tweets of a user (by user id) with paging control");
				
				Paging pagingInstance = new Paging();
				Integer pageNum = 1;
				Integer elementsPerPage = 40;
				pagingInstance.setPage(pageNum);
				pagingInstance.setCount(elementsPerPage);
				
				Long userId = 807095l;
				ResponseList<Status> timeline;
				try {
					timeline = twitter.getUserTimeline(userId, pagingInstance);
					if(timeline != null && timeline.size() > 0) {
						System.out.println("Retrieved " + timeline.size() + " tweets (user ID: "  + userId + ", page: " + (pageNum - 1) + ". Tweets per page: " + elementsPerPage + ")");
						//ta2.append("Retrieved " + timeline.size() + " tweets (user ID: "  + userId + ", page: " + (pageNum - 1) + ". Tweets per page: " + elementsPerPage + ")");
						Iterator<Status> statusIter = timeline.iterator();
						Integer countTw = 1;
						while(statusIter.hasNext()) {
							Status status = statusIter.next();
							if(status != null && status.getCreatedAt() != null) {
								System.out.println(countTw++ + " > @" + status.getUser().getScreenName() + " (" + status.getCreatedAt().toString() + ") : " + status.getText() + "\n");
								
							//ta.append(countTw++ + " > @" + status.getUser().getScreenName() + " (" + status.getCreatedAt().toString() + ") : " + status.getText() + "\n");
							}
						}
					}
				} catch (TwitterException e) {
					logger.info("Exception while searching for tweets of a user timeline: " + e.getMessage());
					e.printStackTrace();
				}
				
			}
			
		});
		pane.add(tff);
		pane.add(butt);
		
		
		
		cont.add(pane);
		cont.add(js);
		//cont.add(js2);
		frm.add(cont);
		frm.setSize(1200, 600);	
		frm.setVisible(true);
		frm.setLocationRelativeTo(null);
		//frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frm.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frm.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				try {

					String ObjButtons[] = { "Yes", "No"};
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit ?",
							"ELibrary", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
							ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}		
}