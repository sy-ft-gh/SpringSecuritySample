package com.example.sample1.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

// Member登録画面用入力フォームクラス
public class MemberRegistForm {
    // ユーザ名
    @NotEmpty
    @Size(min = 1, max = 25)
    private String userName;

    // パスワード
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]{6,12}", message="アルファベットと数字6～12文字で入力してください")
    private String password;

    // 以下、getter・setter
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userId) {
        this.userName = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
