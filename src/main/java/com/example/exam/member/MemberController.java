package com.example.exam.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(MemberForm memberForm) {
        return "member_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member_form";
        }
        if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", " 두 비밀번호가 일치하지 않습니다.");
        }
        try {
            this.memberService.signup(memberForm.getUsername(), memberForm.getPassword1(), memberForm.getNickname());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 아이디입니다.");
            return "member_form";
        } catch (RuntimeException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "member_form";
        }
        return "redirect:/article/list";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @PostMapping("/login")
    public String login(Model model, Principal principal) {
        model.addAttribute("username",principal.getName());
        return "redirect:/article/list";
   }
}
