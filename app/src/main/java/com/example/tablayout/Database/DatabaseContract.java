package com.example.tablayout.Database;


import android.provider.BaseColumns;

class DatabaseContract {
    static final String TABLE_INDONESIA = "tb_indonesia";
    static final String TABLE_BETAWI = "tb_betawi";

    static final class KataColumns implements BaseColumns{
        //kata awal
        static final String WORD = "word";
        //deskripsi kata
        static final String DESCRIPTION = "description";
    }
}
