package org.pwr.client.services;

import interfaces.IStatusListener;
import model.Status;

import java.rmi.RemoteException;

public class StatusService implements IStatusListener {

    @Override
    public void statusChanged(int id, Status status) throws RemoteException {

    }
}
