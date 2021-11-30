package sensornetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Initialization {
	private int numOfSensor;
	private int Width;
	private int Height;
	private int radius;
	private ArrayList<Sensor> listSensor;
	private ArrayList<Edge> listEdge;
	private ArrayList<Sensor> listJointS;
	private ArrayList<Sensor> listJointT;
	private int[][] MaTrix;
	private List<List<Integer>> paths;
	private ArrayList<ArrayList<Sensor>> allPath;

	public Initialization(int Width, int Height, int radius, int numOfSensor) {
		super();
		this.Width = Width;
		this.Height = Height;
		this.radius = radius;
		this.numOfSensor = numOfSensor;
		allPath = new ArrayList<ArrayList<Sensor>>();
		createSensor();
		checkJoin();
		createEdge();
		createMatrix();
	}

	public Initialization(int Height,int Width,int radius,ArrayList<Sensor> listSensor)//hàm khởi tạo
	{
		this.Height=Height;
		this.Width=Width;
		this.radius=radius;
		this.listSensor=listSensor;
		this.numOfSensor=listSensor.size();
		listEdge=new ArrayList<Edge>();
		listJointS=new ArrayList<Sensor>();
		listJointT=new ArrayList<Sensor>();
		allPath=new ArrayList<ArrayList<Sensor>>();
		checkJoin();
		createMatrix();
		createEdge();
		//dispa();
	}

	
	public void createSensor() {
		listSensor = new ArrayList<Sensor>();
		Random rand = new Random();
		for (int i = 0; i < numOfSensor; i++) {
			int x = rand.nextInt(Height) + 50;
			int y = rand.nextInt(Width) + 50;
			Sensor sensor = new Sensor(x, y, radius);
			listSensor.add(sensor);
		}
	}

	public void checkJoin() {
		listJointS = new ArrayList<Sensor>();
		listJointT = new ArrayList<Sensor>();
		for (Sensor s : getListSensor()) {
			if (s.getX() - radius - 50 < 0) {
				listJointS.add(s);
				s.setIsJoinS(1);				
			}else
				s.setIsJoinS(0);
			if (s.getX() + radius > Height) {
				listJointT.add(s);
				s.setIsJoinT(1);
			}else
				s.setIsJoinT(0);
			
		}
	}

	public void createEdge() {
		listEdge = new ArrayList<Edge>();
		ArrayList<Sensor> sensorListTemp = getListSensor();
		for (int i = 0; i < numOfSensor; i++) {
			Sensor si = sensorListTemp.get(i);
			for (int j = i + 1; j < numOfSensor; j++) {
				Sensor sj = sensorListTemp.get(j);
				if (getLength(si, sj) < 2 * radius) {
					Edge edge = new Edge(si, sj);
					listEdge.add(edge);
				}
			}
		}
	}

	public void createMatrix() {

		int n = listSensor.size() + 2;
		MaTrix = new int[n][n];				

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				MaTrix[i][j] = 0;

		for (int i = 1; i <= listSensor.size(); i++)
			if (listSensor.get(i - 1).getIsJoinS() == 1)
				MaTrix[0][i] = 1;

		for (int i = listSensor.size(); i > 0; i--)
			if (listSensor.get(i - 1).getIsJoinT() == 1)
				MaTrix[i][listSensor.size() + 1] = 1;

		for (int i = 1; i <= listSensor.size(); i++)
			for (int j = 1; j <= listSensor.size(); j++)
				if (i != j)
					MaTrix[i][j] = (getLength(listSensor.get(i - 1), listSensor.get(j - 1))) <= 2 * radius? 1 : 0;


		int m = (n - 2) * 2 + 2; 
		int k = n - 2; 
		int[][] graph = new int[m][m];

		
		for (int i = 0; i < m; i++)
			for (int j = 0; j < m; j++)
				graph[i][j] = 0;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n - 1; j++)
				graph[i][j] = MaTrix[i][j];

		for (int i = 0; i < n; i++)
			if (MaTrix[i][n - 1] == 1)
				graph[i][m - 1] = 1;


		for (int i = 1; i < n - 1; i++)
			for (int j = 1; j < n - 1; j++)
				graph[i][i + k] = 1;

		for (int i = 1; i < n - 1; i++)
			for (int j = 1; j < n - 1; j++)
				if (MaTrix[i][j] == 1) {
					if (graph[i][m - 1] == 1) {
						graph[i][m - 1] = 0;
						graph[i + k][m - 1] = 1;
					}
					graph[i][j] = 0; 
					graph[i + k][j] = 1;
				}

///////////////// ÁP DỤNG THUẬT TOÁN FORD-FULKERSON///////////////	        	       
		FordFulkerson ff = new FordFulkerson();
		paths = ff.maxFlow(graph, 0, m - 1);
		System.out.println("Các đường dẫn rời rạc");

		for (List<Integer> path : paths) {
			ArrayList<Sensor> as = new ArrayList<Sensor>();
			for (int i : path) {
				if (i < n - 1) {
					System.out.print(i + " "); // in các đường dẫn bao gồm cả s và t!
					if (i == 0)
						continue;
					else
						as.add(listSensor.get(i - 1));
				}
			}
			System.out.print(n - 1);
			System.out.println();
			allPath.add(as);
		}
		System.out.println();
		System.out.println("Tổng số đường dẫn: " + allPath.size());
	}

	public float getLength(Sensor s1, Sensor s2) {
		int temp1 = s1.getX() - s2.getX();
		int temp2 = s1.getY() - s2.getY();
		return (float) Math.sqrt(temp1 * temp1 + temp2 * temp2);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getWidth() {
		return Width;
	}

	public int getNumOfSensor() {
		return numOfSensor;
	}

	public void setNumOfSensor(int numOfSensor) {
		this.numOfSensor = numOfSensor;
	}

	public void setWidth(int Width) {
		this.Width = Width;
	}

	public int getHeight() {
		return Height;
	}

	public void setHeight(int Height) {
		this.Height = Height;
	}

	public ArrayList<Sensor> getListSensor() {
		return listSensor;
	}

	public void setListSensor(ArrayList<Sensor> listSensor) {
		this.listSensor = listSensor;
	}

	public ArrayList<Edge> getListEdge() {
		return listEdge;
	}

	public void setListEdge(ArrayList<Edge> listEdge) {
		this.listEdge = listEdge;
	}

	public ArrayList<Sensor> getListJointS() {
		return listJointS;
	}

	public void setListJointS(ArrayList<Sensor> listJointS) {
		this.listJointS = listJointS;
	}

	public ArrayList<Sensor> getListJointT() {
		return listJointT;
	}

	public void setListJointT(ArrayList<Sensor> listJointT) {
		this.listJointT = listJointT;
	}
	
	public ArrayList<ArrayList<Sensor>> getAllPath() {
		return allPath;
	}

}
