package team_iproject_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team_iproject_main.exception.*;
import team_iproject_main.model.DO.*;
import team_iproject_main.model.Request.*;
import team_iproject_main.model.SO.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Value("${admin.adminEmail}")
    private String adminEmail;

    @Value("${admin.adminPassword}")
    private String adminPassword;

    @Autowired
    private UserService userService;

    @GetMapping("/main")
    public String home() {
        return "main";
    }

    //0508손주현
    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("email");
        session.invalidate();
        return "redirect:/main";
    }

    @GetMapping("/signup_options")
    public String signup_optionForm() {
        return "signup_options";
    }

    @GetMapping("/signup_editor")
    public String signup_editorForm() {
        return "signup_editor";
    }

    ////0508 손주현-편집자 회원가입 수정
    @PostMapping("/signup_editor")
    public String signup_editor(RegisterRequest req, Model model) {
        String view = "";
        try {
            userService.editor_signUp(req);
            model.addAttribute("msg", "회원가입 되었습니다.");
            view = "/main";

        }
        catch (DuplicateEmailException e) {
            model.addAttribute("msg", "이미 등록된 이메일입니다.");
            view = "/signup_editor";
        }
        catch (DuplicateNickNameException e) {
            model.addAttribute("msg", "이미 존재하는 닉네임입니다.");
            view = "/signup_editor";
        }
        return view;

    }

    @GetMapping("/signup_youtuber")
    public String signup_youtuberForm(Model model) {
        return "signup_youtuber";
    }

    //0508-유튜버 회원가입 수정
    @PostMapping("/signup_youtuber")
    public String signup_youtuber(RegisterRequest req, Model model) {
        String view = "";
        if(req.getChannel_id() == null) {
            model.addAttribute("resultMsg", "채널 인증이 되지 않아 가입을 진행할 수 없습니다.");

            return "signup_youtuber";
        }
        try {
            userService.youtuber_signUp(req);
            model.addAttribute("msg", "회원가입 되었습니다.");
            view = "/main";
        } catch (DuplicateEmailException e) {
            model.addAttribute("msg", "이미 등록된 이메일입니다.");
            view = "/signup_youtuber";
        }
        catch (DuplicateNickNameException e) {
            model.addAttribute("msg", "이미 존재하는 닉네임입니다.");
            view = "/signup_youtuber";
        }
        catch (DuplicateChannelException e) {
            model.addAttribute("msg", "채널아이디가 중복입니다.");
            view = "/signup_youtuber";
        }
        return view;
    }

    @GetMapping("/login")
    public String loginForm(HttpSession session){
        session.invalidate();
        return "login";
    }

    //0508-유튜버 로그인 수정
    @PostMapping("/login")
    public String login(LoginCommand login, HttpSession session, Model model){
        String view = "";

        if(login.getEmail().equals(adminEmail) && login.getPassword().equals(adminPassword)) {
            session.setAttribute("email", login.getEmail());
            session.setAttribute("type" , "관리자");
            session.setAttribute("nickname", "관리자");
            return "redirect:/main";
        }

        try{
            if(userService.checkLoginAuth(login)){
                UserDO users = userService.findUser(login.getEmail());
                login.setEmail(users.getEmail());
                login.setPassword("");
                session.setAttribute("email", users.getEmail());
                session.setAttribute("type" , users.getUser_type());
                session.setAttribute("nickname", users.getNickname());
                view = "redirect:/main";

            }
        }catch(UserNotFoundException e){
            model.addAttribute("error", "등록되지 않은 아이디입니다.");
            view = "/login";
        }
        catch(WrongPasswordException e){
            model.addAttribute("error", "비밀번호를 잘못 입력했습니다. \n입력하신 내용을 다시 확인해주세요.");
            view = "/login";
        }
        return view;
    }


    //0506-손주현
    //find_id.html 불러오기
    @GetMapping("/find_id")
    public String find_idForm() {
        return "find_id";
    }

    //0506-손주현
    //Post find_id_result
    @PostMapping("/find_id")
    public String find_id(FindIdRequest req, Model model) {
        String view = "";
        try{
            if(userService.checkFindId(req)){
                UserDO users = userService.findUser(req.getName(), req.getPhone_number());
                req.setName(req.getName());
                model.addAttribute("email", users.getEmail());
                model.addAttribute("name", users.getName());
                view = "/find_id";
            }
        }
        catch(UserNotFoundException e){
            model.addAttribute("error", "입력하신 정보에 해당하는 아이디를 찾지 못했습니다.\n입력하신 내용을 다시 확인해주세요.");
            view = "/find_id";
        }

        return view;
    }

    @GetMapping("/find_password")
    public String find_passwordForm(Model model) {
        return "find_password";
    }

    // 비밀번호 찾기 메서드,  230507 장준원
    @PostMapping("/find_password")
    public String find_passwordFrom(@ModelAttribute("formData") FindPasswordRequest pwReq, Model model) {

        String email = pwReq.getEmail();
        String name  = pwReq.getName();
        String phNum = pwReq.getPhone_number();

        UserDO userDO = userService.findUser(email);

        if(userDO == null) {
            model.addAttribute("failMsg", "<script> alert('존재하지 않는 이메일입니다.'); </script>");
            return "find_password";
        }
        else if(!(userDO.getName().equals(name) && userDO.getPhone_number().equals(phNum))) {
            model.addAttribute("failMsg","<script> alert('일치하는 회원정보가 없습니다.'); </script>");
            return "find_password";
        }
        else {
            model.addAttribute("email", email);
            return "find_change_password";
        }
    }
    // 비밀번호 변경 메서드(찾기에서 변경),  230509 장준원
    @PostMapping("/find_change_password")
    public String changePassword(@RequestParam(value = "email") String email, @RequestParam(value = "newpwd") String newpwd, Model model) {

        String setEmail = email;
        userService.changePwd(setEmail, newpwd);

        return "main";
    }

    //5.8 양서림
    @GetMapping("/myPage")
    public String myPageForm(HttpSession session,Model model) {
        String email = String.valueOf(session.getAttribute("email"));
        UserDO user = userService.findById(email); // 기존설정한 아이디와 일치하는 자료 db에서 가져옴
        model.addAttribute("user", user); //모델에 저장후 html에서 표시
        model.addAttribute("nickname", user.getNickname());
        return "myPage";
    }

    //5.9 양서림
    @PostMapping("/myPage")
    public String myPageEdit(@ModelAttribute("member") UserUpdateRequest userUpdateRequest, Model model, HttpSession session, RedirectAttributes redirectAttributes){
        String email = String.valueOf(session.getAttribute("email"));
        UserDO check = userService.findNickname(userUpdateRequest.getNickname());

        if(check != null && !userUpdateRequest.getNickname().equals(String.valueOf(session.getAttribute("nickname")))){
            redirectAttributes.addFlashAttribute("duplicateNickname","이미 존재하는 닉네임 입니다.");
            return "redirect:/myPage";
        }

        userService.mypageupdate(userUpdateRequest, email); // 사용자가 입력한값 데베에 업데이트
        session.setAttribute("nickname", userUpdateRequest.getNickname());
        return "redirect:/myPage";
    }

    @GetMapping("/changepwd")
    public String ChangepwdForm(Model model) {
        return "changepwd";
    }

    @PostMapping("/changepwd")
    public String Changepwd(@RequestParam(value = "current") String current, @RequestParam(value = "newpwd") String newpwd , Model model, HttpSession session) {
        String email = String.valueOf(session.getAttribute("email"));
        UserDO userDO = userService.findUser(email);

        if(!current.equals(userDO.getPassword())){
            model.addAttribute("failMsg","비밀번호를 잘못입력하셨습니다. 비밀번호를 확인해주세요.");
            return "changepwd";
        }
        else {
            userService.changePwd(email, newpwd);
            return "redirect:/myPage";
        }
    }

    //0511- 손주현
    @GetMapping("/disableAccount")
    public String disalbeAccountForm(Model model) {
        return "disableAccount";
    }

    //0511- 손주현
    @PostMapping("/disableAccount")
    public String disalbeAccount(@RequestParam(value = "password") String password,
                                 HttpSession session, Model model){
        String email = String.valueOf(session.getAttribute("email"));
        UserDO userDO = userService.findUser(email);

        if(!password.equals(userDO.getPassword())){
            model.addAttribute("errorMsg","비밀번호를 잘못 입력하셨습니다. 비밀번호를 확인해주세요.");
            return "disableAccount";
        }

        userService.deleteUser(email);
        model.addAttribute("msg","회원탈퇴 처리 되었습니다.");
        session.removeAttribute("email");
        session.invalidate();
        return "/main";
    }

    //희수
    //관리자 (회원 조회)
    @GetMapping("memberManage")
    public String list(Model model) {
        List<UserDO> members = userService.findMembers();
        model.addAttribute("members", members);
        return "memberManage";
    }
    //희수
    //id(이메일),닉네임 조회
    @PostMapping("findMemberProcess")
    public String findid(@RequestParam(value = "job") String job, @RequestParam(value = "searchtext") String searchtext, Model model) {
        List<UserDO> members = userService.findMember(job, searchtext);
        model.addAttribute("members", members);
        return "memberManage";
    }
    //희수
    //유저 삭제
    @PostMapping("deleteMember")
    public String delectId(@RequestParam(value = "delete") String email, Model model) {
        userService.deleteMember(email);
        return "memberManage";
    }
}
