package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CustomWidget extends JPanel implements MouseListener {
    private java.util.List<ShapeObserver> observers;
    
    
    private final Color HEX_SELECTED_COLOR = Color.green;
	
	
    private final Color HEX_DEFAULT_COLOR = Color.white;
	
	private final Color OCT_SELECTED_COLOR = Color.red;
	
	private final Color OCT_DEFAULT_COLOR = Color.white;
    private boolean hexagonSelected;
	private boolean octagonSelected;
    private Point[] hexagonVertex;
	private Point[] octagonVertex;

    
    public CustomWidget() {
        observers = new ArrayList<>();
        
        hexagonSelected = true;
        hexagonVertex = new Point[6];
        for(int i = 0; i < hexagonVertex.length; i++) {
			hexagonVertex[i] = new Point(); }
        Dimension dim = getPreferredSize();
		

		
		octagonSelected = false;
		octagonVertex = new Point[8];
		for(int i = 0; i <octagonVertex.length; i++){
			octagonVertex[i] = new Point();
		}
		calculateVertices(dim.width, dim.height);
        setBorder(BorderFactory.createLineBorder(Color.black));
        
		addMouseListener(this);
    }

    
    public void addShapeObserver(ShapeObserver observer) {
        if(!observers.contains(observer)) observers.add(observer);
    }
    public void removeShapeObserver(ShapeObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        ShapeEvent event = new ShapeEvent(hexagonSelected, octagonSelected);
        for(ShapeObserver obs : observers) {
            obs.shapeChanged(event);
        }
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    private void calculateVertices(int width, int height) {
        // Square size should be half of the smallest dimension (width or height).
 		double theta = 0;
        for(int i = 0; i < hexagonVertex.length; i++) {
            theta = 2 * Math.PI / hexagonVertex.length;
			hexagonVertex[i].setLocation((width/3) + Math.cos(theta * i) * 100, 
					height/2 + Math.sin(theta * i) *100);
			
		}
		for(int i = 0; i < octagonVertex.length; i++){
			theta = 2 * Math.PI / octagonVertex.length;
			octagonVertex[i].setLocation((width/3) * 2.3 + Math.cos(theta * + i) * 100, 
					height/2 + Math.sin(theta * i) *100);
			
		}
		
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        calculateVertices(getWidth(), getHeight());
        Shape[] shapes = getShapes();
        
        
			g2d.setColor(Color.black);
			g2d.draw(shapes[0]);
			g2d.draw(shapes[1]);
			if(hexagonSelected) {
				g2d.setColor(HEX_SELECTED_COLOR);
				g2d.fill(shapes[0]); 
				g2d.setColor(HEX_DEFAULT_COLOR);
				g2d.fill(shapes[1]); 
						   
			}
			if(octagonSelected) {
				g2d.setColor(OCT_SELECTED_COLOR);
				g2d.fill(shapes[1]);
				g2d.setColor(OCT_DEFAULT_COLOR);
				g2d.fill(shapes[0]);
			}
			 
			
		}
		
		
    

    public void mouseClicked(MouseEvent event) {
        Shape[] shapes = getShapes();
        for(int i = 0; i <shapes.length; i++){
			if(shapes[i].contains(event.getX(), event.getY())) {
				hexagonSelected = !hexagonSelected;
				notifyObservers();
			}
			repaint(shapes[i].getBounds());
		}
		for(int i = 0; i < shapes.length; i++){
			if(shapes[i].contains(event.getX(), event.getY())){
				octagonSelected = !octagonSelected;
				notifyObservers();
			}
		}
        
    }
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    
    public Shape[] getShapes() {
        
		int[] x = new int[hexagonVertex.length];
        int[] y = new int[hexagonVertex.length];
		int[] m = new int[octagonVertex.length];
		int[] n = new int[octagonVertex.length];
	     
		for(int i = 0; i < hexagonVertex.length; i++) {
            x[i] = hexagonVertex[i].x;
            y[i] = hexagonVertex[i].y;
        }
	     
		for(int i = 0; i < octagonVertex.length; i++) {
            m[i] = octagonVertex[i].x;
            n[i] = octagonVertex[i].y;
        }
		Shape[] shapes = new Shape[2];
        shapes[0] = new Polygon(x, y, hexagonVertex.length);
		shapes[1] = new Polygon(m,n, octagonVertex.length);
       
		
			return shapes;
		
    }
    public boolean hexagonisSelected() { return hexagonSelected;}
	
	public boolean octagonisSelected() { return octagonSelected; }



	public static void main(String[] args) {
		JFrame window = new JFrame("Custom Widget");
        window.add(new CustomWidget());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 300);
        window.setVisible(true);
	}
}
