package com.xrosstools.gef.parts;

import java.util.List;

public interface EditPartHandler {
    void reorderChild(List parts, EditPart editPart, int index);
    void addChildModel(List parts, Object child, int index);
    void removeChild(List parts, EditPart childPart);
}
