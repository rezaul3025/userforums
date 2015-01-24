package com.userforums.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.userforums.pojo.Image;

@Repository
public class ImageDaoImp implements dbOperation<Image>
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Image create(Image ob)
	{
		// TODO Auto-generated method stub
		Session session =sessionFactory.getCurrentSession();
		
		session.save(ob);
		
		return null;
	}

	@Override
	public Image getById(Integer id)
	{
		// TODO Auto-generated method stub
		Session session =sessionFactory.getCurrentSession();
		
		Image image =(Image)session.get(Image.class, id);
		
		return image;
	}

	@Override
	public List<Image> getAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image update(Image ob)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image removeOne(Image ob)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Image> getAllAsMap()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
