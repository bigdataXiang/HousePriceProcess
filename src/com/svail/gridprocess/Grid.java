package com.svail.gridprocess;
import java.util.ArrayList;
public class Grid {
	public ArrayList<DataPoint> dataPoints=new ArrayList<DataPoint>();
    public void addDataPoint(DataPoint p){
		        dataPoints.add(p);
	}
	 public double getGridValue(){
		 int size=dataPoints.size();
	     double sum=0;
	     for(DataPoint p:dataPoints){
	            sum+=p.data;
	      }
	      return sum/size;
	    }
}


/*画网格
 * public class Grid extends JPanel {
	public static void main(String[] args) {
		JFrame frame = new JFrame("DrawLine");
		frame.getContentPane().add(new Grid());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setVisible(true);
		}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=50;i<=500;i+=50){
		   g.drawLine(50, i, 500, i);
		for(int k=50;k<=500;k+=50){
		g.drawLine(k, 50, k, 500);
			}
		//g.drawOval(50, 50, 100, 100);//java画圈
		}
 */
