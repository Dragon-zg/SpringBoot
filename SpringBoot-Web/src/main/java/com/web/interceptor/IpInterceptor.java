package com.web.interceptor;

import com.google.common.collect.Lists;
import com.web.annotation.IpStint;
import com.web.constants.PrefixConstant;
import com.web.constants.SymbolConstant;
import com.web.utils.PropertiesUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Dragon-zg
 * @date 2018/10/25 14:25
 **/
@Log4j2
public class IpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            IpStint ipFilter = ((HandlerMethod) handler).getMethodAnnotation(IpStint.class);
            if (ipFilter == null) {
                return true;
            }

            //获取请求主机IP地址
            String ipAddress = getIpAddress(request);
            //黑名单
            List<String> denyIpList = null;
            //白名单
            List<String> allowIpList = null;

            if (ArrayUtils.isEmpty(ipFilter.denyIp())) {
                //读取默认配置
                String denyIps = PropertiesUtil.getPropertiesValue(PrefixConstant.IP_INTERCEPTION_DEFAULT_DENY);
                if (StringUtils.isNotBlank(denyIps)) {
                    denyIpList = Lists.newArrayList(denyIps.trim().split(SymbolConstant.SYMBOL_COMMA_HALF));
                }
            } else {
                denyIpList = Lists.newArrayList(ipFilter.denyIp());
            }

            if (ArrayUtils.isEmpty(ipFilter.allowIp())) {
                //读取默认配置
                String allowIps = PropertiesUtil.getPropertiesValue(PrefixConstant.IP_INTERCEPTION_DEFAULT_ALLOW);
                if (StringUtils.isNotBlank(allowIps)) {
                    allowIpList = Lists.newArrayList(allowIps.trim().split(SymbolConstant.SYMBOL_COMMA_HALF));
                }
            } else {
                allowIpList = Lists.newArrayList(ipFilter.allowIp());
            }

            //设置了黑名单,黑名单内ip不给通过
            if (CollectionUtils.isNotEmpty(denyIpList)) {
                if (denyIpList.contains(ipAddress)) {
                    log.warn("denyIpList: {} ,IP: {} 被拦截!", denyIpList, ipAddress);
                    response.setStatus(500);
                    return false;
                }
            }

            //设置了白名单,  只有白名单内的ip给通过
            if (CollectionUtils.isNotEmpty(allowIpList)) {
                if (allowIpList.contains(ipAddress)) {
                    log.debug("allowIpList: {} ,IP: {} 被放行!", allowIpList, ipAddress);
                    return true;
                } else {
                    log.warn("allowIpList: {} ,IP: {} 没有放行权利!", allowIpList, ipAddress);
                    response.setStatus(500);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws IOException
     */
    public final static String getIpAddress(HttpServletRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (log.isInfoEnabled()) {
            log.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (log.isInfoEnabled()) {
                    log.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (log.isInfoEnabled()) {
                    log.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (log.isInfoEnabled()) {
                    log.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (log.isInfoEnabled()) {
                    log.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (log.isInfoEnabled()) {
                    log.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}
