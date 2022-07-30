package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 检查用户是否已经完成登录
 */
@WebFilter(value = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LogginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取本次请求的URI
        String requestURI = request.getRequestURI();

        //2.判断本次请求是否需要处理
        //定义不需要拦截的uri
        String[] uris = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "user/login"
        };
        boolean check = check(uris,requestURI);

        //3.如果不需要处理，则直接放行
        if(check){
            filterChain.doFilter(request,response);
            return;
        }

        //4.判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("employee") != null ){

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setThreadLocal(empId);

            filterChain.doFilter(request,response);
            return;
        }
        //5.如果未登录则返回未登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    //定义check方法，判断请求是否需要拦截
    public boolean check(String[] uris,String requestURI){
        for (String uri : uris ){
            boolean match = PATH_MATCHER.match(uri,requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }

}
