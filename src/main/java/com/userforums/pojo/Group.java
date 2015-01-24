package com.userforums.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="groups")
public class Group implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer groupId;
	@Column(length=255)
	private String title;
	@Column(length=Integer.MAX_VALUE)
	private String description;
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="imageOwnerId")
	private Set<Image> image;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="groupId")
	private Set<Post> posts;
	
	public Integer getGroupId()
	{
		return groupId;
	}
	public void setGroupId(Integer groupId)
	{
		this.groupId = groupId;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Set<Image> getImage()
	{
		return image;
	}
	public void setImage(Set<Image> image)
	{
		this.image = image;
	}
	public Set<Post> getPosts()
	{
		return posts;
	}
	public void setPosts(Set<Post> posts)
	{
		this.posts = posts;
	}
	
}
