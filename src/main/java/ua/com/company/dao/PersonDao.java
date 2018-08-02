package ua.com.company.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.com.company.daoInterface.IpersonDao;
import ua.com.company.entity.Person;

@Repository
public class PersonDao implements IpersonDao {

	@Autowired
	private SessionFactory factory;

	@Override
	public Person findOneById(int id) {
		Person person = factory.getCurrentSession().get(Person.class, id);
		return person;
	}

	@Override
	public Person savePerson(Person person) {
		factory.getCurrentSession().save(person);
		return person;
	}

	@Override
	public List<Person> findAll() {
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
		Root<Person> root = criteria.from(Person.class);
		criteria.select(root);
		Query<Person> query = session.createQuery(criteria);

		return query.getResultList();
	}

	@Override
	public Person findByName(String name) {
		Session session = factory.getCurrentSession();
		String querryString = "SELECT p FROM Person p WHERE p.name = (:name)";
		TypedQuery<Person> query = session.createQuery(querryString, Person.class);
		query.setParameter("name", name);

		return query.getSingleResult();
	}

		

}
