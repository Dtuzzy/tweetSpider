import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
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

public class google extends JFrame {

	JPanel search_pane;
	Container cont;
	JPanel menuPane, white;
	JButton butt;
	JLabel logo_02, logo, loading;
	JTextField tff;

	public google() {
		super("Tweet Spider");

		cont = new Container();
		cont.setBackground(Color.WHITE);
		search_pane = new JPanel();
		search_pane.setLayout(new GridLayout());
		search_pane.setBorder(BorderFactory.createRaisedBevelBorder());
		search_pane.setBounds(300, 200, 600, 40);

		ImageIcon load = new ImageIcon(this.getClass().getResource("images/loading.gif"));
		loading = new JLabel(load);
		loading.setBounds(540, 160, 106, 106);
		loading.setVisible(false);
		cont.add(loading);

		final JTextArea ta = new JTextArea();
		ta.setBorder(BorderFactory.createRaisedBevelBorder());
		ta.setLineWrap(true);
		ta.setBackground(Color.white);
		ta.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		ta.setEditable(false);
		final JScrollPane js = new JScrollPane(ta);
		js.setBorder(BorderFactory.createRaisedBevelBorder());
		js.setBounds(70, 90, 1050, 450);
		js.setVisible(false);
		cont.add(js);
		ImageIcon logz = new ImageIcon(this.getClass().getResource("images/logo_02.jpg"));
		logo = new JLabel(logz);
		logo.setBounds(540, 80, 106, 106);
		cont.add(logo);

		ImageIcon log_02 = new ImageIcon(this.getClass().getResource("images/logo_small.png"));
		logo_02 = new JLabel(log_02);
		logo_02.setBounds(5, 3, 45, 45);
		logo_02.setVisible(false);
		cont.add(logo_02);

		tff = new JTextField();
		tff.setFont(new Font("Lucida", Font.PLAIN, 20));
		butt = new JButton("Crawl Tweets");
		butt.setBorder(BorderFactory.createRaisedBevelBorder());
		butt.setFont(new Font("MS PGothic", Font.BOLD, 20));
		// butt.setContentAreaFilled(false);
		butt.setBackground(new Color(30, 144, 255));
		butt.setForeground(Color.WHITE);
		butt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread trd = new Thread(new Runnable() {

					public void run() {
						int rool = 0;
						while (rool < 5) {
							rool += 1;
							try {
								Thread.sleep(100);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							if (rool == 2) {
								search_pane.setBounds(60, 0, 600, 40);
								logo_02.setVisible(true);
								logo.setVisible(false);
								ta.setText("");
								js.setVisible(true);
								loading.setVisible(true);
								// scrapeTweets();
							} else if (rool == 4) {
								// ta.setText("");
								scrapeTweets();
							}

						}

					}
				});
				trd.start();
			}

			private void scrapeTweets() {
				// 1) Instantiate a Twitter Factory
				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true).setJSONStoreEnabled(true);
				TwitterFactory tf = new TwitterFactory(cb.build());

				// 2) Instantiate a new Twitter client
				// Go to https://dev.twitter.com/ to register a new Twitter App and get
				// credentials
				Twitter twitter = tf.getInstance();
				AccessToken accessToken = new AccessToken("180912654-w2oGG1eJ0UclBpBgd9dRVXtmNzMI8eym0SoA0TEA",
						"UQrIWhsEzx3obCstCUs9eCWjnRzFmdGZozQOcMIW8dm2e");
				twitter.setOAuthConsumer("qpK0XXox197NpCqBuY8kLoCZg",
						"b3Q4GGF35O58Mju5L4XmfBrZynlrN7jNT4o2QFKCs1oTExpFzG");
				twitter.setOAuthAccessToken(accessToken);

				// Task1: search for all the tweets with the keywords: football world cup
				// Reference JavaDoc http://twitter4j.org/javadoc/twitter4j/Query.html to
				// customize the query
				// System.out.println("*******************************************************************************");
				System.out.println("***** TASK 1: search for all the tweets with the keywords: football world cup");
				// ta.setText("********************************************************************************\n");
				// ta.setText("***** TASK 1: search for all the tweets with the keywords:
				// football world cup\n");
				String queryString = tff.getText();
				Query query = new Query(queryString);
				query.count(100); // sets the number of tweets to return per page, up to a max of 100
				QueryResult result;
				try {
					result = twitter.search(query);
					Integer countTw = 1;
					System.out.println("Query result for " + queryString + ":");
					for (Status status : result.getTweets()) {
						System.out.println(countTw++ + " > @" + status.getUser().getScreenName() + " ("
								+ status.getCreatedAt().toString() + ") : " + status.getText() + "\n");
						ta.append("" + countTw++ + " > @" + status.getUser().getScreenName() + " ("
								+ status.getCreatedAt().toString() + ") : " + status.getText() + "\n");
						if (ta.getText().contains("@")) {
							loading.setVisible(false);
							ta.append("" + countTw++ + " > @" + status.getUser().getScreenName() + " ("
									+ status.getCreatedAt().toString() + ") : " + status.getText() + "\n");

						}
					}
				} catch (TwitterException e) {
					JOptionPane.showMessageDialog(null, "Tweet Spider Exceptions!" + e, "Error!",
							JOptionPane.INFORMATION_MESSAGE);
					// logger.info("Exception while searching for tweets by a query string: " +
					// e.getMessage());
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

				Long userId = 15982159l;
				ResponseList<Status> timeline;
				try {
					timeline = twitter.getUserTimeline(userId, pagingInstance);
					if (timeline != null && timeline.size() > 0) {
						System.out.println("Retrieved " + timeline.size() + " tweets (user ID: " + userId + ", page: "
								+ (pageNum - 1) + ". Tweets per page: " + elementsPerPage + ")");
						// ta2.append("Retrieved " + timeline.size() + " tweets (user ID: " + userId +
						// ", page: " + (pageNum - 1) + ". Tweets per page: " + elementsPerPage + ")");
						Iterator<Status> statusIter = timeline.iterator();
						Integer countTw = 1;
						while (statusIter.hasNext()) {
							Status status = statusIter.next();
							if (status != null && status.getCreatedAt() != null) {
								System.out.println(countTw++ + " > @" + status.getUser().getScreenName() + " ("
										+ status.getCreatedAt().toString() + ") : " + status.getText() + "\n");

								// ta.append(countTw++ + " > @" + status.getUser().getScreenName() + " (" +
								// status.getCreatedAt().toString() + ") : " + status.getText() + "\n");
							}
						}
					}
				} catch (TwitterException e) {
					// logger.info("Exception while searching for tweets of a user timeline: " +
					// e.getMessage());
					e.printStackTrace();
				}

			}

		});
		search_pane.add(tff);
		search_pane.add(butt);
		cont.add(search_pane);

		ImageIcon yf = new ImageIcon(this.getClass().getResource("images/bg.jpg"));
		JLabel welf = new JLabel(yf);
		welf.setBounds(0, 0, 1200, 600);

		cont.add(welf);
		add(cont);
		setSize(1200, 600);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {
				try {

					String ObjButtons[] = { "Yes", "No" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit ?",
							"Tweet Spider", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
							ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					} else if (PromptResult == JOptionPane.NO_OPTION) {

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public static void main(String[] args) {

		try {

			google n = new google();
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}