package team_iproject_main.model.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team_iproject_main.model.DO.ApplyEditorDO;
import team_iproject_main.model.Mapper.ApplyEditorRowMapper;

@Repository
public class ApplyEditorDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //주현 0511
    //구인글에 지원한 지원자 넣기
    public void addApplyEditor(int recruitNO, String email){
        String sql = "INSERT INTO APPLY_EDITOR(APPLY_NO, RECRUIT_NO, EDITOR_EMAIL) VALUES(APPLY_EDITOR_SEQ.NEXTVAL,?,?)";
        jdbcTemplate.update(sql,recruitNO,email);
    }

    //주현 0511
    //구인글에 지원한 지원자를 넣을 때 중복을 방지하기 위해서 만든 것
    public ApplyEditorDO findApplyEditorByNumAndEmail(int recruitNO, String email){
        String sql = "SELECT * FROM APPLY_EDITOR WHERE RECRUIT_NO = ? AND EDITOR_EMAIL = ?";
        ApplyEditorDO applyEditorDO = null;
        try{
            applyEditorDO = jdbcTemplate.queryForObject(sql, new ApplyEditorRowMapper(), recruitNO, email);
            return  applyEditorDO;
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }

    }
    //주현 0511
    //지원한 구인글에 링크와 하고싶은말 업데이트하기
    public void updateApplyEditor(int recruitNo, String editor_email, String edited_link, String editor_memo){
        String sql = "UPDATE APPLY_EDITOR SET EDITED_LINK = ?, EDITOR_MEMO = ? WHERE RECRUIT_NO = ? AND EDITOR_EMAIL = ?";
        jdbcTemplate.update(sql, edited_link, editor_memo, recruitNo, editor_email);
    }

}
