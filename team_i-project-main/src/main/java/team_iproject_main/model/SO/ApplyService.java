package team_iproject_main.model.SO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_iproject_main.model.DAO.ApplyEditorDao;

@Service
public class ApplyService {

    @Autowired
    private ApplyEditorDao applyEditorDao;

    public void applyUploadVideo(int recruitNo, String editor_email, String edited_link, String editor_memo){
        applyEditorDao.updateApplyEditor(recruitNo, editor_email, edited_link, editor_memo);
    }
}
