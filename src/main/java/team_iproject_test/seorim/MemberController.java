package team_iproject_test.seorim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home() {
        return "main";
    }


    @GetMapping("/myPage")
    public String myPageForm() {
        return "/myPage";
    }

    @GetMapping("/myPagetest")
    public String myPagetestForm() {
        return "/myPagetest";
    }

    @PostMapping("/myPagetest")
    public String myPagetest(@ModelAttribute("member") MemberDO memberDO, Model model){
        memberService.mypageupdate(memberDO);
        return "redirect:/";
    }

}
