package team_iproject_main.model.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import team_iproject_main.model.DO.ChannelCategoryDO;
import team_iproject_main.model.DO.EditToolsRecruitDO;
import team_iproject_main.model.DO.RecruitBoardDO;
import team_iproject_main.model.DO.RecruitDO;
import team_iproject_main.model.Mapper.ChannelCategoryRowMapper;
import team_iproject_main.model.Mapper.EditToolsRecruitRowMapper;
import team_iproject_main.model.Mapper.RecruitRowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class RecruitBoardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createRecruitBoard(RecruitBoardDO recruitDO) {

        String sql = "INSERT INTO RECRUIT_BOARD(RECRUIT_NO, YOUTUBER_EMAIL, RECRUIT_TITLE, DUTY, WORKLOAD, REQUIREMENT, " +
                "SALARY_DETAIL, PREFERENCE, ENVIRONMENT, WELFARE, RECRUIT_DETAIL, SALARY, DEADLINE, ORIGINAL_LINK) " +
                "VALUES (RECRUIT_BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[] { "RECRUIT_NO" });
            ps.setString(1, recruitDO.getYoutuber_email());
            ps.setString(2, recruitDO.getRecruit_title());
            ps.setString(3, recruitDO.getDuty());
            ps.setString(4, recruitDO.getWorkload());
            ps.setString(5, recruitDO.getRequirement());
            ps.setString(6, recruitDO.getSalary_detail());
            ps.setString(7, recruitDO.getPreference());
            ps.setString(8, recruitDO.getEnvironment());
            ps.setString(9, recruitDO.getWelfare());
            ps.setString(10, recruitDO.getRecruit_detail());
            ps.setLong(11, recruitDO.getSalary());
            ps.setDate(12, Date.valueOf(recruitDO.getDeadline()));
            ps.setString(13, recruitDO.getOriginal_link());
            return ps;
        }, keyHolder);

        int recruitNo = keyHolder.getKey().intValue();

        String sqltools = "INSERT INTO EDIT_TOOLS_RECRUIT(EDIT_TOOLS_NO, RECRUIT_NO, EDIT_TOOL) VALUES (EDIT_TOOLS_RECRUIT_SEQ.NEXTVAL, ?, ?)";
        for(String tool : recruitDO.getEdit_tools()){
            jdbcTemplate.update(sqltools, recruitNo, tool);
        }

        String sqlcategories = "INSERT INTO CHANNEL_CATEGORY(CATEGORY_NO, RECRUIT_NO, CATEGORY) VALUES (CHANNEL_CATEGORY_SEQ.NEXTVAL, ?, ?)";
        for(String category : recruitDO.getChannel_categories()){
            jdbcTemplate.update(sqlcategories, recruitNo, category);
        }
    }

    //희수
    //게시글 조회
    public List<RecruitDO> RecruitAll() {
        return jdbcTemplate.query("select * from RECRUIT_BOARD", new RecruitRowMapper());
    }


    //희수
    //게시글 상세조회
    public RecruitDO selectRecruitPost(int recruitNo) {
        return jdbcTemplate.queryForObject("select * from recruit_board where RECRUIT_NO = ? " , new RecruitRowMapper(), recruitNo);
    }


    //주현 0511
    //편집자가 마이페이지 - 지원현황 : 자신만 지원한 구인글 내림차순 추출
    public List<RecruitDO> findAllApplyByEmail(String email){
        String sql = "SELECT * FROM RECRUIT_BOARD WHERE RECRUIT_NO IN" +
                "(SELECT RECRUIT_NO FROM APPLY_EDITOR WHERE EDITOR_EMAIL = ?) ORDER BY RECRUIT_NO DESC";

        return jdbcTemplate.query(sql, new RecruitRowMapper(), email);
    }

    public List<ChannelCategoryDO> getCategories(int recruitNo){
        String sql = "SELECT * FROM CHANNEL_CATEGORY WHERE RECRUIT_NO = ?";
        return jdbcTemplate.query(sql, new ChannelCategoryRowMapper(), recruitNo);
    }

    public List<EditToolsRecruitDO> getTools(int recruitNo){
        String sql = "SELECT * FROM EDIT_TOOLS_RECRUIT WHERE RECRUIT_NO = ?";
        return jdbcTemplate.query(sql, new EditToolsRecruitRowMapper(), recruitNo);
    }

    //희수
    //게시글 조회
    // 준영 페이징 추가
    public List<RecruitDO> findRecruit(int postsPerPage, int offset) {
        String query = "SELECT * FROM (SELECT ROWNUM AS rn, rb.* FROM RECRUIT_BOARD rb) WHERE rn BETWEEN ? AND ?";
        return jdbcTemplate.query(query, new Object[]{offset + 1, offset + postsPerPage}, new RecruitRowMapper());
    }

    // 준영 페이징 추가
    public int getTotalPosts() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RECRUIT_BOARD", Integer.class);
    }

    //희수
    //구인글  삭제
    public void deleteRecruitPost(String email) {
        jdbcTemplate.update("DELETE FROM recruit_board WHERE YOUTUBER_EMAIL = ?", email);
    }
}
