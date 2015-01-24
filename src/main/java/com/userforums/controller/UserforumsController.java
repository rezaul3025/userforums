package com.userforums.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.userforums.pojo.Group;
import com.userforums.pojo.Image;
import com.userforums.pojo.Post;
import com.userforums.pojo.PostDocument;
import com.userforums.service.GroupService;
import com.userforums.service.ImageService;
import com.userforums.service.PostDocService;
import com.userforums.service.PostService;
import com.userforums.utils.Utils;

@Controller
public class UserforumsController
{

	@Autowired
	private GroupService groupService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private PostService postService;

	@Autowired
	private PostDocService postDocService;

	@Autowired
	private Utils utils;

	@RequestMapping(value = "/")
	public ModelAndView index(HttpServletRequest request)
	{
		HttpSession session = request.getSession();

		ModelAndView mav = new ModelAndView("index");

		mav.addObject("welcome", "Welcome to user forums application");

		Map<Integer, Group> groups = groupService.getAllAsMap();

		mav.addObject("groups", groups);

		session.setAttribute("groupsInsession", groups);

		return mav;
	}

	@RequestMapping(value = "/create-group")
	public String createGroupInit(Model model)
	{
		model.addAttribute("group", new Group());

		return "fragments/group";
	}

	@RequestMapping(value = "/create-group", method = RequestMethod.POST)
	public String createGroup(@ModelAttribute Group group, @RequestParam("file") MultipartFile file, Model model)
	{

		model.addAttribute("description", "Please, use this below from to create user gruop");

		Image groupImage = new Image();

		if (!file.isEmpty())
		{
			/*
			 * try {
			 * 
			 * BufferedImage bImage = ImageIO.read(file.getInputStream());
			 * 
			 * BufferedImage convertedImage = utils.convertImage(80, 80, bImage,
			 * "jpg");
			 * 
			 * byte[] convertedImageAsByteArray =
			 * utils.convertBufferedImgeToByteArray(convertedImage, "jpg");
			 * 
			 * Blob groupImageBolb = new
			 * javax.sql.rowset.serial.SerialBlob(convertedImageAsByteArray);
			 * 
			 * 
			 * groupImage.setImageContent(groupImageBolb);
			 * groupImage.setFormat(file.getContentType());
			 * groupImage.setSize(0);
			 * groupImage.setHeight(convertedImage.getHeight());
			 * groupImage.setWidth(convertedImage.getWidth());
			 * groupImage.setAltText(file.getOriginalFilename());
			 * 
			 * 
			 * 
			 * } catch (SerialException e) {
			 * 
			 * e.printStackTrace(); } catch (SQLException e) {
			 * 
			 * e.printStackTrace(); } catch (IOException e) {
			 * 
			 * e.printStackTrace(); }
			 */

		}

		Set<Image> groupImages = utils.getGroupImages(file, group);

		group.setImage(groupImages);

		groupService.create(group);
		// imageService.create(groupImage);

		return "redirect:/";
	}

	@RequestMapping(value = "/group_details/{groupId}")
	public String groupDetails(Model model, HttpSession session, @PathVariable("groupId") Integer groupId)
	{
		@SuppressWarnings("unchecked")
		Map<Integer, Group> groups = (Map<Integer, Group>) session.getAttribute("groupsInsession");

		if (groups == null || groups.isEmpty())
		{
			return "redirect:/";
		}

		Group selectedGroup = groups.get(groupId);

		model.addAttribute("group", selectedGroup);

		session.setAttribute("slectedGroup", selectedGroup);

		Map<Integer, Post> postsAsMap = utils.getPostAsMap(selectedGroup.getPosts());

		session.setAttribute("postsInSession", postsAsMap);

		return "fragments/group_details";
	}

	@RequestMapping(value = "/create_post")
	public String createPost(Model model, HttpServletRequest request)
	{
		model.addAttribute("post", new Post());

		Map<String, MultipartFile> uploadableImageMap = new HashMap<String, MultipartFile>();

		HttpSession session = request.getSession();

		session.setAttribute("uploadableImageMap", uploadableImageMap);

		return "fragments/post";
	}

