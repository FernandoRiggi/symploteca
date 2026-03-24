import br.edu.ifsp.domain.usecases.book.BookDAO;
import br.edu.ifsp.domain.usecases.book.FindBookUseCase;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindBookUseCaseTest {
    @Mock
    private BookDAO bookDAO;

    @InjectMocks
    FindBookUseCase sut;

    @Test()
    @DisplayName("should throw Illegal Argument Exception when id is null")
    void shouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> sut.findOne(null));
    }
}
