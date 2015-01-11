package edu.khai.k105.hydrosystem.dataModel.project;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final QName Q_NAME = new QName(XMLConstants.NULL_NS_URI, "data");

    //@XmlElementDecl()
    public JAXBElement<Project> createData(Project value) {
        return new JAXBElement<Project>(Q_NAME, Project.class, null, value);
    }

}
