module br.edu.ifsp {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.edu.ifsp to javafx.fxml;
    opens br.edu.ifsp.domain.entities.book;

    exports br.edu.ifsp;
    exports br.edu.ifsp.domain.usecases.book;
    exports br.edu.ifsp.domain.usecases.utils;
}