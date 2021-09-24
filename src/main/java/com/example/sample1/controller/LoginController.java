package com.example.sample1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    
    @RequestMapping("/login")
    public ModelAndView showLoginForm(ModelAndView model) {
        //ログイン画面へ遷移。(Login.html)
        model.setViewName("Login");
        return model;
    }
    /**
     * メインページに遷移する。
     * ログインが成功した場合、このメソッドが呼び出される。
     */
    @RequestMapping("/")
    public ModelAndView login(ModelAndView model) {
        //メインページ。
        model.setViewName("index");
        return model;
    }
}
