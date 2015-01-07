package edu.khai.k105.hydrosystem.gui;

import edu.khai.k105.hydrosystem.application.Application;
import edu.khai.k105.hydrosystem.application.project.graph.GraphModel;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.gui.project.calculation.OperatingPointViewer;
import edu.khai.k105.hydrosystem.gui.project.circuit.HydraulicEditor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ApplicationWindow extends JFrame implements Runnable {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem createProjectMenuItem;
    private JMenuItem openProjectMenuItem;
    private JMenuItem saveProjectMenuItem;
    private JMenuItem saveAsProjectMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu calculationMenu;
    private JMenuItem calculateOperatingPointMenuItem;
    private HydraulicEditor hydraulicEditor;
    private Application application;

    public ApplicationWindow(Application application) {
        super();
        this.application = application;
    }

    @Override
    public void run() {
        setTitle("Гидросистема");
        setJMenuBar(getMenu());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e){
            System.out.println("Ошибка при загрузке Metal-Look-And-Feel");
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private JMenuBar getMenu() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            menuBar.add(getProjectJMenu());
            menuBar.add(getCalculationJMenu());
        }
        return menuBar;
    }

    private JMenu getProjectJMenu() {
        if (fileMenu == null) {
            fileMenu = new JMenu("Проект");
            fileMenu.add(getCreateProjectMenuItem());
            fileMenu.add(getOpenProjectMenuItem());
            fileMenu.add(getSaveProjectMenuItem());
            fileMenu.add(getSaveAsProjectMenuItem());
            fileMenu.addSeparator();
            fileMenu.add(getExitMenuItem());
        }
        return fileMenu;
    }

    private JMenuItem getCreateProjectMenuItem() {
        if (createProjectMenuItem == null) {
            createProjectMenuItem = new JMenuItem("Создать");
            createProjectMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createProject();
                }
            });
        }
        return createProjectMenuItem;
    }

    private JMenuItem getOpenProjectMenuItem() {
        if (openProjectMenuItem == null) {
            openProjectMenuItem = new JMenuItem("Открыть");
            openProjectMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openProject();
                }
            });
        }
        return openProjectMenuItem;
    }

    private JMenuItem getSaveProjectMenuItem() {
        if (saveProjectMenuItem == null) {
            saveProjectMenuItem = new JMenuItem("Сохранить");
            saveProjectMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    application.saveProject();
                }
            });
        }
        return saveProjectMenuItem;
    }

    private JMenuItem getSaveAsProjectMenuItem() {
        if (saveAsProjectMenuItem == null) {
            saveAsProjectMenuItem = new JMenuItem("Сохранить как");
            saveAsProjectMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveProject();
                }
            });
        }
        return saveAsProjectMenuItem;
    }

    private JMenuItem getExitMenuItem() {
        if (exitMenuItem == null) {
            exitMenuItem = new JMenuItem("Выход");
            exitMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        return exitMenuItem;
    }

    private void openProjectEditor() {
        hydraulicEditor = new HydraulicEditor(application);
        setContentPane(hydraulicEditor.getContentPane());
        validate();
    }

    private void openProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new HSPFileFilter());
        if (application.getCurrentProjectFile() != null) {
            fileChooser.setCurrentDirectory(application.getCurrentProjectFile().getParentFile());
        }
        int ret = fileChooser.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            application.openProject(fileChooser.getSelectedFile());
            setTitle(application.getCurrentProject().getTitle());
            openProjectEditor();
        }
    }

    private void saveProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new HSPFileFilter());
        if (application.getCurrentProjectFile() != null) {
            fileChooser.setCurrentDirectory(application.getCurrentProjectFile().getParentFile());
        }
        int ret = fileChooser.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            application.saveProject(fileChooser.getSelectedFile());
        }
    }

    private void createProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new HSPFileFilter());
        if (application.getCurrentProjectFile() != null) {
            fileChooser.setCurrentDirectory(application.getCurrentProjectFile().getParentFile());
        }
        int ret = fileChooser.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            application.createNewProject(fileChooser.getSelectedFile());
            setTitle(application.getCurrentProject().getTitle());
            openProjectEditor();
        }
    }

    private class HSPFileFilter extends FileFilter {

        public String getDescription() {
            return "Проект гидросистемы";
        }

        public boolean accept(File f) {
            if(f != null) {
                if(f.isDirectory()) {
                    return true;
                }
                return f.toString().endsWith(".hsp");
            }
            return false;
        }
    }

    private JMenu getCalculationJMenu() {
        if (calculationMenu == null) {
            calculationMenu = new JMenu("Расчет");
            calculationMenu.add(getCalculateOperatingPointMenuItem());
        }
        return calculationMenu;
    }

    private JMenuItem getCalculateOperatingPointMenuItem() {
        if (calculateOperatingPointMenuItem == null) {
            calculateOperatingPointMenuItem = new JMenuItem("Рабочая точка");
            calculateOperatingPointMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Рабочая точка");
                    frame.setContentPane(new OperatingPointViewer(application.getCurrentProject().getCircuit())
                            .getContentPane());
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
                    frame.setVisible(true);
                }
            });
        }
        return calculateOperatingPointMenuItem;
    }

}
