package team_iproject_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team_iproject_main.model.DO.PortfolioDO;
import team_iproject_main.model.DO.PortfolioEditDO;
import team_iproject_main.model.Request.PortfolioEditRequest;
import team_iproject_main.model.SO.PortfolioService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    //희수
    //구직자 게시판 조회
    @GetMapping("/portfolio_board")
    public String list2(Model model) {
        List<PortfolioDO> portfolioDO = portfolioService.findPortfolio();
        model.addAttribute("portfolioDO", portfolioDO);
        return "portfolio_board";
    }

    @GetMapping("/myPage/portfolio_result")
    public String portfolioresult(Model model, HttpSession session) {
        try {
            PortfolioDO portfolioDO = portfolioService.portfoloview(String.valueOf(session.getAttribute("email")));
            model.addAttribute("portfolioDO",portfolioDO);
        }
        catch(EmptyResultDataAccessException e) {
            return "redirect:/portfolio_edit";
        }

        return "portfolio_result";
    }

    //5.11 양서림
    @PostMapping("/portfolio_edit")
    public String portfolioedit(HttpSession session, PortfolioEditRequest edit, Model model, @RequestParam("edit_link") String[] edit_link) {
        String email = String.valueOf(session.getAttribute("email"));
        portfolioService.portfolioupdate(edit,email); //편집영상제외 db저장

        portfolioService.portfolioVideoUpdate(edit_link,email); //편집영상 db 저장
        model.addAttribute("msg","포트폴리오가 수정되었습니다");
        return "redirect:/portfolio_edit";
    }

    //5.12 양서림 초기조회 추가
    @GetMapping("/portfolio_edit")
    public String portfolioForm(String email, Model model,HttpSession session , RedirectAttributes redirectAttributes) {

        if(!email.equals(String.valueOf(session.getAttribute("email")))) {
            redirectAttributes.addFlashAttribute("msg","작성자만 수정 할 수 있습니다.");
            return "redirect:/portfolio_result?email=" + email;
        }

        PortfolioEditDO portfolioEditDO = portfolioService.portfolioedit(email); // 초기조회
        //편집툴 초기
        //대표영상링크 초기 서비스에 넣어서 하는게 나을듯
        model.addAttribute("portfolioEditDO",portfolioEditDO);
//        redirectAttributes.addFlashAttribute("portfolioEditDO", portfolioEditDO);
        return "portfolio_edit";
    }

    //희수
    //구직자 상세 조회
    @GetMapping("/portfolio_result")
    public String portfolioresult(String email, Model model) {
        System.out.println("조회에 쓸 이메일 값 " + email);
        PortfolioDO portfolioDO = portfolioService.portfoloview(email);
        model.addAttribute("portfolioDO", portfolioDO);
        return "portfolio_result";
    }
    //희수
    //포트폴리오 삭제
    @GetMapping("/portfolio_delete")
    public String portfolio_delete(String email1, HttpSession session, RedirectAttributes redirectAttributes) {

        if(!email1.equals(String.valueOf(session.getAttribute("email")))){
            redirectAttributes.addFlashAttribute("msg","작성자만 삭제 할 수 있습니다.");
            return "redirect:/portfolio_result?email=" + email1;
        }
        portfolioService.deletePortfole(email1);
        return "portfolio_board";
    }
}
