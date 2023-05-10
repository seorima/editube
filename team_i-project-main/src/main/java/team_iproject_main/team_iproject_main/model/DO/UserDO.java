package team_iproject_main.team_iproject_main.model.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDO {

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone_number;
    private String address;
    private String detail_addr;
    private String user_type;
    private String gender;
    private LocalDate birth_date;
    private LocalDateTime register_date;
    private String profile_photo;
    private String channel_id;
    private Long subscribe;
    private Long video_count;
    private Long view_count;

    public UserDO (String email, String password, String name, String nickname, String phone_number, String address, String detail_addr, String user_type,
                   String gender, LocalDate birth_date) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone_number = phone_number;
        this.address = address;
        this.detail_addr = detail_addr;
        this.user_type = user_type;
        this.gender = gender;
        this.birth_date = birth_date;
    }


    public UserDO (String email, String password, String name, String nickname, String phone_number, String address, String detail_addr, String user_type,
                   String gender, LocalDate birth_date, String channel_id, Long subscribe, Long video_count, Long view_count) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone_number = phone_number;
        this.address = address;
        this.detail_addr = detail_addr;
        this.user_type = user_type;
        this.gender = gender;
        this.birth_date = birth_date;
        this.channel_id = channel_id;
        this.subscribe = subscribe;
        this.video_count = video_count;
        this.view_count = view_count;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    //0506- 손주현
    //매개변수의 name이랑 phonenum 비교해서 boolean으로 넘겨줌
    public boolean checkNameAndPhonenum(String name, String phone_number) {
        return (this.name.equals(name) && this.phone_number.equals(phone_number));
    }


}