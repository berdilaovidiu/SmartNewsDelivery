package dataaquisition;

import org.apache.commons.feedparser.*;
import org.apache.commons.feedparser.network.ResourceRequest;
import org.apache.commons.feedparser.network.ResourceRequestFactory;
import org.jdom.Element;
import org.jdom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ovidiu
 * Date: 11/17/11
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) throws IOException, FeedParserException {
        //create a new FeedParser...
        FeedParser parser = FeedParserFactory.newFeedParser();

        //create a listener for handling our callbacks
        FeedParserListener listener = new DefaultFeedParserListener() {

            public void onChannel(FeedParserState state,
                                  String title,
                                  String link,
                                  String description) throws FeedParserException {

                System.out.println("Found a new channel: " + title);

            }

            public void onItem(FeedParserState state,
                               String title,
                               String link,
                               String description,
                               String permalink) throws FeedParserException {

                displayContent(state.current.getContent());
//                System.out.println("Found a new published article: " + permalink);
//                System.out.println("Title: " + title);
//                System.out.println("Description: " + description);

            }

            public void onCreated(FeedParserState state, Date date) throws FeedParserException {
                System.out.println("Which was created on: " + date);
            }

        };

        //specify the feed we want to fetch
//        String resource = "http://www.ft.com/rss/companies/financials";
        String resource = "http://www.piticigratis.com/feed/";

        if (args.length == 1)
            resource = args[0];

        System.out.println("Fetching resource:" + resource);

        //use the FeedParser network IO package to fetch our resource URL
        ResourceRequest request = ResourceRequestFactory.getResourceRequest(resource);

        //grab our input stream
        InputStream is = request.getInputStream();

        //start parsing our feed and have the above onItem methods called
        parser.parse(listener, is, resource);
    }

    private static void displayContent(List content){
        for(Object el : content){
            if(el instanceof Text){
                System.out.println(((Text)el).getText());
            }else{
                if(el instanceof Element)
                displayContent(((Element)el ).getContent());
            }
        }
    }
}
