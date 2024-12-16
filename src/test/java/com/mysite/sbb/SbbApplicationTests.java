package com.mysite.sbb;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;
//	@Test
//	void testJpaMake() {
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해서 알고 싶습니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1);
//	}
	@Test
	void testJpa() {
		List<Question> all = questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void testJpa1(){
		Optional<Question> oq = questionRepository.findById(1);
		if(oq.isPresent()){
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}
	@Test
	void testSubject(){
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}
	@Test
	void testSubjectAndContent(){
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
	}

	@Test
	void testSubjectLike(){
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}
	@Test
	void testEntityUpdate(){
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}
	@Test
	void testEntityDelete(){
		assertEquals(2, questionRepository.count());
		//현재 데이터베이스의 개수
		Optional<Question> oq = questionRepository.findById(1);
		//데이터베이스의 id가 1이 있으면 대입
		assertTrue(oq.isPresent());
		//만약 oq의 데이터가 있으면 true하고 테스트 계속
		Question q = oq.get();
		//oq데이터를 q에 대입
		questionRepository.delete(q);
		//oq의 값을 가진 q의 id를 갖고있는 데이터를 삭제
		assertEquals(1, questionRepository.count());
		//데이터가 1개가 됐다면 테스트 종료
	}

	@Test
	void testAnswerAdd(){
		Optional<Question> oq = questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		answerRepository.save(a);
	}

	@Test
	void testAnswerFind(){
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@Test
	@Transactional
	void testAnswerQuestFind(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}
