package com.feng.po;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;
@Entity
@Table(name="t_type")
public class Type {
	@Id
	@GeneratedValue
	private  Long id;

	/*
	* 提交的是type对象 后端校验 通过注解
	* */

	@NotNull(message = "请添加一个分类")
	private String name;

	@OneToMany(mappedBy = "type")
	private List<Blog> blogs=new ArrayList<>();

	public Type() {
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Type{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}


}
