package kr.co.jisung.mvc.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import kr.co.jisung.mvc.domain.Member;

@Repository
public interface MemberRepository {
	//유저 정보
	ArrayList<Member> findByMemberId(String member_id);

	//유저 저장
	int memberSave(Member member);

	//유저 권한 저장
	int memberRoleSave(int member_seq, int role_seq);

	//유저 FK번호 알아내기
	int findMemberSeq( String member_id);

	//권한 FK번호 알아내기
	int findRoleSeq( String roleName);
}
