package sensornetwork;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class SimulationSurface extends JComponent{
	int i=0;// tổng số lần kích hoạt các rào cản
    Timer timer;// 
    int status[];
    double age[];
    double maxAge=0;
    int l;
    int k;
    int m;
    int r;
    int x;
    int f;
	private Initialization Ini; //đối tượng của lớp Initialization
	public SimulationSurface(int width,int height,int radius,int numOfSensor,int l ,int k,int m,int r,Initialization Ini) //Contructor khởi tạo
	{
		super();
		this.Ini=Ini;
		status=new int[Ini.getAllPath().size()];// set trạng thái cho các rào cản 
		age=new double[Ini.getAllPath().size()];// mảng chứa thời gian sử dụng của rào cản max = 1
		this.k=k;
		this.m=m;
		this.r=r;
		this.l=l;
		this.x=gcd(r,k);
		this.f=r/x;
		for(int i=1;i<=k;i++)
			maxAge+=1.0/k;
			
		ActionListener animate = new ActionListener() {
	            public void actionPerformed(ActionEvent ae) {
	                repaint();
	            }
	    };
        timer = new Timer(2500,animate);
        timer.start();
	}
	public void setIni(Initialization ini) {
		Ini = ini;
	}
	private void simulationDisJoinPath(Graphics g) // vẽ ra các rào cản trong mạng cảm biến
	{
		
		Graphics2D g2d = (Graphics2D) g; 
		BasicStroke bs1 = new BasicStroke(3, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs1);
		if(i<=l+f-1)
		{
			 if(Ini.getAllPath()!=null)
			   	{  
		    		ArrayList<Integer> pathMaxAge=new  ArrayList<Integer>();
		        	if(i<=l-1) // Kích hoạt lần lượt tập các rào cản trong tập l
		        	{
		        		for(int j=k*(i)+1;j<=k*(i+1);j++) 
		        		{
		        			age[j-1]=maxAge;
		        			status[j-1]=1;
		        			pathMaxAge.add(j-1);
		        		}	
		        	}
		        	else
		        	{
		        		ArrayList<Integer> list= new ArrayList<Integer>();// list chứa những rào cản vừa đc kích hoạt
		        		int g1=(i-l+k/x-1)%f;// tính g trong stint
		        		for(int j=i-l;j!=g1+1;) //Duyệt Xj-->Xg
		        		{
		        			if(j==f)
		        				j=0;
		        			for(int n=m-r+1+x*j;n<=m-r+x*(j+1);n++) //Duyệt path trong Xj
		        			{
		        				
		        				list.add(n-1);
		        				if(age[n-1]+1.0*x/k!=maxAge)
		        				{
		        					status[n-1]=1;
		        					age[n-1]+=1.0*x/k;
		        					
		        				}
		        				else if(age[n-1]+1.0*x/k==maxAge)
		        				{
		        					status[n-1]=1;
		        					age[n-1]=maxAge;
		        					pathMaxAge.add(n-1);

		        				}
		        				System.out.print(n+" ");
		        			}
		        			j++;
		        		}
		        		System.out.print("  sensor còn lại:");
		        		
		        		for(int j=0;j<m;j++)     //gán trạng thái cho những rào cản còn lại
		        		{
		        			if(list.contains(j)==true||age[j]==maxAge)
		        				continue;
		        			else if(age[j]>0)
		        				status[j]=2;
		        			System.out.print(j+1+" ");
		        		}
		        		System.out.print("\n");
		        	}
		        	for(int p=0;p<status.length;p++)
		        	{
		        		
		        		if(status[p]==0)// trạng thái rào cản chưa đc kích hoạt lần nào
		        		{
		        			g2d.setColor(Color.BLACK);
		        			
		        		}
		        		if(status[p]==1)// trạng thái rào cản đang được kích hoạt
		        		{
		        			g2d.setColor(Color.BLUE);
		        			
		        		}
		        		if(status[p]==2)// trạng thái rào cản đã được kích hoạt từ trước nhưng chưa dùng hết thời gian sống
		        		{
		        			g2d.setColor(Color.YELLOW);
		        			
		        		}
		        		if(status[p]==3)// trạng thái rào cản đã sử dụng hết thời gian sống
		        		{
		        			g2d.setColor(Color.RED);
		        			
		        		}
			    		for(int l=0;l<Ini.getAllPath().get(p).size()-1;l++)
						{
						    g2d.drawLine(Ini.getAllPath().get(p).get(l).getPosition().x,Ini.getAllPath().get(p).get(l).getPosition().y,Ini.getAllPath().get(p).get(l+1).getPosition().x, Ini.getAllPath().get(p).get(l+1).getPosition().y);
						}
		        			
		           }
		        	if(pathMaxAge.isEmpty()==false)
		        	{
		        		for(int a=0;a<pathMaxAge.size();a++)
		        		{
		        			status[pathMaxAge.get(a)]=3;
		        		}
		        	}
		           
			   	}
		}
       
        
        	
    }
	private void paintPath(Graphics g) // vẽ ra đồ thị mạng cảm biến
	{
		Graphics2D g2d = (Graphics2D) g;       
        BasicStroke bs2 = new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs2);
        g2d.setColor(Color.BLACK);
        
        char [] s = {'s'};
        g2d.drawChars (s, 0, s.length, 20, 50+Ini.getHeight()/2);
        
        char [] t = {'t'};
        g2d.drawChars (t, 0, s.length, 20+Ini.getWidth()+60, 50+Ini.getHeight()/2);
        
        g2d.setColor(Color.BLUE);
        
        for(ArrayList<Sensor> ars:Ini.getAllPath())
        {
        	for(Sensor S:ars)  	
            {
        		if(S.getIsJoinS()==1)
            	    g2d.drawLine(20+10, 50+Ini.getHeight()/2,S.getPosition().x,S.getPosition().y);        
        		if(S.getIsJoinT()==1)
                	g2d.drawLine(20+Ini.getWidth()+60-10, 50+Ini.getHeight()/2,S.getPosition().x,S.getPosition().y);
        		else
        			continue;
	      
            }
        }
	}
	
	int gcd(int a, int b) { //hàm tìm ước
	    if (b == 0) return a;
	    return gcd(b, a % b);
	}
    private void paintLastPath(Graphics g)
    {
    	for(int p=0;p<m;p++)
    	{
    		Graphics2D g2d = (Graphics2D) g; 
    		BasicStroke bs1 = new BasicStroke(3, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
            g2d.setStroke(bs1);
    		g2d.setColor(Color.RED);
    		for(int l=0;l<Ini.getAllPath().get(p).size()-1;l++)
			{
			    g2d.drawLine(Ini.getAllPath().get(p).get(l).getPosition().x,Ini.getAllPath().get(p).get(l).getPosition().y,Ini.getAllPath().get(p).get(l+1).getPosition().x, Ini.getAllPath().get(p).get(l+1).getPosition().y);
			}
    			
       }
    }
	@Override
    public void paintComponent(Graphics g) //hàm ghi đè từ lớp cha để thực thi vẽ 
	{ 
		paintPath(g);
		simulationDisJoinPath(g);
		if(i<l+f)
		{
			i++;
		}
		else
		{
			paintLastPath(g);
			timer.stop();
		}
    }		

}
