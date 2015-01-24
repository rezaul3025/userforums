package com.userforums.service;

import java.util.List;
import java.util.Map;

import com.userforums.pojo.Group;

public interface GroupService
{
	public Group create(Group group);
	public List<Group> getAll();
	public Map<Integer, Group> getAllAsMap();
}
