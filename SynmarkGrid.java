package org.synote.player.client;

import java.util.ArrayList;
import java.util.List;

import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.RowParams;
import com.gwtext.client.widgets.grid.RowSelectionModel;

public class SynmarkGrid extends GridPanel
{
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_START = "start";
	public static final String COLUMN_END = "end";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_NOTE = "note";
	public static final String COLUMN_TAGS = "tags";
	public static final String COLUMN_NEXT = "next";
	public static final String COLUMN_OWNER = "owner";
	
	///////////////////////////////////////////////////////
	public static final String COLUMN_FLAG = "flag";

	private boolean scrolled;
	private boolean expanded;
	private boolean mineOnly;

	public SynmarkGrid()
	{
		init();
	}

	private void init()
	{
		Store store = new Store(new MemoryProxy(new Object[][]{{}}), new ArrayReader(createRecordDef()));
		store.load();
		setStore(store);

		GridView view = new GridView()
		{
			@Override
			public String getRowClass(Record record, int index, RowParams rowParams, Store store)
			{
				if (expanded)
				{
					String note = (record.getAsObject(COLUMN_NOTE) != null) ? record.getAsString(COLUMN_NOTE) : "&nbsp;";
					rowParams.setBody(Format.format("<p style='margin: 5px'>{0}</p>", note));

					return "x-grid3-row-expanded";
				}
				else
					return "x-grid3-row-collapsed";
			}
		};

		view.setForceFit(true);
		view.setEnableRowBody(true);
		setView(view);

		setColumnModel(createColumnModel());

		setSelectionModel(new RowSelectionModel(true));

		setBorder(false);
		setStripeRows(true);
		setAutoExpandColumn(COLUMN_NOTE);
	}

	private RecordDef createRecordDef()
	{
		return new RecordDef (
			new FieldDef[] {
				new IntegerFieldDef(COLUMN_ID),
				new IntegerFieldDef(COLUMN_START),
				new IntegerFieldDef(COLUMN_END),
				new StringFieldDef(COLUMN_TITLE),
				new StringFieldDef(COLUMN_NOTE),
				new StringFieldDef(COLUMN_TAGS),
				new IntegerFieldDef(COLUMN_NEXT),
				new StringFieldDef(COLUMN_OWNER),
				new StringFieldDef(COLUMN_FLAG)
			}
		);
	}

	private ColumnModel createColumnModel()
	{
		ColumnModel columnModel = new ColumnModel(createColumnConfig());

		columnModel.setRenderer(COLUMN_START, new Renderer()
		{
			public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store)
			{
				if (value == null || !(value instanceof Integer))
					return null;

				return TimeFormat.getInstance().toString((Integer) value);
			}
		});