	@RequestMapping(value = "/create_post", method = RequestMethod.POST)
	public String createPost(Model model, @ModelAttribute Post post, MultipartHttpServletRequest request, HttpServletResponse response, @RequestParam(value = "files", required = false) MultipartFile[] files)
	{
		HttpSession session = request.getSession();

		/*
		 * for (MultipartFile mFiles : files) { String fType =
		 * mFiles.getContentType(); String t = fType; }
		 */

		@SuppressWarnings("unchecked")
		Map<String, MultipartFile> uploadableImageMap = (HashMap<String, MultipartFile>) session.getAttribute("uploadableImageMap");

		Set<PostDocument> postDocs = utils.convertPostImage(uploadableImageMap);

		post.setPostDoc(postDocs);

		Group group = (Group) session.getAttribute("slectedGroup");
		model.addAttribute("group", group);

		post.setGroup(group);

		postService.create(post);

		// onchange="readURL(this, 'module')" display on

		return "fragments/group_details";
	}

	@RequestMapping(value = "/view-post/{postId}")
	public String viewPost(Model model, HttpServletRequest request, @PathVariable("postId") Integer postId)
	{
		HttpSession session = request.getSession();

		@SuppressWarnings("unchecked")
		Map<Integer, Post> postsAsMap = (Map<Integer, Post>) session.getAttribute("postsInSession");

		if (postsAsMap != null && !postsAsMap.isEmpty())
		{
			Post post = postsAsMap.get(postId);
			model.addAttribute("viewpost", post);
			session.setAttribute("postDocuments", utils.getPostDocsAsMap(post.getPostDoc()));
		}

		return "fragments/viewpost";
	}

	@RequestMapping(value = "/add_uploadable_image", method = RequestMethod.POST)
	public @ResponseBody String addUploadableImage(Model model, HttpServletRequest request, @RequestParam(value = "files", required = false) MultipartFile[] files)
	{
		HttpSession session = request.getSession();

		@SuppressWarnings("unchecked")
		Map<String, MultipartFile> uploadableImageMap = (HashMap<String, MultipartFile>) session.getAttribute("uploadableImageMap");

		StringBuffer strBuffer = new StringBuffer();

		for (MultipartFile mFile : files)
		{
			String imageName = mFile.getOriginalFilename();
			imageName = imageName.substring(0, imageName.lastIndexOf("."));
			uploadableImageMap.put(imageName, mFile);

		}

		session.setAttribute("uploadableImageMap", uploadableImageMap);

		strBuffer.append("<table class='table'>");

		for (Map.Entry<String, MultipartFile> entry : uploadableImageMap.entrySet())
		{
			strBuffer.append("<tr>");
			strBuffer.append("<td><img src='/userforums/display-uploadable-image/" + entry.getKey() + "' height='25' width='25' /> </td>");
			strBuffer.append("<td>" + entry.getKey() + "</td>");
			strBuffer.append("<td>" + entry.getValue().getContentType() + "</td>");
			strBuffer.append("<td> <button type='button' class='btn btn-default btn-sm' onclick='deleteUploadableImage(\"" + entry.getKey() + "\")'><span class='glyphicon glyphicon-remove'></span></button></td>");
			strBuffer.append("</tr>");
		}

		strBuffer.append("</table>");

		model.addAttribute("uploadImageDisplay", session.getAttribute("uploadableImageMap"));

		return strBuffer.toString();
	}

	@RequestMapping(value = "/delete_uploadable_image/{imageName}")
	public @ResponseBody String deleteUploadableImage(HttpServletRequest request, @PathVariable("imageName") String imageName)
	{
		HttpSession session = request.getSession();

		@SuppressWarnings("unchecked")
		Map<String, MultipartFile> uploadableImageMap = (HashMap<String, MultipartFile>) session.getAttribute("uploadableImageMap");

		StringBuffer strBuffer = new StringBuffer();

		if (uploadableImageMap != null && !uploadableImageMap.isEmpty())
		{
			uploadableImageMap.remove(imageName);

			session.setAttribute("uploadableImageMap", uploadableImageMap);

			strBuffer.append("<table class='table'>");

			for (Map.Entry<String, MultipartFile> entry : uploadableImageMap.entrySet())
			{
				strBuffer.append("<tr>");
				strBuffer.append("<td><img src='/userforums/display-uploadable-image/" + entry.getKey() + "' height='25' width='25' /> </td>");
				strBuffer.append("<td>" + entry.getKey() + "</td>");
				strBuffer.append("<td>" + entry.getValue().getContentType() + "</td>");
				strBuffer.append("<td> <button type='button' class='btn btn-default btn-sm' onclick='deleteUploadableImage(\"" + entry.getKey() + "\")'><span class='glyphicon glyphicon-remove'></span></button></td>");
				strBuffer.append("</tr>");
			}

			strBuffer.append("</table>");
		}

		// model.addAttribute("uploadImageDisplay",
		// session.getAttribute("uploadableImageMap"));

		return strBuffer.toString();
	}

