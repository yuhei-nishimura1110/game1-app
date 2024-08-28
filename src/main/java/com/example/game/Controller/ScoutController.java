package com.example.game.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.game.Forn.ScoutForm;
import com.example.game.Repository.Family_nameRepository;
import com.example.game.Repository.PlayerRepository;
import com.example.game.Repository.UserRepository;
import com.example.game.Repository.User_infoRepository;
import com.example.game.Scout.Scout;
import com.example.game.Security.UserPrincipal;
import com.example.game.Service.Transformation;
import com.example.game.entity.Player;
import com.example.game.entity.User;
import com.example.game.entity.User_info;

@RequestMapping("/home/scout")
@Controller
public class ScoutController {

	private final UserRepository userRepository;
	private final PlayerRepository playerRepository;
	private final Family_nameRepository family_nameRepository;
	private final User_infoRepository user_infoRepository;

	public ScoutController(UserRepository userRepository, PlayerRepository playerRepository,
			Family_nameRepository family_nameRepository, User_infoRepository userinfoRepository) {
		this.userRepository = userRepository;
		this.playerRepository = playerRepository;
		this.family_nameRepository = family_nameRepository;
		this.user_infoRepository = userinfoRepository;
	}

	@GetMapping()
	public String scout(@AuthenticationPrincipal UserPrincipal userDetailsImpl, Model model,
			Transformation transformation) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		ScoutForm scoutForm = new ScoutForm(family_nameRepository, user.getId(), "");
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		model.addAttribute("scoutForm", scoutForm);
		model.addAttribute("transformation", transformation);
		return "scout";
	}

	@Transactional
	@PostMapping("/result")
	public String scoutresult(@ModelAttribute @Validated ScoutForm scoutForm2,
			@AuthenticationPrincipal UserPrincipal userDetailsImpl, Model model, Transformation transformation,
			Scout scout, @PageableDefault(size = 1000) Pageable pe) {

		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		Player player = scout.Scouta(scoutForm2.getName(), user, playerRepository);
		user_info.setMoney(user_info.getMoney() - 1000);
		user_infoRepository.save(user_info);
		Page<Player> players = playerRepository.findByUser_id(user.getId(), pe);
		transformation.time(user_info, 1, user_infoRepository, players, playerRepository);
		model.addAttribute("player", player);
		model.addAttribute("scoutForm", scoutForm2);
		ScoutForm scoutForm = new ScoutForm(family_nameRepository, user.getId(), "");
		model.addAttribute("scoutForm", scoutForm);
		model.addAttribute("transformation", transformation);
		model.addAttribute("user_info", user_info);
		return "scoutresult";
	}
}
