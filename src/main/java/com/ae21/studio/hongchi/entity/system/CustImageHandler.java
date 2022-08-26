/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.system;

import com.ae21.handler.ImageHandler;
import java.awt.Color;

/**
 *
 * @author Alex
 */
public class CustImageHandler  extends ImageHandler {

    public CustImageHandler() {
    }
    
    public Color rgbParse(String code)  throws Exception{
        Color color=null;
        int r=0, g=0, b=0;
        try{
            if(code!=null  && code.length()>=7){
               r=Integer.parseInt(code.substring(1, 3),16);
               g=Integer.parseInt(code.substring(3, 5),16);
               b=Integer.parseInt(code.substring(5, 7),16);
               //System.out.println("Color: "+code+":"+r+":"+g+":"+b);
               color=new Color(r,g,b);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return color;
    }
    
    public Color argbParse(String code, Double opacity)  throws Exception{
        Color color=null;
        int r=0, g=0, b=0;
        int alpha=255;
        try{
            if(code!=null  && code.length()>=7){
               r=Integer.parseInt(code.substring(1, 3),16);
               g=Integer.parseInt(code.substring(3, 5),16);
               b=Integer.parseInt(code.substring(5, 7),16);
               
                alpha=(int)Math.ceil(opacity*255);
                //System.out.println("Color: "+code+":"+r+":"+g+":"+b+":"+alpha+":"+opacity);
               color=new Color(r,g,b, alpha);
               /*System.out.println(color.getRed());
               System.out.println(color.getGreen());
               System.out.println(color.getBlue());*/
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return color;
    }
}
