package ua.com.company.daoInterface;

import java.util.List;
import ua.com.company.entity.Person;

public interface IpersonDao {

	Person findOneById(int id);

	Person savePerson(Person person);

	List<Person> findAll();
	
	Person findByName(String name);
	
	
}
