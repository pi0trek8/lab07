package org.pwr.client.status;

import interfaces.IStatusListener;
import model.Status;
import org.pwr.client.controller.Controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StatusListenerImpl extends UnicastRemoteObject implements IStatusListener {
    private Controller controller;

    public StatusListenerImpl(Controller controller) throws RemoteException {
        this.controller = controller;
    }
    @Override
    public void statusChanged(int id, Status status) throws RemoteException {
        controller.refreshStatus(id, status);
    }
}
