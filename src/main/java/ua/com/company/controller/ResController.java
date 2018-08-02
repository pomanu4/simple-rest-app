package ua.com.company.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.io.FeedException;
import ua.com.company.component.CreateCryptedHeader;
import ua.com.company.component.RssFeedUtill;
import ua.com.company.entity.Person;
import ua.com.company.serviceInterface.IpersonService;

@RestController
public class ResController {

	@Autowired
	private IpersonService persServ;

	@RequestMapping(path = { "/ref/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Person> getOnePersonById(@PathVariable("id") int id) {
		
		Person person = persServ.findOneById(id);
		return new ResponseEntity<Person>(person,  HttpStatus.OK);
	}

	@RequestMapping(path = { "/ref/all" }, method = RequestMethod.GET)
	public ResponseEntity<?> getAllPersonsFromDb() {
		List<Person> persons = persServ.findAll();
		return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(persons);
	}

	@RequestMapping(path = { "/ref/save" }, method = RequestMethod.POST)
	public ResponseEntity<String> savePerson(@RequestBody Person person, UriComponentsBuilder bilder) {
		if (person == null) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		Person saved = persServ.savePerson(person);
		System.out.println(saved);
		return new ResponseEntity<String>("person saved with id " + saved.getP_id(), HttpStatus.OK);

	}

	@RequestMapping(path = { "/ref/delete/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> deletPerson(@PathVariable("id") int id) {

		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}

	@RequestMapping(path = { "/admin" }, method = RequestMethod.GET)
	public ResponseEntity<String> adminOnlyEvailebleReference() {
		String message = "from admin allow only resource";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@RequestMapping(path = { "/getxml" }, method = RequestMethod.POST)
	public ResponseEntity<Person> getXML(@RequestBody Person person) {
		Person personBack = new Person(person.getName(), "get back object");
		
		return new ResponseEntity<Person>(personBack, HttpStatus.OK);
	}

	@RequestMapping(path = { "/params" }, method = RequestMethod.POST)
	public ResponseEntity<String> getParameterFromRequest(@RequestParam("name") String name,
			@RequestParam("password") String password) {
		
		String message = CreateCryptedHeader.getSecyredHesder(name, password);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@RequestMapping(path = { "/ref/update" }, method = RequestMethod.POST)
	public ResponseEntity<String> update(@RequestBody Person person){
		if(person == null) {
			return new ResponseEntity<>("null value", HttpStatus.PAYMENT_REQUIRED);
		}
		Person updPers = persServ.updatePerson(person.getP_id(), person.getName(), person.getNickname());
		String respMessage = "User succesfuly updated   " + updPers.getName()+" "+ updPers.getNickname();
	return new ResponseEntity<String>(respMessage, HttpStatus.OK);	
	}
	

	@RequestMapping(path = { "/ref/rss" }, method = RequestMethod.GET, produces=MediaType.APPLICATION_RSS_XML_VALUE)
	public Channel rssTry() throws FeedException{
		// WireFeed for atom_1.0 feed type
		//Channel for rss_2.0 feed type
		
	 			
	return  (Channel) RssFeedUtill.createFeed().createWireFeed();
	}

}
