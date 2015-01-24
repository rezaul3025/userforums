package com.userforums.service;

import java.util.List;
import java.util.Map;
import com.userforums.pojo.Post;

public interface PostService
{
	public Post create(Post post);
	public List<Post> getAll();
	public Map<Integer, Post> getAllAsMap();
}
