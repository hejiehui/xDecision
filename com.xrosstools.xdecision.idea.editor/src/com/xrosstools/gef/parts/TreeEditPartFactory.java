package com.xrosstools.gef.parts;

public interface TreeEditPartFactory {
    TreeEditPart createEditPart(TreeEditPart parent, Object model);
}
