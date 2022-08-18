package com.xrosstools.idea.gef.parts;

public interface EditPartFactory {
    EditPart createEditPart(EditPart parent, Object model);
}
