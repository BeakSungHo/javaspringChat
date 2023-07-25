package com.korea.testChat.Controller;



import com.korea.testChat.CreateForm.UserCreateForm;
import com.korea.testChat.Entity.User_T;
import com.korea.testChat.Repository.UserRepository;
import com.korea.testChat.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

//구글 api를 사용하기위한 추가 컨트롤러
//https://console.cloud.google.com/apis/credentials?hl=ko&project=evocative-tube-392908
//https://chb2005.tistory.com/182
//https://chb2005.tistory.com/173
@RequestMapping(value ="/user",produces = "application/json")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final User_T user_t;




    @GetMapping("/login")
//    public ModelAndView userLogin(){
    public String userLogin(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("Login/login_main");
        return "Login/login_main";
    }


    @GetMapping("/Initiation_General")
    public  String  user_Initiation_General(UserCreateForm userCreateForm){
        return "Login/initiation_general";
    }
    //    회원가입 Post맵핑
    @PostMapping("/Initiation_General")
    public String Initiation_GeneralPost(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Login/initiation_general";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "Login/initiation_general";
        }

        userService.create(userCreateForm.getLoginId(),
                userCreateForm.getEmail(), userCreateForm.getPassword1(),userCreateForm.getNickname());

        return "redirect:/chat";
    }

//    아이디중복체크 ajax방식으로 교환
    @RequestMapping(value ="/idCheck",method =RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> idCheck(@RequestParam Map<String, Object> map){
        String s= (String)map.get("loginId");
//        System.out.println("입력값 확인");
//        System.out.println(s);
//        System.out.println(userService.checkLoginIdDuplicate(s));
        if (userService.checkLoginIdDuplicate(s)) {
            return ResponseEntity.ok("DUPLICATED");
        } else {
            return ResponseEntity.ok("AVAILABLE");
        }
    }

}



