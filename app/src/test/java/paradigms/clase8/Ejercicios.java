package paradigms.clase8;


import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import java.lang.module.FindException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Resuelve cada uno de los ejercicios según el enunciado del test. Imprime cada ejecución de forma que se pueda
 * corroborar el correcto funcionamiento.
 *
 */
public class Ejercicios {

    /** TODO:
     *    - Multiplica todos los elementos por 2.
     */
    @Test
    void multiplicaTodoPor2() {

        final var source = Observable.fromIterable(List.of(1, 2, 3, 4, 5, 6));
        source.map(integer -> integer*2)
                .subscribe(System.out::println);


    }

    /** TODO:
     *    - Quédate solo con los elementos pares y multiplícalos por 2.
     */
    @Test
    void multiplicaLosParesPor2() {
        final var source = Observable.fromIterable(List.of(1, 2, 3, 4, 5, 6));
        source.filter(integer -> integer % 2 == 0)
                .map(integer -> integer * 2)
                .subscribe(System.out::println);

    }

    /** TODO:
     *    - Repite 3 veces cada elemento.
     */
    @Test
    void repite3VecesCadaElemento() {
        final var source = Observable.fromIterable(List.of(1, 2, 3, 4, 5, 6));
        source.map(integer -> integer.toString().repeat(3))
                .subscribe(System.out::println);

    }

    /** TODO:
     *    - Repite 3 veces cada elemento par y 1 sola vez los elementos impares.
     */
    @Test
    void repite3VecesLosParesY1VezLosImpares() {
        final var observable = Observable.fromIterable(List.of(1, 2, 3, 4, 5, 6));

        observable
                .groupBy(value -> value % 2 == 0 ? "PAR" : "IMPAR")
                .subscribe(
                        groupedObservable ->
                                groupedObservable.subscribe(
                                        value ->
                                                Optional.of(groupedObservable.getKey().equals("PAR"))
                                                        .filter(isEven -> isEven)
                                                        .map(even -> Observable.fromIterable(Collections.nCopies(3,value)))
                                                        .orElseGet(() -> Observable.just(value)).subscribe(System.out::println)));

    }

    /** TODO:
     *    - Emite elementos hasta que encuentres un elemento impar, entonces lanza un error.
     */
    @Test
    void emiteElementosHastaQueEncuentresUnElementoImpar() {
        final var source = Observable.fromIterable(List.of(8, 2, 3, 4, 6, 6));
        source
                .groupBy(value -> value % 2 == 0 ? "PAR" : "IMPAR")
                .subscribe(
                        groupedObservable ->
                                groupedObservable.subscribe(
                                        value ->
                                                Optional.of(groupedObservable.getKey().equals("PAR"))
                                                        .filter(isEven -> isEven)
                                                        .map(even -> Observable.just(value).subscribe(System.out::println))
                                                        .orElseThrow(() ->
                new IllegalArgumentException("Encontramos un valor impar: " + value + " --- Cortamos la ejecución")),
                                        Throwable::printStackTrace));

    }
}
