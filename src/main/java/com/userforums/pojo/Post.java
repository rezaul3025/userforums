package com.userforums.pojo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="post")
public class Post
{
	@Id
	@GeneratedValue
	private Integer postId;
	@Column(length=50)
	private String category;
	@Column(length=255)
	private String title;
	@Column(length=Integer.MAX_VALUE)
	private String description;
	@Column(name="groupId",insertable=false, updatable=false)
	private Integer groupId;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="docOwnerId")
	private Set<PostDocument> postDoc;
	@ManyToOne
	@JoinColumn(name="groupId")
	private Group group;
	
	public Integer getPostId()
	{
		return postId;
	}
	public void setPostId(Integer postId)
	{
		this.postId = postId;
	}
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
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
	public Set<PostDocument> getPostDoc()
	{
		return postDoc;
	}
	public void setPostDoc(Set<PostDocument> postDoc)
	{
		this.postDoc = postDoc;
	}
	public Integer getGroupId()
	{
		return groupId;
	}
	public void setGroupId(Integer groupId)
	{
		this.groupId = groupId;
	}
	public Group getGroup()
	{
		return group;
	}
	public void setGroup(Group group)
	{
		this.group = group;
	}
	
}
