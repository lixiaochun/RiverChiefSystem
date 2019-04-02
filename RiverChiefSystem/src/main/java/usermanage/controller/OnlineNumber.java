package usermanage.controller;

import common.util.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import usermanage.service.LogService;
import usermanage.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/online")
public class OnlineNumber {

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @RequestMapping("/getnum")
    public @ResponseBody
    Map<Object, Object> getnum(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            SessionListener sessionListener = new SessionListener();
            map.put("online", sessionListener.getCounter());
            map.put("result", 1);
            logService.addLog(struserId, "/online/getnum", "在线人数", "none", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/online/getnum", "在线人数", "none", "info", "fail，请登录后操作");
        }
        return map;
    }
}
