package com.feng.service;

import com.feng.po.Comment;

import java.util.List;

/*
* 获取列表
* */
public interface CommentService {
	List<Comment> listCommentByBlogId(Long blogId);

	Comment saveComment(Comment comment);

}
