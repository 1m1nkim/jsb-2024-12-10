package com.mysite.sbb;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";
    }

    @GetMapping("/sbb")
    @ResponseBody
    public String index(){
        return "index";
    }
    @GetMapping("/question/list")
    public String list(Model model){
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);
        //model로 보낸걸 questionList의 이름으로 타임리프에서 받을 수 있게
        return "question_list";
    }

    @GetMapping("/question/detail/{id}")
    public String detial(Model model, @PathVariable("id") Integer id){
        return "question_detail";
    }
}
