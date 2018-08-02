package ua.com.company.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MessageConverter extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		 Jaxb2RootElementHttpMessageConverter xmlConverter = new Jaxb2RootElementHttpMessageConverter();
		 MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		 AtomFeedHttpMessageConverter rssConverter=new AtomFeedHttpMessageConverter();
		 RssChannelHttpMessageConverter channeConverter = new RssChannelHttpMessageConverter();
		 converters.add(xmlConverter);
		 converters.add(jsonConverter);
		 converters.add(rssConverter);
		 converters.add(channeConverter);
	}
	
	

}
