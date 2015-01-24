package com.userforums.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userforums.pojo.Post;

@Repository
public class PostDaoImp implements dbOperation<Post>
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Post create(Post ob)
	{
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.save(ob);

		return ob;
	}

	@Override
	public Post getById(Integer id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Post> getAllAsMap()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post update(Post ob)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post removeOne(Post ob)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
