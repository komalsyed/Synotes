package org.synote.player.client;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.ValidationException;
import com.gwtext.client.widgets.form.Validator;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

////////////////////////////////////////////////////////////
import com.gwtext.client.widgets.form.ComboBox; 
import com.gwtext.client.widgets.form.event.ComboBoxCallback;   
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;   
import com.gwtext.client.data.Store;  
import com.gwtext.client.data.Record;   
import com.gwtext.client.data.SimpleStore;


import com.google.gwt.core.client.EntryPoint;   
import com.google.gwt.user.client.ui.RootPanel;     
import com.gwtext.client.widgets.Panel;   
import com.gwtext.client.widgets.form.Field;    

public class SynmarkDialog extends Window
{
	private AbstractFrame frame;

	private TextField idField;
	private TextField startField;
	private TextField endField;
	private TextField titleField;
	private TextArea noteTextArea;
	private TextField tagsField;
	private TextField nextField;
	private ComboBox flagField;
		

	private	com.google.gwt.user.client.ui.Button startIncButton;
	private com.google.gwt.user.client.ui.Button startDecButton;
	private com.google.gwt.user.client.ui.Button endIncButton;
	private com.google.gwt.user.client.ui.Button endDecButton;

	private Button confirmButton;
	private Button cancelButton;

	private boolean validating;

	public SynmarkDialog(AbstractFrame parent)
	{
		this.frame = parent;
	}

	public void init()
	{
		initDialog();
		initValidators();
		initListeners();
	}

	private void initDialog()
	{
		setTitle("Synmark");
		setModal(true);
		setPlain(true);

		setLayout(new FitLayout());
		setPaddings(5);
		setWidth(400);
		//setHeight(300);
		setHeight(400);

		setCloseAction(Window.HIDE);
		setButtonAlign(Position.CENTER);

		FormPanel form = new FormPanel();
		add(form);
		form.setBaseCls("x-plain");
		form.setLabelWidth(55);

		idField = new TextField("ID");
		form.add(idField, new AnchorLayoutData("100%"));
		idField.setWidth("100%");
		idField.setDisabled(true);

		startField = new TextField("Start");
		startField.setAllowBlank(false);
		startField.setCls("textFieldMulti");
		if(Utils.getBrowser() == Utils.Browser.IE)
		{
			startField.setLabelStyle("width:63px");
		}
		else
		{
			startField.setLabelStyle("width:55px");
		}

		startIncButton = new com.google.gwt.user.client.ui.Button();
		startIncButton.setStyleName("timeIncButton");

		startDecButton = new com.google.gwt.user.client.ui.Button();
		startDecButton.setStyleName("timeDecButton");

		form.add(startDecButton);
		form.add(startIncButton);
		form.add(startField, new AnchorLayoutData("-60"));

		endField = new TextField("End");
		endField.setCls("textFieldMulti");
		if(Utils.getBrowser() == Utils.Browser.IE)
		{
			endField.setLabelStyle("width:63px");
		}
		else
		{
			endField.setLabelStyle("width:55px");
		}

		endIncButton = new com.google.gwt.user.client.ui.Button();
		endIncButton.setStyleName("timeIncButton");

		endDecButton = new com.google.gwt.user.client.ui.Button();
		endDecButton.setStyleName("timeDecButton");

		form.add(endDecButton);
		form.add(endIncButton);
		form.add(endField, new AnchorLayoutData("-60"));

		titleField = new TextField("Title");
		form.add(titleField, new AnchorLayoutData("-20"));

		noteTextArea = new TextArea("Note");
		form.add(noteTextArea, new AnchorLayoutData("-20"));
		noteTextArea.setHideLabel(true);

		tagsField = new TextField("Tags");
		form.add(tagsField, new AnchorLayoutData("-20"));

		nextField = new TextField("Next");
		form.add(nextField, new AnchorLayoutData("-20"));
		nextField.setAllowBlank(true);
		
		
		
		//private TextField flagField;
	 //create a Store using local array data   
        final Store store = new SimpleStore(new String[]{"abbr", "flag"}, getFlags());   
        store.load();   
  
        flagField = new ComboBox();   
        flagField.setForceSelection(true);   
        flagField.setMinChars(1);   
        flagField.setFieldLabel("Flag");   
        flagField.setStore(store);   
        flagField.setDisplayField("flag");   
        flagField.setMode(ComboBox.LOCAL);   
        flagField.setTriggerAction(ComboBox.ALL);   
        flagField.setEmptyText("Enter flag");   
        flagField.setLoadingText("Searching...");   
        flagField.setTypeAhead(true);   
        flagField.setSelectOnFocus(true);   
        flagField.setWidth(200);   
		
		flagField.setHideTrigger(false);   
  
        flagField.addListener(new ComboBoxListenerAdapter() {   
            public boolean doBeforeQuery(ComboBox comboBox, ComboBoxCallback flagField) {   
                System.out.println("ComboBox::doBeforeQuery()");   
                return true;   
            }   
  
            public boolean doBeforeSelect(ComboBox comboBox, Record record, int index) {   
                System.out.println("ComboBox::doBeforeSelect(" +   
                                        record.getAsString("flag") + ")");   
                return super.doBeforeSelect(comboBox, record, index);   
            }   
  
            public void onCollapse(ComboBox comboBox) {   
                System.out.println("ComboBox::onCollapse()");   
            }   
  
            public void onExpand(ComboBox comboBox) {   
                System.out.println("ComboBox::onExpand()");   
            }   
  
            public void onSelect(ComboBox comboBox, Record record, int index) {   
                System.out.println("ComboBox::onSelect('" + record.getAsString("flag") + "')");   
            }   
  
            public void onBlur(Field field) {   
                System.out.println("ComboBox::onBlur()");   
            }   
  
            public void onChange(Field field, Object newVal, Object oldVal) {   
                System.out.println("ComboBox::onChange(" + oldVal + "-->" + newVal + ")");   
            }   
  
            public void onFocus(Field field) {   
                System.out.println("ComboBox::onFocus()");   
            }   
  
            public void onInvalid(Field field, String msg) {   
                super.onInvalid(field, msg);   
            }   
  
            public void onSpecialKey(Field field, EventObject e) {   
                System.out.println("ComboBox::onSpecialKey(key code " + e.getKey() + ")");   
            }   
        });   

		///////////////////////////////////////////////////////////////////////

		///////////////////////////////////////////////////////////////////////
		//flagField = new TextField("Flag");
		form.add(flagField, new AnchorLayoutData("-20"));
		//flagField.setAllowBlank(true);
		
		confirmButton = new Button("Confirm");
		addButton(confirmButton);
		confirmButton.setTooltip("Confirms all changes (Ctrl + Alt + Enter)");

		cancelButton = new Button("Cancel");
		addButton(cancelButton);
		cancelButton.setTooltip("Cancels all changes (Ctrl + Alt + Q)");
		
		
	}
	
