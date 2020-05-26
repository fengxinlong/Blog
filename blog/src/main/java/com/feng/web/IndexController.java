package com.feng.web;

import com.feng.NotFoundException;
import com.feng.service.BlogService;
import com.feng.service.TagService;
import com.feng.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	/*@GetMapping("/{id}/{name}")
	public String index(@PathVariable Integer id, @PathVariable String name){
////		int i=9/0;
//		String blog=null;
//		if(blog==null){
//			throw  new NotFoundException("博客不存在");
//		}
		System.out.println("-------------");
		return "index";
	}*/
	@Autowired
	private BlogService blogService;
	@Autowired
	private TypeService typeService;

	@Autowired
	private TagService tagService;

	@GetMapping("/")
	public String index(@PageableDefault(size=6,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
						Model model){
		model.addAttribute("page",blogService.listBlog(pageable));
		model.addAttribute("types",typeService.listTypeTop(6));
		model.addAttribute("tags",tagService.listTagTop(10));
		model.addAttribute("recommendBlogs",blogService.listRecommentBlogTop(8));
////		int i=9/0;
//		String blog=null;
//		if(blog==null){
//			throw  new NotFoundException("博客不存在");
//		}
//		System.out.println("-------------");
		return "index";
	}

	@PostMapping("/search")
	public String search(@PageableDefault(size=8,sort={"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
						 Model model,@RequestParam String query){

		/*
		* 不会自动处理query
		* */
		model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
		//返回要查的字符串
		model.addAttribute("query",query);

		return "search";
	}

	@GetMapping("/blog/{id}")
	public String blog(@PathVariable Long id,Model model){
		model.addAttribute("blog",blogService.getAndConvert(id));
		return "blog";
	}

	@GetMapping("/footer/newblog")
	public String newblogs(Model model){
		model.addAttribute("newblogs",blogService.listRecommentBlogTop(3));
		return "_fragments :: newblogList";
	}




}
