package com.huaxing.ssm.controller;

import com.huaxing.ssm.pojo.UserLogPO;
import com.huaxing.ssm.pojo.UserPO;
import com.huaxing.ssm.service.LogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/log")
public class LogController {

    @Resource
    private LogService logService;

    private static Log log = LogFactory.getLog(LogController.class);


    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(UserPO user, HttpSession Session){


        ModelAndView mav = new ModelAndView();

        UserPO userLogin = null;
        try {
            userLogin = logService.login(user);
            if(user == null){
                throw new Exception("登录失败");
            }

            Session.setAttribute("USER",userLogin);

            mav.setViewName("redirect:/log/logs.do");

            log.debug("login success!");

            return mav;

        } catch (Exception e) {

            mav.setViewName("loginError");
            e.printStackTrace();
        }
        return mav;
    }


    /**
     * 获取日志
     */
    @RequestMapping(value = "/logs")
    public ModelAndView toLogUI(HttpSession Session){
        ModelAndView mav = new ModelAndView();

        UserPO user = (UserPO) Session.getAttribute("USER");

        //获取日志
        List<UserLogPO> userLogs = logService.getLogs(user.getUserId());
        mav.setViewName("log");
        mav.addObject("userLogs",userLogs);
        return mav;
    }
}
