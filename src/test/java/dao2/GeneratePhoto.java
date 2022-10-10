/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao2;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Alex
 */
public class GeneratePhoto {

    public GeneratePhoto() {
    }
    
    
    public void genereatePhoto(File action, File emotion, String role, String outputPath,String output2Path, int[] pos){
        BufferedImage image=null;
        BufferedImage image2=null;
        BufferedImage product=null;
        int width=0;
        int height=0;
        File output=null;
        File output2=null;
        AlphaComposite alphaChannel=null;
        try{
             image=ImageIO.read(action);
             image2=ImageIO.read(emotion);
             if(image!=null && image2!=null){
                 width=image.getWidth();
                 height=image.getHeight();
                BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.setComposite(AlphaComposite.Clear);
                //g2d.fillRect(0, 0, width, height);
                
                
                alphaChannel = AlphaComposite.getInstance(
                                        AlphaComposite.SRC_OVER, 1f);
                g2d.setComposite(alphaChannel);
                g2d.drawImage(image, 0,0 ,null);
                g2d.drawImage(image2, pos[0],pos[1] ,null);
                
                g2d.dispose();
                
                output=new File(outputPath+"/"+role+"_"+(action.getName().toLowerCase().replace(".png", "")+"_"+(emotion.getName().toLowerCase().replace(".png", "")+".png")));
                
                
                ImageIO.write(bufferedImage, "png", output);
              
                System.out.println("File saved to:");
                System.out.println(output.getAbsoluteFile());
             }
             
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int[] getPosition(String role, String action){
        int [] pos={0,0};
        try{
            if(role.equalsIgnoreCase("loklok")){
                    pos[0]=0; 
                    pos[1]=0;
                    
                    if(action.equalsIgnoreCase("sit")){
                         pos[0]=75; 
                         pos[1]=5;
                    }
            }else if(role.equalsIgnoreCase("longlong")){
                if(action.equalsIgnoreCase("sit")){
                         pos[0]=62; 
                         pos[1]=0;
                    }
            }else if(role.equalsIgnoreCase("lokkid")){
                if(action.equalsIgnoreCase("stand")){
                         pos[0]=144; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("sit")){
                        pos[0]=180; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("jump")){
                        pos[0]=130; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("kick")){
                        pos[0]=230; 
                         pos[1]=3;
                    }else if(action.equalsIgnoreCase("creep")){
                        pos[0]=20; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("point")){
                        pos[0]=150; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("run")){
                        pos[0]=160; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("situp")){
                        pos[0]=220; 
                         pos[1]=0;
                    }
            }else if(role.equalsIgnoreCase("sunkid")){
                if(action.equalsIgnoreCase("stand")){
                         pos[0]=35; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("sit")){
                        pos[0]=115; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("jump")){
                        pos[0]=60; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("kick")){
                        pos[0]=160; 
                         pos[1]=3;
                    }else if(action.equalsIgnoreCase("creep")){
                        pos[0]=0; 
                         pos[1]=4;
                    }else if(action.equalsIgnoreCase("point")){
                        pos[0]=95; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("run")){
                        pos[0]=60; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("situp")){
                        pos[0]=140; 
                         pos[1]=2;
                    }
            }else if(role.equalsIgnoreCase("marukid")){
                if(action.equalsIgnoreCase("stand")){
                         pos[0]=50; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("sit")){
                        pos[0]=150; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("jump")){
                        pos[0]=100; 
                         pos[1]=10;
                    }else if(action.equalsIgnoreCase("kick")){
                        pos[0]=195; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("creep")){
                        pos[0]=18; 
                         pos[1]=15;
                    }else if(action.equalsIgnoreCase("point")){
                        pos[0]=100; 
                         pos[1]=10;
                    }else if(action.equalsIgnoreCase("run")){
                        pos[0]=100; 
                         pos[1]=20;
                    }else if(action.equalsIgnoreCase("situp")){
                        pos[0]=154; 
                         pos[1]=20;
                    }
            }else if(role.equalsIgnoreCase("longkid")){
                if(action.equalsIgnoreCase("stand")){
                         pos[0]=140; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("sit")){
                        pos[0]=175; 
                         pos[1]=0;
                    }else if(action.equalsIgnoreCase("jump")){
                        pos[0]=175; 
                         pos[1]=30;
                    }else if(action.equalsIgnoreCase("kick")){
                        pos[0]=370; 
                         pos[1]=40;
                    }else if(action.equalsIgnoreCase("creep")){
                        pos[0]=50; 
                         pos[1]=40;
                    }else if(action.equalsIgnoreCase("point")){
                        pos[0]=200; 
                         pos[1]=40;
                    }else if(action.equalsIgnoreCase("run")){
                        pos[0]=180; 
                         pos[1]=25;
                    }else if(action.equalsIgnoreCase("situp")){
                        pos[0]=235; 
                         pos[1]=20;
                    }
            }else{
                pos[0]=0; 
                pos[1]=0;
            }
            System.out.println("POS: "+pos[0]);
        }catch(Exception e){
            e.printStackTrace();
        }
        return pos;
    }
}
