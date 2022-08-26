
import com.ae21.bean.ResultBean;
import com.ae21.bean.SystemConfigBean;
import com.ae21.studio.hongchi.entity.bean.EditorInfo;
import com.ae21.studio.hongchi.entity.bean.EditorItem;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.EditorDAO;
import com.ae21.studio.hongchi.entity.dao.UserDAO;
import java.util.List;
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
public class ImageRotation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationContext context;
        UserDAO userDAO=null;
        EditorDAO edDAO=null;
        UserInfo user=null;
        EditorInfo editor=null;
        List<EditorItem> itemList=null;
        ResultBean result=null;
        SystemConfigBean config=null;
        try{
             context
                   = new FileSystemXmlApplicationContext("P:/Git/DEV/hongchi/src/main/webapp/WEB-INF/applicationContext.xml");
             
            userDAO=(UserDAO)context.getBean("userDAO");
            edDAO=(EditorDAO)context.getBean("editorDAO");
            config = (SystemConfigBean) context.getBean("defaultConfig");
            editor=edDAO.loadEditor("5A85775C-1B23-4D7E-9D36-198E229C343C");
            if(editor!=null){
                System.out.println("Process editor: "+editor.getName());
                user=userDAO.loadUser(0); 
                result=edDAO.generatePhoto(editor, user, config);
                System.out.println("Result: "+result.getCode());
                /*itemList=edDAO.loadEditorItem(editor);
                for(int i=0; itemList!=null && i<itemList.size();i++){
                    
                }*/
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
