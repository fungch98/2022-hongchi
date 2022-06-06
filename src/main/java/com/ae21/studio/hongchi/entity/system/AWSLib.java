/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ae21.studio.hongchi.entity.system;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;

/**
 *
 * @author Alex
 */
public class AWSLib {
    public AWSLib() {
    }
    
    public String getResignedURL(AmazonS3 s3, AWSBean aws, String path) throws Exception {
        String returnURL = null;
        URL url;
        GeneratePresignedUrlRequest generatePresignedUrlRequest;
        try {
            if(s3==null){
                s3=aws.getS3();
            }
            
            java.util.Date expiration = new java.util.Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += 1000 * 60 * 60; // Add 1 hour.
            expiration.setTime(milliSeconds);

            generatePresignedUrlRequest
                    = new GeneratePresignedUrlRequest(aws.getBucketName(), path);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration);
            url = s3.generatePresignedUrl(generatePresignedUrlRequest);
            if(url!=null){
                //returnURL = "http://"+aws.getBucketName()+url.getPath();
                returnURL = url.toString().replace("https://", "http://");
                //System.out.println("url: "+url.toString());
            }

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        return returnURL;
    }
    
    public void destroy() {
            com.amazonaws.http.IdleConnectionReaper.shutdown();
    }
}
