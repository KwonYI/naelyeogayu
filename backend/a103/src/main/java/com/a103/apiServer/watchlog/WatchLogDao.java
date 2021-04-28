package com.a103.apiServer.watchlog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Watchlog;

@Repository
public interface WatchLogDao extends JpaRepository<Watchlog, Long>{
	List<Watchlog> findListWatchlogByProductId(long productId);
	
}
