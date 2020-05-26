package com.feng.service;

import com.feng.po.Blog;
import com.feng.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
	Blog getBlog(Long id);

	/*
	* 条件封装成对象
	*
	* */
	Page<Blog> listBlog(Pageable pageable,BlogQuery blog);

	Blog savaBlog(Blog blog);

	Blog updateBlog(Long id,Blog blog);

	void deleteBlog(Long id);

	Page<Blog> listBlog(Pageable pageable);
	Page<Blog> listBlog(String query,Pageable pageable);
	List<Blog> listRecommentBlogTop(Integer size);

	Page<Blog> listBlog(Long tagId,Pageable pageable);

	/*
	* 处理前端内容格式
	* */
	Blog getAndConvert(Long id);

	/*
	* 归档
	* */
	Map<String,List<Blog>> archiveBlog();

	Long countBlog();


}
