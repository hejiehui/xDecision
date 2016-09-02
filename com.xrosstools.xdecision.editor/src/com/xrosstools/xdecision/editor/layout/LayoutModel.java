package com.xrosstools.xdecision.editor.layout;

import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class LayoutModel implements IPropertySource {
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	private static IPropertyDescriptor[] descriptors;
	private int verticalSpace = 150;
	private int horizantalSpace = 150;
	private float alignment = 0;
	private int nodeWidth = 150;
	private int nodeHeight = 100;
	
	public static final String VERTICAL_SPACE = "Vertical space";
	public static final String HORIZANTAL_SPACE = "Horizantal space";
	public static final String ALIGNMENT = "Alignment";
	public static final String NODE_WIDTH = "Node width";
	public static final String NODE_HEIGHT = "Node height";
	public static final String LAYOUT = "layout";
	
	static {
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(VERTICAL_SPACE, VERTICAL_SPACE),
				new TextPropertyDescriptor(HORIZANTAL_SPACE, HORIZANTAL_SPACE),
				new TextPropertyDescriptor(ALIGNMENT, ALIGNMENT),
				new TextPropertyDescriptor(NODE_WIDTH, NODE_WIDTH),
				new TextPropertyDescriptor(NODE_HEIGHT, NODE_HEIGHT),
			};
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (VERTICAL_SPACE.equals(propName))
			return String.valueOf(getVerticalSpace());

		if (HORIZANTAL_SPACE.equals(propName))
			return String.valueOf(getHorizantalSpace());

		if (ALIGNMENT.equals(propName))
			return String.valueOf(getAlignment());

		if (NODE_WIDTH.equals(propName))
			return String.valueOf(getNodeWidth());

		if (NODE_HEIGHT.equals(propName))
			return String.valueOf(getNodeHeight());

		return null;
	}

	public void setPropertyValue(Object id, Object value){
		if (VERTICAL_SPACE.equals(id))
			setVerticalSpace(Integer.valueOf((String)value));

		if (HORIZANTAL_SPACE.equals(id))
			setHorizantalSpace(Integer.valueOf((String)value));

		if (ALIGNMENT.equals(id))
			setAlignment(Float.valueOf((String)value));

		if (NODE_WIDTH.equals(id))
			setNodeWidth(Integer.valueOf((String)value));

		if (NODE_HEIGHT.equals(id))
			setNodeHeight(Integer.valueOf((String)value));
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}

	public void resetPropertyValue(Object propName){
	}

	public int getVerticalSpace() {
		return verticalSpace;
	}

	public void setVerticalSpace(int verticalSpace) {
		this.verticalSpace = verticalSpace;
		fireLayoutChange();
	}

	public int getHorizantalSpace() {
		return horizantalSpace;
	}

	public void setHorizantalSpace(int horizantalSpace) {
		this.horizantalSpace = horizantalSpace;
		fireLayoutChange();
	}

	public float getAlignment() {
		return alignment;
	}

	public void setAlignment(float alignment) {
		this.alignment = alignment;
		fireLayoutChange();
	}

	public int getNodeWidth() {
		return nodeWidth;
	}

	public void setNodeWidth(int nodeWidth) {
		this.nodeWidth = nodeWidth;
		fireLayoutChange();
	}

	public int getNodeHeight() {
		return nodeHeight;
	}

	public void setNodeHeight(int nodeHeight) {
		this.nodeHeight = nodeHeight;
		fireLayoutChange();
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}
	
	private void fireLayoutChange(){
		listeners.firePropertyChange(LAYOUT, null, null);
	}

}
