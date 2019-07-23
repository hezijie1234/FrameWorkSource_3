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

import java.util.ArrayList;
import java.util.List;

/**
 * 参考：https://www.jianshu.com/p/29999c1a93cd
 * https://blog.csdn.net/luoyanglizi/article/details/51958091
 *
 * 测试得出结论：到这里基本上就可以下结论了：AIDL中的定向 tag 表示了在跨进程通信中数据的流向，其中 in 表示数据只能由客户端流向服务端，
 * out 表示数据只能由服务端流向客户端，而 inout 则表示数据可在服务端与客户端之间双向流通。其中，数据流向是针对在客户端中的那个传入方法的对象而言的。
 * in 为定向 tag 的话表现为服务端将会接收到一个那个对象的完整数据，但是客户端的那个对象不会因为服务端对传参的修改而发生变动；out 的话表现为服务端将会接收到那个
 * 对象的参数为空的对象，但是在服务端对接收到的空对象有任何修改之后客户端将会同步变动；inout 为定向 tag 的情况下，服务端将会接收到客户端传来对象的完整信息，
 * 并且客户端将会同步服务端对该对象的任何变动，而反之服务端不能同步客户端对该对象的改动，就是说客户端对某个对象进行修改后，服务端已经获取到的该对象不会同步修改，
 * 反之如果服务器端对该对象进行了修改时，在定向tag为out 和InOut时，客户端的这个对象会进行同步修改。
 ---------------------
 作者：lypeer
 来源：CSDN
 原文：https://blog.csdn.net/luoyanglizi/article/details/51958091
 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class MainActivity extends AppCompatActivity {

    private BookController bookController;
    private List<Book> bookList = new ArrayList<>();
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

    /**
     * @param view 07-23 09:47:28.006 24581-24599/com.zijie.frameworksource E/111: addBookInOut: book name：这是来自于服务器的一本书InOut
     *             07-23 09:47:30.553 24716-24716/com.zijie.aidlclient E/111: getBookList: [book name：服务器改了新书的名字 InOut]
     *             07-23 09:47:33.985 24581-24599/com.zijie.frameworksource E/111: addBookInOut: 这是来自于服务器的一本书In
     *             07-23 09:47:35.621 24716-24716/com.zijie.aidlclient E/111: getBookList: [book name：服务器改了新书的名字 InOut, book name：这是来自于服务器的一本书In]
     *             07-23 09:47:40.111 725-20724/? I/VDEC: VDM_HalProcess, wait time out for G_VDMHWDONEEVENT
     *             07-23 09:47:47.567 24581-24599/com.zijie.frameworksource E/111: addBookInOut: null
     *             07-23 09:47:51.561 24716-24716/com.zijie.aidlclient E/111: getBookList: [book name：服务器改了新书的名字 InOut, book name：这是来自于服务器的一本书In, book name：服务器改了新书的名字 Out]
     *             <p>
     *             通过上面的测试日志可以知道，当定向tag为InOut时，服务器和客户端的对象的数据是实时刷新的，
     */

    public void getBookList(View view) {
        Log.e("111", "客户端的数据是 " + bookList);
//        if (collected) {
//            List<Book> bookList = null;
//            try {
//                bookList = bookController.getBookList();
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//
//        }
    }

    public void getServerBookList(View view) {

        if (collected) {
            List<Book> bookList = null;
            try {
                bookList = bookController.getBookList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e("111", "服务端的数据是 " + bookList);
        }
    }


    public void addBook(View view) {
        try {
            Book book = new Book("这是来自于服务器的一本书InOut");
            bookList.add(book);
            bookController.addBookInOut(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void modifyLocalInout(View view) {
        Book book = bookList.get(0);
        book.setName("本地修改书籍对象后InOut");
    }
    public void modifyLocalIn(View view) {
        Book book = bookList.get(1);
        book.setName("本地修改书籍对象后In");
    }
    public void modifyLocalOut(View view) {
        Book book = bookList.get(2);
        book.setName("本地修改书籍对象后Out");
    }

    public void addBookIn(View view) {
        try {
            Book book = new Book("这是来自于服务器的一本书In");
            bookList.add(book);
            bookController.addBookIn(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addBookOut(View view) {
        try {
            Book book = new Book("这是来自于服务器的一本书Out");
            bookList.add(book);
            bookController.addBookOut(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
