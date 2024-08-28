package com.example.game.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import com.example.game.Forn.ChangeJob;
import com.example.game.Forn.ItemForm;
import com.example.game.Forn.PlayerNameEditForm;
import com.example.game.Forn.Playertrainingform;
import com.example.game.Repository.ItemRepository;
import com.example.game.Repository.PartyRepository;
import com.example.game.Repository.PlayerRepository;
import com.example.game.Repository.UserRepository;
import com.example.game.Repository.User_infoRepository;
import com.example.game.Security.UserPrincipal;
import com.example.game.Service.Transformation;
import com.example.game.entity.Item;
import com.example.game.entity.Party;
import com.example.game.entity.Player;
import com.example.game.entity.User;
import com.example.game.entity.User_info;

@RequestMapping("/home/training")
@Controller
public class TrainingController {
	private final UserRepository userRepository;
	private final PlayerRepository playerRepository;
	private final PartyRepository partyRepository;
	private final ItemRepository itemRepository;
	private final User_infoRepository user_infoRepository;

	public TrainingController(UserRepository userRepository, PlayerRepository playerRepository,
			PartyRepository partyRepository, ItemRepository itemRepository, User_infoRepository user_infoRepository) {
		this.userRepository = userRepository;
		this.playerRepository = playerRepository;
		this.partyRepository = partyRepository;
		this.itemRepository = itemRepository;
		this.user_infoRepository = user_infoRepository;

	}

	@GetMapping()
	public String training(@AuthenticationPrincipal UserPrincipal userDetailsImpl, Model model,
			Transformation transformation,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		Page<Player> playerPage = playerRepository.findByUser_id(user.getId(), pageable);
		model.addAttribute("playerPage", playerPage);
		model.addAttribute("transformation", transformation);

		return "training/training";
	}

	@GetMapping("/{id}")
	public String show(@AuthenticationPrincipal UserPrincipal userDetailsImpl, @PathVariable(name = "id") Integer id,
			Model model, Transformation transformation) {
		Player player = playerRepository.getReferenceById(id);
		model.addAttribute("player", player);
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		model.addAttribute("transformation", transformation);
		return "training/show";
	}

	@GetMapping("/{id}/nameEdit")
	public String nameEdit(@AuthenticationPrincipal UserPrincipal userDetailsImpl,
			@PathVariable(name = "id") Integer id, Model model, Transformation transformation) {
		Player player = playerRepository.getReferenceById(id);
		model.addAttribute("player", player);
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		PlayerNameEditForm playerNameEditForm = new PlayerNameEditForm(player.getName());
		model.addAttribute("playerNameEditForm", playerNameEditForm);
		model.addAttribute("transformation", transformation);
		return "training/nameEdit";
	}

	@PostMapping("/{id}/nameEdited")
	public String nameEdited(@AuthenticationPrincipal UserPrincipal userDetailsImpl,
			@ModelAttribute @Validated PlayerNameEditForm playerNameEditForm,
			@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes,
			Transformation transformation) {
		Player player = playerRepository.getReferenceById(id);
		model.addAttribute("player", player);
		player.setName(playerNameEditForm.getName());
		playerRepository.save(player);
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		model.addAttribute("playerNameEditForm", playerNameEditForm);
		model.addAttribute("transformation", transformation);
		redirectAttributes.addFlashAttribute("successMessage", "冒険者の名前の変更に成功しました。");
		return "training/nameEdit";
	}

	@GetMapping("/{id}/changejob")
	public String changeJob(@AuthenticationPrincipal UserPrincipal userDetailsImpl,
			@PathVariable(name = "id") Integer id, Model model, Transformation transformation) {
		Player player = playerRepository.getReferenceById(id);
		model.addAttribute("player", player);
		ChangeJob changeJob = new ChangeJob(player.getJob());
		model.addAttribute("changeJob", changeJob);
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		model.addAttribute("transformation", transformation);
		return "training/changeJob";

	}

