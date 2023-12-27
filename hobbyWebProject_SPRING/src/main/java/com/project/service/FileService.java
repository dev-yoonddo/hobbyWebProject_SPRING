package com.project.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.project.vo.BoardVO;

@Component
public interface FileService {

	BoardVO fileupload(HttpServletRequest request, HttpServletResponse response);
	int fileDownload(HttpServletRequest request, HttpServletResponse response);

}
