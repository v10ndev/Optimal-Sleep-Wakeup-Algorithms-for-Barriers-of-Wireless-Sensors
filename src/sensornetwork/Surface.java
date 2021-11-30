package sensornetwork;

import java.awt.BasicStroke;import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.*;


public class Surface extends JComponent {
	
	private Initialization ini;
	public Surface(Initialization ini) {
		this.ini = ini;
	}
	
	
	
	public Initialization getIni() {
		return ini;
	}


	public void drawWSN(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		BasicStroke bs1 = new BasicStroke(3, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        BasicStroke bs2 = new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        g2D.setStroke(bs1);
        g2D.setColor(Color.RED);
        g2D.drawRect(50, 50, ini.getHeight(), ini.getWidth());
        
        g2D.setStroke(bs2);
        for(Sensor s : ini.getListSensor()) {
        	int radius = s.getRadius();
        	g2D.setColor(Color.RED);
        	g2D.fillOval(s.getX() - (radius/12), s.getY() - (radius/12), radius/6, radius/6);
        	g2D.setColor(Color.GREEN);
        	g2D.drawOval(s.getX() - (radius), s.getY() - (radius), radius*2, radius*2);
        	
        }
	}
	
	public void drawGraph(Graphics g) {
		int radius = ini.getRadius();
		int  Width = ini.getWidth();
		Graphics2D g2d = (Graphics2D) g;
		BasicStroke bs1 = new BasicStroke(3, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
		g2d.setStroke(bs1);
		g2d.setColor(Color.red);
		g2d.drawRect(50, Width + radius +100, ini.getHeight(), ini.getWidth());
		
//		BasicStroke bs2 = new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
//		g2d.setStroke(bs2);
//        for(Sensor s : ini.getListSensor()) {
//        	//int radius = s.getRadius();
//        	g2d.setColor(Color.RED);
//        	g2d.fillOval(s.getX() - (radius/12), s.getY() - (radius/12) + radius + Width +50, radius/6, radius/6);
//        	g2d.setColor(Color.green);
//        	g2d.drawOval(s.getX() - (radius), s.getY() - (radius) + radius + Width +50, radius*2, radius*2);
//        	
//        }
        
        BasicStroke bs3 = new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
		g2d.setStroke(bs3);
		g2d.setColor(Color.RED);
        for(Edge edge : ini.getListEdge()) {
        	Sensor s1 = edge.getS1();
        	Sensor s2 = edge.getS2();        	
        	g2d.drawLine(s1.getX(), s1.getY()+radius + Width +50, s2.getX(), s2.getY()+ radius + Width +50);
        }
                
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.PLAIN, 30));
        char []s = new char[]{'s'};
        g2d.drawChars(s, 0, s.length, 8, Width+radius+100+Width/2);
        char []t = new char[]{'t'};
        g2d.drawChars(t, 0, t.length, 70+ 2 + ini.getHeight(), Width+radius+100+Width/2);
        
        for(Sensor sensor : ini.getListJointS())
        	g2d.drawLine(sensor.getX(), sensor.getY() + radius + Width + 50, 8 , Width+radius+100+Width/2);
        for(Sensor sensor : ini.getListJointT())
        	g2d.drawLine(sensor.getX(), sensor.getY() + radius + Width + 50, 70+ 2 + ini.getHeight() , Width+radius+100+Width/2);
                    	
	}
	
	private void paintDisJoinPath(Graphics g)
	{
		int radius = ini.getRadius();
		int  Width = ini.getWidth();
		Graphics2D g2d = (Graphics2D) g; 
		BasicStroke bs1 = new BasicStroke(3, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs1);
		g2d.setColor(Color.BLUE);
        if(ini.getAllPath()!=null)
	   	{  
	   		 if(ini.getAllPath().size()>0)
	            for(ArrayList<Sensor> lisSS:ini.getAllPath())
	            {
	           	     if(lisSS.size()==0)
	           		 continue;
					 for(int l=0;l<lisSS.size()-1;l++)
					 {
						 g2d.drawLine(lisSS.get(l).getX(),lisSS.get(l).getY()+ radius + Width + 50, 
								 lisSS.get(l+1).getX(), lisSS.get(l+1).getY()+ radius + Width + 50);
						 
					 }
	            }
	    }
        
	}

	@Override
	protected void paintComponent(Graphics g) { 
		drawWSN(g);
		drawGraph(g);
		paintDisJoinPath(g);
	}
}
