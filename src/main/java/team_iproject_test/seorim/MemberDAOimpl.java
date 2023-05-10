package team_iproject_test.seorim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberDAOimpl implements MemberDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public MemberDAOimpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(UserUpdateRequest userUpdateRequest,String id) {
        String sql = "update user_info set nickname=?,name=?,phone_number=?,address=?,detail_addr=?,birth_date=? where id=?";
        jdbcTemplate.update(sql,userUpdateRequest.getNickname(),userUpdateRequest.getName(), userUpdateRequest.getPhone_number(),
                userUpdateRequest.getAddress(),userUpdateRequest.getDetail_address(), LocalDate.parse(userUpdateRequest.getBirth()),id);

    }

    @Override
    public MemberDO findnickname(String nick) {
        String sql = "SELECT * FROM user_info WHERE nickname = ?";
        MemberDO user = null;

        try{
            user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), nick);
        }
        catch (EmptyResultDataAccessException e){
        }
        return user;
    }




    @Override
    public MemberDO findById(String id) {

        String sql= "select * from user_info where id=?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            MemberDO memberDO = new MemberDO();
            memberDO.setId(rs.getString("id"));
            memberDO.setName(rs.getString("name"));
            memberDO.setNickname(rs.getString("nickname"));
            memberDO.setPhone_number(rs.getString("phone_number"));
            memberDO.setAddress(rs.getString("address"));
            memberDO.setDetail_address(rs.getString("detail_addr"));
            memberDO.setBirth(rs.getDate("birth_date").toLocalDate());
            return memberDO;
        },id);

    }




}
