package com.userforums.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userforums.pojo.Group;

@Repository
public class GroupDaoImp implements dbOperation<Group>
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Group create(Group ob)
	{
		// TODO Auto-generated method stub
		//return null;
		Session session = sessionFactory.getCurrentSession();
		
		session.save(ob);
		
		return ob;
	}

	@Override
	public Group getById(Integer id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getAll()
	{
		// TODO Auto-generated method stub
		//return null;
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		List<Group> groups = session.createQuery("from Group order by groupId").list();
		
		Map<Integer, Group> groupsAsMap = groups.stream().collect(Collectors.toMap(Group::getGroupId, (Group g)->g));
		
		tx.commit();
		session.close();
		
		return groups;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<Integer, Group> getAllAsMap()
	{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		return (Map<Integer, Group>)session.createQuery("from Group").list().stream().collect(Collectors.toMap(Group::getGroupId, (Group g)->g));
	}

	@Override
	public Group update(Group ob)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group removeOne(Group ob)
	{
		// TODO Auto-generated method stub
		return null;
	}

	

}
