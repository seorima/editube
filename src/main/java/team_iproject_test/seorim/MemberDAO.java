package team_iproject_test.seorim;

import java.util.List;
import java.util.Optional;

public interface MemberDAO {

    void update(UserUpdateRequest userUpdateRequest,String id);

    MemberDO findnickname(String nick);


    MemberDO findById(String id);
}
