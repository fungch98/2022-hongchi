
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.MigrationDAO;
import com.ae21.studio.hongchi.entity.dao.UserDAO;
import java.util.regex.Pattern;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Alex
 */
public class Migration {
     public static void main(String[] args) {
          String root="P:\\temp\\Ernest\\HongChi\\migration\\20221126\\教學圖庫\\教學圖庫";
          String target="/home/alexander21/project/hongchi";
          String imgPath="P:\\Git\\DEV\\hongchi\\src\\main\\webapp\\images\\material";
         ApplicationContext context;
         MigrationDAO migDAO=null;
         UserDAO userDAO=null;
         UserInfo user=null;
        try{
             System.out.println(root);
            
            context
                   = new FileSystemXmlApplicationContext("P:/Git/DEV/hongchi/src/main/webapp/WEB-INF/applicationContext.xml");
             migDAO=(MigrationDAO)context.getBean("migDAO");
             userDAO=(UserDAO)context.getBean("userDAO");
             //migDAO.migration2(root,target, userDAO.loadUser(0));
             //migDAO.generateSearchIndex(0);
             migDAO.addChatImage(imgPath);
             //String fileName="南昌教學圖庫/食物/糖果 (3).jpg";
             /*String fileName="糖果(3).jpg";
             String [] item=fileName.split(Pattern.quote("."));
             System.out.println("len: "+item.length+":"+migDAO.isImageFile(fileName));*/
        }catch(Exception e){
            e.printStackTrace();
        }
     }
}
