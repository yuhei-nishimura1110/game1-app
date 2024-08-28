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

import com.example.game.Forn.ItemForm;
import com.example.game.Repository.ItemRepository;
import com.example.game.Repository.PartyRepository;
import com.example.game.Repository.PlayerRepository;
import com.example.game.Repository.UserRepository;
import com.example.game.Repository.User_infoRepository;
import com.example.game.Scout.ItemScout;
import com.example.game.Security.UserPrincipal;
import com.example.game.Service.Transformation;
import com.example.game.entity.Item;
import com.example.game.entity.Party;
import com.example.game.entity.Player;
import com.example.game.entity.User;
import com.example.game.entity.User_info;

@Controller
@RequestMapping("/home/createitem")
public class CreateItemController {
	private final ItemRepository itemRepository;
	private final PlayerRepository playerRepository;
	private final UserRepository userRepository;
	private final PartyRepository partyRepository;
	private final User_infoRepository user_infoRepository;

	public CreateItemController(ItemRepository itemRepository, PlayerRepository playerRepository,
			UserRepository userRepository, PartyRepository partyRepository, User_infoRepository user_infoRepository) {
		this.itemRepository = itemRepository;
		this.playerRepository = playerRepository;
		this.userRepository = userRepository;
		this.partyRepository = partyRepository;
		this.user_infoRepository = user_infoRepository;

	}

	@GetMapping()
	public String createitem(@AuthenticationPrincipal UserPrincipal userDetailsImpl, Model model,
			Transformation transformation) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		ItemForm itemForm = new ItemForm(user.getId(), null, 0, 0);
		model.addAttribute("itemForm", itemForm);
		model.addAttribute("user_info", user_info);
		model.addAttribute("transformation", transformation);
		return "createitem";
	}

	@Transactional
	@PostMapping("/ed")
	public String createitemeder(@AuthenticationPrincipal UserPrincipal userDetailsImpl, Model model,
			@ModelAttribute @Validated ItemForm itemForm2, Transformation transformation, ItemScout itemScout,
			@PageableDefault(size = 1000) Pageable pe) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		switch (itemForm2.getItem_kind()) {
		case 1, 2, 5, 6, 7, 9 -> {
			Party party = partyRepository.findByUser_idAndCount(user.getId(), 2);
			itemForm2.setItem_strong(itemScout.itemscouta(itemForm2.getItem_kind(), party, transformation));
			transformation.blacksmith(party.getPlayer1(), playerRepository);
			transformation.blacksmith(party.getPlayer2(), playerRepository);
			transformation.blacksmith(party.getPlayer3(), playerRepository);
			transformation.blacksmith(party.getPlayer4(), playerRepository);
			transformation.blacksmith(party.getPlayer5(), playerRepository);
			transformation.blacksmith(party.getPlayer6(), playerRepository);
			transformation.blacksmith(party.getPlayer7(), playerRepository);
			transformation.blacksmith(party.getPlayer8(), playerRepository);

		}
		case 3 -> {
			Party party = partyRepository.findByUser_idAndCount(user.getId(), 3);
			itemForm2.setItem_strong(itemScout.itemscouta(itemForm2.getItem_kind(), party, transformation));
			itemForm2.setItem_strong(itemScout.itemscouta(itemForm2.getItem_kind(), party, transformation));
			transformation.arrow(party.getPlayer1(), playerRepository);
			transformation.arrow(party.getPlayer2(), playerRepository);
			transformation.arrow(party.getPlayer3(), playerRepository);
			transformation.arrow(party.getPlayer4(), playerRepository);
			transformation.arrow(party.getPlayer5(), playerRepository);
			transformation.arrow(party.getPlayer6(), playerRepository);
			transformation.arrow(party.getPlayer7(), playerRepository);
			transformation.arrow(party.getPlayer8(), playerRepository);
		}
		case 4 -> {
			Party party = partyRepository.findByUser_idAndCount(user.getId(), 4);
			itemForm2.setItem_strong(itemScout.itemscouta(itemForm2.getItem_kind(), party, transformation));
			itemForm2.setItem_strong(itemScout.itemscouta(itemForm2.getItem_kind(), party, transformation));
			transformation.katana(party.getPlayer1(), playerRepository);
			transformation.katana(party.getPlayer2(), playerRepository);
			transformation.katana(party.getPlayer3(), playerRepository);
			transformation.katana(party.getPlayer4(), playerRepository);
			transformation.katana(party.getPlayer5(), playerRepository);
			transformation.katana(party.getPlayer6(), playerRepository);
			transformation.katana(party.getPlayer7(), playerRepository);
			transformation.katana(party.getPlayer8(), playerRepository);
		}
		case 10 -> {
			Party party = partyRepository.findByUser_idAndCount(user.getId(), 5);
			itemForm2.setItem_strong(itemScout.itemscouta(itemForm2.getItem_kind(), party, transformation));
			itemForm2.setItem_strong(itemScout.itemscouta(itemForm2.getItem_kind(), party, transformation));
			transformation.armor(party.getPlayer1(), playerRepository);
			transformation.armor(party.getPlayer2(), playerRepository);
			transformation.armor(party.getPlayer3(), playerRepository);
			transformation.armor(party.getPlayer4(), playerRepository);
			transformation.armor(party.getPlayer5(), playerRepository);
			transformation.armor(party.getPlayer6(), playerRepository);
			transformation.armor(party.getPlayer7(), playerRepository);
			transformation.armor(party.getPlayer8(), playerRepository);
		}
		case 8 -> {
			Party party = partyRepository.findByUser_idAndCount(user.getId(), 6);
			itemForm2.setItem_strong(itemScout.itemscouta(itemForm2.getItem_kind(), party, transformation));
			itemForm2.setItem_strong(itemScout.itemscouta(itemForm2.getItem_kind(), party, transformation));
			transformation.cane(party.getPlayer1(), playerRepository);
			transformation.cane(party.getPlayer2(), playerRepository);
			transformation.cane(party.getPlayer3(), playerRepository);
			transformation.cane(party.getPlayer4(), playerRepository);
			transformation.cane(party.getPlayer5(), playerRepository);
			transformation.cane(party.getPlayer6(), playerRepository);
			transformation.cane(party.getPlayer7(), playerRepository);
			transformation.cane(party.getPlayer8(), playerRepository);
		}
		}

		Item item = new Item();
		item.setUser(user);
		item.setItemkind(itemForm2.getItem_kind());
		item.setItemstrong(itemForm2.getItem_strong());
		itemRepository.save(item);
		model.addAttribute("item", item);
		model.addAttribute("transformation", transformation);
		ItemForm itemForm = new ItemForm(user.getId(), null, 0, 0);
		model.addAttribute("itemForm", itemForm);
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		user_info.setMoney(user_info.getMoney() - 5000);
		user_infoRepository.save(user_info);
		Page<Player> players = playerRepository.findByUser_id(user.getId(), pe);
		transformation.time(user_info, 15, user_infoRepository, players, playerRepository);
		model.addAttribute("user_info", user_info);
		return "createitemresult";
	}
}
