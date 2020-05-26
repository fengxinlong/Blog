package com.feng.po;

import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_blog")
public class Blog {
	@Id
	@GeneratedValue   /*自动生成
							*/
	private  Long id;
	private String title;
	@Basic(fetch = FetchType.LAZY)
	@Lob//大字段类型 ,第一次初始化时才有效 用get时再加载
	private String content;
	private String firstPicture;
	private String flag;
	private Integer views;
	//赞赏
	private boolean appreciation;
	//转载声明
	private boolean shareStatement;
	//评论区
	private boolean commentabled;
	private boolean published;
	private boolean recommend;
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	/*
	* 关系维护端
	* */
	@ManyToOne()
	private Type type;
	/*
	* 当新增一个博客也同时对应增加一个标签
	* 级联新增
	* */
	@ManyToMany(cascade = {CascadeType.PERSIST})
	private List<Tag> tags= new ArrayList<>();
	@ManyToOne()
	private User user;
	@OneToMany(mappedBy = "blog")
	private List<Comment> comments = new ArrayList<>();
	@Transient
	private String tagIds;


	public void init(){
		this.tagIds = tagsToIds(this.getTags());
	}

	// 1,2,3
	private String tagsToIds(List<Tag> tags){
		if(!tags.isEmpty()){
			StringBuffer ids = new StringBuffer();
			boolean flag = false;
			for(Tag tag:tags){
				if(flag){
					ids.append(",");
				}else {
					flag=true;
				}
				ids.append(tag.getId());
			}
			return ids.toString();
		}else {
			return tagIds;
		}
	}

	@Override
	public String toString() {
		return "Blog{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", firstPicture='" + firstPicture + '\'' +
				", flag='" + flag + '\'' +
				", views=" + views +
				", appreciation=" + appreciation +
				", shareStatement=" + shareStatement +
				", commentabled=" + commentabled +
				", published=" + published +
				", recommend=" + recommend +
				", description='" + description + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", type=" + type +
				", tags=" + tags +
				", user=" + user +
				", comments=" + comments +
				", tagIds='" + tagIds + '\'' +
				'}';
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Blog() {
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFirstPicture() {
		return firstPicture;
	}

	public void setFirstPicture(String firstPicture) {
		this.firstPicture = firstPicture;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public boolean isAppreciation() {
		return appreciation;
	}

	public void setAppreciation(boolean appreciation) {
		this.appreciation = appreciation;
	}

	public boolean isShareStatement() {
		return shareStatement;
	}

	public void setShareStatement(boolean shareStatement) {
		this.shareStatement = shareStatement;
	}

	public boolean isCommentabled() {
		return commentabled;
	}

	public void setCommentabled(boolean commentabled) {
		this.commentabled = commentabled;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
