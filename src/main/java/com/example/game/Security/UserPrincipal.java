package com.example.game.Security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.game.entity.User;


public class UserPrincipal implements UserDetails{
	
	private User user;  // Userオブジェクトを保持します。
	
	public User getUser() {
		return this.user;
	}
    

    // コンストラクタでUserオブジェクトを受け取り、それをこのクラスのuserにセットします。
 
	public UserPrincipal(User user) {
        this.user = user;
    }
    public String getName(){
    	return user.getName();
    };
    public String getEvaluation(){
    	return user.getEvaluation();
    };
    public Integer getId() {
    	return user.getId();
    }
	

    @Override
	public String getPassword() {
		// TODO 自動生成されたメソッド・スタブ
		return user.getPassword();
	}

    @Override
	public String getUsername() {
		// TODO 自動生成されたメソッド・スタブ
		return user.getUsername();
	}
	
	
	// アカウントが期限切れでなければtrueを返す
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    // ユーザーがロックされていなければtrueを返す
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }    
    
    // ユーザーのパスワードが期限切れでなければtrueを返す
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		return Collections.singleton(new SimpleGrantedAuthority("USER"));
	}
    
    
	
}
