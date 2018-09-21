package Controller;

import com.gluonhq.charm.glisten.control.BottomNavigationButton;

import Datebase.PatientsDatebaseHandler;
import Model.Patient;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class ControllerWindow {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private BottomNavigationButton schedule;

	@FXML
	private BottomNavigationButton analyzes;

	@FXML
	private BottomNavigationButton hospital;

	@FXML
	private BottomNavigationButton recipes;

	@FXML
	private BottomNavigationButton patients;

	@FXML
	private BottomNavigationButton references;

	@FXML
	private BottomNavigationButton exit;

	@FXML
	private AnchorPane paneSchedule;

	@FXML
	private TableView<?> tableSchedule;

	@FXML
	private TableColumn<?, ?> numberSchedule;

	@FXML
	private TableColumn<?, ?> nameSchedule;

	@FXML
	private TableColumn<?, ?> addresSchedule;

	@FXML
	private TableColumn<?, ?> siteNumberSchedule;

	@FXML
	private AnchorPane paneHospital;

	@FXML
	private TableView<Patient> tabelHospital;

	@FXML
	private TableColumn<Patient, String> nameHospital;

	@FXML
	private TableColumn<Patient, String> diagnosisHospital;

	@FXML
	private TableColumn<Patient, String> workHospital;

	@FXML
	private TableColumn<Patient, LocalDate> openDateHospital;

	@FXML
	private TableColumn<Patient, LocalDate> closeDateHospital;

	@FXML
	private Button addHospital;

	@FXML
	private Button closeUpHospital;

	@FXML
	private AnchorPane analyzesPane;

	@FXML
	private Label dataLabel;

	@FXML
	private MenuButton nameDoctor;

	@FXML
	private MenuItem changePassword;

	@FXML
	private MenuItem changeLogin;

	@FXML
	private Button refreshButton;

	@FXML
	void initialize() {
		timeLine();
		buttonsEvent();
		addIcon();
		addTable();
	}

	private void addTable() {
		nameHospital.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNamePatients()));
		diagnosisHospital.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDiagnosisPatients()));
		workHospital.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPlaceOfWork()));
		openDateHospital.setCellValueFactory(new PropertyValueFactory<Patient, LocalDate>("openingDate"));
		closeDateHospital.setCellValueFactory(new PropertyValueFactory<Patient, LocalDate>("closingDate"));
		PatientsDatebaseHandler handler = new PatientsDatebaseHandler();
		tabelHospital.setItems(FXCollections.observableList(handler.getAllPatients()));// передать
		handler.getAllPatients();
		tabelHospital.setEditable(true);
		nameHospital.setCellFactory(TextFieldTableCell.<Patient>forTableColumn());
		nameHospital.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Patient, String>>() {
			@Override
			public void handle(CellEditEvent<Patient, String> event) {
				if (event != null) {
					Patient patients = event.getTableView().getItems().get(event.getTablePosition().getRow());
					String oldName = patients.getNamePatients();
					String newName = event.getNewValue();
					handler.updateNamePatient(newName, oldName);
					patients.setNamePatients(newName);
				}

			}
		});

		diagnosisHospital.setCellFactory(TextFieldTableCell.<Patient>forTableColumn());
		diagnosisHospital.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Patient, String>>() {
			@Override
			public void handle(CellEditEvent<Patient, String> event) {
				if (event != null) {
					Patient patients = event.getTableView().getItems().get(event.getTablePosition().getRow());
					String newDiagnosis = event.getNewValue();
					handler.updateDiagnosisPatients(newDiagnosis, patients.getNamePatients());
					patients.setDiagnosisPatients(newDiagnosis);
				}

			}
		});

		workHospital.setCellFactory(TextFieldTableCell.<Patient>forTableColumn());
		workHospital.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Patient, String>>() {
			@Override
			public void handle(CellEditEvent<Patient, String> event) {
				if (event != null) {
					Patient patients = event.getTableView().getItems().get(event.getTablePosition().getRow());
					String newWork = event.getNewValue();
					handler.updateWork(newWork, patients.getNamePatients());
					patients.setPlaceOfWork(newWork);
				}
			}
		});

		Callback<TableColumn<Patient, LocalDate>, TableCell<Patient, LocalDate>> dateCellFactory = (
				TableColumn<Patient, LocalDate> param) -> new DateEditingCell();
		openDateHospital.setCellFactory(dateCellFactory);
		openDateHospital.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Patient, LocalDate>>() {
			@Override
			public void handle(CellEditEvent<Patient, LocalDate> event) {
				if (event != null) {
					Patient patients = event.getTableView().getItems().get(event.getTablePosition().getRow());
					LocalDate newOpen = event.getNewValue();
					handler.updateOpenDate(newOpen, patients.getNamePatients());
					patients.setOpeningDate(newOpen);
				}
			}

		});

		closeDateHospital.setCellFactory(dateCellFactory);
		closeDateHospital.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Patient, LocalDate>>() {
			@Override
			public void handle(CellEditEvent<Patient, LocalDate> event) {
				if (event != null) {
					Patient patients = event.getTableView().getItems().get(event.getTablePosition().getRow());
					LocalDate newClose = event.getNewValue();
					handler.updateCloseDate(newClose, patients.getNamePatients());
					patients.setClosingDate(newClose);
				}
			}

		});
	}

	class DateEditingCell extends TableCell<Patient, LocalDate> {
		private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

		@Override
		protected void updateItem(LocalDate item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setText(null);
			} else {
				this.setText(format.format(java.sql.Date.valueOf(item)));

			}
		}

		private DatePicker datePicker;

		private DateEditingCell() {
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createDatePicker();
				setText(null);
				setGraphic(datePicker);
			}

		}

		private void createDatePicker() {
			datePicker = new DatePicker(getDate());
			datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			datePicker.setOnAction((e) -> {
				System.out.println("Committed: " + datePicker.getValue().toString());
				commitEdit(datePicker.getValue());
			});
		}

		private LocalDate getDate() {
			return getItem() == null ? LocalDate.now() : getItem();
		}

	}

	private void addIcon() {
		Image image = new Image(getClass().getResourceAsStream("../Icons/icons8-moleskine-32.png"));// присваетват
		// кнопкам картинки
		schedule.graphicProperty().setValue(new ImageView(image));

		Image image1 = new Image(getClass().getResourceAsStream("../Icons/icons8-разочарованный-24.png"));
		hospital.graphicProperty().setValue(new ImageView(image1));

		Image image2 = new Image(getClass().getResourceAsStream("../Icons/icons8-syringe-32.png"));
		analyzes.graphicProperty().setValue(new ImageView(image2));

		Image image3 = new Image(getClass().getResourceAsStream("../Icons/icons8-add-list-24.png"));
		recipes.graphicProperty().setValue(new ImageView(image3));

		Image image4 = new Image(getClass().getResourceAsStream("../Icons/icons8-conference-24.png"));
		patients.graphicProperty().setValue(new ImageView(image4));

		Image image5 = new Image(getClass().getResourceAsStream("../Icons/icons8-document-32.png"));
		references.graphicProperty().setValue(new ImageView(image5));

		Image image6 = new Image(getClass().getResourceAsStream("../Icons/icons8-delete-26.png"));
		exit.graphicProperty().setValue(new ImageView(image6));

		Image image7 = new Image(getClass().getResourceAsStream("../Icons/icons8-перезапуск-24.png"));
		refreshButton.graphicProperty().setValue(new ImageView(image7));// присваетват кнопкам картинки
	}

	private void timeLine() {
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			dataLabel
					.setText(LocalDate.now().format(dateFormatter) + " / " + LocalDateTime.now().format(timeFormatter));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}

	private void buttonsEvent() {
		addHospital.setOnAction(event -> {
			Stage stage = new Stage();
			FXMLLoader loader1 = new FXMLLoader();
			loader1.setLocation(getClass().getResource("/View/windowPatients.fxml"));
			try {
				loader1.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Parent root1 = loader1.getRoot();
			stage.setTitle("Добавить больного");
			stage.setScene(new Scene(root1, 600, 350));
			stage.show();
			stage.setResizable(false);
		});

		schedule.setOnAction(event -> {// переключение событий
			paneSchedule.setVisible(true);
			paneHospital.setVisible(false);
			analyzesPane.setVisible(false);
		});

		hospital.setOnAction(event -> {
			paneSchedule.setVisible(false);
			paneHospital.setVisible(true);
			analyzesPane.setVisible(false);
		});
		
		analyzes.setOnAction(event -> {
			paneSchedule.setVisible(false);
			paneHospital.setVisible(false);
			analyzesPane.setVisible(true);
		});
		
		exit.setOnAction(event -> {
			Main.primaryStage.close();
		});

		refreshButton.setOnAction(evetn -> {
			addTable();
		});
		
		closeUpHospital.setOnAction(event->{
			int row = tabelHospital.getSelectionModel().getSelectedIndex();
			tabelHospital.getItems().remove(row);
		});
	}
}
