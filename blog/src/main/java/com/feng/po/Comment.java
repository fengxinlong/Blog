package com.feng.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_comment")
public class Comment {
	@Id
	@GeneratedValue
	private Long id;
	private String email;
	private String content;
	private String avatar;
	private String nickname;
	private boolean adminComment;
	/*
	*
	* */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	@ManyToOne
	private  Blog blog;
	@OneToMany(mappedBy = "parentComment")
	private List<Comment> replyComments=new ArrayList<>();

	/*
	* 相邻父类 只有一个
	* */
	@ManyToOne
	private Comment parentComment;

	public Comment() {
	}

	public Comment(String email, String content, String avatar, String nickname, Date createdTime, Blog blog, List<Comment> replyComments, Comment parentComment) {
		this.email = email;
		this.content = content;
		this.avatar = avatar;
		this.nickname = nickname;
		this.createdTime = createdTime;
		this.blog = blog;
		this.replyComments = replyComments;
		this.parentComment = parentComment;
	}

	public boolean isAdminComment() {
		return adminComment;
	}

	public void setAdminComment(boolean adminComment) {
		this.adminComment = adminComment;
	}

	public List<Comment> getReplyComments() {
		return replyComments;
	}

	public void setReplyComments(List<Comment> replyComments) {
		this.replyComments = replyComments;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", email='" + email + '\'' +
				", content='" + content + '\'' +
				", avatar='" + avatar + '\'' +
				", nickname='" + nickname + '\'' +
				", adminComment=" + adminComment +
				", createdTime=" + createdTime +
				", blog=" + blog +
				", replyComments=" + replyComments +
				", parentComment=" + parentComment +
				'}';
	}
}
