package com.example.game.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.game.Forn.PartyForm;
import com.example.game.Repository.PartyRepository;
import com.example.game.Repository.PlayerRepository;
import com.example.game.Repository.UserRepository;
import com.example.game.Repository.User_infoRepository;
import com.example.game.Security.UserPrincipal;
import com.example.game.Service.Transformation;
import com.example.game.entity.Party;
import com.example.game.entity.Player;
import com.example.game.entity.User;
import com.example.game.entity.User_info;

@Controller
@RequestMapping("home/organization")
public class organizationController {
	private final PlayerRepository playerRepository;
	private final UserRepository userRepository;
	private final PartyRepository partyRepository;
	private final User_infoRepository user_infoRepository;

	public organizationController(PlayerRepository playerRepository, UserRepository userRepository,
			PartyRepository partyRepository, User_infoRepository user_infoRepository) {
		this.playerRepository = playerRepository;
		this.userRepository = userRepository;
		this.partyRepository = partyRepository;
		this.user_infoRepository = user_infoRepository;
	}

	@GetMapping("/{id}")
	public String index(@PageableDefault(size = 1000) Pageable pageable, Model model,
			@AuthenticationPrincipal UserPrincipal userDetailsImpl,
			Transformation transformation, @PathVariable(name = "id") Integer id) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		model.addAttribute("id", id);
		switch (id) {
		case 1 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJobLessThan(user.getId(), 13, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 2 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 13, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 3 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 14, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 4 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 15, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 5 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 16, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 6 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 17, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 7 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 18, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		}

		Party party = partyRepository.findByUser_idAndCount(user.getId(), id);
		model.addAttribute("party", party);
		PartyForm partyForm = new PartyForm(null, null, null, null, null, null, null, null);
		model.addAttribute("transformation", transformation);
		return "organization";
	}

	@PostMapping("/{id}/ed")
	public String inded(@PageableDefault(size = 100) Pageable pageable, Model model,
			@AuthenticationPrincipal UserPrincipal userDetailsImpl,
			Transformation transformation, @ModelAttribute @Validated PartyForm partyForm,
			RedirectAttributes redirectAttributes, @PathVariable(name = "id") Integer id) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		switch (id) {
		case 1 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJobLessThan(user.getId(), 13, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 2 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 13, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 3 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 14, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 4 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 15, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 5 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 16, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 6 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 17, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		case 7 -> {
			Page<Player> playerPage = playerRepository.findByUser_idAndJob(user.getId(), 18, pageable);
			model.addAttribute("playerPage", playerPage);
		}
		}
		Party party = partyRepository.findByUser_idAndCount(user.getId(), id);
		model.addAttribute("party", party);
		model.addAttribute("transformation", transformation);
		if (transformation.party(partyForm.getPlayer_id1(), partyForm.getPlayer_id2(), partyForm.getPlayer_id3(),
				partyForm.getPlayer_id4(), partyForm.getPlayer_id5(), partyForm.getPlayer_id6(),
				partyForm.getPlayer_id7(), partyForm.getPlayer_id8())) {
			if (partyForm.getPlayer_id1() != null) {
				party.setPlayer1(playerRepository.getReferenceById(partyForm.getPlayer_id1()));
			} else {
				party.setPlayer1(null);
			}
			if (partyForm.getPlayer_id2() != null) {
				party.setPlayer2(playerRepository.getReferenceById(partyForm.getPlayer_id2()));
			} else {
				party.setPlayer2(null);
			}
			if (partyForm.getPlayer_id3() != null) {
				party.setPlayer3(playerRepository.getReferenceById(partyForm.getPlayer_id3()));
			} else {
				party.setPlayer3(null);
			}
			if (partyForm.getPlayer_id4() != null) {
				party.setPlayer4(playerRepository.getReferenceById(partyForm.getPlayer_id4()));
			} else {
				party.setPlayer4(null);
			}
			if (partyForm.getPlayer_id5() != null) {
				party.setPlayer5(playerRepository.getReferenceById(partyForm.getPlayer_id5()));
			} else {
				party.setPlayer5(null);
			}
			if (partyForm.getPlayer_id6() != null) {
				party.setPlayer6(playerRepository.getReferenceById(partyForm.getPlayer_id6()));
			} else {
				party.setPlayer6(null);
			}
			if (partyForm.getPlayer_id7() != null) {
				party.setPlayer7(playerRepository.getReferenceById(partyForm.getPlayer_id7()));
			} else {
				party.setPlayer7(null);
			}
			if (partyForm.getPlayer_id8() != null) {
				party.setPlayer8(playerRepository.getReferenceById(partyForm.getPlayer_id8()));
			} else {
				party.setPlayer8(null);
			}

			partyRepository.save(party);

		} else {
			redirectAttributes.addFlashAttribute("successMessage", "冒険者の重複が無いように編成してください");
			return "redirect:/home/organization/{id}";
		}
		return "organization";
	}
}
