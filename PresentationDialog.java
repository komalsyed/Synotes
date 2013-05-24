package org.synote.player.client;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.ValidationException;
import com.gwtext.client.widgets.form.Validator;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class PresentationDialog extends Window
{
	private AbstractFrame frame;

	private TextField idField;
	private ComboBox indexField;
	private TextField urlField;
	private TextField startField;

	private	com.google.gwt.user.client.ui.Button startIncButton;
	private com.google.gwt.user.client.ui.Button startDecButton;
	private com.google.gwt.user.client.ui.Button currentTimeButton;

	private Button confirmButton;
	private Button cancelButton;

	private Integer index;

	public PresentationDialog(AbstractFrame parent)
	{
		///////////////////////////////CHANGE BY F&K
		this.frame = parent;
	}

	public void init()
	{
		initDialog();
		initValidators();
		initListeners();
	}

	public void initDialog()
	{
		setModal(true);
		setPlain(true);

		setLayout(new FitLayout());
		setPaddings(7);
		setWidth(400);
		setHeight(220);

		setCloseAction(Window.HIDE);
		setButtonAlign(Position.CENTER);

		FormPanel form = new FormPanel();
		add(form);
		form.setBaseCls("x-plain");
		form.setLabelWidth(55);

		idField = new TextField("ID");
		form.add(idField, new AnchorLayoutData("-20"));
		idField.setDisabled(true);

		indexField = new ComboBox("Index");
		form.add(indexField, new AnchorLayoutData("-20"));
		indexField.setAllowBlank(false);
		indexField.setDisplayField("Index");
		indexField.setTypeAhead(true);
		indexField.setMode(ComboBox.LOCAL);
		indexField.setTriggerAction(ComboBox.ALL);
		indexField.setSelectOnFocus(true);
		indexField.setForceSelection(true);
		indexField.setEditable(false);

		urlField = new TextField("URL");
		form.add(urlField, new AnchorLayoutData("-20"));
		urlField.setAllowBlank(false);
		urlField.setEmptyText("Insert the url of the slide image...");

		startField = new TextField("Start");
		startField.setAllowBlank(true);
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

		currentTimeButton = new com.google.gwt.user.client.ui.Button();
		currentTimeButton.setText("Current time");
		currentTimeButton.setStyleName("currentTimeButton");
		form.add(currentTimeButton);

		confirmButton = new Button("Confirm");
		addButton(confirmButton);
		confirmButton.setTooltip("Confirms all changes");

		cancelButton = new Button("Cancel");
		addButton(cancelButton);
		cancelButton.setTooltip("Cancels all changes");
	}

	public void initValidators()
	{
		startField.setValidator(new Validator()
		{
			public boolean validate(String value) throws ValidationException
			{
				//if (value.trim().length() == 0)
				//	return false;

				try
				{
					TimeFormat.getInstance().parse(value.trim());

					return true;
				}
				catch (NumberFormatException ex)
				{
					startField.setInvalidText("Time format is not correct. Please input hh:mm:ss");
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

		currentTimeButton.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				startField.setValue(TimeFormat.getInstance().toString(getController().getPosition()));
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

		Integer start;
		if(startField.getText().trim().length() == 0)
		{
			start = null;
		}
		else
		{
			start = TimeFormat.getInstance().parse(startField.getText().trim());
		}

		String presentationId;
		if(getModel().getPresentationModel().getPresentations().length > 0)
			presentationId = getModel().getPresentationModel().getPresentation(0).getId();
		else
			presentationId = null;
		//from users' view the index begins from 1, but from our view, it begins from 0!
		int newIndex = Integer.parseInt(indexField.getText().trim()) - 1;
		PresentationSlideData slideData = new PresentationSlideData(null, start, urlField.getText().trim());

		if(idField.getText().length() == 0)//create new slide
		{
			getModel().getPresentationModel().create(presentationId, newIndex, slideData);
		}
		else //edit a slide
		{
			getModel().getPresentationModel().edit(presentationId, index, newIndex, slideData);
		}

		super.hide();
	}

	private boolean isValid()
	{
		return indexField.isValid() &&
			urlField.isValid() &&
			startField.isValid();
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

	public void show(Integer index, int start)
	{
		setTitle("Create slide");

		idField.setValue(null);
		initIndexField((index != null && index != -1) ? index : 0, true);
		urlField.setValue(null);
		startField.setValue(TimeFormat.getInstance().toString(start));

		confirmButton.setText("Create");
		confirmButton.setTooltip("Creates new slide");

		super.show();

		urlField.clearInvalid();
	}

	public void show(String id, Integer index, String start, String url)
	{
		setTitle("Edit slide");

		idField.setValue(id);
		initIndexField(index, false);
		urlField.setValue(url);
		startField.setValue(start);

		confirmButton.setText("Save");
		confirmButton.setTooltip("Updates slide");

		super.show();
	}

	private void initIndexField(Integer index, boolean isCreate)
	{
		this.index = index;

		PresentationModel model = getModel().getPresentationModel();
		Store store = null;
		if(model.getPresentations().length>0)
		{
			int length = model.getPresentation(0).getSlides().length;
			String[] indexStr;
			if(isCreate)
				indexStr = new String[length+1];
			else
				indexStr = new String[length];
			int i=0;
			for(;i<length;i++)
			{
				indexStr[i] = String.valueOf(i+1);
			}

			if(isCreate)
			{
				indexStr[i] = String.valueOf(i+1);
			}
			store = new SimpleStore("Index", indexStr);
		}
		else //there's no presentation
		{
			store = new SimpleStore("Index", new String[]{"1"});
		}
		store.load();
		indexField.setStore(store);

		indexField.setValue(String.valueOf(index + 1));
	}
}
