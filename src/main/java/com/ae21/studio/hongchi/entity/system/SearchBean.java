/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.system;

import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class SearchBean {
    private List<ProductInfo> resultList=null;
    private List<List<ProductInfo>> pageList=null;
    private int size=0;
    private int page=0;     //Total number of page
    private int pageItems=30;
    private String key="";
    private int curPage=0;
    private int maxPage=0; //

    public SearchBean() {
    }

    public List<ProductInfo> getResultList() {
        return resultList;
    }

    public void setResultList(List<ProductInfo> resultList) {
        this.resultList = resultList;
        this.calculatePage();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        this.calculatePage();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageItems() {
        return pageItems;
    }

    public void setPageItems(int pageItems) {
        this.pageItems = pageItems;
        this.calculatePage();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
        this.calculatePage();
    }

    public List<List<ProductInfo>> getPageList() {
        return pageList;
    }

    public void setPageList(List<List<ProductInfo>> pageList) {
        this.pageList = pageList;
    }
    
    public void calculatePage(){
        double needPage=0;
        try{
            this.page=0;
            this.maxPage=0;
            this.size=0;
            
            if(this.getResultList()!=null){
                this.size=this.getResultList().size();
                if(this.size>0){
                    needPage=Math.ceil(this.getSize()/this.getPageItems());
                    this.page=(int)needPage;
                    this.maxPage=this.getPage();
                }
            }
        }catch(Exception ignore){
            ignore.printStackTrace();
        }
    }
    
    public void generatePageList()throws Exception{
        int count=0;
        List<ProductInfo> row=null;
        try{
            if(this.getResultList()!=null){
                this.pageList=new ArrayList<List<ProductInfo>>();
                for(int i=0; i<this.getResultList().size();i++){
                    if(count==0){
                        row=new ArrayList<ProductInfo>();
                    }
                    
                    row.add(this.getResultList().get(i));
                    count++;
                    
                    if(row.size()==this.getPageItems()){
                        this.pageList.add(row);
                        row=null;
                        count=0;
                    }
                    //System.out.println("Count: "+count);
                    //System.out.println("Row: "+(row!=null?row.size():"NA"));
                    //System.out.println("Page: "+(this.getPageList()!=null?this.getPageList().size():"NA"));
                }
                
                if(row!=null){
                    this.pageList.add(row);
                }
                //System.out.println("Page: "+(this.getPageList()!=null?this.getPageList().size():"NA"));
            }
        }catch(Exception ignore){
            ignore.printStackTrace();
        }
    }
    
}
