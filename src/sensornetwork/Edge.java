package sensornetwork;

public class Edge {
	private Sensor s1;
	private Sensor s2;

	public Edge(Sensor s1, Sensor s2) {
		super();
		this.s1 = s1;
		this.s2 = s2;
	}

	public Sensor getS1() {
		return s1;
	}

	public void setS1(Sensor s1) {
		this.s1 = s1;
	}

	public Sensor getS2() {
		return s2;
	}

	public void setS2(Sensor s2) {
		this.s2 = s2;
	}		

}
