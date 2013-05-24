package org.synote.player.client;

import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.layout.FitLayout;

public class WindowFrame extends AbstractFrame
{
	//measured by percentage
	/*public static double MEDIAPLAYER_LEFT = 0.025;
	public static double MEDIAPLAYER_TOP = 0.025;
	public static double MEDIAPLAYER_WIDTH = 0.3;
	public static double MEDIAPLAYER_HEIGHT = 0.45;
	public static double TRANSCRIPT_LEFT = 0.025;
	public static double TRANSCRIPT_TOP = 0.525;
	public static double TRANSCRIPT_WIDTH = 0.3;
	public static double TRANSCRIPT_HEIGHT = 0.45;
	public static double SYNMARKS_LEFT = 0.375;
	public static double SYNMARKS_TOP = 0.025;
	public static double SYNMARKS_WIDTH = 0.6;
	public static double SYNMARKS_HEIGHT = 0.6;
	public static double PRESENTATIONS_LEFT = 0.375;
	public static double PRESENTATIONS_TOP = 0.675;
	public static double PRESENTATIONS_WIDTH = 0.6;
	public static double PRESENTATIONS_HEIGHT = 0.3;*/

	//Fixed margins
	public static int SPACE_LEFT = 20;
	public static int SPACE_TOP = 20;
	public static int SPACE_BOTTOM = 20;
	public static int SPACE_RIGHT = 20;
	public static int SPACE_MID_VERTICLE = 20;
	public static int SPACE_MID_HORIZONTAL = 20;

	public static double MEDIAPLAYER_WIDTH = 0.33;
	public static double MEDIAPLAYER_HEIGHT = 0.5;
	
	public static double TRANSCRIPT_WIDTH = 0.33;
	public static double TRANSCRIPT_HEIGHT = 0.5;

	public static double SYNMARKS_WIDTH = 0.67;
	public static double SYNMARKS_HEIGHT = 0.67;

	public static double PRESENTATIONS_WIDTH = 0.67;
	public static double PRESENTATIONS_HEIGHT = 0.33;

	private Window mediaPlayerWindow;
	private Window transcriptWindow;
	private Window presentationWindow;
	private Window synmarkWindow;
	
	////////////////CHANGE MADE BY F&K
	private Window conceptMapWindow;

	public WindowFrame(Player parent)
	{
		super(parent);
	}

