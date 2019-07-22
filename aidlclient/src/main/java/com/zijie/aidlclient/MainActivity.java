package com.zijie.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zijie.frameworksource.Book;
import com.zijie.frameworksource.BookController;

import java.util.List;

/**
 * 参考：https://www.jianshu.com/p/29999c1a93cd
 */
public class MainActivity extends AppCompatActivity {

    private BookController bookController;
    private List<Book> bookList;
    private boolean collected;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookController = BookController.Stub.asInterface(service);
            collected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            collected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.zijie.frameworksource");
        intent.setAction("com.zijie.frameworksource.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }


    public void getBookList(View view) {
        if (collected){
            List<Book> bookList = null;
            try {
                bookList = bookController.getBookList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e("111", "getBookList: " + bookList );
        }
    }

    public void addBook(View view) {
        try {
            bookController.addBookInOut(new Book("这是来自于服务器的一本书InOut"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addBookIn(View view) {
        try {
            bookController.addBookInOut(new Book("这是来自于服务器的一本书In"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addBookOut(View view) {
        try {
            bookController.addBookInOut(new Book("这是来自于服务器的一本书Out"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
