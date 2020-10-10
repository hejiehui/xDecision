package com.xrosstools.xdecision.idea.editor.layout;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xrosstools.xdecision.idea.editor.model.*;

public class LayoutAlgorithm {
	public void layout(DecisionTreeDiagram diagram){
		findRoots(diagram);
		int nextTreePos = 0;
		for(DecisionTreeRoot root : diagram.getRoots()){
			root.getRows().clear();
			root.setWidth(visit(root.getRows(), root.getRootNode(), 0, 0));
			layout(diagram, root, nextTreePos);
			nextTreePos += root.getWidth();
		}
	}

	private void findRoots(DecisionTreeDiagram diagram){
		List<DecisionTreeRoot> newRoots = new ArrayList<DecisionTreeRoot>();
		Set<DecisionTreeNode> proceed = new HashSet<DecisionTreeNode>();

		// First keep old root at list start
		for(DecisionTreeRoot root : diagram.getRoots()){
			DecisionTreeNode node = root.getRootNode();
			if(node.getInput() == null && diagram.getNodes().contains(node)){
				newRoots.add(root);
				proceed.add(node);
			}
		}

		for(DecisionTreeNode node: diagram.getNodes()){
			if(proceed.contains(node))
				continue;
			if(node.getInput() == null)
				newRoots.add(new DecisionTreeRoot(node));
		}

		diagram.setRoots(newRoots);
	}

	private int visit(List<DecisionTreeRow> rows, DecisionTreeNode curNode, int depth, int pos){
		curNode.setVirtualPos(pos);

		if(rows.size() == depth)
			rows.add(new DecisionTreeRow());

		DecisionTreeRow row = rows.get(depth);
		row.getRowNodes().add(curNode);

		if(row.getMaxChidrenNumber() < curNode.getOutputs().size())
			row.setMaxChidrenNumber(curNode.getOutputs().size());

		if(curNode.getOutputs().size() == 0) {
			curNode.setVirtualWidth(1);
			return 1;
		}

		int virtualWidth = 0;
		for(DecisionTreeNodeConnection path : curNode.getOutputs()){
			virtualWidth += visit(rows, path.getChild(), depth + 1, pos + virtualWidth);
		}

		curNode.setVirtualWidth(virtualWidth);
		return virtualWidth;
	}

	private void layout(DecisionTreeDiagram diagram, DecisionTreeRoot root, int nextTreePos){
		Dimension size = new Dimension(diagram.getNodeWidth(), diagram.getNodeHeight());
		int horizantalSpace = diagram.isHorizantal() ? diagram.getVerticalSpace() : diagram.getHorizantalSpace();
		int verticalSpace = diagram.isHorizantal() ? diagram.getHorizantalSpace() : diagram.getVerticalSpace();

		int nodeWidth = diagram.isHorizantal() ? diagram.getNodeHeight() : diagram.getNodeWidth();
		int nodeHeight = diagram.isHorizantal() ? diagram.getNodeWidth() : diagram.getNodeHeight();

		int margin  = 100;
		int leftSpace = margin + (horizantalSpace + nodeWidth) * nextTreePos;

		for(int rowNum = 0; rowNum < root.getRows().size(); rowNum++){
			DecisionTreeRow row = root.getRows().get(rowNum);

			for(int colNum = 0; colNum < row.getRowNodes().size(); colNum++){
				DecisionTreeNode node = row.getRowNodes().get(colNum);
				node.setSize(size);

				//[NODE]__[NODE]
				int curNodeWidth = node.getVirtualWidth() * (horizantalSpace + nodeWidth) - horizantalSpace;
				int curNodePos = node.getVirtualPos() * (horizantalSpace + nodeWidth);

				int x = (int)(leftSpace + curNodePos + (curNodeWidth - nodeWidth) * diagram.getAlignment());
				int y = margin + (rowNum) * (verticalSpace + nodeHeight);

				node.setLocation(diagram.isHorizantal()? new Point(y, x): new Point(x, y));
			}
		}
	}
}