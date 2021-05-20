package com.a103.apiServer.product.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	public void setS3Client() throws Exception;
	
	public String upload(MultipartFile file) throws Exception;
}
