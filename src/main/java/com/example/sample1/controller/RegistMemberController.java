package com.example.sample1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.sample1.domain.Member;
import com.example.sample1.form.MemberRegistForm;
import com.example.sample1.service.MemberRegisterService;


// Member登録コントローラ
@Controller
@SessionAttributes(value = {"inputForm"})
public class RegistMemberController {
    
    @Autowired
    private MemberRegisterService memberRegistService;

    @ModelAttribute("inputForm")
    public MemberRegistForm getMemberRegistForm() {
        return new MemberRegistForm();
    }

    /**
     * 会員情報入力画面に遷移する。
     */
    @RequestMapping("/Member/RegistForm")
    public ModelAndView showRegistMemberForm(SessionStatus sessionStatus, ModelAndView model) {
        
        // view名を設定(入力、エラー表示、登録確認で共通)
        model.setViewName("memberregist");

        // セッション情報をクリアする
        sessionStatus.setComplete();
        //MEMBER情報入力表示
        model.addObject("status", 0);
        //セッションに入力情報を追加
        getMemberRegistForm();
        return model;
    }

    @RequestMapping("/Member/Register")
    public ModelAndView registerUser(@ModelAttribute("inputForm") @Validated MemberRegistForm memberRegistForm,
                                     BindingResult result,
                                     SessionStatus sessionStatus, 
                                     ModelAndView model) {

        // view名を設定
        model.setViewName("memberregist");
        if (result.hasErrors()) {
            //入力エラー表示
            model.addObject("status", 1);
            return model;
        }

        //MEMBERテーブルにINSERTする時の引数。
        Member member = new Member();

        member.setUsername(memberRegistForm.getUserName());
        member.setPassword(memberRegistForm.getPassword());

        //MEMBERテーブルにINSERTする。
        if (!memberRegistService.registerMember(member)){
            // 既に同じユーザ名の登録があるエラーとする
            model.addObject("status", 2);
            return model;
        }

        // セッション情報の削除
        sessionStatus.setComplete();
        //MEMBER情報登録確認表示
        model.addObject("status", 3);

        return model;
    }
}