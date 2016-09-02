package com.xrosstools.xdecision.editor;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.xrosstools.xdecision.editor.actions.DecisionTreeActionConstants;

public class Activator extends AbstractUIPlugin implements DecisionTreeActionConstants {
	public static final String PLUGIN_ID	= "com.xrosstools.xdecision.editor";	//$NON-NLS-1$
	private static BundleContext s_context;
	private static Activator plugin;
	public static final String HOME = "icons/";
	public static final String ICO = ".ico";
	
	public static final String TREE = "tree";
	public static final String NODE = "node";
	public static final String CONNECTION = "connection";
	

	public static BundleContext getContext() {
		return s_context;
	}
	
	public static Activator getDefault() {
		return plugin;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		s_context = context;
	}

	public void stop(BundleContext context) throws Exception {
		s_context = null;
		plugin = null;
		super.stop(context);
	}

    private void put(ImageRegistry reg, String id){
    	reg.put(id, imageDescriptorFromPlugin(PLUGIN_ID, getIconPath(id)));
    }
	
    protected void initializeImageRegistry(ImageRegistry reg) {
    	put(reg, ALIGN_LEFT);
    	put(reg, ALIGN_CENTER);
    	put(reg, ALIGN_RIGHT);
    	
    	put(reg, ALIGN_BOTTOM);
    	put(reg, ALIGN_MIDDLE);
    	put(reg, ALIGN_TOP);
    	
    	put(reg, INCREASE_NODE_HEIGHT);
    	put(reg, DECREASE_NODE_HEIGHT);
    	put(reg, INCREASE_NODE_WIDTH);
    	put(reg, DECREASE_NODE_WIDTH);
    	
    	put(reg, INCREASE_VERTICAL_SPACE);
    	put(reg, DECREASE_VERTICAL_SPACE);
    	
    	put(reg, INCREASE_HORIZANTAL_SPACE);
    	put(reg, DECREASE_HORIZANTAL_SPACE);

    	reg.put(CONNECTION, imageDescriptorFromPlugin(PLUGIN_ID, HOME + CONNECTION +".gif"));
    	put(reg, TREE);
    	put(reg, NODE);
    }
    
    private static String getIconPath(String iconId){
    	return HOME + iconId + ICO;
    }

    public Image getImage(String id){
    	return getImageRegistry().get(id);
    }
}
