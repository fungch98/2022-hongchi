/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ae21.studio.hongchi.entity.system;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import javax.annotation.Resource;

/**
 *
 * @author Admin
 */
public class AWSBean {
    @Resource(name = "status")
    private String status;
    
    @Resource(name = "prefix")
    private String prefix;
    
    @Resource(name = "bucketName")
    String bucketName;
    
    private AWSCredentials credentials;
    private String username="";
    private String sercet="";
    private AmazonS3 s3;

    

    public AWSCredentials getCredentials() {
        this.loginAWS();
        return credentials;
    }

    public void setCredentials(AWSCredentials credentials) {
         this.loginAWS();
    }
    
    public void loginAWS(){
        try{
            if(this.status!=null && this.status.equals("PROD")){
                this.credentials = new BasicAWSCredentials("AKIAJFMN7GZBWEZVCDLQ", "ABVvrP7X9DHldYBOUvVg2n8SYIYQ3ANzb3uTQJ9f");
            }else{
                this.credentials = new BasicAWSCredentials("AKIAT4EOYGXTUAK4FWNN", "87kA9ZMrKLMLcYUBhauVtLxll2lYLP4Hu+gdHqLe");
            }
        }catch(Exception ignore){
            ignore.printStackTrace();
        }
        
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public AmazonS3 getS3() {
        this.s3 = new AmazonS3Client(this.getCredentials());
        Region usWest2=null;
        if(this.status!=null && this.status.equals("PROD")){
            usWest2 = Region.getRegion(Regions.AP_NORTHEAST_2);
        }else{
             usWest2 = Region.getRegion(Regions.AP_NORTHEAST_2);
             //ap-east-1
             //System.out.println("Region: "+usWest2.getName());
        }
        
                s3.setRegion(usWest2);
        return this.s3;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
