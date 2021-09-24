package com.example.sample1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sample1.domain.LoginUserDetails;
import com.example.sample1.domain.Member;
import com.example.sample1.repository.MemberRepository;

// SpringSecurityがログインの実処理をDIコンテナから探せるようSerivceアノテーションを付加
// UserDetailsServiceを継承しUserDetailsを応答するサービス。SpringSecurityのユーザの照会機能に応える
@Service
public class MenberLoginService implements UserDetailsService {

    // DBからのMember情報選択 
    @Autowired
    private MemberRepository memberRepository;
    
    /**
     * loadUserByUserNameを上書く事でログイン処理としてデータベース処理を実装できる
     * @param username SpringSecurityにより引き渡されるusername← (http.formLogin().usernameParameter("username"))
     * @exception ユーザが見つからない場合はUsernameNotFoundExceptionを生成
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
        //DBからユーザ情報を取得。
        Member member = memberRepository.getMember(username)
                            // 取れなかった場合は例外を生成
                            .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        //ログイン情報として生成
        var login = new LoginUserDetails(member);
        //管理者権限を設定(アプリケーションの簡略化のため、ユーザ名がADMINの場合は管理者とする)
        login.setAdmin("ADMIN".equals(member.getUsername()));
        return login;
    }
}
