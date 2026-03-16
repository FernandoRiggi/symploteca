import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookGender;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.usecases.book.BookDAO;
import br.edu.ifsp.domain.usecases.book.CreateBookUseCase;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateBookUseCaseTest {
    private Book book;

    @Mock
    private BookDAO bookDAO;

    @InjectMocks
    private CreateBookUseCase createBookUseCase;

    @BeforeEach
    void setUp() {
        book = new Book(
                1,
                320,
                "Clean Code",
                "Robert C. Martin",
                "Prentice Hall",
                "9780132350884",
                BookGender.TECHNICAL,
                BookStatus.AVAILABLE);
    }

    @Test
    @DisplayName("Should create a book successfully")
    void shouldCreateBookSuccessfully() {
        when(bookDAO.create(book)).thenReturn(1);

        createBookUseCase.insert(book);
        verify(bookDAO).create(book);
    }

    @Test
    @DisplayName("Should throw when name is empty")
    void shouldFailWhenNameIsEmpty() {
        book.setName("");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> createBookUseCase.insert(book));
    }

    @Test
    @DisplayName("Should throw when ISBN already exists")
    void shouldFailWhenIsbnAlreadyExists() {
        when(bookDAO.findByIsnb(book.getIsbn())).thenReturn(Optional.of(book));

        assertThatExceptionOfType(EntityAlreadyExistsException.class)
                .isThrownBy(() -> createBookUseCase.insert(book));
    }
}
