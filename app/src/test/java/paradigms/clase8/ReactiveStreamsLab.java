package paradigms.clase8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.reactivex.rxjava3.core.Observable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

public class ReactiveStreamsLab {

  @Test
  void justEmmitOneValue() {
    final var singleEvent = "One event... whatever";
    final var observableSource = Observable.just(singleEvent);

    observableSource.subscribe(event -> assertEquals(singleEvent, event));
  }

  @Test
  void emittingMultiplesValues() {
    final var multiplesEvents = List.of("event1", "event2", "event3");
    final var observableSource = Observable.fromIterable(multiplesEvents);

    observableSource
        .map(event -> event.concat("-checked"))
        .subscribe(event -> assertTrue(event.contains("-checked")));
  }

  @Test
  void observablesLifeCicle() {
    final var multiplesEvents = List.of("a", "b", "c");
    final var observableSource = Observable.fromIterable(multiplesEvents);
    final var stringBuilder = new StringBuilder();

    observableSource.subscribe(
        stringBuilder::append, // onNext
        Throwable::printStackTrace, // onError
        () -> stringBuilder.append("_sarasa")); // onComplete

    assertEquals("abc_sarasa", stringBuilder.toString());
  }

  @Test
  void scanningObservables() {
    final var source = List.of("1", "2", "3", "4");
    final var observable = Observable.fromIterable(source);
    final var result = new StringBuilder();
    observable.scan("", (a, b) -> a + b).subscribe(result::append);

    assertEquals("1121231234", result.toString());
  }

  @Test
  void groupingByEvents() {
    final var source = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    final var observable = Observable.fromIterable(source);
    int[] PAR = {0};
    int[] IMPAR = {0};

    observable
        .groupBy(value -> value % 2 == 0 ? "EVEN" : "ODD")
        .subscribe(
            groupedObservable ->
                groupedObservable.subscribe(
                    value ->
                        Optional.of(groupedObservable.getKey().equals("EVEN"))
                            .filter(isEven -> isEven)
                            .map(even -> PAR[0] += value)
                            .orElseGet(() -> IMPAR[0] += value)));

    assertEquals(30, PAR[0]);
    assertEquals(25, IMPAR[0]);
  }

  @Test
  void filteringEvents() {
    final var source = List.of(23, 12, 56, 28, 45);
    final var observable = Observable.fromIterable(source);
    final var accumulator = new AtomicInteger();

    observable
        .filter(value -> value < 30)
        .subscribe(under30 -> accumulator.getAndAccumulate(under30, Integer::sum));

    assertEquals(63, accumulator.get());
  }
}
