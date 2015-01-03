package edu.khai.k105.hydrosystem;

import edu.khai.k105.hydrosystem.project.FileSystemHandler;
import edu.khai.k105.hydrosystem.project.Project;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Main class
 */
public class Application {

    private Project currentProject;
    private FileSystemHandler fileSystemHandler = new FileSystemHandler();

    public void createNewProject(File path) {
        currentProject = new Project();
        currentProject.setTitle("Безымянный проект");
        saveProject(path);
    }

    public void openProject(File path) {
        try {
            currentProject = fileSystemHandler.openProject(path);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
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
