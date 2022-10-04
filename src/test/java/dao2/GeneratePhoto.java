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
            }else{
                pos[0]=0; 
                pos[1]=0;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return pos;
    }
}
