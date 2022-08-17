
import com.ae21.studio.hongchi.entity.system.CustImageHandler;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author Alex
 */
public class Image {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CustImageHandler img=new CustImageHandler();
        BufferedImage product = null;
        Graphics2D g = null;
        //String path = "P:\\Git\\DEV\\hongchi\\target\\hongchi-1.0-1.5\\output\\34DCB392-A63B-4CF0-881C-4A1D1AB84FF6\\D290C161-D4E1-41E2-AFFD-052636163474\\3-1.png";
        try {
            Path outPath = Paths.get("P:\\temp\\Alex\\demo");
        if (!Files.exists(outPath)) {
            Files.createDirectory(outPath);
        }
            int width = 1200;
        int height = 600;
            String timeNow = DateTimeFormatter
                .ofPattern("yyyy_MM_dd__HH_mm_ss_SSS")
                .format(LocalDateTime.now());
        //String filename = "test_png_pic__" + timeNow + "__.png";
        //System.out.println(outPath.resolve(filename).toAbsolutePath());
        //File absOutFile = outPath.resolve(filename).toFile();
        File absOutFile=new File("P:\\temp\\Alex\\demo\\abc.png");
            BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, width, height);

        g2d.setComposite(AlphaComposite.Src);
        int alpha = 200; // 50% transparent
        g2d.setColor(img.argbParse("#22FF00", 0.8));
        //g2d.setColor(new Color(34, 255, 0,alpha));
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(new Color(77, 77, 77));
        g2d.fillRect(30, 30, 60, 60);

        g2d.dispose();
        ImageIO.write(bufferedImage, "png", absOutFile);
        System.out.println("File saved to:");
        System.out.println(absOutFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
