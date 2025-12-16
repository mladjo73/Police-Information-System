package model;


import javax.swing.tree.DefaultMutableTreeNode;


/**
 * Klasa kojom se definise model aplikacije
 * @author grupa4
 * 
 *
 */

public class ApplicationModel {

    private final XMLTreeModel xmlTreeModel;
   

   public XMLTreeModel getTreeModel() {
        return xmlTreeModel;
    }

    public ApplicationModel(String xmlPath) {
        this.xmlTreeModel = new XMLTreeModel(new DefaultMutableTreeNode(),xmlPath);

       
    }
   
} 

