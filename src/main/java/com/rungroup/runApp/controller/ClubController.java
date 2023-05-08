package com.rungroup.runApp.controller;

import com.rungroup.runApp.dto.ClubDto;
import com.rungroup.runApp.models.Club;
import com.rungroup.runApp.models.UserEntity;
import com.rungroup.runApp.security.SecurityUtil;
import com.rungroup.runApp.service.ClubService;
import com.rungroup.runApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ClubController {
    private ClubService clubService;
    private UserService userService;

    @Autowired
    public ClubController(ClubService clubService, UserService userService) {
        this.clubService = clubService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/clubs";
    }

    @GetMapping("/clubs")
    public String listClubs(Model model){
        UserEntity user = new UserEntity();
        List<ClubDto> clubs = clubService.findAllClubs();
        if(user != null){
            String userEmail = SecurityUtil.getSessionUser();
            user = userService.findByEmail(userEmail);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }
    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") Long clubId, Model model){
        UserEntity user = new UserEntity();
        ClubDto clubDto = clubService.findClubById(clubId);
        if(user != null){
            String userEmail = SecurityUtil.getSessionUser();
            user = userService.findByEmail(userEmail);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }


    @GetMapping("/clubs/new")
    public String createClubForm(Model model){
        String existingUser = SecurityUtil.getSessionUser();
        if(existingUser == null) {
            return "redirect:/login";
        }
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") ClubDto clubDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }
        clubService.saveClub(clubDto);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId") Long clubId){
         clubService.delete(clubId);
         return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") Long clubId, Model model){
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        return "clubs-edit";
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") Long clubId, @Valid @ModelAttribute("club")
                                ClubDto club, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("club", club);
            return "/clubs-edit";
        }
        club.setId(clubId);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam(value = "query") String query, Model model){
        List<ClubDto> clubs = clubService.searchClubs(query);
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }
}
