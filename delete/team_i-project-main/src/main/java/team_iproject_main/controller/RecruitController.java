package team_iproject_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team_iproject_main.model.DO.ChannelCategoryDO;
import team_iproject_main.model.DO.EditToolsRecruitDO;
import team_iproject_main.model.DO.RecruitDO;
import team_iproject_main.model.Request.RecruitBoardEditRequest;
import team_iproject_main.model.SO.RecruitService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    // 준영 페이징 추가
    @GetMapping("/recruit_board")
    public String list1(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int postsPerPage = 10;
        int pageNavigationLinks = 5;

        List<RecruitDO> recruitDO = recruitService.findRecruit(page, postsPerPage);
        model.addAttribute("recruitDO", recruitDO);

        // Calculate pagination variables
        int totalPosts = recruitService.getTotalPosts();
        int totalPages = (int) Math.ceil((double) totalPosts / postsPerPage);
        int startPage = Math.max(1, page - (pageNavigationLinks / 2));
        int endPage = Math.min(startPage + pageNavigationLinks - 1, totalPages);

        model.addAttribute("currentPage", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "recruit_board";
    }


    @GetMapping("/recruit_result")
    public String recruitresult(int recruitNo, Model model) {
        RecruitDO recruitDO = recruitService.boardview(recruitNo);
        List<ChannelCategoryDO> chCategories = recruitService.getChannelCategory(recruitNo);
        List<EditToolsRecruitDO> editTools = recruitService.getEditTools(recruitNo);
        String categories = " ";
        String tools = " ";
        //타임리프에서 th:each 사용시 자동으로 줄바꿈 되게 되어 있어서 이렇게 했습니다. 쩔수 없임...
        for(ChannelCategoryDO category : chCategories){
            categories += category.getCategory() + " ";
        }
        for(EditToolsRecruitDO tool : editTools){
            tools += tool.getEdit_tool() + " ";
        }
        model.addAttribute("recruitDO",recruitDO);
        model.addAttribute("categories", categories);
        model.addAttribute("tools", tools);

        return "recruit_result";
    }

    @GetMapping("/recruit_board_edit")
    public String recruit_board_editForm() {
        return "recruit_board_edit";
    }

    @PostMapping("/recruit_board_edit")
    public String recruit_board_edit(RecruitBoardEditRequest req, HttpSession session, Model model) {
        String email = session.getAttribute("email").toString();
        recruitService.recruit_boardWrite(email, req);
        return "redirect:/recruit_board";
    }

    //희수
    //구인글 삭제
    @GetMapping("/recruit_delete")
    public String recruit_delete(String email, HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println("삭제에 쓸 이메일 값 " + email);
        if(!email.equals(String.valueOf(session.getAttribute("email")))){
            redirectAttributes.addFlashAttribute("msg","작성자만 삭제 할 수 있습니다.");
            return "recruit_board";
        }
        recruitService.deleteRecruit(email);
        return "recruit_board";
    }

}
