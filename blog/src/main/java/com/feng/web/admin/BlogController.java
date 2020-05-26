package com.feng.web.admin;

import com.feng.po.Blog;
import com.feng.po.User;
import com.feng.service.BlogService;
import com.feng.service.TagService;
import com.feng.service.TypeService;
import com.feng.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.jar.Attributes;

@Controller
@RequestMapping("/admin")
public class BlogController {

	private static final String INPUT="admin/blogs-input";
	private static final String LIST="admin/blogs";
	private static final String REDIRECT_LIST="redirect:/admin/blogs";

	@Autowired
	private BlogService blogService;
	@Autowired
	private TypeService typeService;

	@Autowired
	private TagService tagService;

	@GetMapping("/blogs")
	public String blogs(Model model, @PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog){

		model.addAttribute("types",typeService.listType());
		model.addAttribute("page",blogService.listBlog(pageable,blog));


		return LIST;
	}

	@PostMapping("/blogs/search")
	public String Search(Model model, @PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog){


		model.addAttribute("page",blogService.listBlog(pageable,blog));


		return "admin/blogs::blogList";
	}
	@GetMapping("/blogs/input")
	public String input(Model model){


		setTypeAndTag(model);

		model.addAttribute("blog",new Blog());
		return INPUT;
	}

	private  void setTypeAndTag(Model model){
		model.addAttribute("types",typeService.listType());
		model.addAttribute("tags",tagService.listTag());
	}


	@GetMapping("/blogs/{id}/input")
	public String editInput(@PathVariable Long id, Model model){


		setTypeAndTag(model);
		Blog blog =blogService.getBlog(id);
		blog.init();

		model.addAttribute("blog",blog);
		return INPUT;
	}


/*
* 从session里获取user
* */
	@PostMapping("/blogs")
	public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
		//获得用户
		blog.setUser((User) session.getAttribute("user"));
		//对象数据初始化
		blog.setType(typeService.getType(blog.getType().getId()));
		blog.setTags(tagService.listTag(blog.getTagIds()));
		Blog b;
		if(blog.getId()==null){
			b=blogService.savaBlog(blog);
		}else{
			b=blogService.updateBlog(blog.getId(),blog);
		}


		if(b == null){
			attributes.addFlashAttribute("message","操作失败");
		}else{
			attributes.addAttribute("message","操作成功");
		}

		return REDIRECT_LIST;
	}

	@GetMapping("/blogs/{id}/delete")
	public String delete(RedirectAttributes attributes,@PathVariable long id){
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message","删除成功");
		return REDIRECT_LIST;

	}


}
