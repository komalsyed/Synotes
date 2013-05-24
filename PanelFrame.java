package org.synote.player.client;

import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

//added by Fareesa
import com.gwtext.client.dd.DD; 
import com.gwtext.client.widgets.Viewport;   

public class PanelFrame extends AbstractFrame
{
	private static final String PROFILE_ENTRY_NAME_PRESENTATION_HEIGHT = "PRESENTATION_HEIGHT";
	private static final int PROFILE_ENTRY_DEFAULT_VALUE_PRESENTATION_HEIGHT = 180;

	Panel presentationPanel;

	public PanelFrame(Player parent)
	{
		super(parent);
	}

	@Override
	public void init()
	{
		initComponents();
		initListeners();
	}

	private void initComponents()
	{
		setLayout(new BorderLayout());

		//SynmarkDialog synmarkDialog = new SynmarkDialog(this);
		//synmarkDialog.init();

		PresentationDialog presentationDialog = new PresentationDialog(this);
		presentationDialog.init();

		MultimediaWidget multimediaWidget = new MultimediaWidget(this);
		multimediaWidget.init();
		
		
		//TranscriptWidget transcriptWidget = new TranscriptWidget(this);
		//transcriptWidget.init();
		
		
		PresentationWidget presentationWidget = new PresentationWidget(this, presentationDialog);
		presentationWidget.init();
		//SynmarkWidget synmarkWidget = new SynmarkWidget(this, synmarkDialog);
		//synmarkWidget.init();


		

		//Right
		Panel rightPanel = new Panel();
		//rightPanel.setTitle("Synmarks");
		rightPanel.setWidth("70%");
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBorder(false);
		BorderLayoutData rightLayout = new BorderLayoutData(RegionPosition.WEST);
		rightLayout.setSplit(true);
		add(rightPanel, rightLayout);

		// Right - Top
		
		Panel synmarkPanel = new Panel();
		synmarkPanel.setTitle("Synmarks");
		synmarkPanel.setLayout(new FitLayout());
		
		BorderLayoutData synmarkLayout = new BorderLayoutData(RegionPosition.NORTH);
		synmarkLayout.setSplit(true);
		//synmarkPanel.add(synmarkWidget);
		//rightPanel.add(synmarkPanel, synmarkLayout);
		

		// Right - Bottom
		presentationPanel = new Panel();
		presentationPanel.setTitle("Presentation");
		presentationPanel.setLayout(new FitLayout());
		int height = getProfile().getValue(PROFILE_ENTRY_NAME_PRESENTATION_HEIGHT, PROFILE_ENTRY_DEFAULT_VALUE_PRESENTATION_HEIGHT);
		presentationPanel.setHeight(height);
		presentationPanel.add(presentationWidget);
		presentationPanel.setCollapsible(true);
		
		BorderLayoutData presentationLayout = new BorderLayoutData(RegionPosition.CENTER);
		presentationLayout.setSplit(true);
		rightPanel.add(presentationPanel, presentationLayout);
		
		
		
		
		
		// Left
		Panel leftPanel = new Panel();
		//leftPanel.setTitle("Multimedia");
		leftPanel.setWidth("30%");
		leftPanel.setBorder(false);
//		leftPanel.setCollapsible(true);
		leftPanel.setLayout(new BorderLayout());

		BorderLayoutData leftLayout = new BorderLayoutData(RegionPosition.CENTER);
		leftLayout.setSplit(true);
		add(leftPanel, leftLayout);

		// Left - Top
		Panel multimediaPanel = new Panel();
		multimediaPanel.setTitle("Multimedia");
		multimediaPanel.setBorder(true);
		multimediaPanel.setLayout(new FitLayout());
		multimediaPanel.setHeight(300);
		multimediaPanel.add(multimediaWidget);
		
		DD dd = new DD(multimediaPanel); //added by Fareesa 
		
		BorderLayoutData multimediaLayout = new BorderLayoutData(RegionPosition.SOUTH);
		//multimediaLayout.setSplit(true);
	   leftPanel.add(multimediaPanel, multimediaLayout);
	   //add(multimediaPanel, multimediaLayout);
		
		
		// Left - Bottom
	
		Panel transcriptPanel = new Panel();
		
		transcriptPanel.setTitle("Transcript");
		transcriptPanel.setLayout(new FitLayout());
		//transcriptPanel.add(transcriptWidget);

		BorderLayoutData transcriptLayout = new BorderLayoutData(RegionPosition.CENTER);
		//transcriptLayout.setSplit(true);
		//leftPanel.add(transcriptPanel, transcriptLayout);
		
		
		
	}

	private void initListeners()
	{
		getModel().getPresentationModel().addListener(new DataModelListener()
		{
			public String getName()
			{
				return "PanelFrame";
			}

			public void started(DataModelEvent event)
			{
				// Do nothing.
			}

			public void finished(DataModelEvent event)
			{
				PresentationModel model = (PresentationModel) event.getSource();

				if (model.getPresentations().length == 0)
				{
					presentationPanel.collapse();
				}
			}

			public void failed(DataModelEvent event)
			{
				// Do nothing.
			}
		});

		presentationPanel.addListener(new PanelListenerAdapter()
		{
			public void onBodyResize(Panel panel, String width, String height)
			{
				getProfile().storeValue(PROFILE_ENTRY_NAME_PRESENTATION_HEIGHT, panel.getHeight());
			}
		});
	}
}
