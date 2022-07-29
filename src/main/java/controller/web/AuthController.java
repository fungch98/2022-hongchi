/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.web;

import com.ae21.bean.ResultBean;
import com.ae21.bean.UserAuthorizedBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.UserDAO;
import com.ae21.studio.hongchi.entity.system.CustFrameHandler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController {
     private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    
    @RequestMapping(value = "/{langCode}/login.html")
    protected String login(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
            )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "web/page/login.jsp");
         CommonHandler common=new CommonHandler();
        ResultBean result=null;
         try{ 
            request.setAttribute("pageLink", "login");
            request.setAttribute("pagePrefix", "auth/");
            this.frameHandler.initLang(request, "login", langCode);
            if(request.getSession().getAttribute("ERROR.LOGIN")!=null){
                result=(ResultBean)request.getSession().getAttribute("ERROR.LOGIN");
                System.out.println(result.getCode());
                request.setAttribute("SAVE_RESULT", result);
                request.getSession().removeAttribute("ERROR.LOGIN");
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "index", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/login/submit.html")
    protected String loginSubmit(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
            )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "web/page/login.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        UserDAO userDAO=null;
        UserAuthorizedBean userAuth = null;
        UserInfo user=null;
         try{ 
            request.setAttribute("pageLink", "login");
            request.setAttribute("pagePrefix", "auth/");
            this.frameHandler.initLang(request, "login", langCode);
            userDAO=(UserDAO)common.getDAOObject(request, "userDAO");
            
            result=userDAO.loginByUser(request);
            
            if (result.getCode() == 1) { //login Success
                userAuth=(UserAuthorizedBean)result.getObj();
                this.frameHandler.login(request, userAuth);
                user = (UserInfo) userAuth.getLoginedUser();
                //System.out.println("User: "+user.getDisplayName());
                return "redirect:/panel/"+langCode+"/dashboard.html";
            } else {
                this.frameHandler.logout(request);
                //request.setAttribute("loginMsg", userAuth);
                request.getSession().setAttribute("ERROR.LOGIN", result);
                return "redirect:/auth/"+langCode+"/login.html";
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "index", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return "redirect:/auth/"+langCode+"/login.html";
    }
    
    @RequestMapping(value = "/{langCode}/logout.html")
    protected String Logout(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode)
            throws Exception {
        HttpSession session = request.getSession();
        try {
            //System.out.println("Login out:" + userAuth.getErrorMsg());
            session.removeAttribute("UserAuthorized");
            session.removeAttribute("UserAuthorizedLogin");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Exception: " + e.getMessage());
            logger.throwing(this.getClass().getName(), "Logout", e);
        } finally {
            //config.initPage(request);
        }
        return "redirect:/auth/"+langCode+"/login.html";
    }
    
    @RequestMapping(value = "/google/oauth2callback.html")
    protected String googleHandShaking(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        this.frameHandler = new CustFrameHandler(request, "user/login.jsp");
        UserDAO userDAO = null;
        UserAuthorizedBean userAuth = null;
        CommonHandler common=new CommonHandler();
        String returnUrl = "redirect:/index.html";
        String accessToken = null;
        String code = "";
        String state = request.getParameter("state");
        String langCode="en";
        try {
            userDAO = (UserDAO) common.getDAOObject(request, "userDAO");

            // Google取得access_token的url
            URL urlObtainToken = new URL("https://accounts.google.com/o/oauth2/token");
            HttpURLConnection connectionObtainToken = (HttpURLConnection) urlObtainToken.openConnection();

            // 設定此connection使用POST
            connectionObtainToken.setRequestMethod("POST");
            connectionObtainToken.setDoOutput(true);

            // 開始傳送參數
            code = request.getParameter("code");
            OutputStreamWriter writer = new OutputStreamWriter(connectionObtainToken.getOutputStream());
            writer.write("code=" + code + "&");   // 取得Google回傳的參數code
            writer.write("client_id=" + this.frameHandler.getFreamBean().getGoogleConfig().getGoogleId() + "&");   // 這裡請將xxxx替換成自己的client_id
            writer.write("client_secret=" + this.frameHandler.getFreamBean().getGoogleConfig().getGoogleSecret() + "&");   // 這裡請將xxxx替換成自己的client_serect
           // writer.write("client_id=" + "363263052104-gt3g9vun3635mpj3lifa8ogkj5u0guie.apps.googleusercontent.com" + "&");   // 這裡請將xxxx替換成自己的client_id
            //writer.write("client_secret=" + "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" + "&");   // 這裡請將xxxx替換成自己的client_serect
            /*System.out.println("redirect_uri=" + request.getScheme() + "://"
                    + request.getServerName() + (request.getServerPort()==443 || request.getServerPort()==80?"": ":" + request.getServerPort())
                    + request.getContextPath() + "/auth/google/oauth2callback.html&"); */
            writer.write("redirect_uri=" + request.getScheme() + "://"
                    + request.getServerName() + (request.getServerPort()==443 || request.getServerPort()==80?"": ":" + request.getServerPort())
                    + request.getContextPath() + "/auth/google/oauth2callback.html&");   // 這裡請將xxxx替換成自己的redirect_uri
            writer.write("grant_type=authorization_code");
            writer.close();

            // 如果認證成功
            //System.out.println("Resp Code: "+connectionObtainToken.getResponseCode());
            if (connectionObtainToken.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sbLines = new StringBuilder("");
                //System.out.println("Link: "+connectionObtainToken.toString());
                // 取得Google回傳的資料(JSON格式)
                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(connectionObtainToken.getInputStream(), "utf-8"));
                String strLine = "";
                while ((strLine = reader.readLine()) != null) {
                    //System.out.println("Line: " + strLine);
                    sbLines.append(strLine);
                }
                //System.out.println("Response 1: " + sbLines);

                try {

                    // 把上面取回來的資料，放進JSONObject中，以方便我們直接存取到想要的參數
                    JSONObject jo = new JSONObject(sbLines.toString());

                    // 印出Google回傳的access token
                    accessToken = jo.getString("access_token");
                } catch (JSONException je) {
                    je.printStackTrace();
                    accessToken = null;
                }
            }

            if (accessToken != null && !accessToken.isEmpty()) {
                URL urUserInfo
                        = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken);

                HttpURLConnection connObtainUserInfo = (HttpURLConnection) urUserInfo.openConnection();

                //如果認證成功
                if (connObtainUserInfo.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    StringBuilder sbLines = new StringBuilder("");

                    // 取得Google回傳的資料(JSON格式)
                    BufferedReader reader
                            = new BufferedReader(new InputStreamReader(connObtainUserInfo.getInputStream(), "utf-8"));
                    String strLine = "";
                    while ((strLine = reader.readLine()) != null) {
                        sbLines.append(strLine);
                    }

                    try {
                        //System.out.println("Response: " + sbLines);
                        // 把上面取回來的資料，放進JSONObject中，以方便我們直接存取到想要的參數
                        JSONObject jo = new JSONObject(sbLines.toString());

                        // 印出Google回傳的"emailtoken
                        //resp.getWriter().println(jo.getString("email"));
                        userAuth = userDAO.loginByGoogle(jo, code, accessToken);
                        if(!userAuth.isLogined()){
                            this.frameHandler.logout(request);
                            ResultBean result=new ResultBean();
                            result.setCode(-2001);
                            result.setMsg("ERROR.LOGIN.GOOGLE.INVALID");
                            //request.setAttribute("loginMsg", userAuth);
                            request.getSession().setAttribute("ERROR.LOGIN", result);
                            return "redirect:/auth/"+langCode+"/login.html";
                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                    this.frameHandler.login(request, userAuth);
                    //return "redirect:/auth/en/login.html";
                    if(request.getSession().getAttribute(Globals.LOCALE_KEY)!=null){
                        langCode=""+request.getSession().getAttribute(Globals.LOCALE_KEY);
                        if(langCode!=null && langCode.equalsIgnoreCase("zh_TW")){
                            langCode="zh";
                        }else if(langCode!=null && langCode.equalsIgnoreCase("zh_CN")){
                            langCode="cn";
                        }else{
                            langCode="en";
                        }
                    }
                    
                    return "redirect:/panel/"+langCode+"/dashboard.html";
                    /*if(userAuth.getLoginedUser().getIsAdmin()==1){
                        returnUrl = "redirect:/user/info.html";
                    }*/
                    /*if (userAuth.isLogined()) {
                        returnUrl = userDAO.getLoginedLink(request, (state != null && state.equals("Y")));
                    }*/
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Exception: " + e.getMessage());
            logger.throwing(this.getClass().getName(), "googleHandShaking", e);
        } finally {
            request = this.frameHandler.initPage(request);
        }
        //return this.frameHandler.getReturnPath(request);
        return returnUrl;
    }
}
