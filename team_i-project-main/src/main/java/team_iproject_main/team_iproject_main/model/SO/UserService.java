package team_iproject_main.team_iproject_main.model.SO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_iproject_main.team_iproject_main.exception.*;
import team_iproject_main.team_iproject_main.model.DAO.UserDao;
import team_iproject_main.team_iproject_main.model.DO.*;

import javax.sound.sampled.Port;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void editor_signUp(RegisterRequest req) {
        UserDO user = userDao.findByEmail(req.getEmail());
        UserDO userSelectByNickName = userDao.findByNickname(req.getNickname());

        if (user != null) {
            throw new DuplicateEmailException();
        }
        if (userSelectByNickName != null){
            throw new DuplicateNickNameException();
        }
        user = new UserDO(req.getEmail(), req.getPassword(), req.getName(), req.getNickname(), req.getPhone_number(), req.getAddress(),
                req.getDetail_addr(), "편집자", req.getGender(), LocalDate.parse(req.getBirth_date()));
        userDao.createEditor(user);
    }

    public void youtuber_signUp(RegisterRequest req) {
        UserDO user = userDao.findByEmail(req.getEmail());
        UserDO userSelectByNickName = userDao.findByNickname(req.getNickname());
        RegisterReqeustChannel uq = userDao.findByChannel(req.getChannel_id());
        if (user != null) {
            throw new DuplicateEmailException();
        }
        if (userSelectByNickName != null){
            throw new DuplicateNickNameException();
        }
        if (uq != null) {
            throw new DuplicateChannelException("channel_id address is already registered.");
        }

        user = new UserDO(req.getEmail(), req.getPassword(), req.getName(), req.getNickname(), req.getPhone_number(), req.getAddress(),
                req.getDetail_addr(), "유튜버", req.getGender(), LocalDate.parse(req.getBirth_date()), req.getChannel_id(),
                req.getSubscribe(), req.getVideo_count(), req.getView_count());
        userDao.createYoutuber(user);
    }

    public List<UserDO> getAllMembers() {
        return userDao.findAll();
    }

    public UserDO findUser(String email){
        return userDao.findByEmail(email);
    }


    //0506-손주현 findUser 메소드 오버로딩
    public UserDO findUser(String name, String phone_number){
        return userDao.findByNameAndPhone(name, phone_number);
    }
    //0508손주현 - checkLoginAuth 수정
    public boolean checkLoginAuth(LoginCommand login) {
        boolean result = false;
        UserDO users = userDao.findByEmail(login.getEmail());
        if(users == null){
            throw new UserNotFoundException();
        } else if (!users.checkPassword(login.getPassword())) {
            throw new WrongPasswordException();
        }
        if(users != null && users.checkPassword(login.getPassword())){
            result = true;
        }
        return result;
    }

    //0506-손주현
    //0508손주현 - checkFindId 수정
    public boolean checkFindId(FindIdRequest req) throws UserNotFoundException {
        boolean result = false;
        UserDO users = userDao.findByNameAndPhone(req.getName(), req.getPhone_number());
        if(users == null){
            throw new UserNotFoundException();
        }
        if(users != null && users.checkNameAndPhonenum(req.getName(), req.getPhone_number())){
            result = true;
        }
        return result;
    }

    // 비밀번호 찾기 후 비밀번호 변경
    public void changePwd(String email, String newpwd) {
        userDao.updatePassword(email, newpwd);
    }

    public void mypageupdate(UserUpdateRequest userUpdateRequest, String id) {
        userDao.updateUserInfo(userUpdateRequest,id);
    }

    public void portfolioupdate(PortfolioEditRequest edit, String email){
        userDao.portfolioupdate(edit,email);
    }

    public UserDO findNickname(String nickname){return userDao.findByNickname(nickname);}

    public UserDO findById(String email) {
        return userDao.findByEmail(email);
    }
}
