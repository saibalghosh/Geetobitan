package com.druidzworks.geetobitan.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.druidzworks.geetobitan.entities.Alphabets;
import com.druidzworks.geetobitan.entities.Song;

/**
 * Created by Saibal Ghosh on 8/18/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static String DB_PATH;// = "/data/data/com.druidzworks.geetobitan/databases/";
    private static String DB_Name = "Geetobitan";
    private SQLiteDatabase geetobitanDB;
    private final Context dbContext;

    public DBHelper(Context context)
    {
        super(context, DB_Name, null, 1);
        DB_PATH = context.getFilesDir().getPath();
        this.dbContext = context;
    }

    public void CreateDB() throws IOException
    {
        boolean dbExists = CheckDatabase();
        if(!dbExists)
        {
            //this.getReadableDatabase();
            try
            {
                CopyDatabase();
            }
            catch(IOException ex)
            {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean CheckDatabase()
    {
        SQLiteDatabase checkDB = null;

        try
        {
            String dbPath = DB_PATH + "/" + DB_Name;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException ex)
        {

        }

        if(checkDB != null)
        {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void CopyDatabase() throws IOException {
        if(!CheckDatabase()) {
            InputStream input = dbContext.getAssets().open("Geetobitan.db");
            String outputFileName = DB_PATH + "/" + DB_Name;
            OutputStream output = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = input.read(buffer)) > 0)
                {
                    output.write(buffer, 0, length);
                }
            try
            {
                output.flush();
                output.close();
                input.close();
            }
            catch (IOException ex)
            {

            }
        }
    }

    public void OpenDatabase() throws SQLException
    {
        String path = DB_PATH + "/" + DB_Name;
        try
        {
            CopyDatabase();
        }
        catch (IOException ex)
        {

        }
        geetobitanDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

    }


    public HashMap<Integer,String> GetSongInitialHeaders()
    {
        HashMap<Integer,String> lstSongHeaders = new HashMap<>();
        lstSongHeaders.put(0, "বর্ণানুক্রমিক তালিকা");
        try
        {
            OpenDatabase();
        }
        catch (SQLException ex)
        {

        }
        String query = "SELECT A.Id, A.Alphabet, COUNT(NameBeng) FROM AlphabetList A, Title T  WHERE A.Id = T.AlphabetId GROUP BY AlphabetId";

        try {
            Cursor cursor = geetobitanDB.rawQuery(query, null);
            /*
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    String header = cursor.getString(1);
                    int count = Integer.parseInt(cursor.getString(2));
                    lstSongHeaders.put(id, header + "(" + count + ")");
                } while (cursor.moveToNext());
            }
            */

            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                int id = Integer.parseInt(cursor.getString(0));
                String header = cursor.getString(1);
                int count = Integer.parseInt(cursor.getString(2));
                lstSongHeaders.put(id, header + "(" + count + ")");
                cursor.moveToNext();
            }
        }
        catch(Exception ex)
        {

        }
        finally {
            if(geetobitanDB.isOpen())
            {
                geetobitanDB.close();
            }
        }

        //lstSongHeaders.remove("");

        return lstSongHeaders;

    }
    public List<String> GetSongsByInitialHeaders(int initialId)
    {
        List<String> lstSongs = new ArrayList<>();
        OpenDatabase();
        String query = "SELECT NameBeng FROM Title WHERE AlphabetId = " + initialId;

        try {
            Cursor cursor = geetobitanDB.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String song = cursor.getString(0);
                    lstSongs.add(song);
                } while (cursor.moveToNext());
            }
        }
        catch(Exception ex)
        {

        }
        finally {
            if(geetobitanDB.isOpen())
            {
                geetobitanDB.close();
            }
        }

        return lstSongs;
    }
    public List<Alphabets> GetAlphabetMenu() throws SQLException
    {
        List<Alphabets> lstAlphabetMenu = new ArrayList<Alphabets>();
        String query = "SELECT A.Id, A.Alphabet, count(NameBeng) FROM AlphabetList A, Title T  WHERE A.Id = T.AlphabetId GROUP BY AlphabetId";

        try
        {
            CopyDatabase();
        }
        catch (IOException ex)
        {

        }
        SQLiteDatabase dbGeetobitan = SQLiteDatabase.openDatabase(DB_PATH + "/" + DB_Name, null, SQLiteDatabase.OPEN_READONLY);

        try {
            Cursor cursor = dbGeetobitan.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Alphabets alpha = new Alphabets();
                    alpha.set_alphabetId(Integer.parseInt(cursor.getString(0)));
                    alpha.set_character(cursor.getString(1));
                    alpha.set_prefixCount(Integer.parseInt(cursor.getString(2)));

                    lstAlphabetMenu.add(alpha);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        finally {
            if(dbGeetobitan.isOpen()) {
                dbGeetobitan.close();
            }
        }

        return lstAlphabetMenu;
    }

    public List<Song> GetSongListByPrefix(int AlphabetId) throws SQLException
    {
        List<Song> lstSongTitles = new ArrayList<Song>();
        String query = "SELECT * FROM Title WHERE AlphabetId = " + AlphabetId;

        SQLiteDatabase dbSongsByPrefix = SQLiteDatabase.openDatabase(DB_PATH + "/" + DB_Name, null, SQLiteDatabase.OPEN_READONLY);

        try
        {
            Cursor cursor = dbSongsByPrefix.rawQuery(query, null);
            if(cursor.moveToFirst())
            {
                do {
                    Song song = new Song();
                    song.set_title(cursor.getString(0));
                    lstSongTitles.add(song);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception ex){}
        finally {
            if(dbSongsByPrefix.isOpen())
            {
                dbSongsByPrefix.close();
            }
        }

        return lstSongTitles;

    }

    @Override
    public synchronized void close()
    {
        if(geetobitanDB != null)
        {
            geetobitanDB.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db){}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}