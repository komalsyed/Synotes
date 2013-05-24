package org.synote.player.client;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarTextItem;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

////////////////////////////////////////////////////////////////
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.layout.BorderLayout;   
import com.gwtext.client.widgets.layout.BorderLayoutData; 
import com.gwtext.client.core.RegionPosition;  
import com.gwtext.client.widgets.form.Label;

/////////////////////////////////////////
import java.util.ArrayList;
import java.util.List;

//////////////////////////////////////////
import com.google.gwt.user.client.ui.Image;

///////////////////////////////////////////
import com.google.gwt.core.client.EntryPoint;   
import com.gwtext.client.core.*;   
import com.gwtext.client.data.*;   
import com.gwtext.client.util.Format;   
import com.gwtext.client.widgets.*;   
import com.gwtext.client.widgets.form.*;   
import com.gwtext.client.widgets.grid.*;
import com.gwtext.client.widgets.layout.FitLayout;   


public class AbstractFrame extends Panel
{
	private Player player;

	public AbstractFrame(Player parent)
	{
		//super();
		this.player = parent;
		initTopToolbar();
	}

	public void init()
	{
		// Do nothing.
	}

	private void initTopToolbar()
	{
		Toolbar playerTopToolbar = new Toolbar();

		ToolbarButton homeButton = new ToolbarButton("Home");
		homeButton.setTooltip("Go to home page");
		homeButton.setIconCls("home-icon");
		homeButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				Utils.redirectLocation("synote/");
			}
		});
		playerTopToolbar.addButton(homeButton);

		ToolbarButton listButton = new ToolbarButton("Recordings");
		listButton.setTooltip("Go to recording list");
		listButton.setIconCls("multimediaList-icon");
		listButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				Utils.redirectLocation("synote/multimediaResource/list");
			}
		});
		playerTopToolbar.addButton(listButton);

		ToolbarButton printButton  = new ToolbarButton("Print");
		printButton.setTooltip("Display text and images for printing");
		printButton.setIconCls("print-icon");
		printButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				Utils.redirectLocation("synote/recording/print/"+Utils.getMultimediaId());
			}
		});
		playerTopToolbar.addButton(printButton);

		ToolbarButton helpButton = new ToolbarButton("Help");
		helpButton.setTooltip("Synote guide");
		helpButton.setIconCls("help-icon");
		helpButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				Utils.openNewWindow("synote/guide.pdf", "Synote Guide");
			}
		});
		playerTopToolbar.addButton(helpButton);

		
		//////////////////CHANGE MADE BY FAREESA////////////////////////
		ToolbarButton surveyButton = new ToolbarButton("Survey");
		surveyButton.setTooltip("Please help us decide the most effective interface");
		surveyButton.setIconCls("help-icon");
		surveyButton.addListener(new ButtonListenerAdapter()
		{
			@Override
			public void onClick(Button button, EventObject event)
			{
				//Utils.redirectLocation("http://www.makesurvey.net/cgi-bin/survey.dll/5C753E994E904CCDB37745C88CA44EEF");
				Utils.openNewWindow("synote/survey.html", "Survey");
			}
		});
		playerTopToolbar.addButton(surveyButton);
		//////////////////////////////////////////////////////////////
		
		//////////////////CHANGE MADE BY FAREESA////////////////////////
		ToolbarButton extraButton = new ToolbarButton("View Timeline");
		extraButton.setTooltip("Open new frame!!!");
		extraButton.setIconCls("help-icon");
		
		//Label topTitle = new Label("Welcome to Timeline: " + Utils.getLoggedInUserName() + " !" );
		//BorderLayoutData northData = new BorderLayoutData(RegionPosition.NORTH);   
        //northData.setMargins(3, 0, 3, 3); 
		
		final Window window = new Window(); 	
        window.setTitle("TIMELINE WINDOW");   
        window.setClosable(true);   
        window.setWidth(500);   
        window.setHeight(550);   
        window.setPlain(true);   
        window.setLayout(new BorderLayout());   
        //window.add(topTitle, northData);
		
		

        window.setCloseAction(Window.HIDE); 
		

		
		///////////////////////TIME LINE!!!
		extraButton.addListener(new ButtonListenerAdapter()
		{
			//@Override
			public void onClick(Button button, EventObject event)
			{
				int totalFlags = 0;
				int totalQuestions = 0;
				int totalProblems = 0;
				int totalComments = 0;
				int totalSolutions = 0;
				
				System.out.println( Utils.getLoggedInUserName() );
				
				SynmarkModel sm = getModel().getSynmarkModel();
				List<SynmarkData> synmarks = sm.getSynmarks();
				
				int firstSynmarkStart =  synmarks.get(0).getStart();
				int lastSynmarkStart =  synmarks.get(synmarks.size()-1).getStart();

				for (int i = 0; i < synmarks.size(); i++){
					SynmarkData synmark = synmarks.get(i);
					
					//System.out.println( synmark.getFlag() );
					
					if(synmark.getFlag() != null){
					
						totalFlags++;
						if(synmark.getFlag().equals("Question"))
							totalQuestions++;
						else if(synmark.getFlag().equals("Problem Area"))
							totalProblems++;
						else if(synmark.getFlag().equals("Comment"))
							totalComments++;
					
						//colour blue from start to end
						//System.out.println( synmark.getStart() + " "+ synmark.getEnd());
					}
					
				}
				
				totalSolutions = totalFlags - totalQuestions - totalProblems - totalComments;
				
				Label stats = new Label("Total flags: " + totalFlags +
								"\nTotal problems: " + totalProblems +
								"\nTotal questions: " + totalQuestions +
								"\nTotal solutions: " + totalSolutions +
								"\nTotal comments: " + totalComments);
								
								
								
		
				BorderLayoutData northData = new BorderLayoutData(RegionPosition.NORTH); 
				window.add(stats, northData);
		
				BorderLayoutData centerData = new BorderLayoutData(RegionPosition.CENTER);   
				centerData.setMargins(3, 3, 3, 3);
				
				
//////////////////////////////////////////////////////////////////////////
//REAL TIMELINE CODE STARTS HERE
				
				

				totalQuestions = 0;
				totalProblems = 0;
				totalComments = 0;
				totalSolutions = 0;
				
				String numOfComments = "";
				String numOfQuestions = "";
				String numOfSolutions = "";
				String numOfProblems = "";
				
				int start=firstSynmarkStart;
				int end= lastSynmarkStart;
				int numOfIntervals = 5;
				int intervals = (lastSynmarkStart - firstSynmarkStart)/numOfIntervals;
				
				System.out.println("START!!!" + start);
				System.out.println("end " + end);
				System.out.println("intervals " + intervals);
				
				int interval[] = new int[numOfIntervals];
				int que[] = new int[numOfIntervals];
				int com[] = new int[numOfIntervals];
				int pro[] = new int[numOfIntervals];
				int sol[] = new int[numOfIntervals];
				
				for(int j=0;j<numOfIntervals;j++){
				
					interval[j]= start + (intervals*j);
					que[j]=0;
					com[j]=0;
					pro[j]=0;
					sol[j]=0;								
				}
				
				System.out.println("EELLO1!!!");
				
				
				for(int n=0;n<numOfIntervals;n++){
					for (int i = 0; i < synmarks.size(); i++){
				
						SynmarkData synmark = synmarks.get(i);
						
						System.out.println(synmark.getFlag());
						System.out.println(synmark.getStart());
						System.out.println(interval[n]);
						System.out.println(interval[n]+intervals);
						//if((synmark.getFlag() != null) && ((synmark.getStart())>=(interval[n])) && ((synmark.getStart())<=(interval[n]))){
						
						if(synmark.getFlag() != null && synmark.getStart()>=interval[n] && synmark.getStart()<=interval[n]+intervals){
							System.out.println("INSIDEEEE!!!");
							if(synmark.getFlag().equals("Question")){
								que[n] = que[n] + 1;
							}
							else if(synmark.getFlag().equals("Problem Area")){
								pro[n] = pro[n] + 1;
							}
							else if(synmark.getFlag().equals("Comment")){
								com[n] = com[n] + 1;
								System.out.println("IN COMMENT!!!");
							}
							else if(synmark.getFlag().equals("Solution")){
								sol[n] = sol[n] + 1;
							}
						}
					}
				
				}
				
				/*
				for(int n=0;n<(numOfIntervals-1);n++){
					for (int i = 0; i < synmarks.size(); i++){
					
						SynmarkData synmark = synmarks.get(i);
						//if((synmark.getFlag() != null) && ((synmark.getStart())>=(interval[n])) && ((synmark.getStart())<=(interval[n+1]))){
						if(synmark.getFlag() != null && synmark.getStart()>=interval[n] && synmark.getStart()<=interval[n+1]){
							if(synmark.getFlag().equals("Question"))
								que[n] = que[n] + 1;
							else if(synmark.getFlag().equals("Problem Area"))
								pro[n] = pro[n] + 1;
							else if(synmark.getFlag().equals("Comment")){
								com[n] = com[n] + 1;
								System.out.println("IN COMMNE");
								}
							else if(synmark.getFlag().equals("Solution"))
								sol[n] = sol[n] + 1;
					
						//colour blue from start to end
						//System.out.println( synmark.getStart() + " "+ synmark.getEnd());
						}
					}
					 
					
				}*/
				
				System.out.println("EELLO2!!!");
				/*
				imageURL += numOfComments;
				imageURL += numOfQuestions;
				imageURL += numOfSolutions;
				imageURL += numOfProblems;
				
				//0:00|0:10|0:20|0:30|0:40
				*/
				///////////////////////
				//Komal's Handi
				String imageURL = "http://chart.apis.google.com/chart?chxt=x,y&cht=bvg&chs=400x400&chd=t:";
				String qval="";
				String cval="";
				String pval="";
				String sval="";
				String I="";
				
				for(int v=0;v<numOfIntervals;v++){
				
					if(v==(numOfIntervals-1)){
					//this is for the last
						qval+=que[v];
						cval+=com[v];
						pval+=pro[v];
						sval+=sol[v];
					
					}
					else{
					qval+=que[v]+",";
					cval+=com[v]+",";
					pval+=pro[v]+",";
					sval+=sol[v]+",";
					}
				
				}
				System.out.println("EELLO3!!!");
				imageURL+=cval+"|"+qval+"|"+sval+"|"+pval;
				
				imageURL+="&chds=0,120&chdl=Comment|Question|Solution|Problem Area&chco=BBBB00,4d89f9,00B88A,FF0000&chbh=20&chds=0,20&chbh=a&chxs=0,000000,0,0,_&chxt=y&chm=N,000000,0,,10|N,000000,1,,10|N,000000,2,,10|N,000000,3,,10&chxt=y,y,x,x&chxl=1:|Flag Quantity|2:|";
				
				//0:00|0:10|0:20|0:30|0:40
				String sectime ="";
				String mintime ="";
				int temp=0;
				
				for(int h = 0;h<numOfIntervals;h++){
				
					mintime += (int)interval[h]/60000;
					
					
					temp= (interval[h] % 60000)/1000;
					
					if(temp<10)
					sectime+= "0"+temp;
					
					else
					sectime+=temp;
					
					if(h==numOfIntervals-1){
					//doing the conversion from miliseconds to minute notation
					
						I+=mintime +":" +sectime;
					}
					else{
						
						I+=mintime +":" +sectime+"|";
					}
					
					mintime="";
					sectime="";
					
				}
				System.out.println("EELLO4!!!");
				imageURL+=I;
				
				imageURL+="|3:|  Time Interval&chxp=1,40|3,50";
				
				
				//////////////////////////////////////
				
				
				
				
			//HOW TO DISPLAY ANM IMAGE!!!!
			//Image imgLogo = new Image("D:/Macfob/projects/synote/web-app/images/synote_logo.png");
			//Image chart = new Image(imageURL + "5,6,7,8|2,3,4,5|10,10,10,8|15,15,15,9&chds=0,120&chdl=Comment|Question|Solution|Problem Area&chco=BBBB00,4d89f9,00B88A,FF0000&chbh=20&chds=0,20&chbh=a&chxs=0,000000,0,0,_&chxt=y&chm=N,000000,0,,10|N,000000,1,,10|N,000000,2,,10|N,000000,3,,10&chxt=y,y,x,x&chxl=1:|Flag Quantity|2:|0:00|0:10|0:20|0:30|0:40|3:|  Time Interval&chxp=1,40|3,50");
			
				System.out.println(imageURL);
				Image chart = new Image(imageURL);
			
				window.add(chart, centerData); 
			
				System.out.println("HELLO FAREESA!!!");
				window.show();
			}
		});
		playerTopToolbar.addButton(extraButton);
		//////////////////////////////////////////////////////////////
		
		
		playerTopToolbar.addFill();

		//change made by Fareesa for testing! 
		ToolbarTextItem userNameItem = new ToolbarTextItem("Welcome to synote player:<b>"+Utils.getLoggedInUserName()+"</b>   ");
		
		playerTopToolbar.addItem(userNameItem);

		setTopToolbar(playerTopToolbar);
	}
	

	private static Object[][] getData() {   
			return new Object[][]{   
				new Object[]{"0:10", new Integer(1), new Integer(4), new Integer(1), new Integer(4)},   
					new Object[]{"0:20", new Integer(2), new Integer(6), new Integer(2), new Integer(4)},   
					new Object[]{"0:30", new Integer(1), new Integer(5), new Integer(1), new Integer(4)},   
					new Object[]{"0:40", new Integer(8), new Integer(7), new Integer(3), new Integer(4)},   
					new Object[]{"0:50", new Integer(1), new Integer(6), new Integer(1), new Integer(4)}   
				};   
	}  
	

	public Player getPlayer()
	{
		return player;
	}

	public ProfileManager getProfile()
	{
		return player.getProfile();
	}

	public PlayerModel getModel()
	{
		return player.getModel();
	}

	public MultimediaController getController()
	{
		return player.getController();
	}
}
