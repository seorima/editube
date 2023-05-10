package team_iproject_main.team_iproject_main.model.DO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JosoModel {

    private String username;
    private String password;
    private String address;
}
