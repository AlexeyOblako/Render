module com.cgvsu {
    exports com.cgvsu.model;
    exports com.cgvsu.math;
    exports com.cgvsu.render_engine;
    exports com.cgvsu.objreader;
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;
    //requires junit;


    opens com.cgvsu to javafx.fxml;
    exports com.cgvsu;
}