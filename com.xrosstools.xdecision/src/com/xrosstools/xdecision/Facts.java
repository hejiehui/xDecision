package com.xrosstools.xdecision;


public interface Facts {
    String[] getNames();
    boolean contains(String name);
	Object get(String name);
}
