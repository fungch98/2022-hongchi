
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.MigrationDAO;
import com.ae21.studio.hongchi.entity.dao.UserDAO;
import com.ae21.studio.hongchi.entity.system.CustImageHandler;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Alex
 */
public class Folder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String root="P:\\temp\\Ernest\\HongChi\\migration\\教學圖庫";
         ApplicationContext context;
         MigrationDAO migDAO=null;
         UserDAO userDAO=null;
         UserInfo user=null;
        try{
            System.out.println(root);
            /*context
                   = new FileSystemXmlApplicationContext("P:/Git/DEV/hongchi/src/main/webapp/WEB-INF/applicationContext.xml");
             migDAO=(MigrationDAO)context.getBean("migDAO");
             userDAO=(UserDAO)context.getBean("userDAO");
             //migDAO.migration(root, userDAO.loadUser(0));
             //migDAO.generateSearchIndex();
            
            String val="#12345678";
            //System.out.println(val.length()+":"+val.substring(7));
            //System.out.println(val.length()+":"+val.substring(0,7));
            CustImageHandler img=new CustImageHandler();
            //img.ColorParse("#FFFFFF");
            
            String hex="9E";
            int decimal=Integer.parseInt(hex,16);
            System.out.println(decimal/(double)255);
            System.out.println(158/255);
*/
           /* int scaleAll=2;
            int width=600;
            int x=0;
            double val=0;
            int widthOfImage=0;
            
            System.out.println(val);
            widthOfImage=600*2;
            System.out.println(""+((0*2)+(widthOfImage/2)));*/
           
           Double value=new Double(0.38);
           System.out.println(value*255);
           String color="#FFAABB";
           System.out.println(Integer.parseInt(color.substring(1, 3),16));
           System.out.println(Integer.parseInt(color.substring(3, 5),16));
           System.out.println(Integer.parseInt(color.substring(5, 7),16));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
           String fonts[] = ge.getAvailableFontFamilyNames();
                        for(int a=0; fonts!=null && a<fonts.length;a++){
                            System.out.println(fonts[a]);
                        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
}
