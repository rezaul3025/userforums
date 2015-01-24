package com.userforums.service;

import com.userforums.pojo.PostDocument;

public interface PostDocService
{
	public PostDocument create(PostDocument postDoc);
	public PostDocument getById(Integer id);
}
