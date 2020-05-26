package com.feng.service;

import com.feng.NotFoundException;
import com.feng.dao.BlogRepository;
import com.feng.po.Blog;
import com.feng.po.Type;
import com.feng.util.MarkdownUtils;
import com.feng.util.MyBeanUtils;
import com.feng.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.sql.Time;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
	@Autowired
	private BlogRepository blogRepository;

	@Override
	public Blog getBlog(Long id) {
		/*
		* 用插件展示内容 Markdown转换HTML
		* */
		return blogRepository.getOne(id);
	}

	@Override
	public Page<Blog> listBlog( Pageable pageable, BlogQuery blog) {



		return blogRepository.findAll(new Specification<Blog>() {
			/*
			* 处理查询条件
			* root:可以获得表的字段 属性值
			* criteriaQuery:容器，存放规则
			* criteriaBuilder：两个条件相等 构建
			* */
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if(!"".equals(blog.getTitle())&& blog.getTitle()!=null){
					predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
				}
				if(blog.getTypeId()!=null){
					predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
				}
				if(blog.isRecommend()){
					predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.getTypeId()));
				}
					cq.where(predicates.toArray(new Predicate[predicates.size()]));
				/*
				* 自动拼接 自动组合
				* */
				return null;
			}
		},pageable);
	}

	@Transactional
	@Override
	public Blog savaBlog(Blog blog) {
		//新增
		if(blog.getId()==null){
			blog.setCreateTime(new Date());
			blog.setUpdateTime(new Date());
			blog.setViews(0);
		}else {
			//更新时间
			blog.setUpdateTime(new Date());
		}
		return blogRepository.save(blog);
	}
	@Transactional
	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog b = blogRepository.getOne(id);

		if(b==null){
			throw new NotFoundException("该博客不存在");
		}
		BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
		b.setUpdateTime(new Date());
		return blogRepository.save(b);
	}
	@Transactional
	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		return blogRepository.findAll(pageable);
	}

	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {
		return blogRepository.findByQuery(query,pageable);
	}

	@Override
	public List<Blog> listRecommentBlogTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
		Pageable pageable = PageRequest.of(0,size,sort);
		return blogRepository.findTop(pageable);
	}

	@Override
	public Page<Blog> listBlog(Long tagId, Pageable pageable) {
		return blogRepository.findAll(new Specification<Blog>() {
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				//通过关联
				Join join = root.join("tags");
				return cb.equal(join.get("id"),tagId);
			}
		},pageable);
	}

	@Transactional
	@Override
	public Blog getAndConvert(Long id) {
		Blog blog = blogRepository.getOne(id);

		if(blog == null){
			throw new NotFoundException("该博客不存在");
		}
		/*
		* 新建一个
		* */
		Blog b = new Blog();
		BeanUtils.copyProperties(blog,b);
		String content=b.getContent();

		/*
		* 操作会改变数据库
		* */
		String formatContent = MarkdownUtils.markdownToHtmlExtensions(content);
		b.setContent(formatContent);

		blogRepository.updateViews(id);

		return b;
	}

	@Override
	public Map<String, List<Blog>> archiveBlog() {
		List<String> years=blogRepository.findGroupYear();
		Map<String,List<Blog>> map = new HashMap<>();
		for(String year:years){
			map.put(year,blogRepository.findByYear(year));
		}
		return map;
	}

	@Override
	public Long countBlog() {
		return blogRepository.count();
	}
}
