package com.mysite.sbb;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/")
    public String root(){
        return "redirect:/list";
    }

    @GetMapping("/sbb")
    @ResponseBody
    public String index(){
        return "index";
    }
    @GetMapping("/list")
    public String list(Model model){
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);
        //model로 보낸걸 questionList의 이름으로 타임리프에서 받을 수 있게
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
