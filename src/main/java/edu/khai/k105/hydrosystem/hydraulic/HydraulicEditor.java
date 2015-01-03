package edu.khai.k105.hydrosystem.hydraulic;

import edu.khai.k105.hydrosystem.Application;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class HydraulicEditor {

    private JPanel contentPane;
    private JTree projectTree;
    private JPanel propertiesPanel;
    private Application application;

    public JPanel getContentPane() {
        return contentPane;
    }

    public HydraulicEditor(Application application) {
        this.application = application;
        projectTree.setModel(new ProjectTreeModel(application.getCurrentProject()));
    }

}
