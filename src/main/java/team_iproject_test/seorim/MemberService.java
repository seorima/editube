package team_iproject_test.seorim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberDAO memberDAO;
    public void mypageupdate(MemberDO memberDO) {
        memberDAO.update(memberDO);
    }

}
