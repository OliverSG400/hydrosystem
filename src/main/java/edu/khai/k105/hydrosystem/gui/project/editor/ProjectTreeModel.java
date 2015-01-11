package edu.khai.k105.hydrosystem.gui.project.editor;

import edu.khai.k105.hydrosystem.dataModel.project.Project;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Pump;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.element.Element;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.List;

public class ProjectTreeModel implements TreeModel {

    private static final int PROJECT_LINK_CHILDS_COUNT = 1;
    private static final int CIRCUIT_LINK_CHILDS_COUNT = 3;
    private Project root;

    public ProjectTreeModel(Project project) {
        root = project;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof Project) {
            return ((Project) parent).getCircuit();
        }
        if (parent instanceof Circuit) {
            return getChild((Circuit)parent, index);
        }
        if (parent instanceof List) {
            return ((List<Element>) parent).get(index);
        }
        return null;
    }

    private Object getChild(Circuit parent, int index) {
        switch (index) {
            case 0 : return parent.getWorkingFluid();
            case 1 : return parent.getPump();
            case 2 : return parent.getElements();
        }
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof Project) {
            return PROJECT_LINK_CHILDS_COUNT;
        }
        if (parent instanceof Circuit) {
            return CIRCUIT_LINK_CHILDS_COUNT;
        }
        if (parent instanceof List) {
            return ((List) parent).size();
        }
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        if (node instanceof Fluid || node instanceof Pump || node instanceof Element) {
            return true;
        }
        return false;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}
