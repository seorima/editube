package team_iproject_main.model.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team_iproject_main.model.DO.PortfolioDO;
import team_iproject_main.model.DO.PortfolioEditDO;
import team_iproject_main.model.Mapper.PortfolioEditRowMapper;
import team_iproject_main.model.Mapper.PortfolioRowMapper;

import java.util.List;

@Repository
public class PortfolioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //희수
    //포트폴리오 조회

    public List<PortfolioDO> PortfolioAll() {
        return jdbcTemplate.query("select * from portfolio where is_public = 'TRUE'" , new PortfolioRowMapper());
    }

    //희수
    //포트폴리오 상세 조회
    public PortfolioDO selectPortfolioPost(String email) {
        return jdbcTemplate.queryForObject("select * from portfolio where EDITOR_EMAIL = ?", new PortfolioRowMapper(), email);
    }

    //5.12 서림 포트폴리오 기본 데베값 받아오기
    public PortfolioEditDO selectPortfolioEdit(String email) {

        String sql = "SELECT ui.gender, ui.name, ec.start_date,ec.end_date,p.*\n" +
                "FROM user_info ui " +
                "JOIN edit_career ec ON ui.email = ec.editor_email " +
                "JOIN portfolio p ON ui.email = p.editor_email " +
                "WHERE ui.email = ?";

        return jdbcTemplate.queryForObject(sql, new PortfolioEditRowMapper(),email);
    }


    //5.11양서림
    public void portfolioupdate(PortfolioEditDO portfolioEditDO) {
        String sqlportfolio = "update PORTFOLIO set WORKABLE_LOCATION=?,MESSAGE=?,OTHER_CAREER=?,PORTFOLIO_TITLE=?,DESIRED_SALARY=?," +
                "DESIRED_WORK_TYPE=?,MESSAGE_TO_YOUTUBER=? where EDITOR_EMAIL=?";
        jdbcTemplate.update(sqlportfolio, portfolioEditDO.getLocation(), portfolioEditDO.getMessage(), portfolioEditDO.getCareer(),
                portfolioEditDO.getTitle(), portfolioEditDO.getSalary()
                , portfolioEditDO.getWorktype(), portfolioEditDO.getToyoutuber(), portfolioEditDO.getEdit_email());

        String sqlcareer = "update edit_career set START_DATE=?,END_DATE=? where EDITOR_EMAIL=?";
        jdbcTemplate.update(sqlcareer, portfolioEditDO.getStartdate(), portfolioEditDO.getEnddate(), portfolioEditDO.getEdit_email());

        String sqltools = "INSERT INTO EDIT_TOOLS_LIST VALUES(EDIT_TOOLS_NO_SEQ.NEXTVAL, ?,?)";
        for (String tool : portfolioEditDO.getEdit_tools()) {
            jdbcTemplate.update(sqltools, portfolioEditDO.getEdit_email(), tool);
            //조회할때 가장 최근의 번호 가져오면됨
        }

        String sqlmainvideo = "INSERT INTO VIDEO_LINKS VALUES(VIDEO_LINKS_SEQ.NEXTVAL, ?, ?, 1)"; //대표영상저장
        jdbcTemplate.update(sqlmainvideo,portfolioEditDO.getEdit_email(),portfolioEditDO.getMain_link());
    }

    public void editlinkUpdate(String link,String email,int count){ //편집영상저장
        String sqleditlink="INSERT INTO VIDEO_LINKS " +
                "VALUES(VIDEO_LINKS_SEQ.NEXTVAL,? , ?, ?)";
        jdbcTemplate.update(sqleditlink,email,link,count);


    }

    public void deletevideo(String email){
        String sqldelvideo = "Delete from video_links where EDITOR_EMAIL=?";
        jdbcTemplate.update(sqldelvideo,email);
    }

    public void deletetools(String email){
        String sqldedeltool = "Delete from EDIT_TOOLS_LIST where EDITOR_EMAIL=?";
        jdbcTemplate.update(sqldedeltool,email);
    }

    //희수
    //포트폴리오 삭제
    public void deletePortfolioPost(String email1) {
        jdbcTemplate.update("DELETE FROM portfolio WHERE EDITOR_EMAIL = ?", email1);
    }
}
