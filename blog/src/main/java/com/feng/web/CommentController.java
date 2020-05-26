package com.feng.web;

import com.feng.po.Comment;
import com.feng.service.BlogService;
import com.feng.service.CommentService;
import com.feng.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private BlogService blogService;
	@Value("${comment.avatar}")
	private  String avatar;

	/*
	* 根据id获取评论列表
	*
	* */
	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId, Model model){
		model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
		return "blog :: commentList";
	}
/*
* 发布之后提交信息
* */
	@PostMapping("/comments")
	public String post(Comment  comment, HttpSession session){

		/*
		* 设置comment 里的blog对象
		* */
		Long blogid=comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogid));
		User user =(User)session.getAttribute("user");
		if(user !=null){
			comment.setAvatar(user.getAvatar());
			comment.setAdminComment(true);

		}else{
			comment.setAvatar(avatar);
		}

		commentService.saveComment(comment);
		return "redirect:/comments/"+comment.getBlog().getId();
	}
}
