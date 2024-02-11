package client.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class UserModel {
    private String fullName;
    private String username;
    private String address;
    private String email;
    private String password;

    // Constructors
    public UserModel(String fullName, String username, String address, String email, String password){
        this.fullName = fullName;
        this.username = username;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername() {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress() {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail() {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword() {
        this.password = password;
    }
    // Method to register a new user
    public void registerUser() {
        try {
            // Load the XML file
            File xmlFile = new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document;

            // If the file does not exist, create a new document
            if (!xmlFile.exists()) {
                document = dBuilder.newDocument();
                Element rootElement = document.createElement("users");
                document.appendChild(rootElement);
            } else {
                // Parse the existing XML file
                document = dBuilder.parse(xmlFile);

                // Check if the user already exists
                String username = getUsername();
                String email = getEmail();
                NodeList userList = document.getElementsByTagName("user");
                for (int i = 0; i < userList.getLength(); i++) {
                    Node userNode = userList.item(i);
                    if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element userElement = (Element) userNode;
                        String existingUsername = userElement.getAttribute("username");
                        String existingEmail = userElement.getAttribute("email");
                        if (existingUsername.equals(username) || existingEmail.equals(email)) {
                            // To do if the username or email exists
                            System.out.println("User with the same username or email already exists.");
                            return; // Exit the method without registering the user
                        }
                    }
                }
            }
            // Create a new user element
            Element userElement = document.createElement("user");

            // Add user attributes
            userElement.setAttribute("username", getUsername());
            userElement.setAttribute("fullName", getFullName());
            userElement.setAttribute("address", getAddress());
            userElement.setAttribute("email", getEmail());
            userElement.setAttribute("password", getPassword());

            // Append the user element to the document
            document.getDocumentElement().appendChild(userElement);

            // Write the updated XML document back to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    } // End of registerUser Method

    public boolean validateCredentials(String username, String password) {
        // Logic to check if the provided username and password match
        return this.username.equals(username) && this.password.equals(password);
    }
}// End of UserModel Class
