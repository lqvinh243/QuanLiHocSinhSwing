/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

/**
 *
 * @author Admin
 */
public class BulkActionResult {

    private final Integer total;
    private Integer success;
    private Integer fail;

    public BulkActionResult(Integer total) {
        this.total = total;
        this.success = 0;
        this.fail = 0;
    }

    public void Success() {
        this.success++;
    }

    public void Fail() {
        this.fail++;
    }
    
    public Integer getTotal(){
        return this.total;
    }
    
    public Integer getSuccess(){
        return this.success;
    }
    
    public Integer getFail(){
        return this.fail;
    }
}
