package org.synote.player.client;

import com.google.gwt.user.client.Window;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;

public class SynmarkWidget extends AbstractWidget
{
	private static final int CARD_LOADING = 0;
	private static final int CARD_CONTENT = 1;
	private static final int CARD_ERROR_LOADING = 2;
	private static final int CARD_ERROR_CREATING = 3;
	private static final int CARD_ERROR_EDITING = 4;
	private static final int CARD_ERROR_DELETING = 5;

	private SynmarkDialog dialog;

	private SynmarkGrid grid;

	private ToolbarButton scrollButton;
	private ToolbarButton expandButton;
	private ToolbarButton mineButton;
	private ToolbarButton chainButton;

	private ToolbarButton createButton;
	private ToolbarButton editButton;
	private ToolbarButton deleteButton;
	private ToolbarButton refreshButton;

	public SynmarkWidget(AbstractFrame parent, SynmarkDialog dialog)
	{
		super(parent);

		this.dialog = dialog;
	}

	public void init()
	{
		initComponent();
		initListeners();
	}

	private void initComponent()
	{
		grid = new SynmarkGrid();

		addWidget(new ProgressWidget(" Loading..."));
		addWidget(grid);
		addErrorMessage("Error while loading synmarks");
		addErrorMessage("Error while creating synmark");
		addErrorMessage("Error while editing synmark");
		addErrorMessage("Error while deleting synmark");

		scrollButton = new ToolbarButton("Autoscroll is On");
		addButton(scrollButton);
		scrollButton.setTooltip("Toggles automatic scrolling of synmarks");
		scrollButton.setEnableToggle(true);
		scrollButton.setPressed(true);
		grid.setScrolled(scrollButton.isPressed());

		expandButton = new ToolbarButton("Full view is On");
		addButton(expandButton);
		expandButton.setTooltip("Toggles full view of synmarks");
		expandButton.setEnableToggle(true);
		expandButton.setPressed(true);
		grid.setExpanded(expandButton.isPressed());

		mineButton = new ToolbarButton("Mine only is Off");
		addButton(mineButton);
		mineButton.setTooltip("Toggles display of only my synmarks");
		mineButton.setEnableToggle(true);
		mineButton.setPressed(false);
		grid.setMineOnly(mineButton.isPressed());

		chainButton = new ToolbarButton("Chaining is Off");
		addButton(chainButton);
		chainButton.setTooltip("Toggles control of next synmark");
		chainButton.setEnableToggle(true);
		chainButton.setPressed(false);
		chainButton.setDisabled(true);

		addSpace();

		createButton = new ToolbarButton("Create");
		addButton(createButton);
		createButton.setTooltip("Creates new synmark");

		editButton = new ToolbarButton("Edit");
		addButton(editButton);
		editButton.setTooltip("Edits selected synmark");
		editButton.setDisabled(true);

		deleteButton = new ToolbarButton("Delete");
		addButton(deleteButton);
		deleteButton.setTooltip("Deletes selected synmark");
		deleteButton.setDisabled(true);

		refreshButton = new ToolbarButton("Refresh");
		addButton(refreshButton);
		refreshButton.setTooltip("Reloads synmarks from server");

		setActiveItem(CARD_CONTENT);

		initWidget(getOuterPanel());
	}

