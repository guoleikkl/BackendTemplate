package com.personalize.userservice.contorller;
import com.personalize.common.entity.R;
import com.personalize.userservice.entity.LoginFormDTO;
import com.personalize.userservice.entity.NoteDTO;
import com.personalize.userservice.entity.UserDTO;
import com.personalize.userservice.entity.UserRegistrationDTO;
import com.personalize.userservice.service.INoteService;
import com.personalize.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public IUserService userService;
    @Autowired
    public INoteService noteService;

    @PostMapping("/register")
    public R<String> register(@RequestBody UserRegistrationDTO userRegister){
        R<String> res = userService.registerUser(userRegister);
        return res;
    }
    @PostMapping("/login")
    public R<String> login(@RequestBody LoginFormDTO loginFormDTO, HttpServletRequest request){
        R<String> res = userService.login(loginFormDTO,request);
        return res;
    }
    @GetMapping("/me")
    public R<UserDTO> getUserInfo(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        R<UserDTO> res = userService.getUserInfo(authorization);
        return res;
    }
    @PutMapping("/user")
    public R<Boolean> updateUserInfo(@RequestBody UserDTO userDTO){
        R<Boolean> res = userService.updateUserInfo(userDTO);
        return res;
    }
    @PostMapping("/note")
    public R<Boolean> updateUserNote(@RequestBody NoteDTO noteDTO, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        R<Boolean> res =  noteService.updateUserNote(noteDTO,authorization);
        return res;
    }
    @GetMapping("/note/{knowledgeId}")
    public R<NoteDTO> getUserNote(@PathVariable("knowledgeId")String knowledgeId,HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        R<NoteDTO> res =  noteService.getUserNote(knowledgeId,authorization);
        return res;
    }
    @GetMapping("/notes")
    public R<List<NoteDTO>> getAllNotes(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        R<List<NoteDTO>> res = noteService.getAllNotes(authorization);
        return res;
    }
}
