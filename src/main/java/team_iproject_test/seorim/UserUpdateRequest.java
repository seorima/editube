package team_iproject_test.seorim;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UserUpdateRequest {

    private String nickname;
    private String name;
    private String phone_number;
    private String address;
    private String detail_address;
    private String birth;


}
