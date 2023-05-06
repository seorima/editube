package team_iproject_test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import team_iproject_test.model.UserModel;


@Log4j2
@RequiredArgsConstructor
@Controller
public class JoinController {

    @GetMapping("address-pop")
    public String addressPop(){



        return "addresspop";
    }


    @GetMapping("address-search")
    public String addressSearch(){

        return "addressSearch";
    }

    @GetMapping(value = "/join")
    public String rodeReturn() {


        return "join";
    }


    @ResponseBody
    @PostMapping("try-join")
    public ResponseEntity userName(@RequestBody UserModel userdata){
        // [object]

        System.out.println("로그인 시도");
        System.out.println(userdata);
        System.out.println(userdata.toString());
        // 로그인이나.. 회원가입 로직...
        // 오라클 DB 접속 후 insert ...

        // 중복유저...

        return new ResponseEntity("{\"result\" : \"성공!\"}", HttpStatus.OK);
    }



}