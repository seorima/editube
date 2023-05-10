package team_iproject_main.team_iproject_main.model.DO;

import lombok.Data;

@Data
public class PortfolioEditRequest {

    private String gender;
    private String area;
    private String location; //근무가능지역
    private String editor; //편집툴
    private String startdate; //편집경력시작일
    private String enddate; //편집경력마감일
    private String career; //다른기업경력
    private String message; //하고싶은말
    private String main_link;
    private String edit_link;

    private String title;
    private String salary;
    private String worktype;
    private String toyoutuber;



}
