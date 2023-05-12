package team_iproject_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import team_iproject_main.model.DAO.QNADao;
import team_iproject_main.model.DO.QnADO;
import team_iproject_main.model.SO.QNAService;

import javax.servlet.http.HttpSession;

@Controller
public class QNAController {

    @Autowired
    private QNAService qnaService;
    @GetMapping("/Q&A_board")
    public String QA_board(HttpSession session) {
        return "Q&A_board";
    }

    @PostMapping("/Q&A_board")
    public String QA_boardForm(HttpSession session, QnADO qnaDO, QNADao qnaDao) {
        String email = String.valueOf(session.getAttribute("email"));
        qnaService.qnaUpdate(email,qnaDO);



        return "redirect:/Q&A_board";
    }




}
