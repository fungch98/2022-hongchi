
package com.ae21.studio.hongchi.entity.system;

import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class FamilyBean {
    private CategoryInfo current=null;
    private CategoryInfo parent=null;
    private ArrayList<CategoryInfo> path=null;
    private List<CategoryInfo> subFolder=null;
    private boolean hasSubfolder=false;

    public FamilyBean(CategoryInfo current) {
        this.current=current;
    }

    public CategoryInfo getCurrent() {
        return current;
    }

    public void setCurrent(CategoryInfo current) {
        this.current = current;
    }

    public CategoryInfo getParent() {
        return parent;
    }

    public void setParent(CategoryInfo parent) {
        this.parent = parent;
    }

    public ArrayList<CategoryInfo> getPath() {
        return path;
    }

    public void setPath(ArrayList<CategoryInfo> path) {
        this.path = path;
    }

    public List<CategoryInfo> getSubFolder() {
        return subFolder;
    }

    public void setSubFolder(List<CategoryInfo> subFolder) {
        this.subFolder = subFolder;
    }

   

  
    public boolean isHasSubfolder() {
        return hasSubfolder;
    }

    public void setHasSubfolder(boolean hasSubfolder) {
        this.hasSubfolder = hasSubfolder;
    }
    
    
    
}
