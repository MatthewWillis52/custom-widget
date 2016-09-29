package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

import java.awt.*;
import java.awt.event.*;

public class CustomWidgetTest {
    private CustomWidget widget;
    
    private Point getCenterOfWidget() {
        Shape[] shapes = widget.getShapes();
		Rectangle bounds = null;
		for(int i = 0; i < shapes.length; i++){
			 bounds = shapes[i].getBounds();
			
		}
		return new Point(bounds.x + bounds.width/2, bounds.y + bounds.height/2);
	}
    
    @Before
    public void setUp() {
        widget = new CustomWidget();
    }
    
	@Test
	public void testWidgetIsInitiallyDeselected() {
		assertFalse(widget.octagonisSelected());
	}
    
    @Test
    public void testClickingCenterOfWidgetSelectsIt() {
        Point center = getCenterOfWidget();
        MouseEvent event = new MouseEvent(widget, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 
                                          0, center.x, center.y, 1, false);
        
        widget.mouseClicked(event);
		boolean[] selected= new boolean[2];
		selected[0] = widget.hexagonisSelected();
		selected[1] = widget.octagonisSelected();
        assertTrue(selected[0]);
    }
}
