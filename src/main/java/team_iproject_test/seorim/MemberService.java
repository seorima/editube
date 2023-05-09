package team_iproject_test.seorim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {


    @Autowired
    private MemberDAO memberDAO;
    public void mypageupdate(UserUpdateRequest userUpdateRequest,String id) {
        memberDAO.update(userUpdateRequest,id);
    }

    public MemberDO findNickname(String nick){return memberDAO.findnickname(nick);}

    public MemberDO findById(String id) {
        return memberDAO.findById(id);
    }




}
