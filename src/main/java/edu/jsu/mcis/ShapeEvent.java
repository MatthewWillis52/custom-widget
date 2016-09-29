package edu.jsu.mcis;

public class ShapeEvent {
    private boolean octagonSelected;
	private boolean hexagonSelected;
    public ShapeEvent() {
        this(false, false);
    }
    public ShapeEvent(boolean octagonSelected, boolean hexagonSelected) {
        this.octagonSelected = octagonSelected;
		this.hexagonSelected = hexagonSelected;
    }
    public boolean octagonisSelected() { return octagonSelected; }
	public boolean hexagonisSelected() { return hexagonSelected; }
}