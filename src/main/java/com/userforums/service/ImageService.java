package com.userforums.service;

import com.userforums.pojo.Group;
import com.userforums.pojo.Image;

public interface ImageService
{
	public Image create(Image image);
	public Image getById(Integer id);
}
