package com.xrosstools.xdecision.sample;

import java.util.Date;

public class Health {

    /**
     * Customer name;
     */
    private final String name;
    private final String status;

    /**
     * VIP status.
     */
    private final boolean vip;
    private final Date date = new Date();
    
    private Health child;

    /**
     * Creates a customer with the given name and status.
     */
    public Health (String name, boolean vip, String status) {
        this.name = name;
        this.status = status;
        this.vip = vip;
    }
    
    public void setChild(Health child) {
        this.child = child;
    }
}