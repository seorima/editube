package team_iproject_test.seorim;

public interface MemberDAO {

    void update(MemberDO memberdo);

    MemberDO findByEmail(String email);
}
