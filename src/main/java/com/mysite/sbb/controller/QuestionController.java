package com.mysite.sbb.controller;

import com.mysite.sbb.entity.Question;
import com.mysite.sbb.repository.QuestionRepository;
import com.mysite.sbb.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model){
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }
}
