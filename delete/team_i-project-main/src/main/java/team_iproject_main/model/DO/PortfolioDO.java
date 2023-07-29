package team_iproject_main.model.DO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PortfolioDO {
    private String email;
    private String ispublic;
    private String workable;
    private String othercareer; //경력
    private String message; //할말
    private String title;   //제목
    private LocalDate postdate;
    private int desiredsalary;
    private String desiredworktype;
    private String messagetoyoutuber;

    //IS_PUBLIC 부분이 true면 보이게
}
