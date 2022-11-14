package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UploadController {
	
	// upLoadForm 시작화면
	@RequestMapping(value = "upLoadFormStart") // index에서 가져옴
	public String upLoadFormStart(Model model) {
		System.out.println("UploadController upLoadFormStart Start...");
		
		return "upLoadFormStart"; // view
	}
	
	@RequestMapping(value = "uploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		System.out.println("uploadForm GET Start...");
		System.out.println();
	}
	// 파일 업로드
	@RequestMapping(value = "uploadForm", method = RequestMethod.POST)
	public String uploadForm( HttpServletRequest request, MultipartFile file1, Model model) throws IOException, Exception {
		// Servlet 상속받지 못했을 때 realPath 불러오는 방법
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		
		System.out.println("uploadForm POST Strat...");
		log.info("originalName: " + file1.getOriginalFilename());
		log.info("size: " + file1.getSize());
		log.info("contentType: " + file1.getContentType());
		log.info("uploadPath: " + uploadPath);
		String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
		log.info("savedName: " + savedName);
		model.addAttribute("savedName", savedName);
		
		return "uploadResult";
	}
	
	// UploadController에서만 사용하려고  private씀
	private String uploadFile(String originalName, byte[] fileData, String uploadPath)
			throws Exception {
		 // universally unique identifier (UUID). 
		UUID uid = UUID.randomUUID(); // 국제적으로 랜덤한 아이디
		// requestPath = requestPath + "/resources/image";
		System.out.println("uploadPath->"+uploadPath);
		// Directory 생성
		File fileDirectory = new File(uploadPath);
		if (!fileDirectory.exists()) {
			fileDirectory.mkdirs(); // make directory -> 신규 폴더 생성
			System.out.println("업로드용 폴더 생성 : " + uploadPath);
		}
		
		String savedName = uid.toString() + "_" + originalName; // 두 번 올려도 번호만 달라지고 계속 올라감
		log.info("savedName: " + savedName);
		File target = new File(uploadPath, savedName);
// 		File target = new File(requestPath, savedName);
		// File UpLoad --> /uploadPath/UUID + _ + originalName
		FileCopyUtils.copy(fileData, target); // org.springframework.util.FileCopyUtils
		
		return savedName;
	}
	// 파일 삭제
	@RequestMapping(value = "uploadFileDelete", method = RequestMethod.GET)
	public String uploadFileDelete( HttpServletRequest request, Model model)
			throws Exception {
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		String deleteFile = uploadPath + "06fcc17e-012e-4c06-9fe6-00b26aeca80e_7.jpg"; // 삭제할 이미지 파일명
		log.info("deleteFile: " + deleteFile);
		System.out.println("uploadFileDelete POST Start...");
		int delResult = upFileDelete(deleteFile);
		log.info("deleteFile result-> " + delResult);
		model.addAttribute("deleteFile", deleteFile);
		model.addAttribute("delResult", delResult);
		
		return "uploadResult";
	}
	
	private int upFileDelete(String deleteFileName) throws Exception {
		int result = 0;
		log.info("upFileDelete result-> " + deleteFileName);
		File file = new File(deleteFileName);
		if(file.exists()) {
			if(file.delete()) {
				System.out.println("파일삭제 성공");
				result = 1;
			}
			else {
				System.out.println("파일삭제 실패");
				result = 0;
			}
		}
		else {
			System.out.println("파일이 존재하지 않습니다.");
			result = -1;
		}
		return result;
	}
	
	
}
