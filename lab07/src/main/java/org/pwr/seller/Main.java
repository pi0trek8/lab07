package org.pwr.seller;

import org.pwr.seller.controller.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.initialize();
        controller.downloadContent();
        controller.createListeners();

    }
}
