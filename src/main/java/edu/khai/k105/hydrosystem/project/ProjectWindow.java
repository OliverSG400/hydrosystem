package edu.khai.k105.hydrosystem.project;

import edu.khai.k105.hydrosystem.hydraulic.HydraulicEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ProjectWindow extends JFrame {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem createProjectMenuItem;
    private JMenuItem openProjectMenuItem;
    private JMenuItem saveProjectMenuItem;
    private JMenuItem exitMenuItem;
    private HydraulicEditor hydraulicEditor;

    public ProjectWindow() {
        super("Проект2");
        setJMenuBar(getMenu());
        hydraulicEditor = new HydraulicEditor();
        setContentPane(hydraulicEditor.getContentPane());
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
            menuBar.add(getFileJMenu());
        }
        return menuBar;
    }

    private JMenu getFileJMenu() {
        if (fileMenu == null) {
            fileMenu = new JMenu("Файл");
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
            createProjectMenuItem = new JMenuItem("Создать проект");
        }
        return createProjectMenuItem;
    }

    private JMenuItem getOpenProjectMenuItem() {
        if (openProjectMenuItem == null) {
            openProjectMenuItem = new JMenuItem("Открыть проект");
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
            saveProjectMenuItem = new JMenuItem("Сохранить проект");
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
        }
        return exitMenuItem;
    }

    private void openProject() {
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("Selected file = " + file);
        }
    }

    private void saveProject() {
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("Selected file = " + file);
        }
    }

}
