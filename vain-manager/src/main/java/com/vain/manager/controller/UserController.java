package com.vain.manager.controller;

import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.common.exception.ErrorRCodeException;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.BNews;
import com.vain.manager.entity.User;
import com.vain.manager.service.IUserService;
import com.vain.manager.util.ReptileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vain
 * @description: 账号信息操作controller
 * @date 2017/8/31 11:54
 */
@RequestMapping("/user")
@Controller
public class UserController extends AbstractBaseController<User> {

    @Autowired
    private IUserService userService;

    /**
     * 获取新闻数据
     *
     * @return
     */
    public
    @RequestMapping("/getNews/{type}")
    @ResponseBody
    Response<BNews> getNews(@PathVariable("type") int type) throws Exception {
        List<BNews> news = type == 1 ? getNowNews() : getTodayNews();
        Response<BNews> response = new Response<>();
        response.setCode(SysConstants.Code.SUCCESS_CODE);
        response.setMsg(SysConstants.Code.SUCCESS_MSG);
        response.setData(news);
        return response;
    }

    //获取今日热点
    List<BNews> getTodayNews() {
        String url = "http://top.baidu.com/buzz?b=341&c=513&fr=topbuzz_b1";
        String http = ReptileUtils.getHttp(url, "gb2312");
        Document html = Jsoup.parse(http);
        Elements select = html.select("table.list-table > tbody > tr");
        select.removeAttr("tr.item-tr");
        //select.remove(select.select("tr.item-tr"));
        List<BNews> news = new ArrayList<>();
        for (int i = 1; i < 11 && select.size() > 10; i++) {
            if (select.get(i).hasClass("item-tr"))
                continue;
            BNews entity = new BNews(select.get(i).select("td.keyword > a.list-title").text(),
                    select.get(i).select("td.keyword > a.list-title").attr("href"),
                    Integer.valueOf(select.get(i).select("td.first > span").text()),
                    Integer.valueOf(select.get(i).select("td.last > span").text()),
                    select.get(i).select("td.last > span").hasClass("icon-rise"),
                    select.get(i).select("td.keyword > span").hasClass("icon-new")
            );
            news.add(entity);
        }
        return news;
    }

    //获取实时热点
    List<BNews> getNowNews() {
        String url = "http://top.baidu.com/buzz?b=1&c=513&fr=topbuzz_b341_c513";
        String http = ReptileUtils.getHttp(url, "gb2312");
        Document html = Jsoup.parse(http);
        Elements select = html.select("table.list-table > tbody > tr");
        List<BNews> news = new ArrayList<>();
        for (int i = 1; i < 15 && select.size() > 10; i++) {
            if (StringUtils.isEmpty(select.get(i).select("td.keyword > a.list-title").text()))
                continue;
            BNews entity = new BNews(select.get(i).select("td.keyword > a.list-title").text(),
                    select.get(i).select("td.keyword > a.list-title").attr("href"),
                    Integer.valueOf(select.get(i).select("td.first > span").first().text()),
                    Integer.valueOf(select.get(i).select("td.last > span").text()),
                    select.get(i).select("td.last > span").hasClass("icon-rise"),
                    select.get(i).select("td.keyword > span").hasClass("icon-new")
            );
            news.add(entity);
        }
        return news;
    }

    @Override
    public Response<User> getPagedList(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> getList(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> get(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> getById(@PathVariable long id, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> add(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> modify(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> delete(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 用户登录
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response<User> login(@RequestBody User entity) throws ErrorRCodeException {
        if (entity == null || entity.getUserName() == null || entity.getPasswd() == null)
            throw new ErrorRCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<User> response = new Response<>();
        response.setData(userService.login(entity));
        response.setCode(SysConstants.Code.SUCCESS_CODE);
        response.setMsg(SysConstants.Code.SUCCESS_MSG);
        return response;
    }
}
