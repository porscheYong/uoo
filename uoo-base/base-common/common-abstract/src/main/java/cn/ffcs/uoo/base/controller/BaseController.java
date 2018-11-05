package cn.ffcs.uoo.base.controller;

import cn.ffcs.uoo.base.common.support.HttpKit;
import cn.ffcs.uoo.base.common.tool.util.FileUtil;
import cn.ffcs.uoo.base.warpper.BaseControllerWarpper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName BaseController
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/8/31 16:29
 * @Version 1.0.0
*/
public class BaseController {

    protected static String SUCCESS = "SUCCESS";
    protected static String ERROR = "ERROR";

    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";

    protected HttpServletRequest getHttpServletRequest() {
        return HttpKit.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpKit.getResponse();
    }

    protected HttpSession getSession() {
        return HttpKit.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpKit.getRequest().getSession(flag);
    }

    protected String getPara(String name) {
        return HttpKit.getRequest().getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        HttpKit.getRequest().setAttribute(name, value);
    }

//    protected Integer getSystemInvokCount() {
//        return (Integer) this.getHttpServletRequest().getServletContext().getAttribute("systemCount");
//    }


    /**包装一个list，让list增加额外属性
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/8/31 16:33
     * @param warpper
     * @return java.lang.Object
     * @throws
     * @since
     */
    protected Object warpObject(BaseControllerWarpper warpper) {
        return warpper.warp();
    }


    /**删除cookie
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/8/31 16:32
     * @param cookieName
     * @return void
     * @throws
     * @since
     */
    protected void deleteCookieByName(String cookieName) {

        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                Cookie temp = new Cookie(cookie.getName(), "");
                temp.setMaxAge(0);
                this.getHttpServletResponse().addCookie(temp);
            }
        }
    }


    /**删除所有cookie
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/8/31 16:34
     * @param
     * @return void
     * @throws
     * @since
     */
    protected void deleteAllCookie() {

        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            Cookie temp = new Cookie(cookie.getName(), "");
            temp.setMaxAge(0);
            this.getHttpServletResponse().addCookie(temp);
        }
    }


    /**返回前台文件流
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/8/31 16:34
     * @param fileName
     * @param filePath
     * @return org.springframework.http.ResponseEntity<byte[]>
     * @throws
     * @since
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, String filePath) {

        byte[] bytes = FileUtil.toByteArray(filePath);
        return renderFile(fileName, bytes);
    }


    /**返回前台文件流
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/8/31 16:43
     * @param fileName
     * @param fileBytes
     * @return org.springframework.http.ResponseEntity<byte[]>
     * @throws
     * @since
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {

        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
    }
}