	private static String[][] getFlags() {   
        return new String[][]{   
                new String[]{"C","Comment"},   
                new String[]{"Q","Question"},  
				new String[]{"S","Solution"},  
                new String[]{"P","Problem Area"},
			};}

	private void initValidators()
	{
		startField.setValidator(new Validator()
		{
			public boolean validate(String value) throws ValidationException
			{
				if (value.trim().length() == 0)
					return false;

				try
				{
					TimeFormat.getInstance().parse(value.trim());

					if (!validating)
					{
						try
						{
							validating = true;
							endField.validate();
						}
						finally
						{
							validating = false;
						}
					}

					return true;
				}
				catch (NumberFormatException ex)
				{
					startField.setInvalidText("Time format is not correct. Please input hh:mm:ss");
					return false;
				}
			}
		});

		endField.setValidator(new Validator()
		{
			public boolean validate(String value) throws ValidationException
			{
				if (value.trim().length() == 0)
					return true;

				try
				{
					TimeFormat.getInstance().parse(value.trim());

					if (!validating)
					{
						try
						{
							validating = true;
							startField.validate();
						}
						finally
						{
							validating = false;
						}
					}

					return true;
				}
				catch (NumberFormatException ex)
				{
					endField.setInvalidText("Time format is not correct. Please input hh:mm:ss");
					return false;
				}
			}
		});

		nextField.setValidator(new Validator()
		{
			public boolean validate(String value) throws ValidationException
			{
				if (value.trim().length() == 0)
					return true;

				if((endField.getValueAsString().trim().equals("") || endField.isValid() == false)&& !value.trim().equals(""))
				{
					nextField.setInvalidText("You must specify the end time if you want to chain the synmark.");
					return false;
				}
				//id for next field doesn't exist
				if(getModel().getSynmarkModel().getSynmark(value.trim()) != null)
				{
					return true;
				}
				else
				{
					nextField.setInvalidText("Cannot find synmark with id "+value.trim());
					return false;
				}
			}
		});
	}

