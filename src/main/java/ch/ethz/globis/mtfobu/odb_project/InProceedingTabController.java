package ch.ethz.globis.mtfobu.odb_project;

import java.util.Collection;
import java.util.OptionalLong;
import java.util.function.Consumer;
import java.util.function.Function;

import ch.ethz.globis.mtfobu.odb_project.Controller.InProceedingTableEntry;
import ch.ethz.globis.mtfobu.odb_project.Controller.SecondaryPersonTableEntry;
import ch.ethz.globis.mtfobu.odb_project.Controller.TableEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class InProceedingTabController extends TabController<InProceedingTableEntry, SecondaryPersonTableEntry, TableEntry> {

	public InProceedingTabController(Controller c, TableView mainTable, TextField fieldSearch, Button buttonSearch,
			Button buttonNextPage, Button buttonPreviousPage, TextField fieldCurrentPage, Button buttonCreateRecord,
			Button buttonDeleteRecord, TableView secondTbl, Button btnDeleteRefSecond, TableView thirdTbl,
			Button btnDeleteRefthird) {
		super(c, mainTable, fieldSearch, buttonSearch, buttonNextPage, buttonPreviousPage, fieldCurrentPage, buttonCreateRecord,
				buttonDeleteRecord, secondTbl, btnDeleteRefSecond, thirdTbl, btnDeleteRefthird);
		// TODO Auto-generated constructor stub
	}
	
	private TextField inProceedingPagesField;
	private Button inProceedingChangePagesButton;
	private TextField inProceedingProceedingFilterField;
	private ChoiceBox<?> inProceedingProceedingDropdown;
	private Button inProceedingChangeProceedingButton;
	private ChoiceBox<?> inProceedingAuthorDropdown;
	private Button inProceedingAddAuthorButton;
	private TextField inProceedingAuthorFilterField;
	private TextField inProceedingTitleField;
	private Button inProceedingChangeTitleButton;
	private TextField inProceedingYearField;
	private Button inProceedingChangeYearButton;
	
	public void initializeTabSpecificItems(
			 TextField inProceedingPagesField,
			 Button inProceedingChangePagesButton,
			 
			 TextField inProceedingProceedingFilterField,
			 ChoiceBox<?> inProceedingProceedingDropdown,
			 Button inProceedingChangeProceedingButton,
			 
			 ChoiceBox<?> inProceedingAuthorDropdown,
			 Button inProceedingAddAuthorButton,
			 TextField inProceedingAuthorFilterField,
			 
			 TextField inProceedingTitleField,
			 Button inProceedingChangeTitleButton,
			 
			 TextField inProceedingYearField,
			 Button inProceedingChangeYearButton) {
		
		 this.inProceedingPagesField = inProceedingPagesField;
		 this.inProceedingChangePagesButton = inProceedingChangePagesButton;
		 
		 this.inProceedingProceedingFilterField = inProceedingProceedingFilterField;
		 this.inProceedingProceedingDropdown = inProceedingProceedingDropdown;
		 this.inProceedingChangeProceedingButton = inProceedingChangeProceedingButton;
		 
		 this.inProceedingAuthorDropdown = inProceedingAuthorDropdown;
		 this.inProceedingAddAuthorButton = inProceedingAddAuthorButton;
		 this.inProceedingAuthorFilterField = inProceedingAuthorFilterField;
		 
		 this.inProceedingTitleField = inProceedingTitleField;
		 this.inProceedingChangeTitleButton = inProceedingChangeTitleButton;
		 
		 this.inProceedingYearField = inProceedingYearField;
		 this.inProceedingChangeYearButton = inProceedingChangeYearButton;
		
	}

	public void initializeFunctions(Consumer<Long> secondShowFunction) {
		this.mainShowFunction = this::showInProceeding;
		this.secondShowFunction = secondShowFunction;
	}
	
	
	private void showInProceeding(Long objectId) {

		c.database.executeOnObjectById(objectId, show_in_proceeding);
		c.tabPane.getSelectionModel().select(c.inProceedingTab);
	}
	private final Function<Object,Integer> show_in_proceeding = ( obj) -> {
		InProceedings inProc = (InProceedings) obj;
		this.inProceedingTitleField.setText(inProc.getTitle());
		this.inProceedingPagesField.setText(inProc.getPages());
		this.inProceedingYearField.setText(Integer.toString(inProc.getYear()));
		
		Proceedings proc = inProc.getProceedings();
		if(null != proc) {
			this.inProceedingProceedingFilterField.setText(proc.getTitle());
		}
		
		this.inProceedingAuthorFilterField.setText("");
		secondTableList.clear();
		for (Person person : inProc.getAuthors()) {
			secondTableList.add(c.new SecondaryPersonTableEntry(person));
        }
		return 0;
    };
    
    

	@Override
	public void loadData() {
		c.database.executeOnAllInProceedings(updateProceedings, OptionalLong.of((queryPage[0]-1)*c.PAGE_SIZE), OptionalLong.of(queryPage[0]*c.PAGE_SIZE));
	}
	
	private final Function<Collection<InProceedings>,Void> updateProceedings = inProc -> {
		mainTableList.clear();
		for (InProceedings inProceeding: inProc) {
			mainTableList.add(c.new InProceedingTableEntry(inProceeding));
		}
		return null;
    };

	
	@Override
	public void emptyFields() {
		inProceedingTitleField.setText("");
		inProceedingPagesField.setText("");
		inProceedingYearField.setText("");
		inProceedingProceedingFilterField.setText("");
		inProceedingAuthorFilterField.setText("");
		secondTableList.clear();
	}

}