	private void initListeners()
	{
		//This sentence make sure the Frame can get the focus when the browser is firstly refreshed
		/*getModel().getPlayer().getFrame().focus();
		getModel().getPlayer().getFrame().addListener(new ContainerListenerAdapter()
		{
			@Override
			public void onRender(Component component)
			{
				KeyMapConfig kmc = new KeyMapConfig() {
					   {
						   setCtrl(true);
						   this.setKey(13);
						   setKeyListener(new KeyListener()
						   {
							   public void onKey(int key, EventObject e)
							   {
								   showDialog();
							   }
						   });
					   }
				   };
				   component.getEl().addKeyMap(kmc);
			}
		});*/

		//Ctrl+Alt+C
		KeyConfig configCreate = new KeyConfig(new int[]{KeyConfig.KEY_ALT,KeyConfig.KEY_CTRL, KeyConfig.KEY_C });
		KeyboardActionManager.addKeyboardListeners(this,configCreate, new KeyboardActionListener()
		{
			public void onKeysPressed(KeyPressedEvent keyPressedEvent)
			{
				if(createButton.isDisabled() == false)
					showDialog();
			}
		});

		//Ctrl+Alt+E
		KeyConfig configEdit = new KeyConfig(new int[]{KeyConfig.KEY_ALT,KeyConfig.KEY_CTRL, KeyConfig.KEY_E });
		KeyboardActionManager.addKeyboardListeners(this,configEdit, new KeyboardActionListener()
		{
			public void onKeysPressed(KeyPressedEvent keyPressedEvent)
			{
				if(editButton.isDisabled() == false)
				{
					Record selectedSynmark = grid.getSelectionModel().getSelected();
					if (selectedSynmark != null)
					{
						dialog.show
							( selectedSynmark.getAsString(SynmarkGrid.COLUMN_ID)
							, selectedSynmark.getAsString(SynmarkGrid.COLUMN_START)
							, selectedSynmark.getAsString(SynmarkGrid.COLUMN_END)
							, selectedSynmark.getAsString(SynmarkGrid.COLUMN_TITLE)
							, selectedSynmark.getAsString(SynmarkGrid.COLUMN_NOTE)
							, selectedSynmark.getAsString(SynmarkGrid.COLUMN_TAGS)
							, selectedSynmark.getAsString(SynmarkGrid.COLUMN_NEXT)
							, selectedSynmark.getAsString(SynmarkGrid.COLUMN_FLAG)
							);
					}
				}
			}
		});

		
		
		getModel().getMultimediaModel().addListener(new DataModelListener()
		{
			public String getName()
			{
				return SynmarkWidget.this.getName();
			}

			public void started(DataModelEvent event)
			{
				setDisabled();
			}

			public void finished(DataModelEvent event)
			{
				setEnabled(true);
			}

			public void failed(DataModelEvent event)
			{
				// Do nothing.
			}
		});

		getModel().getSynmarkModel().addListener(new DataModelListener()
		{
			public String getName()
			{
				return SynmarkWidget.this.getName();
			}

			public void started(DataModelEvent event)
			{
				setDisabled();

				setActiveItem(CARD_LOADING);
			}

			public void finished(DataModelEvent event)
			{
				SynmarkModel model = (SynmarkModel) event.getSource();

				setEnabled(true);

				grid.refresh(model.getSynmarks());
				setActiveItem(CARD_CONTENT);
				grid.update(getController().getSynmark(getController().getPosition()));
			}

			public void failed(DataModelEvent event)
			{
				setEnabled(false);

				switch (event.getOperation())
				{
					case CREATE: setActiveItem(CARD_ERROR_CREATING); break;
					case EDIT: setActiveItem(CARD_ERROR_EDITING); break;
					case DELETE: setActiveItem(CARD_ERROR_DELETING); break;
					default: setActiveItem(CARD_ERROR_LOADING); break;
				}
			}
		});

		getController().addMultimediaListener(new MultimediaListener()
		{
			public void positionChanged(MultimediaEvent event)
			{
				grid.update(event.getSource().getSynmark(event.getCurrentPosition()));
			}
		});

		getController().addChainListener(new ChainListener()
		{
			public void started(ChainEvent event)
			{
				update(true);
			}

			public void finished(ChainEvent event)
			{
				update(false);
			}

			private void update(boolean chained)
			{
				if (chainButton.isPressed() != chained)
					chainButton.setPressed(chained);
			}
		});

		scrollButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onToggle(Button button, boolean pressed)
			{
				if (pressed)
					scrollButton.setText("Autoscroll is On");
				else
					scrollButton.setText("Autoscroll is Off");

				grid.setScrolled(pressed);

				if (pressed)
					grid.update(getController().getSynmark(getController().getPosition()));
			}
		});

		expandButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onToggle(Button button, boolean pressed)
			{
				if (pressed)
					expandButton.setText("Full view is On");
				else
					expandButton.setText("Full view is Off");

				grid.setExpanded(pressed);
				grid.getView().refresh();
			}
		});

		mineButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onToggle(Button button, boolean pressed)
			{
				if (pressed)
					mineButton.setText("Mine only is On");
				else
					mineButton.setText("Mine only is Off");

				grid.setMineOnly(pressed);
				grid.refresh(getModel().getSynmarkModel().getSynmarks());
			}
		});

		chainButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onToggle(Button button, boolean pressed)
			{
				if (pressed)
					chainButton.setText("Chaining is On");
				else
					chainButton.setText("Chaining is Off");

				getController().setChained(pressed);

				if (getController().isChained())
				{
					getController().play();
				}

			}
		});

		createButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				showDialog();
			}
		});

		editButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				Record selectedSynmark = grid.getSelectionModel().getSelected();
				if (selectedSynmark != null)
				{
					dialog.show
						( selectedSynmark.getAsString(SynmarkGrid.COLUMN_ID)
						, selectedSynmark.getAsString(SynmarkGrid.COLUMN_START)
						, selectedSynmark.getAsString(SynmarkGrid.COLUMN_END)
						, selectedSynmark.getAsString(SynmarkGrid.COLUMN_TITLE)
						, selectedSynmark.getAsString(SynmarkGrid.COLUMN_NOTE)
						, selectedSynmark.getAsString(SynmarkGrid.COLUMN_TAGS)
						, selectedSynmark.getAsString(SynmarkGrid.COLUMN_NEXT)
						, selectedSynmark.getAsString(SynmarkGrid.COLUMN_FLAG));
				}
			}
		});




		deleteButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				boolean confirm = Window.confirm("Are you sure you want to delete this synmark?");
				if(confirm)
				{
					Record selectedSynmark = grid.getSelectionModel().getSelected();
					if (selectedSynmark != null)
						getModel().getSynmarkModel().delete(selectedSynmark.getAsString(SynmarkGrid.COLUMN_ID));
				}
			}
		});

		refreshButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				getModel().getSynmarkModel().refresh();
			}
		});

		grid.addGridRowListener(new GridRowListenerAdapter()
		{
			@Override
			public void onRowClick(GridPanel grid, int rowIndex, EventObject event)
			{
				String synmarkId = grid.getStore().getAt(rowIndex).getAsString(SynmarkGrid.COLUMN_ID);
				SynmarkData synmark = getModel().getSynmarkModel().getSynmark(synmarkId);
				//if(scrollButton.isPressed())
					getController().setPosition(synmark);
			}
		});

		grid.getSelectionModel().addListener(new RowSelectionListenerAdapter()
		{
			@Override
			public void onSelectionChange(RowSelectionModel model)
			{
				if (model.hasSelection())
				{
					Record selectedRow = model.getSelected();
					String synmarkId = selectedRow.getAsString(SynmarkGrid.COLUMN_ID);
					SynmarkData synmark = getModel().getSynmarkModel().getSynmark(synmarkId);

					if (!chainButton.isPressed())
						chainButton.setDisabled(synmark.getEnd() == null);

					editButton.setDisabled(!synmark.canEdit());
					deleteButton.setDisabled(!synmark.canDelete());
				}
				else
				{
					if (!chainButton.isPressed())
						chainButton.setDisabled(true);

					editButton.setDisabled(true);
					deleteButton.setDisabled(true);
				}
			}
		});
	}

	private void setDisabled()
	{
		scrollButton.setDisabled(true);
		expandButton.setDisabled(true);
		mineButton.setDisabled(true);
		chainButton.setDisabled(true);
		createButton.setDisabled(true);
		editButton.setDisabled(true);
		deleteButton.setDisabled(true);
		refreshButton.setDisabled(true);
	}

	private void setEnabled(boolean available)

	{
		if (getModel().getMultimediaModel().getMultimedia() != null)
		{
			scrollButton.setDisabled(!available);
			expandButton.setDisabled(!available);
			mineButton.setDisabled(!available);
			if(getModel().getMultimediaModel().getMultimedia().canCreateSynmark())
			{
				createButton.setDisabled(!available);
			}
			else
			{
				createButton.setDisabled(true);
			}
			refreshButton.setDisabled(false);
		}
	}

	private void showDialog()
	{
		TranscriptSelection selection = TranscriptSelection.getSelection();
		if (!selection.isEmpty())
		{
			TranscriptDataSimple transcript = getModel().getTranscriptModel().getTranscriptSimple(0);

			int start = transcript.getItem(selection.getStartIndex()).getStart();
			int end = transcript.getItem(selection.getEndIndex()).getEnd();

			dialog.show(start, end);
		}
		else
			dialog.show(getController().getPosition());

	}
}
