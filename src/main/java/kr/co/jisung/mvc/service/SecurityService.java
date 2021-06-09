package kr.co.jisung.mvc.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.jisung.configuration.BaseResponse;
import kr.co.jisung.configuration.BaseResponseCode;
import kr.co.jisung.mvc.domain.Member;
import kr.co.jisung.mvc.domain.MemberPrincipalVO;
import kr.co.jisung.mvc.repository.MemberRepository;

@Service
public class SecurityService implements UserDetailsService{
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//이 메소드에서 DB로부터 회원정보를 가져와 있는 회원인지 아닌지 체크여부를 하는 중요한 메소드 입니다.
	//loadUserByUsername()메서드는 SpringSecurity의 속성으로 지정한 loginProcessingUrl("/loginProcess")이 호출될때 수행
	@Override
	public UserDetails loadUserByUsername(String member_id) throws UsernameNotFoundException {
		//DB로 부터 회원정보를 가져옴
		//회원 정보를 List 형태로 받아오는 이유는 유저당 권한이 여러개일 수 도있기 때문
		ArrayList<Member> memberAuthes = memberRepository.findByMemberId(member_id);

		logger.info(memberAuthes.toString());
		logger.info("############loadUserByUsername#############3");

		if(memberAuthes.size() == 0) {
			throw new UsernameNotFoundException("Member " +member_id+ " Not Found");
		}
		
		//UserDetails 클래스를 상속받은 MemberPrincipalVO 리턴한다.
		return new MemberPrincipalVO(memberAuthes);
	}
	
	@Transactional
	public BaseResponse<String> InsertMember(Member member) {
		member.setMember_pw(bCryptPasswordEncoder.encode(member.getMember_pw()));
		member.setRoleName("MEMBER");
		//Member DB에 멤버 저장
		int flag = memberRepository.memberSave(member);
		
		if(flag > 0) {
			int member_seq = memberRepository.findMemberSeq(member.getMember_id());
			int role_seq = memberRepository.findRoleSeq(member.getRoleName());
			memberRepository.memberRoleSave(member_seq, role_seq);
			
			return new BaseResponse<String>(BaseResponseCode.SUCCESS, "회원가입 완료");
		}
		return new BaseResponse<String>(BaseResponseCode.SUCCESS, "회원가입 실패");
	}

}
