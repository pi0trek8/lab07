package org.pwr.seller;

import org.pwr.seller.frontend.Gui;
import org.pwr.seller.controller.Controller;
import org.pwr.seller.service.Service;

public class Main {
    public static void main(String[] args) {
        Gui gui = new Gui();
        Service service = new Service();
        Controller controller = new Controller(gui, service);
        controller.initialize();
        controller.downloadContent();
        controller.createListeners();

    }
}
