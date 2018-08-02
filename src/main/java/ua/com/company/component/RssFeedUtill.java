package ua.com.company.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.impl.DateParser;

public class RssFeedUtill {

	public static SyndFeed createFeed() throws FeedException {
		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setEncoding("UTF-8");
		syndFeed.setFeedType("rss_2.0");
		syndFeed.setTitle("rssFeed created with Rome");
		syndFeed.setLink("https://www.google.com");
		syndFeed.setDescription("short description of this  feed");

		List<SyndEntry> entries = new ArrayList<>();

		SyndEntry entryOne = new SyndEntryImpl();
		entryOne.setPublishedDate(DateParser.parseDate("2018-06-03", Locale.getDefault()));
		entryOne.setAuthor("author roman");
		entryOne.setTitle("title in entry ");
		entryOne.setLink("https://www.google.com.ua");

		SyndContent content = new SyndContentImpl();
		content.setType("text/html");
		content.setValue("<p>content of  this object</p>"
				+ "<p>content vith some a-element <a href=\"#\">reference</a> ref there</p>");
		entryOne.setDescription(content);

		SyndEntry entryTwo = new SyndEntryImpl();
		entryTwo.setTitle("title in second entity");
		entryTwo.setLink("https://www.ukr.net");

		SyndContent content2 = new SyndContentImpl();
		content2.setType("text/html");
		content2.setValue("<p>not</p> <p>much</p> <p>there</p>");
		entryTwo.setDescription(content2);

		entries.add(entryOne);
		entries.add(entryTwo);
		syndFeed.setEntries(entries);

		return syndFeed;
	}

}
