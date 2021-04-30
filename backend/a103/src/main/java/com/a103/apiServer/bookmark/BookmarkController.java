package com.a103.apiServer.bookmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.model.Bookmark;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

	@Autowired
	private BookmarkDao bookmarkDao;

	private static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);

	@PostMapping(value = "")
	public ResponseEntity createBookmark(@RequestBody Bookmark bookmark) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			Bookmark findBookmark = bookmarkDao.findBookmarkByMemberIdAndProductId(bookmark.getMemberId(),
					bookmark.getProductId());

			if (findBookmark != null) {
				result.put("success", "fail");
				entity = new ResponseEntity(result, HttpStatus.OK);
			} else {
				bookmarkDao.save(bookmark);
				result.put("success", "success");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@GetMapping(value = "/list/{member_id}")
	public ResponseEntity getBookmarkList(@PathVariable("member_id") long memberId) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			List<Bookmark> bookmarkList = bookmarkDao.findListBookmarkByMemberId(memberId);

			if (bookmarkList.size() != 0) {
				result.put("success", "success");
				result.put("data", bookmarkList);
				entity = new ResponseEntity(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@DeleteMapping(value = "")
	public ResponseEntity deleteBookmark(@RequestHeader(value = "id") long bookmarkId) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			bookmarkDao.deleteById(bookmarkId);
			result.put("success", "success");
			entity = new ResponseEntity(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}
}
