package com.userforums.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.userforums.dao.dbOperation;
import com.userforums.pojo.Group;

@Service
@Component
public class GroupServiceImp implements GroupService
{

	@Autowired
	private dbOperation<Group> dbOperstion;

	@Override
	@Transactional()
	public Group create(Group group)
	{
		// TODO Auto-generated method stub
		return dbOperstion.create(group);
	}

	@Override
	@Transactional
	public List<Group> getAll()
	{
		// TODO Auto-generated method stub
		return dbOperstion.getAll();
	}
	
	@Override
	@Transactional
	public Map<Integer, Group> getAllAsMap()
	{
		// TODO Auto-generated method stub
		Map<Integer, Group> t = dbOperstion.getAllAsMap();
		return dbOperstion.getAllAsMap();
	}
	
	
}
