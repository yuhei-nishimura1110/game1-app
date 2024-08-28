package com.example.game.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.game.Forn.UserEditForm;
import com.example.game.Repository.UserRepository;
import com.example.game.Repository.User_infoRepository;
import com.example.game.Security.UserPrincipal;
import com.example.game.Service.Transformation;
import com.example.game.entity.User;
import com.example.game.entity.User_info;

@Controller
@RequestMapping("/home")
public class homeController {
	private final UserRepository userRepository;
	private final User_infoRepository user_infoRepository;

	public homeController(UserRepository userRepository, User_infoRepository user_infoRepository) {
		this.userRepository = userRepository;
		this.user_infoRepository = user_infoRepository;
	}

	@GetMapping()
	public String home(@AuthenticationPrincipal UserPrincipal userDetailsImpl, Model model,
			Transformation transformation) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		model.addAttribute("user", user);
		User_info user_info = user_infoRepository.getReferenceById(userDetailsImpl.getUser().getId());
		model.addAttribute("user_info", user_info);
		model.addAttribute("transformtion", transformation);
		return "home";
	}

	@GetMapping("/user")
	public String user(@AuthenticationPrincipal UserPrincipal userDetailsImpl, Model model) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		UserEditForm userEditForm = new UserEditForm(user.getId(), user.getEvaluation());
		model.addAttribute("user", user);
		model.addAttribute("userEditForm", userEditForm);

		return "user";
	}

	@Transactional
	@PostMapping("/useredit")
	public String update(@ModelAttribute @Validated UserEditForm userEditForm,
			@AuthenticationPrincipal UserPrincipal userDetailsImpl, Model model) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());

		user.setEvaluation(userEditForm.getEvaluation());
		userRepository.save(user);
		model.addAttribute("user", user);

		return "user";

	}

	@GetMapping("/information")
	public String information() {
		return "information";
	}

	@GetMapping("/prologue")
	public String prologue() {
		return "prologue";
	}

	@GetMapping("/explanation")
	public String explanation() {
		return "explanation";
	}

}