	@PostMapping("/{id}/changejobed")
	public String changeJob(@ModelAttribute @Validated ChangeJob changeJob, @PathVariable(name = "id") Integer id,
			Model model, Transformation transformation, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UserPrincipal userDetailsImpl,
			@PageableDefault(size = 1000) Pageable pe) {
		Player player = playerRepository.getReferenceById(id);
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("player", player);
		model.addAttribute("transformation", transformation);

		player.setJob(changeJob.getJob());
		transformation.changeJob(player, changeJob.getJob());
		playerRepository.save(player);
		model.addAttribute("changeJob", changeJob);
		user_info.setMoney(user_info.getMoney() - 1000);
		Page<Player> players = playerRepository.findByUser_id(user.getId(), pe);
		transformation.time(user_info, 5, user_infoRepository, players, playerRepository);
		user_infoRepository.save(user_info);
		model.addAttribute("user_info", user_info);
		redirectAttributes.addFlashAttribute("successMessage", "冒険者の転職に成功しました。");

		return "training/changeJob";
	}

	@GetMapping("/{id}/playertraining")
	public String playertraining(@AuthenticationPrincipal UserPrincipal userDetailsImpl,
			@PathVariable(name = "id") Integer id, Model model, Transformation transformation) {
		Player player = playerRepository.getReferenceById(id);
		model.addAttribute("player", player);
		Playertrainingform playertrainingform = new Playertrainingform(player.getJob());
		model.addAttribute("playertrainingform", playertrainingform);
		model.addAttribute("transformation", transformation);
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		return "training/playertraining";
	}

