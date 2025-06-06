package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.tdm.tm.CartTm;
import lk.ijse.tdm.tm.ProgramTm;
import lk.ijse.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationFormController {

    @FXML
    private ComboBox<String> cmbProgramNames;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colUpfrontPayment;

    @FXML
    private Label lblProgrameFee;

    @FXML
    private Label lblRegisterId;

    @FXML
    private TableView<CartTm> tblRegistration;

    @FXML
    private TextField txtStudentName;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtUpfrontPayment;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private DatePicker datePicker;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Student);

    ProgramBO programBO = (ProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Programs);

    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Registration);

    ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        getProgramNames();
        setCellValueFactory();
        getCurrentRegistrationId();
    }

    private void getCurrentRegistrationId() {
        try {
            int currentId = registrationBO.getCurrentRegistrationId();
            String nextOrderId = generateNextOrderId(currentId);
            lblRegisterId.setText(nextOrderId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private String generateNextOrderId(int currentId) {
        if(currentId != 0) {
            /*String[] split = currentId.split("S-");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);*/
            int idNum = currentId;
            return "R" + ++idNum;
        }
        return "R1";
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colUpfrontPayment.setCellValueFactory(new PropertyValueFactory<>("upfrontpayment"));


    }

    private void getProgramNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ProgramDTO> programList = programBO.getAllPrograms();
            List<String> programNameList = new ArrayList<>();

            for (ProgramDTO programDTO: programList){
                programNameList.add(programDTO.getpName());
            }
            for (String name: programNameList) {
                obList.add(name);
            }
            cmbProgramNames.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbProgramNamesOnAction(ActionEvent event) {
        String name = cmbProgramNames.getValue();
        System.out.println(name);
        try {
            ProgramDTO program = programBO.searchProgramByName(name);
            if (program != null) {
                txtProgramId.setText(program.getpId());
                lblProgrameFee.setText(String.valueOf(program.getFee()));
                txtUpfrontPayment.requestFocus();

            }

            txtUpfrontPayment.requestFocus();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        String pId = txtProgramId.getText();
        String pName = cmbProgramNames.getValue();
        double fee = Double.parseDouble(lblProgrameFee.getText());
        double upfrontPay = Double.parseDouble(txtUpfrontPayment.getText());


        CartTm tm = new CartTm(pId, pName, fee, upfrontPay);
        obList.add(tm);
        tblRegistration.setItems(obList);
        clearFields();
    }

    private void clearFields() {
        txtProgramId.clear();
        lblProgrameFee.setText("0/=");
        txtUpfrontPayment.setText("");
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

        List<RegistrationDTO> registrationDTOList = new ArrayList<>();


        // Loop through each item in the table and create a RegistrationDTO for each
        for (CartTm cartTm : tblRegistration.getItems()) {
            // Assuming RegistrationDTO has a constructor that matches the parameters
            RegistrationDTO registrationDTO = new RegistrationDTO(
                    //lblRegisterId.getText(),           // Registration ID
                    txtStudentId.getText(),            // Student ID
                    cartTm.getId(),                    // Program ID
                    cartTm.getUpfrontpayment(),        // Upfront Payment
                    datePicker.getValue()                  // Date (formatted as String)
            );

            // Add each RegistrationDTO to the list
            registrationDTOList.add(registrationDTO);
        }

        registrationBO.placeRegister(registrationDTOList);
    }

    @FXML
    void btnViewRegisOnAction(ActionEvent event) {
        AnchorPane inNode = null;
        try {
            inNode = FXMLLoader.load(this.getClass().getResource("/view/viewRegistration_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rootNode.getChildren().clear();
        rootNode.getChildren().add(inNode);
    }

    @FXML
    void getDateOnAction(ActionEvent event) {
        LocalDate myDate = datePicker.getValue();
        String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtDate.setText(myFormattedDate);
    }
    @FXML
    void txtStudentIdOnAction(ActionEvent event) {
        String id = txtStudentId.getText();
        try {
            StudentDTO student = studentBO.searchStudentId(id);
            if (student != null) {
                txtStudentName.setText(student.getS_name());


            }else{
                new Alert(Alert.AlertType.INFORMATION, "student not found!").show();
            }


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void txtStudentIdOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        AnchorPane inNode = null;
        try {
            inNode = FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rootNode.getChildren().clear();
        rootNode.getChildren().add(inNode);
    }

    @FXML
    void txtPaymentOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.PRICE,txtUpfrontPayment);
    }

    public int isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.PRICE,txtUpfrontPayment)) return 1;
        return 0;
    }


}
