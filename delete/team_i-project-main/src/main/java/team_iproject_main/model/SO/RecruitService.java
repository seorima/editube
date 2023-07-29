package team_iproject_main.model.SO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_iproject_main.model.DAO.ApplyEditorDao;
import team_iproject_main.model.DAO.RecruitBoardDao;
import team_iproject_main.model.DO.*;
import team_iproject_main.model.Request.RecruitBoardEditRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecruitService {

    @Autowired
    private RecruitBoardDao recruitBoardDao;

    @Autowired
    private ApplyEditorDao applyEditorDao;



    public void recruit_boardWrite(String email, RecruitBoardEditRequest req){
        RecruitBoardDO recruitBoardDO = new RecruitBoardDO(req.getRecruit_title(), req.getDuty(), req.getWorkload(), req.getRequirement(),
                req.getSalary_detail(), req.getPreference(), req.getEdit_tools(), req.getEnvironment(), req.getWelfare(), req.getRecruit_detail(),
                req.getSalary(), LocalDate.parse(req.getDeadline()), req.getOriginal_link(), req.getChannel_categories());

        recruitBoardDO.setYoutuber_email(email);
        recruitBoardDao.createRecruitBoard(recruitBoardDO);

    }

    //주현
    public List<RecruitDO> findRecruit(String email) {
        return recruitBoardDao.findAllApplyByEmail(email);
    }

    //희수
    //구인글 게시글 상세 조회
    public RecruitDO boardview(int recruitNo){
        return recruitBoardDao.selectRecruitPost(recruitNo);
    }


    //주현
    //지원자 넣기
    public void recruitBoardApply(int recruitNo, String email){
        applyEditorDao.addApplyEditor(recruitNo, email);
    }

    public ApplyEditorDO findApplyEditorByNumEmail(int recruitNo, String email){
        return applyEditorDao.findApplyEditorByNumAndEmail(recruitNo, email);
    }

    public List<ChannelCategoryDO> getChannelCategory(int recruitNo){
        return recruitBoardDao.getCategories(recruitNo);
    }

    public List<EditToolsRecruitDO> getEditTools(int recruitNo){
        return recruitBoardDao.getTools(recruitNo);
    }

    //희수
    //구인글 게시글 조회
    // 준영 페이징 추가
    public List<RecruitDO> findRecruit(int page, int postsPerPage) {
        int offset = (page - 1) * postsPerPage;
        return recruitBoardDao.findRecruit(postsPerPage, offset);
    }


    // 준영 페이징 추가
    public int getTotalPosts() {
        return recruitBoardDao.getTotalPosts();
    }


    public void deleteRecruit(String email){
        recruitBoardDao.deleteRecruitPost(email);
    }
}
