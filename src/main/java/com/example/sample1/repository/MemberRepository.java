package com.example.sample1.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Options;

import com.example.sample1.domain.Member;

@Mapper
public interface MemberRepository {
    // Memberテーブル用リポジトリ
    // DDL:
    //   CREATE TABLE MEMBER(ID SERIAL PRIMARY KEY , USERNAME VARCHAR(64) NOT NULL, PASSWORD VARCHAR(64) NOT NULL);

    // Memberテーブルへの追加処理
    //
    @Insert("INSERT INTO MEMBER (USERNAME,PASSWORD) VALUES (#{username},#{password})")
    // IDは自動連番のためuseGeneratedKeysでid列を指定する
    @Options(useGeneratedKeys=true, keyColumn="id")
    public int insertMember(Member member);
 
    // UserNameを指定したレコード取得処理
    @Select("SELECT ID,USERNAME,PASSWORD FROM MEMBER WHERE USERNAME=#{username}")
    public Optional<Member> getMember(String username);
}
