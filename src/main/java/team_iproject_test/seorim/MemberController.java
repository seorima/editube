package team_iproject_test.seorim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team_iproject_test.seorim.MemberDO;
import team_iproject_test.seorim.MemberService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public String id="user14@gmail.com";

    @GetMapping("/")
    public String home() {
        return "main";
    }


    //5.8 양서림
    @GetMapping("/myPage")
    public String myPageForm(HttpSession session,Model model) {
        session.setAttribute("id",id); // 아이디 로그인이 연결안된상태라 임의로 string id 지정해놓은 상태
        MemberDO memberDO = memberService.findById(id); // 기존설정한 아이디와 일치하는 자료 db에서 가져옴
        model.addAttribute("memberDO",memberDO); //모델에 저장후 html에서 표시
        return "myPage";
    }
//5.9 양서림음
    @PostMapping("/myPage")
    public String myPageedit(@ModelAttribute("member") UserUpdateRequest userUpdateRequest, Model model,HttpSession session){
        session.setAttribute("id",id);
        MemberDO check = memberService.findNickname(userUpdateRequest.getNickname());
        if(check != null){
            model.addAttribute("duplicateNickname","이미 존재하는 닉네임 입니다.");
            return "myPage";
        } //닉네임 중복확인이 안됨#######

        memberService.mypageupdate(userUpdateRequest,id); // 사용자가 입력한값 데베에 업데이트
        return "redirect:/myPage";
    }

//    @GetMapping("/myPagetest")
//    public String myPagetestForm(HttpSession session,Model model) {
//        session.setAttribute("id",id); //로그인이랑 연결안해서 id임의지정
//        MemberDO memberDO = memberService.findById(id); //db에 id랑 nickname만있음
//        model.addAttribute("memberDO",memberDO);
//        return "/myPagetest";
//    }
//
//    @PostMapping("/myPagetest")
//    public String myPagetest(@ModelAttribute("member") MemberDO memberDO, Model model,HttpSession session){
//        session.setAttribute("id",id);
//        memberService.mypageupdate(memberDO,id);
//        return "redirect:/";
//    }

}
