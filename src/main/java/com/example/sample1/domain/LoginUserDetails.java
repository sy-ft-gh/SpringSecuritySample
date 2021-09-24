package com.example.sample1.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// ログイン時に応答するためのユーザログイン情報(UserDetailsを継承する)
public class LoginUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    // 権限は一般ユーザ、システム管理者の２種類とする
    public enum Authority {ROLE_USER, ROLE_ADMIN}
    // データベースから取得したユーザ情報をメンバ変数として格納
    private Member member;
    // 
    private Set<Authority> authorities;
    
    /***
     * コンストラクタ
     * @param member ログイン情報として生成するユーザ情報。
     * @param authorities 権限情報。(ADMINはユーザメンテナンス可)
     */
    public LoginUserDetails(Member member) {
       this.member = member;
       // 全てのユーザはUSER権限を保持しているものとします
       this.authorities = EnumSet.of(Authority.ROLE_USER);
    }
    
    /**
     * 管理者権限保有状況を返却
     * @return 管理者の場合trueを返す
     */
    public boolean isAdmin() {
        return this.authorities.contains(Authority.ROLE_ADMIN);
    }
    /**
     * 管理者権限保有状況設定
     * @param isAdmin true=管理者権限有として設定、false=管理者権限無しとして設定
     */
    public void setAdmin(boolean isAdmin) {
        if (isAdmin) {
            this.authorities.add(Authority.ROLE_ADMIN);
        } else {
            this.authorities.remove(Authority.ROLE_ADMIN);
        }
    }
    
    @Override
    /**
     *  権限返却関数のオーバーライド 
     * 対象ユーザが持っている権限をSpringSecurityが取り出すために使用される
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Authority authority : this.authorities) {
            authorities.add(new SimpleGrantedAuthority(authority.toString()));
        }
        return authorities;
    }

    /**
            * パスワードの取得
     */
    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    /**
     * ユーザ名の取得
    */
    @Override
    public String getUsername() {
        return this.member.getUsername();
    }
    /**
     * 有効期限が切れているか(true=期限内)
     * 切れている場合は認証時にAccountExpiredExceptionが発生する
    */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * アカウントがロックされているか(true=ロックされていない)
     * 切れている場合は認証時にLockedExceptionが発生する
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 資格情報の有効期限が切れているか(true=期限内)
     * 切れている場合は認証時にCredentialsExpiredExceptionが発生する
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 有効なユーザーか(true=有効)
     * 無効な場合は認証時にDisabledExceptionが発生する
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}