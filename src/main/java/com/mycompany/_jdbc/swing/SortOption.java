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
public final class SortOption {

    String display;
    SortType sortType;

    public SortOption() {
        sortType = SortType.NONE;
    }

    /**
     *
     * @param display
     * @param sortType
     */
    public SortOption(String display, SortType sortType) {
        this.display = display;
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return display; //To change body of generated methods, choose Tools | Templates.
    }

}

enum SortType {
    NONE("none"),
    DESC("desc"),
    ASC("asc");

    public final String label;

    private SortType(String label) {
        this.label = label;
    }
}
