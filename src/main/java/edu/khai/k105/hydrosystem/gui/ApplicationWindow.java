package edu.khai.k105.hydrosystem.gui;

import edu.khai.k105.hydrosystem.application.Application;
import edu.khai.k105.hydrosystem.gui.project.circuit.HydraulicEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationWindow extends JFrame implements Runnable {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem createProjectMenuItem;
    private JMenuItem openProjectMenuItem;
    private JMenuItem saveProjectMenuItem;
    private JMenuItem exitMenuItem;
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
        }
        return menuBar;
    }

    private JMenu getProjectJMenu() {
        if (fileMenu == null) {
            fileMenu = new JMenu("Проект");
            fileMenu.add(getCreateProjectMenuItem());
            fileMenu.add(getOpenProjectMenuItem());
            fileMenu.add(getSaveProjectMenuItem());
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
                    saveProject();
                }
            });
        }
        return saveProjectMenuItem;
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
        int ret = fileChooser.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            application.openProject(fileChooser.getSelectedFile());
            setTitle(application.getCurrentProject().getTitle());
            openProjectEditor();
        }
    }

    private void saveProject() {
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            application.saveProject(fileChooser.getSelectedFile());
        }
    }

    private void createProject() {
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            application.createNewProject(fileChooser.getSelectedFile());
            setTitle(application.getCurrentProject().getTitle());
            openProjectEditor();
        }
    }

}
