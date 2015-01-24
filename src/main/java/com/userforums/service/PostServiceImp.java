package com.userforums.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.userforums.dao.dbOperation;
import com.userforums.pojo.Group;
import com.userforums.pojo.Post;

@Service
@Component
public class PostServiceImp implements PostService
{

	@Autowired
	private dbOperation<Post> dbOperstion;

	@Override
	@Transactional()
	public Post create(Post post)
	{
		// TODO Auto-generated method stub
		return dbOperstion.create(post);
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

}
