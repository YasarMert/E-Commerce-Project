package Main;

import com.formdev.flatlaf.FlatLightLaf;
import models.Product;
import ui.App;

// Yaşar Mert Türkmen
// Öğrenci No:244101081
//
// Mustafa Kaan Nart
// Öğrenci No:244101089
//
// Barış Bursalı
// Öğrenci No:244101025

public class Main {

    public static void main(String[] args) {
        Product.createProductList();

        FlatLightLaf.setup();
        App LoginFrame = new App();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
    }

}
