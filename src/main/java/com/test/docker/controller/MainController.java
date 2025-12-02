package com.test.docker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String index(Model model) {
		
		//스프링 부트가 실행 중인 컨테이너 ID를 가져오기
		String hostname = System.getenv("HOSTNAME"); 
		
		if (hostname == null) {
			hostname = "내 컴퓨터(로컬)";
		}
		
		model.addAttribute("hostname", hostname);
		
		return "index";
	}
	
}