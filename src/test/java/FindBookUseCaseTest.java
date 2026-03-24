import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookGender;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.usecases.book.BookDAO;
import br.edu.ifsp.domain.usecases.book.FindBookUseCase;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class FindBookUseCaseTest {
    private Book cleanArch;
    private Book cleanCode;
    private List<Book> books;

    @Mock
    private BookDAO bookDAO;

    @InjectMocks
    FindBookUseCase sut;

    @Test()
    @DisplayName("should throw Illegal Argument Exception when id is null")
    void shouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> sut.findOne(null));
    }

    @BeforeEach
    void setUp() {
        cleanArch = new Book(
                1,
                1,
                432,
                "Clean Architecture",
                "Robert c. Martin",
                "Prentice Hall",
                "9780134494166",
                BookGender.TECHNICAL,
                BookStatus.AVAILABLE);

        cleanCode = new Book(
                2,
                1,
                320,
                "Clean Code",
                "Robert C. Martin",
                "Prentice Hall",
                "9780132350884",
                BookGender.TECHNICAL,
                BookStatus.AVAILABLE
        );

        books = new ArrayList<>();
        books.add(cleanArch);
        books.add(cleanCode);

        when(bookDAO.findAll()).thenReturn(books);
    }

    @ParameterizedTest()
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("should throw Illegal Argument Exception when Isbn is null")
    void shouldThrowIllegalArgumentExceptionWhenIdIsbnIsNull(String isbn) {
        assertThatIllegalArgumentException().isThrownBy(() -> sut.findOneByIsbn(isbn));
    }

    @Test
    @DisplayName("Should return all book")
    void shouldReturnAllBooks(){
        assertThat(sut.findAll()).isEqualTo(books);
    }
}