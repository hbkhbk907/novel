package com.hbk.novel.controller;

import com.hbk.novel.entity.Novel;
import com.hbk.novel.entity.NovelChapter;
import com.hbk.novel.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    NovelService novelService;

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(Model model){
        List<Novel> novelList = novelService.queryNovels();
        model.addAttribute("novels",novelList);
        return "index.html";
    }

    @RequestMapping(value="/catalog",method = RequestMethod.GET)
    public String novelCatalog(Model model,@RequestParam(name = "id",required = true) Long id,
                               @RequestParam(name = "sortType",defaultValue = "1") String sortType){
        if (!sortType.matches("[12]")){
            return "error.html";
        }
        List<NovelChapter> chapterList = novelService.queryChaptersByNovelId(id,Integer.parseInt(sortType));
        Novel novel = novelService.queryNovelByNovelId(id);
        String sortUrl = null;
        String sortName = null;
        if("1".equals(sortType)){
            sortUrl = request.getRequestURL() + "?id="+ id + "&sortType=2";
            sortName = "倒序";
        }else{
            sortUrl = request.getRequestURL() + "?id="+ id + "&sortType=1";
            sortName = "正序";
        }

        model.addAttribute("novelName",novel.getName());
        model.addAttribute("chapters",chapterList);
        model.addAttribute("sortName",sortName);
        model.addAttribute("sortUrl",sortUrl);
        return "catalog.html";
    }

    @RequestMapping(value = "/error",method=RequestMethod.GET)
    public String error(){
        return "error.html";
    }


}
