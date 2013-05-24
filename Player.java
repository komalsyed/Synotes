package org.synote.player.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.gwtext.client.widgets.Viewport;

public class Player implements EntryPoint
{
	private PlayerServiceAsync service;
	private ProfileManager profile;
	private PlayerModel model;
	private MultimediaController controller;
	private AbstractFrame frame;

	public Player()
	{
		// Do nothing.
	}

	public void onModuleLoad()
	{
		Logger.init();

		service = (PlayerServiceAsync) GWT.create(PlayerService.class);
		((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "rpc");

		profile = new ProfileManager(this);
		profile.init();

		model = new PlayerModel(this, getMultimediaId());
		model.init();

		MediaPlayer mediaPlayer = MediaPlayerFactory.createMediaPlayer(Utils.getMultimediaURL());
		controller = new MultimediaController(this, mediaPlayer);
		controller.init();

//		frame = new PanelFrame(this);
		frame = new WindowFrame(this); //CHANGE MADE BY FAREESA
		frame.init();

		new Viewport(frame);

		model.refresh();

		//start keyboard listener
		KeyboardActionManager.init();
	}

	public PlayerServiceAsync getService()
	{
		return service;
	}

	public ProfileManager getProfile()
	{
		return profile;
	}

	public PlayerModel getModel()
	{
		return model;
	}

	public MultimediaController getController()
	{
		return controller;
	}

	public AbstractFrame getFrame()
	{
		return frame;
	}

	private native String getMultimediaId()
	/*-{
		return $wnd.getMultimediaId();
	}-*/;
}
