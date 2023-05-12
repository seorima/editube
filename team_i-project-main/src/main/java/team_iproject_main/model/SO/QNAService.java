package team_iproject_main.model.SO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_iproject_main.model.DAO.QNADao;
import team_iproject_main.model.DO.QnADO;

@Service
public class QNAService {

    @Autowired
    private QNADao qnaDao;

    public void qnaUpdate(String email,QnADO qnaDO) {
        qnaDao.qnaUpdate(email,qnaDO);
    }

}
