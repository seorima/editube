package team_iproject_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team_iproject_main.model.DO.ApplyEditorDO;
import team_iproject_main.model.DO.ChannelCategoryDO;
import team_iproject_main.model.DO.EditToolsRecruitDO;
import team_iproject_main.model.DO.RecruitDO;
import team_iproject_main.model.SO.ApplyService;
import team_iproject_main.model.SO.RecruitService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ApplyController {

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private ApplyService applyService;


    @GetMapping("/apply")
    public String applyAndGoMyPage(HttpSession session, int recruitNo, RedirectAttributes re){
        String email = session.getAttribute("email").toString();
        ApplyEditorDO applyEditorDO = recruitService.findApplyEditorByNumEmail(recruitNo, email);
        if(applyEditorDO != null) {
            re.addAttribute("msg", "이미 지원한 구인글입니다.");
        }
        else{
            recruitService.recruitBoardApply(recruitNo, email);
            re.addAttribute("msg", "지원 완료 되었습니다\n마이페이지 지원현황에서 확인하세요!");
        }
        return "redirect:/applynow";
    }

    @GetMapping("applynow")
    public String applynowForm(Model model, HttpSession session){
        String email = session.getAttribute("email").toString();
        List<RecruitDO> recruitDO = recruitService.findRecruit(email);
        model.addAttribute("recruitDO", recruitDO);
        return "applynow";
    }

    //주현 0512
    @GetMapping("/applynow_upload")
    public String applynow_uploadForm(int recruitNo, Model model){
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
        return "applynow_upload";
    }

    @PostMapping("/applynow_upload")
    public String applynow_upload(@RequestParam(value = "recruitNo") int recruitNo, @RequestParam(value = "edited_link") String edited_link,
                                  @RequestParam(value = "editor_memo") String editor_memo, HttpSession session, RedirectAttributes re) {
        String email = session.getAttribute("email").toString();
        applyService.applyUploadVideo(recruitNo, email, edited_link, editor_memo);
        re.addAttribute("msg", "저장 완료 되었습니다");

        return "redirect:/applynow";

    }
}
