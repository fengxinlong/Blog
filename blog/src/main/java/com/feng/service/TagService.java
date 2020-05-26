package com.feng.service;

import com.feng.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
	Tag saveTag(Tag tag);

	Tag getTag(Long id);
	Tag getTagByName(String name);

	Page<Tag> listTag(Pageable pageable);

	List<Tag> listTag();
	Tag updateTag(Long id,Tag type);
	List<Tag> listTag(String ids);
	List<Tag> listTagTop(Integer size);
	void deleteTag(Long id);

}
