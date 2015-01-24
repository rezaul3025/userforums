package com.userforums.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userforums.pojo.Image;
import com.userforums.pojo.PostDocument;

@Repository
public class PostDocDaoImp implements dbOperation<PostDocument>
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public PostDocument create(PostDocument ob)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostDocument getById(Integer id)
	{
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		PostDocument postDoc = (PostDocument) session.get(PostDocument.class, id);
		
		return postDoc;
	}

	@Override
	public List<PostDocument> getAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, PostDocument> getAllAsMap()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostDocument update(PostDocument ob)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostDocument removeOne(PostDocument ob)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