	@RequestMapping(value = "/display-uploadable-image/{imageName}")
	public void displayUploadableImage(HttpServletRequest request, HttpServletResponse response, @PathVariable("imageName") String imageName)
	{
		HttpSession session = request.getSession();

		@SuppressWarnings("unchecked")
		Map<String, MultipartFile> uploadableImageMap = (HashMap<String, MultipartFile>) session.getAttribute("uploadableImageMap");

		try
		{
			OutputStream out = response.getOutputStream();
			IOUtils.copy(uploadableImageMap.get(imageName).getInputStream(), out);
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/group_img/{id}")
	public @ResponseBody void renderGroupImg(HttpServletResponse response, @PathVariable("id") Integer imgId)
	{
		Logger.getLogger(UserforumsController.class.getName()).log(Level.SEVERE, null, "@@@@@@@@@@@@@@@@@@@@@@ @         @@@@@@@@@@@@@@@@@@@");
		response.setContentType("text/html;charset=UTF-8");

		try
		{
			Image image = imageService.getById(imgId);

			response.setHeader("Content-Disposition", "inline;filename=tom");
			response.setContentType(image.getFormat());

			OutputStream out = response.getOutputStream();

			IOUtils.copy(image.getImageContent().getBinaryStream(), out);

			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		catch (SQLException ex)
		{
			Logger.getLogger(UserforumsController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*@RequestMapping(value = "/post_doc/{postDocId}")
	public @ResponseBody void renderPostImage(HttpServletResponse response, HttpServletRequest request, @PathVariable("postDocId") Integer postDocId)
	{
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();

		@SuppressWarnings("unchecked")
		Map<Integer, PostDocument> postDocsAsMap = (Map<Integer, PostDocument>) session.getAttribute("postDocuments");

		if (postDocsAsMap != null && !postDocsAsMap.isEmpty())
		{

			PostDocument postDoc = postDocsAsMap.get(postDocId);

			response.setHeader("Content-Disposition", "inline;filename=tom");
			response.setContentType(postDoc.getFormat());

			try
			{
				OutputStream out = response.getOutputStream();
				IOUtils.copy(postDoc.getDocContent().getBinaryStream(), out);

				out.flush();
				out.close();
			}
			catch (IOException | SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/post_thumbnails/{postId}")
	public @ResponseBody void getPostThumbnails(HttpServletResponse response, HttpServletRequest request, @PathVariable("postId") Integer postId)
	{
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();

		@SuppressWarnings("unchecked")
		Map<Integer, Post> postsAsMap = (Map<Integer, Post>) session.getAttribute("postsInSession");

		if (postsAsMap != null && !postsAsMap.isEmpty())
		{
			Post post = postsAsMap.get(postId);

			if (post != null)
			{
				Set<PostDocument> postDocs = post.getPostDoc();

				if (postDocs.size() > 0)
				{
					PostDocument postDoc = (PostDocument) postDocs.toArray()[0];

					response.setHeader("Content-Disposition", "inline;filename=tom");
					response.setContentType(postDoc.getFormat());

					try
					{
						OutputStream out = response.getOutputStream();
						IOUtils.copy(postDoc.getDocContent().getBinaryStream(), out);

						out.flush();
						out.close();
					}
					catch (IOException | SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	*/

	@RequestMapping(value = "/render_post_doc/{postId}/{postDocId}")
	public @ResponseBody void postDocRender(HttpServletResponse response, HttpServletRequest request, @PathVariable("postId") Integer postId, @PathVariable("postDocId") Integer postDocId)
	{
		response.setContentType("text/html;charset=UTF-8");

		if (postDocId == 0)
		{
			HttpSession session = request.getSession();

			@SuppressWarnings("unchecked")
			Map<Integer, Post> postsAsMap = (Map<Integer, Post>) session.getAttribute("postsInSession");

			if (postsAsMap != null && !postsAsMap.isEmpty())
			{
				Post post = postsAsMap.get(postId);

				if (post != null)
				{
					Set<PostDocument> postDocs = post.getPostDoc();

					if (postDocs.size() > 0)
					{
						PostDocument postDoc = (PostDocument) postDocs.toArray()[0];
						postDocId = postDoc.getDocId();
					}
				}
			}
		}

		PostDocument postDoc = postDocService.getById(postDocId);

		if (postDoc != null)
		{
			response.setHeader("Content-Disposition", "inline;filename=tom");
			response.setContentType(postDoc.getFormat());

			try
			{
				OutputStream out = response.getOutputStream();
				IOUtils.copy(postDoc.getDocContent().getBinaryStream(), out);

				out.flush();
				out.close();
			}
			catch (IOException | SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
