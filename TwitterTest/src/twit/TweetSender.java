package twit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Servlet implementation class TweetSender
 */
@WebServlet("/TweetSender")
public class TweetSender extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final TwitterFactory tf;
    private final Twitter twitter;
       
   	private static final String CONSUMER_KEY		= "3fvgHZj20p6pQ7oNhBo0VztCT";
   	private static final String CONSUMER_SECRET 	= "9XWZm3eLF0aNPeTFMNMMsOwsMNfUP67muVSz7lqw2h50ELPB1V";
   	private static final String ACCESS_TOKEN 		= "2796613310-LJkza1xT7rhyuE9KqBtFtrJ4tMWbmf11E8nsQ2r";
   	private static final String ACCESS_TOKEN_SECRET	= "ceqSwyu9n1Ffy8SlosuVxEbJeLIJR7OsvtKf2lJc0bj03";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TweetSender() {
        super();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setPrettyDebugEnabled(true);
		cb.setJSONStoreEnabled(true);
		
		tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		twitter.setOAuthAccessToken(new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET));

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HI, send a POST");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		try {
			String tweet = request.getParameter("tweet");
			System.out.println("POST : " + tweet);
			jsonObject = (JSONObject)parser.parse(tweet);
			System.out.println(jsonObject);

			Status updateStatusResponse = twitter.updateStatus((String)jsonObject.get("text"));
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/json");
			String rawJSON = TwitterObjectFactory.getRawJSON(updateStatusResponse);
			response.getWriter().write(rawJSON);
		} catch (ParseException e) {
			response.sendError(500, e.getMessage());;
		} catch (TwitterException e) {
			response.sendError(500, e.getMessage());;
		}


	}

}
