package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.NodeOrientation;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.event.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import application.LoginController;


public class CourseController extends LoginController {
    @FXML
    Button addButton;
    @FXML
    Button renameConfirmB;
    @FXML
    Button RenameButton;
    @FXML
    Label renameLabel;
    @FXML
    Button courseBox;
    @FXML
    GridPane grid1Pane;
    @FXML
    Label warningLabel;
    @FXML
    TextField newName;
    @FXML
    TextField textField1;
    @FXML
    Integer indexRow = 0;
    @FXML
    Integer indexCol = 0;
    @FXML
    int counter = 0;
    @FXML
    Button logoutButton;




    /**
     * This class is used in order to grab nodes from the gridPane to make deletion possible, as GridPane doesn't have a delete method
     * inherently.
     * @param gridPane1 Any GridPane object
     * @param col The column that is iterated by Integer indexCol
     * @param row The row that is iterated by Integer indexRow
     * @return Returns the node(in this case a button/button bar) of the particular cell of GridPane
     */

    public Node getNode(GridPane gridPane1, Integer col, Integer row)
    {
        for (Node node : gridPane1.getChildren())
        {
            if (gridPane1.getColumnIndex(node) == col && gridPane1.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }


    @FXML
    /**
     * Method for the creation of courses, additionaly creates the buttons that provide deleting,
     * and renaming.  This method also gives functionality to the delete button and rename buttons.
        */
    protected void CreateClick(ActionEvent event) throws IOException
    {
        BufferedReader buffR = new BufferedReader(new FileReader("currentUser.txt"));
        String currentUserName = buffR.readLine();

        //File file1 = new File(currentUserName + ".txt");
        BufferedWriter buffW = new BufferedWriter(new FileWriter(currentUserName+".TXT",true));
        if(!addButton.isPressed())   //If button gets pressed (dw about syntax of that)
        {
            counter++;
            Tab tab1 = new Tab("New Course " + counter);
            tabCourses.getTabs().add(tab1);
            try {
//                    buffW.write(currentUserName + ", ");
                    buffW.write(tab1.getText() + "\n");
                    buffW.close();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(currentUserName + " " + tab1.getText() + " ");
//            currentUser.setText(currentUserName);

        }

     }

    @FXML
     public void initialize() throws IOException
     {
         BufferedReader buffR = new BufferedReader(new FileReader("currentUser.txt"));
         String currentUserName = buffR.readLine();
         currentUser.setText(currentUserName);

         //Scanner courseScan = new Scanner(currentUserName+".TXT");
         BufferedReader courseReader = new BufferedReader(new FileReader(currentUserName + ".txt"));
         String line = null;
         while((line = courseReader.readLine()) != null)
         {
             Tab tab1 = new Tab();
             tab1.setText(line);
             tabCourses.getTabs().add(tab1);
         }

     }


     @FXML
     protected void DeleteClick()
     {
         if(!delButton.isPressed())
         {
             Tab tab1 = tabCourses.getSelectionModel().getSelectedItem();
             removeLineFromFile(tab1.getText());
             tabCourses.getTabs().remove(tab1);
         }
     }
     
     
    @FXML
     protected void RenameClick(ActionEvent event)
     {
         if(!modButton.isPressed())
         {
            renameConfirmB.setVisible(true);
            renameLabelText.setVisible(true);
            renameText.setVisible(true);
         }
     }

     @FXML
     protected void renameConfirmButtonPress() throws IOException
     {
         if(!renameConfirmB.isPressed())
         {

             BufferedReader buffR = new BufferedReader(new FileReader("currentUser.txt"));
             String currentUserName = buffR.readLine();
             Scanner courseReader = new Scanner(new File(currentUserName + ".txt"));
             StringBuffer buffer = new StringBuffer();
             while(courseReader.hasNextLine())
             {
                 buffer.append(courseReader.nextLine()+System.lineSeparator());

             }
             String listOfCourses = buffer.toString();
             String oldName = tabCourses.getSelectionModel().getSelectedItem().getText();
             String newName = renameText.getText();
             listOfCourses = listOfCourses.replaceFirst(oldName, newName);
             FileWriter writer = new FileWriter(currentUserName+".txt");
             writer.write(listOfCourses);
             writer.flush();
             Tab tab1 = tabCourses.getSelectionModel().getSelectedItem();
             tab1.setText(renameText.getText());
             renameConfirmB.setVisible(false);
             renameLabelText.setVisible(false);
             renameText.setVisible(false);
         }
     }

     public void start() {

    }



    @FXML
    public void logoutButton() {
        Parent root;
        try
        {
            usernameSaved = "";
            passwordSaved = "";
            root = FXMLLoader.load(Main.class.getResource("login.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setTitle("Indexify");
            stage.setScene(new Scene(root,530, 400));
            stage.setResizable(false);
            stage.show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}

