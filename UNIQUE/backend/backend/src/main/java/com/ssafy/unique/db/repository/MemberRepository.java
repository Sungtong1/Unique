package com.ssafy.unique.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.unique.db.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByMemberId(String memberId);
	
	@Query(value = " select member_address from member where member_seq = ? ", nativeQuery = true)
	String findMemberAddressByMemberSeq(Long memberSeq);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = " update member set member_address = ? where member_seq = ?", nativeQuery = true)
	Integer updateMemberAddressByMemberSeq(String memberAddress, Long memberSeq);
}