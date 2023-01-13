/**
 * @Autor Piotr Szczypior
 * @Jar mvn package in the directory of project
 * @Run-jar java -cp lab07.jar org.pwr.seller.Main arguments
 *
 * @Argumets
 * @1 port
 * @2 server url
 * @3 host
 */

package org.pwr.seller;

import org.pwr.seller.controller.Controller;
import org.pwr.seller.frontend.Gui;
import org.pwr.seller.service.Service;

public class Main {
    public static void main(String[] args) {


        Gui gui = new Gui();
        Service service = new Service();
        Controller controller = new Controller(gui, service);
        controller.initialize(args);
        controller.downloadContent();
        controller.createListeners();

    }
}
