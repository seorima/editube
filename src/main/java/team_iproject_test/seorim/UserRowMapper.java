package team_iproject_test.seorim;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<MemberDO> {

    @Override
    public MemberDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberDO user = new MemberDO();

        user.setNickname(rs.getString("nickname"));
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPhone_number(rs.getString("phone_number"));
        user.setAddress(rs.getString("address"));
        user.setDetail_address(rs.getString("detail_addr"));
        user.setBirth(rs.getDate("birth_date").toLocalDate());

        return user;
    }
}
