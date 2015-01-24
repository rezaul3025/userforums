package com.userforums.dao;

import java.util.List;
import java.util.Map;

public interface dbOperation<T>
{
	public T create(T ob);
	public T getById(Integer id);
	public List<T> getAll();
	public Map<Integer, T> getAllAsMap();
	public T update(T ob);
	public T removeOne(T ob);
}
