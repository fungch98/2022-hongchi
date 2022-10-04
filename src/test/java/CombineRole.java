
import dao2.GeneratePhoto;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Alex
 */
public class CombineRole {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> nameList=new ArrayList<String>();
        ArrayList<String> actionList=new ArrayList<String>();
        File folder=new File("P:/temp/Ernest/HongChi/result/role");
        File roleDir=null;
        File action=null;
        File emotion=null;
        File output=null;
        File output2=null;
        File [] fileList=null;
        String name="";
        String emotionName="";
        int [] pos={0,0};
        
        GeneratePhoto generator=new GeneratePhoto();
        try{
            //nameList.add("loklok");
            nameList.add("longlong");
            actionList.add("stand");
            actionList.add("sit");
            if(folder!=null && folder.isDirectory() ){
                
                for(int i=0; nameList!=null && i<nameList.size();i++){
                    name=nameList.get(i);
                    roleDir=new File(folder.getAbsoluteFile()+"/"+name);
                    //output=new File(folder.getAbsoluteFile()+"/"+name+"/output");
                    output=new File(folder.getAbsoluteFile()+"/output");
                    if(roleDir.exists() && roleDir.isDirectory()){
                        fileList=roleDir.listFiles();
                        for(int k=0;fileList!=null && k<fileList.length;k++){
                            emotion=fileList[k];
                            if(!emotion.isDirectory()){
                                emotionName=emotion.getName();
                                
                                emotionName=(emotionName!=null?emotionName.replace(".png", ""):"");
                                for(int j=0; j<actionList.size();j++){
                                    action=new File(folder.getAbsoluteFile()+"/"+name+"/action/"+actionList.get(j)+".png");
                                    if(action.exists()){
                                        System.out.println("Current("+emotionName+"): "+emotion.getAbsolutePath());
                                        System.out.println("Current action: "+action.getAbsolutePath());
                                        
                                        pos=generator.getPosition(name, actionList.get(j));
                                        
                                        if(!output.exists()){
                                            output.mkdirs();
                                        }
                                        
                                        generator.genereatePhoto(action, emotion, name, output.getAbsolutePath(),null, pos);
                                    }
                                }
                            }
                        }
                    }
                }
                
                fileList=folder.listFiles();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
