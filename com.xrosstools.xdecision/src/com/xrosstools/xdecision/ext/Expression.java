package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public interface Expression {
    Object evaluate(Facts facts);
}
