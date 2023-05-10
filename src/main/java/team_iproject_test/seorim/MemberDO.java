package team_iproject_test.seorim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
public class MemberDO {


        private String nickname;
        private String id;
        private String name;
        private String phone_number;
        private String address;
        private String detail_address;
        private LocalDate birth;

}


