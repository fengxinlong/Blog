package com.feng.dao;

import com.feng.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
*
* */
public interface CommentRepository extends JpaRepository<Comment,Long>{


	List<Comment> findByBlogIdAndParentCommentNull(Long blogId,Sort sort);
}
