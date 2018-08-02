package spring.rest.example.testClass;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ua.com.company.entity.Person;

public class SpringTestClient {

	private static final String baseURL = "http://localhost:8080/spring.rest.example";

	private static String credentials = "";

	private static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
//		getSecyredHeaderToken();
		getUser(3);
		adminReferenceTest();
//		createUser();
		try {
//			userAccessAdminReference();
		} catch (Exception e) {
			System.out.println("access to this reference are not allowed");
		}
//		

//		updateUser();
		
	}
	
	
	
	private static void getSecyredHeaderToken() {
		System.out.println("Testing create secure token----------");
		String name = "vasia";
		String password = "soplia";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("name", name);
		map.add("password", password);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = restTemplate.exchange(baseURL + "/params", HttpMethod.POST, request, String.class);
		credentials = response.getBody();
		System.out.println(credentials + "\n");
	}

	private static void getUser(int id) {
		System.out.println("Testing get one person API----------");
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURL + "/ref");
		builder.queryParam("name", "paramName");
		builder.queryParam("age", 33);

		HttpEntity<String> request = new HttpEntity<>(getUserHeaders());
		Map<String, String> params = new HashMap<>();
		params.put("id", Integer.toString(id));
		ResponseEntity<Person> responce = restTemplate.exchange(baseURL + "/ref/{id}", HttpMethod.GET, request,
				Person.class, params);
		Person person = responce.getBody();
		System.out.println(person + "\n");
		System.out.println(builder.toUriString());
	}

	private static void createUser() {
		System.out.println("Testing create person API----------");
		Person person = new Person("testPerson", "testPerson");

		HttpEntity<Person> request = new HttpEntity<>(person, getUserHeaders());
		ResponseEntity<String> response = restTemplate.exchange(baseURL + "/ref/save", HttpMethod.POST, request,
				String.class);
		String serverMessage = response.getBody();
		System.out.println(serverMessage + "\n");
	}
	
	private static void updateUser() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Person person = new Person("updatePerson", "updatePerson");
		person.setP_id(3);
		HttpEntity<Person> request = new HttpEntity<Person>(person, headers);
		ResponseEntity<String> responce = restTemplate.exchange(baseURL+"/ref/update", HttpMethod.POST,
				request, String.class);
		String message = responce.getBody();
		String string = responce.getStatusCode().toString();
		System.out.println(string + "\n");
	}

	private static void adminReferenceTest() {
		System.out.println("Testing admin reference ----------");
		HttpEntity<String> request = new HttpEntity<>(getAdminHeaders());
		ResponseEntity<String> responce = restTemplate.exchange(baseURL + "/admin", HttpMethod.GET, request,
				String.class);
		String serverMassage = responce.getBody();
		System.out.println(serverMassage + "\n");
	}

	private static void userAccessAdminReference() {
		System.out.println("Test user to admin only reference-------------");
		HttpEntity<String> request = new HttpEntity<>(getUserHeaders());
		ResponseEntity<String> responce = restTemplate.exchange(baseURL + "/admin", HttpMethod.GET, request,
				String.class);
		String serverMessage = responce.getBody();
		System.out.println(serverMessage + "\n");
	}
	
	private static void getXMLrepresentation() {

		Person person = new Person("json", "send object");
		System.out.println("object for sending -> "+ person);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		headers.setContentType(MediaType.APPLICATION_JSON);
		System.out.println(headers.getContentType());
		
		HttpEntity<Person> request = new HttpEntity<>(person, headers);
		ResponseEntity<Person> responce = restTemplate.exchange(baseURL + "/getxml", HttpMethod.POST, request, Person.class);
		
		person = responce.getBody();
		System.out.println("got back object <- " + person);
		System.out.println(responce.getHeaders().getContentType()+"\n");
		
	}

	private static HttpHeaders getUserHeaders() {
		String token = "vasia:soplia";
		String codedToken = new String(Base64.encode(token.getBytes()));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + codedToken);
//		headers.add("Authorization", "Basic " + credentials);
		headers.setAccept(Arrays.asList(MediaType. APPLICATION_JSON_UTF8));
		return headers;
	}

	private static HttpHeaders getAdminHeaders() {
		String token = "admin:root";
		String codedToken = new String(Base64.encode(token.getBytes()));
		HttpHeaders headers = new HttpHeaders();

		headers.add("Authorization", "Basic " + codedToken);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		return headers;
	}
	
}