	@Override
	public void init()
	{
		setLayout(new FitLayout());
		SynmarkDialog synmarkDialog = new SynmarkDialog(this);
		synmarkDialog.init();

		PresentationDialog presentationDialog = new PresentationDialog(this);
		presentationDialog.init();
		
		///////////////////////////////CHANGE BY F&K
		//PresentationDialog conceptMapDialog = new PresentationDialog(this);
		//conceptMapDialog.init();

		MultimediaWidget multimediaWidget = new MultimediaWidget(this);
		multimediaWidget.init();
		
		/*
		TranscriptWidget transcriptWidget = new TranscriptWidget(this);
		transcriptWidget.init();
		*/
		
		PresentationWidget presentationWidget = new PresentationWidget(this,presentationDialog);
		presentationWidget.init();
		
		///////////////////////////////CHANGE BY F&K
		PresentationWidget conceptMap = new PresentationWidget(this,presentationDialog);
		conceptMap.init();
		
		
		SynmarkWidget synmarkWidget = new SynmarkWidget(this, synmarkDialog);
		synmarkWidget.init();
		

		int windowWidth = com.google.gwt.user.client.Window.getClientWidth();
		int windowHeight = com.google.gwt.user.client.Window.getClientHeight();

		//get the total width and height for content not including the margins and space
		int contentWidth = windowWidth - SPACE_LEFT - SPACE_RIGHT -SPACE_MID_VERTICLE;
		int contentHeight = windowHeight- SPACE_BOTTOM - SPACE_TOP - SPACE_MID_HORIZONTAL;

		mediaPlayerWindow = createWindow("Multimedia",
				//SPACE_LEFT, SPACE_TOP, 
				690, 290,
				(int)(contentWidth*MEDIAPLAYER_WIDTH) + 30,
				(int)(contentHeight*MEDIAPLAYER_HEIGHT) + 50,
				multimediaWidget);
				
				/*
		transcriptWindow = createWindow("Transcript",
				SPACE_LEFT,
				(int)(SPACE_TOP+SPACE_MID_HORIZONTAL+contentHeight*MEDIAPLAYER_HEIGHT),
				(int)(contentWidth*TRANSCRIPT_WIDTH),
				(int)(contentHeight*TRANSCRIPT_HEIGHT),
				transcriptWidget);*/
		synmarkWindow = createWindow("Synmarks",
				(int)(SPACE_LEFT + SPACE_MID_VERTICLE + contentWidth*MEDIAPLAYER_WIDTH),
				SPACE_TOP,
				(int)(contentWidth*SYNMARKS_WIDTH),
				(int)(contentHeight*SYNMARKS_HEIGHT),
				synmarkWidget);

		presentationWindow = createWindow("Presentation",
				//(int)(SPACE_LEFT+SPACE_MID_VERTICLE+contentWidth*TRANSCRIPT_WIDTH),
				//(int)(SPACE_TOP+SPACE_MID_HORIZONTAL + contentHeight*SYNMARKS_HEIGHT),
				0,25,
				//(int)(contentWidth*PRESENTATIONS_WIDTH),
				//(int)(contentHeight*PRESENTATIONS_HEIGHT),
				690,590,
				presentationWidget);
				
		///////////////////////////////CHANGE BY F&K
		conceptMapWindow = createWindow("Concept Map",
				//(int)(SPACE_LEFT+SPACE_MID_VERTICLE+contentWidth*TRANSCRIPT_WIDTH),
				//(int)(SPACE_TOP+SPACE_MID_HORIZONTAL + contentHeight*SYNMARKS_HEIGHT),
				690,25,
				//(int)(contentWidth*PRESENTATIONS_WIDTH),
				//(int)(contentHeight*PRESENTATIONS_HEIGHT),
				300,265,
				conceptMap);


		mediaPlayerWindow.show();
		presentationWindow.show();
		synmarkWindow.show();
		
		//CHANGE MADE BY F&K
		//conceptMapWindow.show();
	}

	private Window createWindow(String title, int left, int top, int width, int height, AbstractWidget widget)
	{
		Window window = new Window();
		window.setTitle(title);
		
		
		///////////CHANGE MADE BY F&K
		window.setMaximizable(true);
		window.setMinimizable(true);
		window.setClosable(true);
		
		window.setPosition(left, top);
		window.setSize(width, height);
		window.setPlain(true);
		window.setLayout(new FitLayout());
		window.add(widget);

		return window;
	}

	/*private void windowResize(int windowWidth, int windowHeight,
			Window mediaPlayerWindow,
			Window transcriptWindow,
			Window presentationWindow,
			Window synmarkWindow)
	{
		//Percentage window resize
		mediaPlayerWindow.setPosition((int)(windowWidth*MEDIAPLAYER_LEFT), (int)(windowHeight*MEDIAPLAYER_TOP));
		mediaPlayerWindow.setSize((int)(windowWidth*MEDIAPLAYER_WIDTH), (int)(windowHeight*MEDIAPLAYER_HEIGHT));

		transcriptWindow.setPosition((int)(windowWidth*TRANSCRIPT_LEFT), (int)(windowHeight*TRANSCRIPT_TOP));
		transcriptWindow.setSize((int)(windowWidth*TRANSCRIPT_WIDTH), (int)(windowHeight*TRANSCRIPT_HEIGHT));

		presentationWindow.setPosition((int)(windowWidth*PRESENTATIONS_LEFT), (int)(windowHeight*PRESENTATIONS_TOP));
		presentationWindow.setSize((int)(windowWidth*PRESENTATIONS_WIDTH), (int)(windowHeight*PRESENTATIONS_HEIGHT));

		synmarkWindow.setPosition((int)(windowWidth*SYNMARKS_LEFT), (int)(windowHeight*SYNMARKS_TOP));
		synmarkWindow.setSize((int)(windowWidth*SYNMARKS_WIDTH), (int)(windowHeight*SYNMARKS_HEIGHT));

		mediaPlayerWindow.show();
		transcriptWindow.show();
		presentationWindow.show();
		synmarkWindow.show();
	}*/
}
