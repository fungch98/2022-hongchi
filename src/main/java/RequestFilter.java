
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class RequestFilter implements Filter  {
    
    public void doFilter(ServletRequest req, ServletResponse res,
        FilterChain chain) throws IOException, ServletException {
        //System.out.println("Fiter:");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse)res;
        String serverName=request.getServerName();
        String firstServer="";
        try{
             //System.out.println("Enter Filter: "+request.getScheme()+":"+request.getServerPort()+":"+request.getServerName());
            if (request.getScheme().equals("http")) {
                //System.out.print(serverName.indexOf("www."));
                if(serverName!=null && !serverName.equalsIgnoreCase("localhost")){
                    if(serverName.indexOf("www.")==-1){
                        serverName="www."+serverName;
                    }
                }
                String url = "https://" + serverName + (request.getServerPort() != 8443 ? (request.getServerPort() ==8080?":8443":""): ":8443")
                        + request.getContextPath() + request.getServletPath();
                if (request.getPathInfo() != null) {
                    url += request.getPathInfo();
                }
                //System.out.println("Enter Filter: "+url);
                response.sendRedirect(url);
            }else if(request.getScheme().equals("https") && serverName!=null && !serverName.equalsIgnoreCase("localhost") && serverName.indexOf("www.")==-1){  //Same no use, blocked by other setting
                serverName="www."+serverName;
                String url = "https://" + serverName + (request.getServerPort() != 8443 ? (request.getServerPort() ==8080?":8443":""): ":8443")
                        + request.getContextPath() + request.getServletPath();
                if (request.getPathInfo() != null) {
                    url += request.getPathInfo();
                }
                //System.out.println("Enter Filter: "+url);
                response.sendRedirect(url);
            } else {
                chain.doFilter(request, response);
            }
        }catch(Exception e){
            e.printStackTrace();
            chain.doFilter(req, res);
        }
        // INSERT YOUR CODE HERE

        

    }
    
     public void init(FilterConfig config) throws ServletException {

    // PERFORM INITIALIZATION HERE

    }

    public void destroy() {

    // RELEASE ALL RESOURCES

    }
}
