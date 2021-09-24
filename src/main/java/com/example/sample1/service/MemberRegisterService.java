package com.example.sample1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


import com.example.sample1.domain.Member;
import com.example.sample1.repository.MemberRepository;

// Controllerから呼び出されるサービスクラスとして作成
@Service
// 本クラスは主にDB処理となるため。参照系のSQLが多くなるのが常なので、読み取り専用をクラス全体として指定する
@Transactional(readOnly = true)
public class MemberRegisterService {    
    // SQL発酵処理を行うリポジトリを割り当て
    @Autowired
    MemberRepository memberRepository;

    // パスワードをエンコーディングするエンコーダクラスを割り当て
    @Autowired
    PasswordEncoder passwordEncoder;
    
    /**
     * Member情報をDBに登録。
     * 登録処理なので読み取り専用は外す。また、トランザクションが発行前の場合新規に発行
     * @param member 登録対象のメンバー情報を設定
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean registerMember(Member member) {
        if (this.getMember(member.getUsername()) != null) {
            // 既に同一ユーザ名で登録されている場合はエラー
            return false; 
        }
        //パスワードをハッシュ化して、SQL実行処理に渡すオブジェクトにセット。
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        // 登録実行
        memberRepository.insertMember(member);
        return true;
    }
    
    /**
     * ユーザ名に該当するメンバー情報を取得
     * @param userName 対象のユーザ名
     * @return 該当あり：メンバー情報、該当なし：null
     */
    public Member getMember(String userName) {
        var member = memberRepository.getMember(userName);
        
        return member.isPresent() ? member.get() : null;
    }
}