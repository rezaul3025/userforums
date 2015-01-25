package com.userforums.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userforums.dao.LdapDao;

@Service
public class LdapUserService
{
	@Autowired
	private LdapDao ldapDao; 
	
	@Transactional
	public Map<String, String> getUserAttributes(String username)
	{
		return ldapDao.getUserAttributes(username);
	}
}
