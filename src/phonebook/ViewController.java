
package phonebook;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class ViewController implements Initializable {
    
    //<editor-fold defaultstate="collapsed" desc="FXML">
    @FXML Pane contactPane;
    @FXML Pane exportPane;
    @FXML TableView table;
    @FXML StackPane menuPane;
    @FXML TextField inputLastName;
    @FXML TextField inputFirstName;
    @FXML TextField inputEmail;
    @FXML TextField inputMobile;
    @FXML Button newContactButton;
    @FXML Button pdfButton;
    @FXML AnchorPane anchorPane;
    @FXML SplitPane splitPane;
    @FXML TextField inputPdfName;
//</editor-fold>
    DB db = new DB();
    
    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";
    
    private final ObservableList<Person> DATA = FXCollections.observableArrayList();
    
    //új kontakt bevitele
   @FXML private void newContact(ActionEvent event){
       String email = inputEmail.getText();
       String mobile = inputMobile.getText();
       String lName = inputLastName.getText();
       if (email.length() > 3 && email.contains("@") && email.contains(".") 
               && mobile.length() > 7 && mobile.contains("+") && mobile.contains("/")) {
           Person newPerson = new Person(inputLastName.getText(),inputFirstName.getText(),email,mobile);
   DATA.add(newPerson);
   db.addPerson(newPerson);
   inputLastName.clear();;
   inputFirstName.clear();
   inputEmail.clear();
   inputMobile.clear();
       }else {
       alert("Rossz adatokat adtál meg!");
       }
   }
   //új pdf készítése
   @FXML private void newPdf(ActionEvent event) {
       
   String fileName = inputPdfName.getText();
   fileName = fileName.replaceAll("\\s+","");
   if(fileName != null && !fileName.equals("")) {
   Pdf pdfCreator = new Pdf();
   pdfCreator.pdfGenerator(fileName,DATA);
   inputPdfName.clear();
   }else {
   alert("Rossz pdf nevet adtál meg!");
   }
       
   }
   //hiba üzenet
    private void alert(String text) {
       splitPane.setDisable(true);
       splitPane.setOpacity(0.4);
       
       Label label = new Label(text);
       Button button = new Button("Ok");
       VBox vbox = new VBox(label,button);
       vbox.setAlignment(Pos.CENTER);
    
       
       button.setOnAction(new EventHandler<ActionEvent>(){
       @Override
       public void handle(ActionEvent e) {
       splitPane.setDisable(false);
       splitPane.setOpacity(1);
       vbox.setVisible(false);
       }
       });
       
       anchorPane.getChildren().add(vbox);
       anchorPane.setTopAnchor(vbox,300.0);
       anchorPane.setLeftAnchor(vbox,400.0);
    }
    //table adatok
    public void tableData() {
    
        TableColumn lastNameCol = new TableColumn ("Vezetéknév");
        lastNameCol.setMinWidth(140);
        lastNameCol.setMaxWidth(180);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
        
        lastNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person,String> t) {
               Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
               actualPerson.setLastName(t.getNewValue());
               db.updatePerson(actualPerson);
        }
        });
        
        TableColumn firstNameCol = new TableColumn ("Keresztnév");
        firstNameCol.setMinWidth(140); 
        firstNameCol.setMaxWidth(180);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        
        firstNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person,String> t) {
            Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
               actualPerson.setFirstName(t.getNewValue());
               db.updatePerson(actualPerson);
            }
        });
        
        TableColumn emailCol = new TableColumn ("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person,String>("email"));
        
        emailCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person,String> t) {
            Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
               actualPerson.setEmail(t.getNewValue());
               db.updatePerson(actualPerson);
            }
        });
        
        TableColumn mobileCol = new TableColumn ("Telefonszám");
        mobileCol.setMinWidth(140);
        mobileCol.setMaxWidth(180);
        mobileCol.setCellFactory(TextFieldTableCell.forTableColumn());
        mobileCol.setCellValueFactory(new PropertyValueFactory<Person,String>("mobile"));
        
        mobileCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person,String> t) {
               Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
               actualPerson.setMobile(t.getNewValue());
               db.updatePerson(actualPerson);
        }
        });
        
       TableColumn removeCol = new TableColumn( "Törlés" );

        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = 
                new Callback<TableColumn<Person, String>, TableCell<Person, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Person, String> param )
                    {
                        final TableCell<Person, String> cell = new TableCell<Person, String>()
                        {   
                            final Button btn = new Button( "Törlés" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                            {
                                                Person person = getTableView().getItems().get( getIndex() );
                                                DATA.remove(person);
                                                db.removeContact(person);
                                       } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );
        
        table.getColumns().addAll(lastNameCol, firstNameCol, emailCol, mobileCol, removeCol);

        DATA.addAll(db.getAllContacts());

        table.setItems(DATA);
    }
    // fa menu
    public void treeMenu() {
        
        TreeItem<String> mainRoot = new TreeItem("Menü");
        TreeView<String> mainView = new TreeView(mainRoot);
        mainView.setShowRoot(false);
        
        TreeItem<String> agA = new TreeItem(MENU_CONTACTS);
       // agA.setExpanded(true);
        Node contactNode = new ImageView(new Image(getClass().getResourceAsStream("/heart.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/x.png")));
            TreeItem<String> agA1 = new TreeItem(MENU_LIST,contactNode);
            TreeItem<String> agA2 = new TreeItem(MENU_EXPORT,exportNode);
        TreeItem<String> agB = new TreeItem(MENU_EXIT);
        
        agA.getChildren().addAll(agA1,agA2);
        mainRoot.getChildren().addAll(agA,agB);
        
        menuPane.getChildren().addAll(mainView);
   
        mainView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        public void changed(ObservableValue observable, Object oldValue,Object newValue) {
            TreeItem<String> selectedItem = (TreeItem<String>) newValue;
            String selectedMenu;
            selectedMenu = selectedItem.getValue();
            
            if(null != selectedMenu) {
            System.out.println();
            switch (selectedMenu) {
                case MENU_CONTACTS:
                    selectedItem.setExpanded(true);
                    break;
                case MENU_LIST:
                    contactPane.setVisible(true);
                    exportPane.setVisible(false);
                    break;
                case MENU_EXPORT:
                    contactPane.setVisible(false);
                    exportPane.setVisible(true);
                    break;
                case MENU_EXIT:
                    System.exit(0);
                    break;
            }
            }
        }
        });
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    tableData();
    treeMenu();
    }
}
