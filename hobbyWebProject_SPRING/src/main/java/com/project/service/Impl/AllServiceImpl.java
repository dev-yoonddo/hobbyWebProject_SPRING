package com.project.service.Impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.dao.BoardDAO;
import com.project.dao.UserDAO;
import com.project.service.AllService;
import com.project.service.BoardService;
import com.project.service.CmtService;
import com.project.service.FileService;
import com.project.service.HeartService;
import com.project.service.UserService;
import com.project.vo.BoardVO;
import com.project.vo.CommentVO;
import com.project.vo.HeartVO;
import com.project.vo.UserVO;

@Service
public abstract class AllServiceImpl implements AllService{
	
	private final UserService userService;
    private final BoardService boardService;
    private final CmtService cmtService;
    private final HeartService heartService;
    private final FileService fileService;

    @Autowired
    public AllServiceImpl(
            UserService userService,
            BoardService boardService,
            CmtService cmtService,
            HeartService heartService,
            FileService fileService
    ) {
        this.userService = userService;
        this.boardService = boardService;
        this.cmtService = cmtService;
        this.heartService = heartService;
        this.fileService = fileService;
    }
    
    

}
