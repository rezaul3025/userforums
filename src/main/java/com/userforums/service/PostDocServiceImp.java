package com.userforums.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.userforums.dao.dbOperation;
import com.userforums.pojo.PostDocument;

@Service
@Component
public class PostDocServiceImp implements PostDocService
{

	@Autowired
	private dbOperation<PostDocument> postDocDao;
	
	@Override
	public PostDocument create(PostDocument postDoc)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public PostDocument getById(Integer id)
	{
		// TODO Auto-generated method stub
		return postDocDao.getById(id);
	}

}
