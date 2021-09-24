package com.example.sample1.domain;

// Memberクラス。ログインユーザの情報を格納
public class Member {
    // ID 整数型のPK（主キー）
    private int id;
    // ユーザ名
    private String userName;
    // パスワード（BCryptによりエンコード＆文字列化したデータ）
    private String password;
    
    // 以下 getter,setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return userName;
    }
    public void setUsername(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
