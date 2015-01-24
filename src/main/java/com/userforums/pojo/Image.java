package com.userforums.pojo;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="image")
public class Image implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer imageId;
	@Column(length=200)
	private String altText;
	@Column(length=50)
	private String format;
	@Column(length=20)
	private String size;
	@Column(length=10)
	private String extension;
	private Integer height;
	private Integer width;
	private Blob imageContent;
	@Column(name="imageOwnerId",insertable=false, updatable=false)
	private Integer imageOwnerId;
	//@ManyToOne
	//@JoinColumn(name="imageOwnerId", nullable=false, updatable=false )
	//private Group group;
	
	public Integer getImageId()
	{
		return imageId;
	}
	public void setImageId(Integer imageId)
	{
		this.imageId = imageId;
	}
	public String getAltText()
	{
		return altText;
	}
	public void setAltText(String altText)
	{
		this.altText = altText;
	}
	public String getFormat()
	{
		return format;
	}
	public void setFormat(String format)
	{
		this.format = format;
	}
	public String getSize()
	{
		return size;
	}
	public void setSize(String size)
	{
		this.size = size;
	}
	public String getExtension()
	{
		return extension;
	}
	public void setExtension(String extension)
	{
		this.extension = extension;
	}
	public Integer getHeight()
	{
		return height;
	}
	public void setHeight(Integer height)
	{
		this.height = height;
	}
	public Integer getWidth()
	{
		return width;
	}
	public void setWidth(Integer width)
	{
		this.width = width;
	}
	public Blob getImageContent()
	{
		return imageContent;
	}
	public void setImageContent(Blob imageContent)
	{
		this.imageContent = imageContent;
	}
	public Integer getImageOwnerId()
	{
		return imageOwnerId;
	}
	public void setImageOwnerId(Integer imageOwnerId)
	{
		this.imageOwnerId = imageOwnerId;
	}
	
	/*public Group getGroup()
	{
		return group;
	}
	public void setGroup(Group group)
	{
		this.group = group;
	}*/
	
}
