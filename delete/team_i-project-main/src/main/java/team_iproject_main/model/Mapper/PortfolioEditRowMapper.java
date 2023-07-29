package team_iproject_main.model.Mapper;

import org.springframework.jdbc.core.RowMapper;
import team_iproject_main.model.DO.PortfolioEditDO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PortfolioEditRowMapper implements RowMapper<PortfolioEditDO> {

    @Override
    public PortfolioEditDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PortfolioEditDO portfolioEditDO = new PortfolioEditDO();
        portfolioEditDO.setGender(rs.getString("GENDER"));
        portfolioEditDO.setName(rs.getString("NAME"));


        portfolioEditDO.setEdit_email(rs.getString("EDITOR_EMAIL"));
        portfolioEditDO.setLocation(rs.getString("WORKABLE_LOCATION"));
        portfolioEditDO.setStartdate(rs.getDate("START_DATE").toLocalDate());
        portfolioEditDO.setEnddate(rs.getDate("END_DATE").toLocalDate());
        portfolioEditDO.setCareer(rs.getString("OTHER_CAREER"));
        portfolioEditDO.setMessage(rs.getString("MESSAGE"));
//        portfolioEditDO.setMain_link(rs.getString("VIDEO_LINK"));
//        portfolioEditDO.setEdit_link(rs.getString("VIDEO_LINK"));
        portfolioEditDO.setTitle(rs.getString("PORTFOLIO_TITLE"));
        portfolioEditDO.setSalary(rs.getString("DESIRED_SALARY"));
        portfolioEditDO.setWorktype(rs.getString("DESIRED_WORK_TYPE"));
        portfolioEditDO.setToyoutuber(rs.getString("MESSAGE_TO_YOUTUBER"));
        return portfolioEditDO;
    }
}
