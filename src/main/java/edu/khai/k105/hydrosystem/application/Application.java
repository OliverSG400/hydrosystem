package edu.khai.k105.hydrosystem.application;

import edu.khai.k105.hydrosystem.application.project.Project;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Main class
 */
public class Application {

    private Project currentProject;
    private File currentProjectFile;
    private FileSystemHandler fileSystemHandler = new FileSystemHandler();

    public void createNewProject(File path) {
        currentProject = new Project();
        currentProject.setTitle(extractShortName(path));
        currentProjectFile = path;
        saveProject(path);
    }

    private String extractShortName(File path) {
        String fileName = path.getName();
        int separatorIndex = fileName.lastIndexOf('.');
        if (separatorIndex != -1) {
            return fileName.substring(0, separatorIndex);
        }
        return fileName;
    }

    public void openProject(File path) {
        try {
            currentProject = fileSystemHandler.openProject(path);
            currentProjectFile = path;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void saveProject() {
        saveProject(currentProjectFile);
    }

    public void saveProject(File path) {
        try {
            fileSystemHandler.saveProject(currentProject, path);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Project getCurrentProject() {
        return currentProject;
    }

}
