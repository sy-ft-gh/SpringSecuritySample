package com.example.sample1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
// Spring SecurityでのConfigurationとしてマークするため@EnableWebSecurityを付加。
// これを実施する事でSpringSecuritygaが有効になる
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
     * configure メソッド
     * HttpSecurity クラスへ設定する事でセキュリティ設定を実施する
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        //ログインページを指定。（指定しない場合はSpringSecurityの規定画面が開かれる）
        http.formLogin()
            //ログインページへのアクセスは全員許可する。
            .loginPage("/login")
                // ログイン処理を行うURL。ここにPOSTするとログイン処理をFWが実施してくれる
                .loginProcessingUrl("/authenticate")
                    // ユーザ名とパスワードのPOSTパラメータ名を指定する
                    .usernameParameter("username")
                    .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll()
            //複数の設定を行う場合はand()でつなげる
            .and()
            //ログアウトのページも全員許可する。(このログアウトページに遷移するとセッションが破棄され、クッキーも削除)
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .permitAll();

        // 全てのリクエスト(anyRequest)に対して認証をかけるようにする(authorizeRequestsに情報を付加する事でURLへの設定を行う)
        http.authorizeRequests()
            // メンバー情報登録機能はADMINロールを持っているユーザに限定する
            .antMatchers("/Member/RegistForm").hasRole("ADMIN")  // Member入力画面(ADMINのロール保有者のみ)
            .antMatchers("/Member/Register").hasRole("ADMIN")    // Member登録画面(ADMINのロール保有者のみ)
            .anyRequest().authenticated();

        // セッション管理設定
        http.sessionManagement()
            // ログイン時のセッションIDを振りなおす（なりすまし防止等）
            .sessionFixation()
                .changeSessionId()
            // セッションが無効な場合(改ざんやクッキー削除)のリダイレクト（転送）先URL
            .invalidSessionUrl("/login")
            // 1ユーザ毎の同時接続可能なセッション数
            .maximumSessions(1)
                // 最大セッション数を超えてのログインを防ぐか（防ぐとエラーが発生する）デフォルト＝ファイル
                // falseの場合、同時接続可能なセッション数を超えた接続時に古いセッションが削除される
                .maxSessionsPreventsLogin(false)
                // セッションの有効期限が切れた場合のリダイレクト先URL
                .expiredUrl("/login");
    }
    
    // PasswordEncoderクラスを返却するメソッドを作成し、BCryptアルゴリズムを使用したエンコーダーを返却する
    // Bean登録する事で、このメソッドがパスワードの暗号化形式をSpringSecurityに認識させる役割となる
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    

}