	private void initListeners()
	{
		startIncButton.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				startField.setValue(TimeFormat.getInstance().toString(incTime(startField.getText().trim())));
			}
		});

		startDecButton.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				startField.setValue(TimeFormat.getInstance().toString(decTime(startField.getText().trim())));
			}
		});

		endIncButton.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				endField.setValue(TimeFormat.getInstance().toString(incTime(endField.getText().trim())));
			}
		});

		endDecButton.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				endField.setValue(TimeFormat.getInstance().toString(decTime(endField.getText().trim())));
			}
		});

		confirmButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				confirm();
			}
		});

		cancelButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				hide();
			}
		});

		//Ctrl + Alt + Enter
		KeyConfig configConfirm = new KeyConfig(new int[] {KeyConfig.KEY_ALT, KeyConfig.KEY_CTRL, KeyConfig.KEY_ENTER});
		KeyboardActionManager.addKeyboardListeners(this,configConfirm, new KeyboardActionListener()
		{
			public void onKeysPressed(KeyPressedEvent keyPressedEvent)
			{
				if(SynmarkDialog.this.isHidden() == false)
					confirm();
			}
		});

		//Ctrl + Alt + Q
		KeyConfig configCancel = new KeyConfig(new int[]{KeyConfig.KEY_ALT, KeyConfig.KEY_CTRL, KeyConfig.KEY_Q});
		KeyboardActionManager.addKeyboardListeners(this,configCancel, new KeyboardActionListener()
		{
			public void onKeysPressed(KeyPressedEvent keyPressedEvent)
			{
				if(SynmarkDialog.this.isHidden() == false)
				{
					hide();
				}
			}
		});

		//Ctrl + Alt + F
		//This is a temporary solution
		KeyConfig configFocus = new KeyConfig(new int[] {KeyConfig.KEY_ALT, KeyConfig.KEY_CTRL, KeyConfig.KEY_F});
		KeyboardActionManager.addKeyboardListeners(this,configFocus, new KeyboardActionListener()
		{
			public void onKeysPressed(KeyPressedEvent keyPressedEvent)
			{
				if(SynmarkDialog.this.isHidden() == false)
				{
					noteTextArea.focus();
				}
			}
		});
	}

	private int incTime(String timeString)
	{
		return TimeFormat.getInstance().parse(timeString) + 1000;
	}

	private int decTime(String timeString)
	{
		int time = TimeFormat.getInstance().parse(timeString);

		return (time >= 1000) ? time - 1000 : 0;
	}

	private void confirm()
	{
		if (!isValid())
			return;

		int start = TimeFormat.getInstance().parse(startField.getText().trim());
		Integer end = (endField.getText().trim().length() > 0) ? TimeFormat.getInstance().parse(endField.getText().trim()) : null;

		if (end != null && end <= start)
		{
			startField.validateValue("INVALID");
			endField.validateValue("INVALID");

			return;
		}

		String title = titleField.getText().trim();
		String note = noteTextArea.getText().trim();
		String[] tags = tagsField.getText().split(",");
		String next = (nextField.getText().trim().length() > 0) ? nextField.getText().trim() : null;

		///////////////////////////////////////////////////////////////////////
		String flag = "";
		
		if(null != flagField.getValue())
			flag = flagField.getValue();
		
		SynmarkData synmark = new SynmarkData(start, end, title, note, tags, next, flag);
		
		if (idField.getText().length() == 0){
			getModel().getSynmarkModel().create(synmark);
			}
		else
			getModel().getSynmarkModel().edit(idField.getText(), synmark);

		super.hide();
	}

	private boolean isValid()
	{
		return idField.isValid() &&
			startField.isValid() &&
			endField.isValid() &&
			titleField.isValid() &&
			noteTextArea.isValid() &&
			tagsField.isValid() &&
			nextField.isValid();
	}

	public AbstractFrame getFrame()
	{
		return frame;
	}

	public PlayerModel getModel()
	{
		return frame.getModel();
	}

	public MultimediaController getController()
	{
		return frame.getController();
	}

	public void show(int start)
	{
		show(start, null);
	}

	public void show(int start, Integer end)
	{
		idField.setValue(null);
		startField.setValue(TimeFormat.getInstance().toString(start));
		endField.setValue(TimeFormat.getInstance().toString(end));
		titleField.setValue(null);
		noteTextArea.setValue(null);
		tagsField.setValue(null);
		nextField.setValue(null);

		/////////////////////////////////////////////
		flagField.setValue(null);
		
		confirmButton.setText("Create");
		confirmButton.setTooltip("Creates new synmark (Ctrl + Alt + Enter)");

		super.show();

		noteTextArea.focus(); // Works only in GC
	}

	
	public void show(String id, String start, String end, String title, String note, String tags, String next, String flag)
	{
		idField.setValue(id);
		startField.setValue(TimeFormat.getInstance().toString(start));
		endField.setValue(TimeFormat.getInstance().toString(end));
		titleField.setValue(title);
		noteTextArea.setValue(note);
		tagsField.setValue(tags);
		nextField.setValue(next);
		
		////////////////////////////////////////////////////////
		flagField.setValue(flag);


		confirmButton.setText("Save");
		confirmButton.setTooltip("Updates synmark (Ctrl + Alt + Enter)");

		super.show();

		noteTextArea.focus(); // Works only in GC
	}
}
