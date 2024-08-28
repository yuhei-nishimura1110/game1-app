package com.example.game.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class authController {

	@GetMapping("/login") // "/login"というURLに対するGETリクエストを処理します
	public String login() {
		return "auth/login"; // login.htmlを表示します
	}

}
