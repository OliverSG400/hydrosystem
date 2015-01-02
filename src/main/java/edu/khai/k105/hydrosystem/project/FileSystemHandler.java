package edu.khai.k105.hydrosystem.project;

import javax.xml.bind.*;
import java.io.File;

public class FileSystemHandler {

    private static final String PACKAGE = Project.class.getPackage().getName();

    public Project openProject(File projectSource) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Project.class);
        Unmarshaller um = jc.createUnmarshaller();
        return (Project) um.unmarshal(projectSource);
    }

    public void saveProject(Project project, File projectDestination) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Project.class);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(project, projectDestination);
    }

}
