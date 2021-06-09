package kr.co.jisung.mvc.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class MemberPrincipalVO implements UserDetails{
	
	private ArrayList<Member> member;
	
	public MemberPrincipalVO(ArrayList<Member> memberAuthes) {
		this.member = memberAuthes;
	}
	
	//유저가 갖고 있는 권한 목록
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(int x=0 ; x<member.size();x++) {
			authorities.add(new SimpleGrantedAuthority(member.get(x).getRoleName()));
		}
		
		return authorities;
		
	}

	//유저 비밀번호
	@Override
	public String getPassword() {
		return member.get(0).getMember_pw();
	}

	@Override
	public String getUsername() {
		return member.get(0).getMember_id();
	}

	//유저 아이디가 만료되었는지
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//유저 아이디가 Lock 걸렸는지
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	//비밀번호가 만료 되었는지
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	//계정이 활성화 되었는지
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
