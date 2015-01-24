package com.userforums.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.userforums.dao.dbOperation;
import com.userforums.pojo.Image;

@Service
@Component
public class ImageServiceImp implements ImageService
{
	@Autowired
	private dbOperation<Image> imageDao;
	
	@Override
	@Transactional
	public Image create(Image image)
	{
		// TODO Auto-generated method stub
		return imageDao.create(image);
	}

	@Override
	@Transactional
	public Image getById(Integer id)
	{
		// TODO Auto-generated method stub
		return imageDao.getById(id);
	}

}
