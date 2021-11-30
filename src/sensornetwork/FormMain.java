package sensornetwork;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;



public class FormMain extends JFrame {

	private JPanel contentPane;
	private JTextField tbWidth;
	private JTextField tbHeight;
	private JTextField tbRadius;
	private JTextField tbNumSensor;	
	private JLabel lbRest;	
	private Surface surface;
	private int index =1 ;
	//private JTextField lbK;
		
	private JTextField txtFilePath;
	private boolean createdData=false;
	private Initialization ini;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMain formMain = new FormMain();
					formMain.setTitle("Thuật toán tối ưu ngủ thức cho rào cản của mạng cảm biến không dây");
					formMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FormMain() {
		
		txtFilePath = new JTextField();
		txtFilePath.setEditable(false); //

		setBounds(100, 20, 1200, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); //
		contentPane.setLayout(null);

		JLabel lbWidth = new JLabel();
		lbWidth.setText("Width of WSN: ");
		lbWidth.setBounds(20, 70, 120, 50);
		contentPane.add(lbWidth);
		JLabel lbHeight = new JLabel();
		lbHeight.setText("Height of WSN: ");
		lbHeight.setBounds(20, 120, 120, 50);
		contentPane.add(lbHeight);
		JLabel lbNumOfSensor = new JLabel();
		lbNumOfSensor.setText("Num of Sensor: ");
		lbNumOfSensor.setBounds(20, 150, 120, 50);
		contentPane.add(lbNumOfSensor);
		JLabel lbRadius = new JLabel();
		lbRadius.setText("Radius of sensor: ");
		lbRadius.setBounds(20, 180, 120, 50);
		contentPane.add(lbRadius);

		tbWidth = new JTextField();
		tbWidth.setBounds(120, 80, 100, 25);
		contentPane.add(tbWidth);
		tbHeight = new JTextField();
		tbHeight = new JTextField();
		tbHeight.setBounds(120, 130, 100, 25);
		contentPane.add(tbHeight);
		tbNumSensor = new JTextField();
		tbNumSensor.setBounds(120, 160, 100, 25);
		contentPane.add(tbNumSensor);
		tbRadius = new JTextField();
		tbRadius.setBounds(120, 190, 100, 25);
		contentPane.add(tbRadius);	
		
						
		JButton btnTao = new JButton("Tạo");
		btnTao.setBounds(20, 240, 80, 30);
		btnTao.setBackground(new Color(224, 255, 255));
		btnTao.setFont(new Font("Times New Roman", Font.BOLD, 15));
		contentPane.add(btnTao);								
		btnTao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(index==1)
				{
					int h,w,r,n;
					try 
					{
						h=Integer.parseInt(tbHeight.getText());
						w=Integer.parseInt(tbWidth.getText());
						r=Integer.parseInt(tbRadius.getText());
						n=Integer.parseInt(tbNumSensor.getText());
						if(h<=0||w<=0||r<=0||n<=0)
						{
							JOptionPane.showMessageDialog(null,
									"Nhập Lại !",
					                "Error",
					                JOptionPane.ERROR_MESSAGE);
							return;
						}
						if(h>220)
						{
							JOptionPane.showMessageDialog(null,
									"Quá rộng,nhập Lại (Height<=220), Recommend h=200,w=400,r=50!",
					                "Error",
					                JOptionPane.ERROR_MESSAGE);
							return;
						}
						if(w>800)
						{
							JOptionPane.showMessageDialog(null,
									"Quá dài,nhập Lại (Width<=800)  Recommend h=200,w=400,r=50!",
					                "Error",
					                JOptionPane.ERROR_MESSAGE);
							return;
						}
						if(r>50)
						{
							JOptionPane.showMessageDialog(null,
									"Bán kính lớn , nhập lại (Radius<=50) Recommend Recommend h=200,w=400,r=50!",
					                "Error",
					                JOptionPane.ERROR_MESSAGE);
							return;
						}
						
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null,
				                "Nhập Lại !",
				                "Error",
				                JOptionPane.ERROR_MESSAGE);
						return;
					}
					ini=new Initialization(Integer.parseInt(tbHeight.getText()),Integer.parseInt(tbWidth.getText()),Integer.parseInt(tbRadius.getText()),Integer.parseInt(tbNumSensor.getText()));
					surface = new Surface(ini);
					surface.setLayout(null);
					surface.setSize(1000, 900);
					surface.setLocation(300, 60);
					lbRest.setText(Integer.toString(surface.getIni().getAllPath().size()));
					contentPane.add(surface);	
					surface.repaint();
					createdData=true;
					index=0;
					btnTao.disable();
					
				}
				
			}
		});
		
		JButton btnXoa = new JButton("Xoá");
		btnXoa.setBounds(120, 240, 80, 30);
		contentPane.add(btnXoa);
		btnXoa.setBackground(new Color(224, 255, 255));
		btnXoa.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnXoa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(index == 0 ) {
					contentPane.remove(surface);
					index = 1;
					contentPane.repaint(); //					
				}
			}
		});
		
		JLabel lblM = new JLabel("m =");
		lblM.setBounds(290, 20, 50, 30);
		contentPane.add(lblM);
		
		lbRest = new JLabel(""); // kiểm tra với k
		lbRest.setBounds(320, 20, 50, 30);
		contentPane.add(lbRest);
		//contentPane.revalidate();
		
		JLabel lblK = new JLabel("k = ");
		lblK.setBounds(20, 300, 50, 30);
		//panel_1.setBounds(10, 50, 220, 230);
		contentPane.add(lblK);
		
		JTextField lbK = new JTextField();
		lbK.setBounds(50,305,70,25);
		contentPane.add(lbK);
		
		JLabel lblNewLabel_1 = new JLabel("l =");
		lblNewLabel_1.setBounds(20, 330, 50, 30);
		contentPane.add(lblNewLabel_1);
		
		JLabel lbL = new JLabel("");
		lbL.setBounds(50,330,50,30);
		contentPane.add(lbL);
		
		JLabel lblNewLabel_2 = new JLabel("r =");
		lblNewLabel_2.setBounds(20, 360, 50, 30);
		contentPane.add(lblNewLabel_2);
		
		JLabel lbR = new JLabel("");
		lbR.setBounds(50,360,50,30);
		contentPane.add(lbR);
		
		JLabel lblNewLabel_3 = new JLabel("Path switch =");
		lblNewLabel_3.setBounds(20, 380, 120, 30);
		contentPane.add(lblNewLabel_3);
		
		JLabel lbPathSwitch = new JLabel("");
		lbPathSwitch.setBounds(120,380,50,30);
		contentPane.add(lbPathSwitch);
				
		JLabel lblT = new JLabel("t = ");
		lblT.setBounds(20, 410, 50, 30);
		contentPane.add(lblT);		
		
		JLabel lbT = new JLabel("");
		lbT.setBounds(51, 410, 50, 30);
		contentPane.add(lbT);
			
		
		
		
		JButton btnNewButton = new JButton("Chạy");
		btnNewButton.setBounds(130, 305, 75, 25);
		//lblK.setBounds(20, 300, 50, 30);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(index==0)
				{
				
					int k;
					try
					{ 
						k=Integer.parseInt(lbK.getText());
					}
					
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null,
				                "Nhập không hợp lệ",
				                "Error",
				                JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(k>Integer.parseInt(lbRest.getText())||k<=0)
					{
						JOptionPane.showMessageDialog(null,
				                "Nhập lại k (0<k<=m)",
				                "Error",
				                JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					lbPathSwitch.setText(Integer.toString(k-gcd(k,Integer.parseInt(lbRest.getText()))));
					if(Integer.parseInt(lbRest.getText())%k==0)
					{
						lbL.setText(Integer.toString(Integer.parseInt(lbRest.getText())/k));
						lbT.setText(Integer.toString(Integer.parseInt(lbRest.getText())/k));
					}
					
					else
					{
						lbL.setText(Integer.toString(Integer.parseInt(lbRest.getText())/k-1));
						lbT.setText(Integer.toString(Integer.parseInt(lbRest.getText())/gcd(k,Integer.parseInt(lbRest.getText())))+"/"+Integer.toString(k/gcd(k,Integer.parseInt(lbRest.getText()))));
					}
					
					lbR.setText(Integer.toString(Integer.parseInt(lbRest.getText())-Integer.parseInt(lbL.getText())*k));
					
					
					SimulationFrame mp= new SimulationFrame(Integer.parseInt(tbWidth.getText()), Integer.parseInt(tbHeight.getText()));
					mp.setTitle("Mô phỏng kích hoạt các rào cản");
					SimulationSurface sf= new SimulationSurface(Integer.parseInt(tbWidth.getText()),Integer.parseInt(tbHeight.getText()),Integer.parseInt(tbRadius.getText()),Integer.parseInt(tbNumSensor.getText()),Integer.parseInt(lbL.getText()),Integer.parseInt(lbK.getText()),Integer.parseInt(lbRest.getText()),Integer.parseInt(lbR.getText()),surface.getIni());
					sf.setLayout(null);
					sf.setSize(900, 900);
					sf.setLocation(0, 0);
					mp.getContentPane().setLayout(null);
					mp.getContentPane().add(sf);
					mp.revalidate();
					mp.repaint();
					mp.setVisible(true);
				}
				
			}
		});	
		
		
		
		JButton btnExport = new JButton("Xuất ra File"); //xoá
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExportFileFunction(e);
			}
		});
		btnExport.setBounds(20, 460, 100, 33); //(29, 344, 29, 14);
		contentPane.add(btnExport);
		
		
		JButton btnCalFile = new JButton("Tính từ File"); //xoá
		btnCalFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCalFileFunction(e);
			}
		});
		btnCalFile.setBounds(20, 520, 120, 33); //(29, 344, 29, 14);
		contentPane.add(btnCalFile);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 50, 220, 230);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panelFile = new JPanel();
		panelFile.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelFile.setBounds(10, 300, 220, 150);
		contentPane.add(panelFile);
		panelFile.setLayout(null);
		
		tbHeight.setText(Integer.toString(200));
		tbWidth.setText(Integer.toString(300));
		tbNumSensor.setText(Integer.toString(60));
		tbRadius.setText(Integer.toString(50));
			
		
	}

	
	private void btnExportFileFunction(java.awt.event.ActionEvent u) {
		if(createdData==true)
		{
			JFileChooser fc = new JFileChooser(); //
			fc.setDialogTitle("Save graph");
			int select = fc.showSaveDialog(this);//
			
			if (select == 0) {
				String path = fc.getSelectedFile().getPath();
				path += ".txt";
				System.out.println(path);
				File filename = new File(path);
				
				try {
					PrintWriter pw = new PrintWriter(filename);
					pw.println(surface.getIni().getHeight() +" "+ surface.getIni().getWidth() +" "+ surface.getIni().getRadius() +" "+ tbNumSensor.getText()+" "+lbRest.getText());
					for(Sensor i:surface.getIni().getListSensor())
						pw.println(i.getPosition().x+" "+i.getPosition().y);
					pw.close();				

					JOptionPane.showMessageDialog(null, "Save success", "Save success",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Save", "Error save file",
							JOptionPane.OK_OPTION);
					System.out.println("Error save file\n" + e.toString());
				}
			}
			
		}
		else
		{
			JOptionPane.showMessageDialog(null,
	                "Chưa đủ dữ liệu để xuất !",
	                "Error",
	                JOptionPane.ERROR_MESSAGE);
			return;
		}
			
	}

	private void btnCalFileFunction(java.awt.event.ActionEvent u) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open graph");
		int select = fc.showOpenDialog(this);
		if (select == 0) {
				
			String path = fc.getSelectedFile().getPath();
			System.out.println(path);
			try {
				BufferedReader br = new BufferedReader(new FileReader(path));
				String tempSur[] = br.readLine().split(" ");
				int Width = Integer.parseInt(tempSur[0]);
				int Height = Integer.parseInt(tempSur[1]);
				int radius = Integer.parseInt(tempSur[2]);
				int numOfSensor = Integer.parseInt(tempSur[3]);
				int m= Integer.parseInt(tempSur[4]);
				ArrayList<Sensor> listSensor = new ArrayList<Sensor>();
				for(int i=0;i<numOfSensor;i++) {
					String[] tempSen = br.readLine().split(" ");
					listSensor.add(new Sensor(Integer.parseInt(tempSen[0]),Integer.parseInt(tempSen[1]),radius));
				}				
				br.close();
				if(surface!=null)
				{
					contentPane.remove(surface);
					contentPane.repaint();
					index=1;
					ini=new Initialization(Width,Height,radius,listSensor);
					surface = new Surface(ini);
					surface.setLayout(null);
					surface.setSize(1000, 900);
					surface.setLocation(300, 60);
					contentPane.add(surface);	
					surface.repaint();
					index=0;
					tbHeight.setText(Integer.toString(Height));
					tbWidth.setText(Integer.toString(Width));
					tbRadius.setText(Integer.toString(radius));
					tbNumSensor.setText(Integer.toString(numOfSensor));
					lbRest.setText(Integer.toString(m));
					
					
				}
				else
				{
					ini=new Initialization(Width,Height,radius,listSensor);
					surface = new Surface(ini);
					surface.setLayout(null);
					surface.setSize(1000, 900);
					surface.setLocation(300, 60);
					contentPane.add(surface);	
					surface.repaint();
					index=0;
					tbHeight.setText(Integer.toString(Height));
					tbWidth.setText(Integer.toString(Width));
					tbRadius.setText(Integer.toString(radius));
					tbNumSensor.setText(Integer.toString(numOfSensor));
					lbRest.setText(Integer.toString(m));
				}
		
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "File not found", "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Error read file\nFile open must is *.txt", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			System.out.println("done read");
		}
	}
	
	int gcd(int a, int b) {
	    if (b == 0) return a;
	    return gcd(b, a % b);
	}
	

}
