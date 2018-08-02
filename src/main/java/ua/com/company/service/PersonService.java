package ua.com.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.company.daoInterface.IpersonDao;
import ua.com.company.entity.Person;
import ua.com.company.serviceInterface.IpersonService;

@Service
@Transactional
public class PersonService implements IpersonService{
	
	@Autowired
	private IpersonDao persDao;

	@Override
	public Person savePerson(Person person) {
		persDao.savePerson(person);
		return person;
	}

	@Override
	public List<Person> findAll() {
		
		return persDao.findAll();
	}

	@Override
	public Person findOneById(int id) {
		
		return persDao.findOneById(id);
	}

	@Override
	public Person findByName(String name) {
		
		return persDao.findByName(name);
	}

	@Override
	public Person updatePerson(int id, String name, String nickname) {
		Person person = persDao.findOneById(id);
		person.setName(name);
		person.setNickname(nickname);
		
		return person;
	}
	
	

}
