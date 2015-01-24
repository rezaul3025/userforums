package com.userforums.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialException;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.userforums.pojo.Group;
import com.userforums.pojo.Image;
import com.userforums.pojo.Post;
import com.userforums.pojo.PostDocument;

@Service
@Component
public class Utils
{
	
	final static String IMAGE_SMALL="SMALL";
	final static String IMAGE_LARGE="LARGE";
	
	public byte[] convertImage(Integer width, Integer height, BufferedImage inputImage, String format)
	{
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayOutputStream baos=null;
		
		try
		{
			baos = new ByteArrayOutputStream();
			BufferedImage outputImage  = Thumbnails.of(inputImage).size(width, height).asBufferedImage();
			ImageIO.write(outputImage,format, baos );
			baos.flush();
			baos.close();
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baos.toByteArray();
	}
	
	public byte[] convertBufferedImgeToByteArray(BufferedImage bufferedImage, String format)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try
		{
			ImageIO.write(bufferedImage,format, baos );
			baos.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baos.toByteArray();
	}
	
	public Set<Image> getGroupImages(MultipartFile file, Group group)
	{
		
		Set<Image> groupImages = new  HashSet<Image>();
		
		try
		{
			BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
			
			String originalName = file.getOriginalFilename();
			
			String imageName = getImageName(originalName);
			
			String imageExtension = getImageExtension(originalName);
			
			//Get small image
			Image imageSmall = new Image();
            byte[] imageSmallAsByte = convertImage(180,110,bufferedImage,imageExtension);
			Blob imageSmallBolb = new javax.sql.rowset.serial.SerialBlob(imageSmallAsByte);
			imageSmall.setAltText(imageName+" "+IMAGE_SMALL);
			imageSmall.setFormat(file.getContentType());
			imageSmall.setImageContent(imageSmallBolb);
			imageSmall.setSize(IMAGE_SMALL);
			//imageSmall.setGroup(group);
			groupImages.add(imageSmall);
			
			
			//Get Large image
			Image imageLarge = new Image();
            byte[] imageLargeAsByte = convertImage(800,150,bufferedImage,imageExtension);
			Blob imageLargeBolb = new javax.sql.rowset.serial.SerialBlob(imageLargeAsByte);
			imageLarge.setAltText(imageName+" "+IMAGE_LARGE);
			imageLarge.setFormat(file.getContentType());
			imageLarge.setImageContent(imageLargeBolb);
			imageLarge.setSize(IMAGE_LARGE);
			//imageLarge.setGroup(group);
			groupImages.add(imageLarge);
			
		}
		catch (IOException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return groupImages;
	}

	private String getImageExtension(String originalName)
	{
		String imageExtension =  originalName.lastIndexOf(".") != 1 ? originalName.substring(originalName.lastIndexOf(".")+1) : "jpeg";
		return imageExtension;
	}

	private String getImageName(String originalName)
	{
		String imageName = originalName.lastIndexOf(".") != 1 ? originalName.substring(0, originalName.lastIndexOf(".")) : "default";
		return imageName;
	}
	
	public Set<PostDocument> convertPostImage(Map<String, MultipartFile> uploadablePostImage) 
	{
		Set<PostDocument> postDocs = new HashSet<PostDocument>();
		
		if(uploadablePostImage != null && !uploadablePostImage.isEmpty())
		{
			MultipartFile postThumbnails = uploadablePostImage.get(0);
			
			for(Map.Entry<String,MultipartFile> entry : uploadablePostImage.entrySet())
			{
				
				MultipartFile file = entry.getValue();
				
				String originalName = file.getOriginalFilename();
				
				String imageName = getImageName(originalName);
				
				String imageExtension = getImageExtension(originalName);
				
				PostDocument postDoc=new PostDocument();
				
				postDoc.setAltText(imageName+"_"+this.IMAGE_LARGE);
				postDoc.setExtension(imageExtension);
				
				try
				{
					Blob imageLargeBolb = getConvertedImageAsBlob(file, imageExtension);
					
					postDoc.setDocContent(imageLargeBolb);
					
					postDoc.setFormat(file.getContentType());
					
					postDoc.setSize(this.IMAGE_LARGE);
					
					postDocs.add(postDoc);
				}
				catch (IOException | SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		return postDocs;
	}
	
	public Map<Integer, Map<Integer, PostDocument>> getPostDocAsMap(Set<Post> posts)
	{
		return posts.stream().collect(Collectors.toMap(Post:: getPostId, (Post p)->p.getPostDoc().stream().collect(Collectors.toMap(PostDocument::getDocId, (PostDocument pd)->pd))));
		 //postDocs.stream().collect(Collectors.toMap(PostDocument::getDocId, (PostDocument pd)->pd));
	}
	
	public Map<Integer, Post> getPostAsMap(Set<Post> posts)
	{
		return posts.stream().collect(Collectors.toMap(Post:: getPostId, (Post p)->p));
		 //postDocs.stream().collect(Collectors.toMap(PostDocument::getDocId, (PostDocument pd)->pd));
	}
	
	public Map<Integer, PostDocument> getPostDocsAsMap(Set<PostDocument> postDocs)
	{
		return postDocs.stream().collect(Collectors.toMap(PostDocument :: getDocId, (PostDocument pd) -> pd));
	}

	private Blob getConvertedImageAsBlob(MultipartFile file, String imageExtension) throws IOException, SerialException, SQLException
	{
		BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
		byte[] imageLargeAsByte = convertImage(600,500,bufferedImage,imageExtension);
		Blob imageLargeBolb = new javax.sql.rowset.serial.SerialBlob(imageLargeAsByte);
		
		return imageLargeBolb;
	}
}
