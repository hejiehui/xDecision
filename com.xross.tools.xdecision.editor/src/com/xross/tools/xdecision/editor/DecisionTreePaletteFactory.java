package com.xross.tools.xdecision.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;

import com.xross.tools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreePaletteFactory {
    public PaletteRoot createPalette() {
        PaletteRoot paletteRoot = new PaletteRoot();
        paletteRoot.addAll(createCategories(paletteRoot));    	
        return paletteRoot;
    }
    
    private List<PaletteContainer> createCategories(PaletteRoot root) {
        List<PaletteContainer> categories = new ArrayList<PaletteContainer>();

        categories.add(createControlGroup(root));

        return categories;
    }

    private PaletteContainer createControlGroup(PaletteRoot root) {
        PaletteGroup controlGroup = new PaletteGroup("Control Group");
        List<PaletteEntry> entries = new ArrayList<PaletteEntry>();

        ToolEntry tool = new SelectionToolEntry();
        entries.add(tool);
        root.setDefaultEntry(tool);

    	tool = new MarqueeToolEntry();
    	entries.add(tool);

    	PaletteSeparator sep = new PaletteSeparator("com.ebay.tools.decisiontree.editor.sep1");
    	sep.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
    	entries.add(sep);

    	entries.add(new ConnectionCreationToolEntry(
    		"Connection Creation",
    		"Creating connection between decision nodes",
    		null,
    		Activator.getDefault().getImageRegistry().getDescriptor(Activator.CONNECTION),
    		Activator.getDefault().getImageRegistry().getDescriptor(Activator.CONNECTION))
    	);
    	
    	entries.add(new CombinedTemplateCreationEntry(
    			"Create node",
    			"Create diagram node",
    			DecisionTreeNode.class,
    			new SimpleFactory(DecisionTreeNode.class),
    			Activator.getDefault().getImageRegistry().getDescriptor(Activator.NODE),
    			Activator.getDefault().getImageRegistry().getDescriptor(Activator.NODE))
    	);


        controlGroup.addAll(entries);
        return controlGroup;
    }    
}