		columnModel.setRenderer(COLUMN_END, new Renderer()
		{
			public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store)
			{
				if (value == null || !(value instanceof Integer))
					return null;

				return TimeFormat.getInstance().toString((Integer) value);
			}
		});

		columnModel.setRenderer(COLUMN_NOTE, new Renderer()
		{
			public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store)
			{
				if (value == null || expanded)
					return null;

				return value.toString();
			}
		});

		return columnModel;
	}

	private ColumnConfig[] createColumnConfig()
	{
		ColumnConfig id = new ColumnConfig();
		id.setId(COLUMN_ID);
		id.setHeader("ID");
		id.setDataIndex(COLUMN_ID);
		id.setSortable(true);
		id.setResizable(true);
		id.setHidden(true);

		ColumnConfig start = new ColumnConfig();
		start.setId(COLUMN_START);
		start.setHeader("Start");
		start.setDataIndex(COLUMN_START);
		start.setSortable(true);
		start.setResizable(true);
		start.setWidth(30);

		ColumnConfig end = new ColumnConfig();
		end.setId(COLUMN_END);
		end.setHeader("End");
		end.setDataIndex(COLUMN_END);
		end.setSortable(true);
		end.setResizable(true);
		end.setWidth(30);

		ColumnConfig title = new ColumnConfig();
		title.setId(COLUMN_TITLE);
		title.setHeader("Title");
		title.setDataIndex(COLUMN_TITLE);
		title.setSortable(true);
		title.setResizable(true);

		ColumnConfig note = new ColumnConfig();
		note.setId(COLUMN_NOTE);
		note.setHeader("Note");
		note.setDataIndex(COLUMN_NOTE);
		note.setSortable(true);
		note.setResizable(true);

		ColumnConfig tags = new ColumnConfig();
		tags.setId(COLUMN_TAGS);
		tags.setHeader("Tags");
		tags.setDataIndex(COLUMN_TAGS);
		tags.setSortable(true);
		tags.setResizable(true);

		ColumnConfig next = new ColumnConfig();
		next.setId(COLUMN_NEXT);
		next.setHeader("Next");
		next.setDataIndex(COLUMN_NEXT);
		next.setSortable(true);
		next.setResizable(true);
		next.setWidth(30);

		ColumnConfig owner = new ColumnConfig();
		owner.setId(COLUMN_OWNER);
		owner.setHeader("Owner");
		owner.setDataIndex(COLUMN_OWNER);
		owner.setSortable(true);
		owner.setResizable(true);
		owner.setWidth(50);
		
		//////////////////////////////////////////////////////////
		ColumnConfig flag = new ColumnConfig();
		flag.setId(COLUMN_FLAG);
		flag.setHeader("Flag");
		flag.setDataIndex(COLUMN_FLAG);
		flag.setSortable(true);
		flag.setResizable(true);
		
////////////////////////////////////////////////////////////////
		return new ColumnConfig[] {id, start, end, title, note, tags, next, owner, flag};
	}

	public boolean isScrolled()
	{
		return scrolled;
	}

	public void setScrolled(boolean scrolled)
	{
		this.scrolled = scrolled;
	}

	public boolean isExpanded()
	{
		return expanded;
	}

	public void setExpanded(boolean expanded)
	{
		this.expanded = expanded;
	}

	public boolean isMineOnly()
	{
		return mineOnly;
	}

	public void setMineOnly(boolean mineOnly)
	{
		this.mineOnly = mineOnly;
	}

	public void refresh(List<SynmarkData> synmarks)
	{
		List<Object[]> list = new ArrayList<Object[]>();

		for (int i = 0; i < synmarks.size(); i++)
		{
			SynmarkData synmark = synmarks.get(i);

			String loggedInUserId = Utils.getLoggedInUserId();
			if (!mineOnly || (mineOnly && synmark.getOwner().getId().equals(loggedInUserId)))
			{
				String tags = "";
				for (int j = 0; j < synmark.getTags().length; j++)
				{
					tags += synmark.getTag(j);
					if (j < synmark.getTags().length - 1)
						tags += ",";
				}

				String owner = synmark.getOwner().getFirstName();
				if (synmark.getOwner().getLastName().length() > 0)
					owner += " " + synmark.getOwner().getLastName().charAt(0) + ".";

					//////////////////////////////////////////////////////////////
				list.add(new Object[] {
					Integer.parseInt(synmark.getId()),
					new Integer(synmark.getStart()),
					synmark.getEnd(),
					synmark.getTitle(),
					synmark.getNote(),
					tags,
					(synmark.getNext() != null) ? Integer.parseInt(synmark.getNext()) : null,
					owner,
					synmark.getFlag(),
				});
			}
		}

		Object[][] array = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++)
			array[i] = list.get(i);

		getStore().setDataProxy(new MemoryProxy(array));
		getStore().load();
	}

	public void update(SynmarkData synmark)
	{
		if (synmark != null)
		{
			for (int i = 0; i < getStore().getTotalCount(); i++)
			{
				if (getStore().getAt(i).getAsString(COLUMN_ID).equals(synmark.getId()))
				{
					getSelectionModel().selectRow(i);
					if (scrolled)
						scrollToVisible(getView(), i);
					break;
				}
			}
		}
		else
			getSelectionModel().clearSelections();
	}

	private native void scrollToVisible(GridView gridView, int rowIndex)
	/*-{
		var view = gridView.@com.gwtext.client.core.JsObject::getJsObj()();
		view.ensureVisible(rowIndex, 0, false);
	}-*/;
}
