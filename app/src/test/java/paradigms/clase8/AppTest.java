package paradigms.clase8;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class AppTest {

    private final ReactiveDB reactiveDB = new ReactiveDB();

    @Test
    void coldObservable() {
        reactiveDB.getColdObservable()
                .subscribe(Reader::read);
    }

    @Test
    void hotObservable() {
        final var source = reactiveDB.getHotObservable();

        source.subscribe(Reader::read, Throwable::printStackTrace);

        IntStream.range(1, 1_000_000).forEach(source::onNext);

    }
}
