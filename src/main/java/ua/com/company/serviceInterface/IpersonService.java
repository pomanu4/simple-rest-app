package ua.com.company.serviceInterface;

import java.util.List;

import ua.com.company.entity.Person;

public interface IpersonService {

	Person savePerson(Person person);

	List<Person> findAll();

	Person findOneById(int id);
	
	Person findByName(String name);
	
	Person updatePerson(int id, String name, String nickname);

}
