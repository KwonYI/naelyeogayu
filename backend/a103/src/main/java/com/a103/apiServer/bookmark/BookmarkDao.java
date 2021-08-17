package com.a103.apiServer.bookmark;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Bookmark;

@Repository
public interface BookmarkDao extends JpaRepository<Bookmark, Long>{
	List<Bookmark> findListBookmarkByMemberId(long memberId);
	Bookmark findBookmarkByMemberIdAndProductId(long memberId, long productId);
}