	@PostMapping("/{id}/playertraininged")
	public String plaertraininga(@AuthenticationPrincipal UserPrincipal userDetailsImpl,
			@PathVariable(name = "id") Integer id, Model model, Transformation transformation,
			@ModelAttribute @Validated Playertrainingform playertrainingform, RedirectAttributes redirectAttributes,
			@PageableDefault(size = 1000) Pageable pe) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		Party party = partyRepository.findByUser_idAndCount(user.getId(), 7);
		Player player = playerRepository.getReferenceById(id);
		model.addAttribute("player", player);
		transformation.playertraining(player, playertrainingform.getTraining(), redirectAttributes, party);
		playerRepository.save(player);
		List<Player> players = playerRepository.findAll();
		playerRepository.saveAll(players);
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		user_info.setMoney(user_info.getMoney() - 500);
		user_infoRepository.save(user_info);
		Page<Player> playeres = playerRepository.findByUser_id(user.getId(), pe);
		transformation.time(user_info, 2, user_infoRepository, playeres, playerRepository);
		model.addAttribute("transformation", transformation);
		model.addAttribute("user_info", user_info);
		return "redirect:/home/training/{id}/playertraining";
	}

	@GetMapping("/{id}/item")
	public String Items(@AuthenticationPrincipal UserPrincipal userDetailsImpl, @PathVariable(name = "id") Integer id,
			Model model, Transformation transformation,
			Pageable pageable) {
		Player player = playerRepository.getReferenceById(id);
		model.addAttribute("player", player);
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		Item nowitem1 = itemRepository.findByPlayer_idAndItemkindNot(player.getId(), 10);
		Item nowitem2 = itemRepository.findByPlayer_idAndItemkind(player.getId(), 10);
		model.addAttribute("nowitem1", nowitem1);
		model.addAttribute("nowitem2", nowitem2);
		Page<Item> itemPage1 = itemRepository.findByUser_idAndPlayer_idAndItemkind(user.getId(),
				null, transformation.jobitem(player.getJob()),
				pageable);
		Page<Item> itemPage2 = itemRepository.findByUser_idAndPlayer_idAndItemkind(user.getId(),
				null, transformation.jobarmor(player.getJob()),
				pageable);
		model.addAttribute("itemPage1", itemPage1);
		model.addAttribute("itemPage2", itemPage2);
		int item1 = 0;
		int item2 = 0;
		if (player.getJob() == 12) {
			item1 = 1;
			item2 = 2;
		} else if (player.getJob() > 12) {
			item1 = 2;
			item2 = 2;
		} else if (player.getJob() < 12) {
			item1 = 1;
			item2 = 1;
		}
		model.addAttribute("item1", item1);
		model.addAttribute("item2", item2);
		model.addAttribute("transformation", transformation);
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		ItemForm itemForm = new ItemForm(null, null, null, null);
		return "training/item";
	}

	@PostMapping("/{id}/item/ed")
	public String Itemsed(@AuthenticationPrincipal UserPrincipal userDetailsImpl, @PathVariable(name = "id") Integer id,
			Model model, Transformation transformation,
			Pageable pageable, @ModelAttribute @Validated ItemForm itemForm) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		Player player = playerRepository.getReferenceById(id);
		model.addAttribute("player", player);
		Item nowitem1 = itemRepository.findByPlayer_idAndItemkindNot(player.getId(), 10);
		Item nowitem2 = itemRepository.findByPlayer_idAndItemkind(player.getId(), 10);

		if (nowitem1 != null) {
			nowitem1.setPlayer(null);
			itemRepository.save(nowitem1);
		}
		if (nowitem2 != null) {
			nowitem2.setPlayer(null);
			itemRepository.save(nowitem2);
		}

		if (itemForm.getPlayer_id() != null) {
			Item item = itemRepository.getReferenceById(itemForm.getPlayer_id());
			item.setPlayer(player);
			itemRepository.save(item);
		}
		if (itemForm.getUser_id() != null) {
			Item itemarmor = itemRepository.getReferenceById(itemForm.getUser_id());
			itemarmor.setPlayer(player);
			itemRepository.save(itemarmor);
		}

		Page<Item> itemPage1 = itemRepository.findByUser_idAndPlayer_idAndItemkind(user.getId(),
				null, transformation.jobitem(player.getJob()),
				pageable);
		Page<Item> itemPage2 = itemRepository.findByUser_idAndPlayer_idAndItemkind(user.getId(),
				null, transformation.jobarmor(player.getJob()),
				pageable);
		model.addAttribute("itemPage1", itemPage1);
		model.addAttribute("itemPage2", itemPage2);
		nowitem1 = itemRepository.findByPlayer_idAndItemkindNot(player.getId(), 10);
		nowitem2 = itemRepository.findByPlayer_idAndItemkind(player.getId(), 10);

		model.addAttribute("nowitem1", nowitem1);
		model.addAttribute("nowitem2", nowitem2);
		int item1 = 0;
		int item2 = 0;
		if (player.getJob() == 12) {
			item1 = 1;
			item2 = 2;
		} else if (player.getJob() > 12) {
			item1 = 2;
			item2 = 2;
		} else if (player.getJob() < 12) {
			item1 = 1;
			item2 = 1;
		}
		model.addAttribute("item1", item1);
		model.addAttribute("item2", item2);
		model.addAttribute("transformation", transformation);
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		ItemForm itemForm2 = new ItemForm(null, null, null, null);
		return "training/item";
	}

	@GetMapping("/{id}/delete")
	public String delete(@AuthenticationPrincipal UserPrincipal userDetailsImpl, @PathVariable(name = "id") Integer id,
			Model model, Transformation transformation) {
		Player player = playerRepository.getReferenceById(id);
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		model.addAttribute("user_info", user_info);
		model.addAttribute("transformation", transformation);
		model.addAttribute("player", player);
		return "training/drop";
	}

	@PostMapping("/{id}/deleted")
	public String deleted(@AuthenticationPrincipal UserPrincipal userDetailsImpl, @PathVariable(name = "id") Integer id,
			RedirectAttributes redirectAttributes) {
		Player player = playerRepository.getReferenceById(id);
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		User_info user_info = user_infoRepository.getReferenceById(user.getId());
		playerRepository.delete(player);
		user_info.setMoney(user_info.getMoney() - 5000);
		user_infoRepository.save(user_info);
		redirectAttributes.addFlashAttribute("successMessage", "戦闘員を退職金を支払って解雇しました。");
		return "redirect:/home/training";
	}

}
