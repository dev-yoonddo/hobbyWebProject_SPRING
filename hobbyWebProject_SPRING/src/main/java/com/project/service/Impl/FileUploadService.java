package com.project.service.Impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;

import com.project.vo.BoardVO;

public class FileUploadService{
	// 파일 업로드가 필요한 form이 전송됐을 때 실행되는 메서드
	public BoardVO fileupload(HttpServletRequest request, HttpServletResponse response) {
			int boardID = 0;
			String userID = null;
			String boardDate = null;
			String boardCategory = null;
			String boardTitle = null;
			String boardContent = null;
			String filename = null;
			String fileRealname = null;
			
			if (ServletFileUpload.isMultipartContent(request)) {
				try {
					DiskFileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload upload = new ServletFileUpload(factory);

					FileItemIterator fileItemIterator = upload.getItemIterator(request);
					// 전달 받은 값들과 같은 이름에 저장한다.
					while (fileItemIterator.hasNext()) {
						FileItemStream item = fileItemIterator.next();
						if (item.isFormField()) {
							String fieldName = item.getFieldName();
							String fieldValue = Streams.asString(item.openStream(), "UTF-8"); // Specify encoding if needed
							if ("boardID".equals(fieldName)) {
								boardID = Integer.parseInt(fieldValue);
							} else if ("userID".equals(fieldName)) {
								userID = fieldValue;
							} else if ("boardCategory".equals(fieldName)) {
								boardCategory = fieldValue;
							} else if ("boardTitle".equals(fieldName)) {
								boardTitle = fieldValue;
							} else if ("boardContent".equals(fieldName)) {
								boardContent = fieldValue;
							}
						} else {
							// 파일을 경로에 저장한다.
							filename = item.getName();
							if (filename != null && !filename.isEmpty()) {
								// git repository와 연결하면 경로가 바뀐다.
								String dir = "C:/Users/admin/git/hobbyWebProject_SPRING/hobbyWebProject_SPRING/src/main/webapp/resources/upload/";
								File fileupload = new File(dir);
								if (!fileupload.exists()) {
									fileupload.mkdirs();
								}
								fileRealname = generateUniqueFileName(filename);
								try (InputStream fileContent = item.openStream();
										FileOutputStream fileOutputStream = new FileOutputStream(
												new File(fileupload, fileRealname))) {

									byte[] buffer = new byte[4096];
									int bytesRead;
									while ((bytesRead = fileContent.read(buffer)) != -1) {
										fileOutputStream.write(buffer, 0, bytesRead);
									}
								}
							}
						}
					}
				} catch (FileUploadException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 데이터들을 vo객체에 저장한 뒤 리턴한다.
			BoardVO vo = new BoardVO(boardID, boardTitle, userID, boardDate, boardContent, boardCategory, filename, fileRealname);
			return vo;

		}

		// 유니크한 파일이름을 생성한다.
		private String generateUniqueFileName(String filename) {
			String baseName = filename.substring(0, filename.lastIndexOf("."));
			String fileExtension = filename.substring(filename.lastIndexOf("."));
			String uniqueID = UUID.randomUUID().toString();
			return baseName + "_" + uniqueID + fileExtension;
		}
}
