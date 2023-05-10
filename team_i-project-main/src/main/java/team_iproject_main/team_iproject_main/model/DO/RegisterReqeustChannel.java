package team_iproject_main.team_iproject_main.model.DO;


import lombok.Data;

@Data
public class RegisterReqeustChannel {
    private String channel_id;
    private String youtuber_email;
    private Long subscribe;
    private Long video_count;
    private Long view_count;
}
