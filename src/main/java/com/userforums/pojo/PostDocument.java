package com.userforums.pojo;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="postdocument")
public class PostDocument
{
	
	@Id
	@GeneratedValue
	private Integer docId;
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
	private Blob docContent;
	@Column(name="docOwnerId",insertable=false, updatable=false)
	private Integer docOwnerId;
	
	public Integer getDocId()
	{
		return docId;
	}
	public void setDocId(Integer docId)
	{
		this.docId = docId;
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
	public Blob getDocContent()
	{
		return docContent;
	}
	public void setDocContent(Blob docContent)
	{
		this.docContent = docContent;
	}
	public Integer getDocOwnerId()
	{
		return docOwnerId;
	}
	public void setDocOwnerId(Integer docOwnerId)
	{
		this.docOwnerId = docOwnerId;
	}
}
