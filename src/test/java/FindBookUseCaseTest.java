import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookGender;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.usecases.book.BookDAO;
import br.edu.ifsp.domain.usecases.book.FindBookUseCase;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    }

    @ParameterizedTest
    @CsvSource({
            "1, Clean Architecture, 9780134494166",
            "2, Clean Code, 9780132350884"
    })
    @DisplayName("should return a book when id is valid")
    void shouldReturnABookWhenIdIsValid(int id, String name, String isbn) {
        Book expectedBook = new Book(
                id,
                1,
                0,
                name,
                "Robert C. Martin",
                "Prentice Hall",
                isbn,
                BookGender.TECHNICAL,
                BookStatus.AVAILABLE
        );

        when(bookDAO.findOne(id)).thenReturn(Optional.of(expectedBook));

        Optional<Book> result = sut.findOne(id);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(name);
        assertThat(result.get().getIsbn()).isEqualTo(isbn);
        verify(bookDAO).findOne(id);
    }

    @ParameterizedTest
    @CsvSource({
            "1, Clean Architecture, 9780134494166",
            "2, Clean Code, 9780132350884"
    })
    @DisplayName("should return a boook if isbn is valid")
    void shouldReturnABookIfIsbnIsValid(int id, String name, String isbn) {
        Book expectedBook = new Book(
                id,
                1,
                0,
                name,
                "Robert C. Martin",
                "Prentice Hall",
                isbn,
                BookGender.TECHNICAL,
                BookStatus.AVAILABLE
        );

        when(bookDAO.findByIsnb(isbn)).thenReturn(Optional.of(expectedBook));

        Optional<Book> result = sut.findOneByIsbn(isbn);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(name);
        assertThat(result.get().getId()).isEqualTo(id);
        verify(bookDAO).findByIsnb(isbn);
    }

    @Test
    @DisplayName("should return empty when no book is found for id")
    void shouldNotReturnABook(){
        when(bookDAO.findOne(99)).thenReturn(Optional.empty());
        int id = 99;
        Optional<Book> result = sut.findOne(id);
        assertThat(result).isEmpty();
        verify(bookDAO).findOne(id);
    }

    @ParameterizedTest()
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("should throw Illegal Argument Exception when Isbn is null")
    void shouldThrowIllegalArgumentExceptionWhenIdIsbnIsNull(String isbn) {
        assertThatIllegalArgumentException().isThrownBy(() -> sut.findOneByIsbn(isbn));
        verifyNoInteractions(bookDAO);
    }

    @Test
    @DisplayName("Should return all book")
    void shouldReturnAllBooks(){
        when(bookDAO.findAll()).thenReturn(books);

        assertThat(sut.findAll()).isEqualTo(books);

        verify(bookDAO).findAll();
    }
}