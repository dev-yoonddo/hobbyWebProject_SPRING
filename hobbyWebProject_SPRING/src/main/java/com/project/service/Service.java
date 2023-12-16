package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.service.Impl.BoardServiceImpl;
import com.project.service.Impl.CmtServiceImpl;
import com.project.service.Impl.FileServiceImpl;
import com.project.service.Impl.HeartServiceImpl;
import com.project.service.Impl.UserServiceImpl;

@Component
public class Service{
	public static Service service;
	public static UserService urservice;
	public static BoardService bdservice;
	public static CmtService cmtservice;
	public static HeartService htservice;
	public static FileService fileservice;
	
	public static void setService(Service service) {
		Service.service = service;
	}
	public void setUrservice(UserService urservice) {
		Service.urservice = urservice;
	}
	public void setBdservice(BoardService bdservice) {
		Service.bdservice = bdservice;
	}
	public void setCmtservice(CmtService cmtservice) {
		Service.cmtservice = cmtservice;
	}
	public void setHtservice(HeartService htservice) {
		Service.htservice = htservice;
	}
	public void setFileservice(FileService fileservice) {
		Service.fileservice = fileservice;
	}
	
	
	
}
