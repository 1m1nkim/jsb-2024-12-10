package com.mysite.sbb;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.repository.AnswerRepository;
import com.mysite.sbb.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.AssertTrue;
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

    @Test
    void makeQuery(){
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장
    }

    @Test
    void findAll(){
        List<Question> all = questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    void findById(){
        Optional<Question> oq = questionRepository.findById(1);
        if(oq.isPresent()){
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    @Test
    void findBySubject(){
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q.getId());
    }

    @Test
    void findBySubjectAndContent(){
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }

    @Test
    void findBySubjectLike(){
        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    void updateQuestion(){
        Optional<Question> oq = questionRepository.findById(1);
        if(oq.isPresent()){
            Question q = oq.get();
            q.setSubject("수정된 제목");
            questionRepository.save(q);
        }
    }

    @Test
    void deleteQuestion(){
        Optional<Question> oq = questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        questionRepository.delete(q);
    }

    @Test
    void createQuestion(){
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
    void getQuestionId(){
        Optional<Answer> oa = answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
    }
    @Test
    @Transactional
    void getIdQuestion(){
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
}
