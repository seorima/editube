package team_iproject_main.model.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team_iproject_main.model.DO.QnADO;

@Repository
public class QNADao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void qnaUpdate(String email, QnADO qnaDO) {
        String sql="INSERT INTO QNA (QNA_NO, EMAIL, QUESTION)" +
                "VALUES (QNA_NO_SEQ.NEXTVAL,?, ?)";

        jdbcTemplate.update(sql, email, qnaDO.getQuestion());

    }

}
