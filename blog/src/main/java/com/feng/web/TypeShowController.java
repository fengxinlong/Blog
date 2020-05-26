package com.feng.web;


import com.feng.po.Type;
import com.feng.service.BlogService;
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

import java.util.List;

@Controller
public class TypeShowController {

	@Autowired
	private TypeService typeService;

	@Autowired
	private BlogService blogService;

	/*
	* 拿到所有分类并倒序排列
	*
	* */
	@GetMapping("/types/{id}")
	public String types(@PageableDefault(size=2,sort={"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
						@PathVariable Long id , Model model){


		List<Type> types=typeService.listTypeTop(10000);
		if(id==-1){
			id=types.get(0).getId();
		}
		BlogQuery blogQuery = new BlogQuery();
		blogQuery.setTypeId(id);
		model.addAttribute("types",types);
		model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
		model.addAttribute("activeTypeId",id);
		return "types";
	}


}
