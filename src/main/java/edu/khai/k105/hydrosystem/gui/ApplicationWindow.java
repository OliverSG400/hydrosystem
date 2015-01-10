package edu.khai.k105.hydrosystem.gui;

import edu.khai.k105.hydrosystem.application.Application;
import edu.khai.k105.hydrosystem.gui.project.calculation.CalculationViewer;
import edu.khai.k105.hydrosystem.gui.project.circuit.SchemeEditor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
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
    private JMenu modeMenu;
    private JRadioButtonMenuItem editorModeMenuItem;
    private JRadioButtonMenuItem viewerModeMenuItem;
    private SchemeEditor schemeEditor;
    private CalculationViewer calculationViewer;
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
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e){
            System.out.println("Ошибка при загрузке Metal-Look-And-Feel");
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(new InitPanel().getContentPane());
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private JMenuBar getMenu() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            menuBar.add(getProjectJMenu());
            menuBar.add(getModeMenu());
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
            createProjectMenuItem.setAccelerator(KeyStroke.getKeyStroke("control N"));
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
            openProjectMenuItem.setAccelerator(KeyStroke.getKeyStroke("control O"));
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
            saveProjectMenuItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
            saveProjectMenuItem.setEnabled(false);
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
            saveAsProjectMenuItem.setAccelerator(KeyStroke.getKeyStroke("control alt S"));
            saveAsProjectMenuItem.setEnabled(false);
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
            exitMenuItem.setAccelerator(KeyStroke.getKeyStroke("control Q"));
            exitMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        return exitMenuItem;
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
            enableAllMenus();
            editorModeMenuItem.doClick();
        }
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
            enableAllMenus();
            editorModeMenuItem.doClick();
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

    private JMenu getModeMenu() {
        if (modeMenu == null) {
            modeMenu = new JMenu("Режим");
            ButtonGroup modesGroup = new ButtonGroup();
            modesGroup.add(getEditorModeMenuItem());
            modesGroup.add(getViewerModeMenuItem());
            modeMenu.add(getEditorModeMenuItem());
            modeMenu.add(getViewerModeMenuItem());
            modeMenu.setEnabled(false);
        }
        return modeMenu;
    }

    private JRadioButtonMenuItem getEditorModeMenuItem() {
        if (editorModeMenuItem == null) {
            editorModeMenuItem = new JRadioButtonMenuItem("Редактирование");
            editorModeMenuItem.setAccelerator(KeyStroke.getKeyStroke("control E"));
            editorModeMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openProjectSchemeEditor();
                }
            });
        }
        return editorModeMenuItem;
    }

    private JRadioButtonMenuItem getViewerModeMenuItem() {
        if (viewerModeMenuItem == null) {
            viewerModeMenuItem = new JRadioButtonMenuItem("Просмотр результатов");
            viewerModeMenuItem.setAccelerator(KeyStroke.getKeyStroke("control R"));
            viewerModeMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openProjectCalculationViewer();
                }
            });
        }
        return viewerModeMenuItem;
    }

    private void openProjectSchemeEditor() {
        schemeEditor = new SchemeEditor(application);
        setContentPane(schemeEditor.getContentPane());
        validate();
    }

    private void openProjectCalculationViewer() {
        calculationViewer = new CalculationViewer(application.getCurrentProject().getCircuit());
        setContentPane(calculationViewer.getContentPane());
        validate();
    }

    private void enableAllMenus() {
        modeMenu.setEnabled(true);
        saveProjectMenuItem.setEnabled(true);
        saveAsProjectMenuItem.setEnabled(true);
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

}
