package com.aire.android.aidlconn.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.aire.android.IBookManager;
import com.aire.android.aidlconn.model.Book;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {
    public AIDLService() {
    }

    private List<Book> mList = new ArrayList();

    private final IBookManager.Stub mBookManager = new IBookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            return mList;
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            mList.add(book);
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            mList.add(book);
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            mList.add(book);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBookManager.asBinder();
    }
}