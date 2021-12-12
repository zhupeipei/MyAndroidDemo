package com.aire.android.aidlconn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.aire.android.IBookManager;
import com.aire.android.aidlconn.model.Book;
import com.aire.android.aidlconn.service.AIDLService;
import com.aire.android.main.MainApplication;
import com.aire.android.test.R;

import java.util.List;

public class AidlConnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_conn);

        bindService(new Intent(MainApplication.INSTANCE, AIDLService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IBookManager bookManager = IBookManager.Stub.asInterface(service);
                try {
                    bookManager.addBookIn(new Book("张三传", 1.0f));
                    bookManager.addBookOut(new Book("李四史", 3.0f));
                    bookManager.addBookInOut(new Book("王二史", 2.0f));
                    List<Book> books = bookManager.getBooks();
                    for (Book book : books) {
                        System.out.println(book.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }

}