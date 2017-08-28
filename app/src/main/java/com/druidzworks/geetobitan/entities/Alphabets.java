package com.druidzworks.geetobitan.entities;

/**
 * Created by Saibal Ghosh on 8/18/2017.
 */

public class Alphabets {
    private int _alphabetId;
    private int _prefixCount;
    private String _bengaliChar;

    public int get_alphabetId() { return _alphabetId; }

    public void set_alphabetId(int alphabetId) { this._alphabetId = alphabetId; }

    public int get_prefixCount() {
        return _prefixCount;
    }

    public void set_prefixCount(int _prefixCount) {
        this._prefixCount = _prefixCount;
    }

    public String get_bengaliChar() {
        return _bengaliChar;
    }

    public void set_character(String characters) {
        this._bengaliChar = characters;
    }

}