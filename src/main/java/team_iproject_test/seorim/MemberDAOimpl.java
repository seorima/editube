package team_iproject_test.seorim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOimpl implements MemberDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void update(MemberDO memberdo) {
        String sql = "update emp_info set email=?,Nickname=? where email='qwer@naver.com'";
        jdbcTemplate.update(sql, memberdo.getEmail(),memberdo.getNickname());

    }
    @Override
    public MemberDO findByEmail(String email) {
        String sql = "SELECT ? FROM user_info WHERE email = ?";
        return null;
    }




}
