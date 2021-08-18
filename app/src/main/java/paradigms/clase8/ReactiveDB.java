package paradigms.clase8;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class ReactiveDB {

    public Observable<Integer> getColdObservable() {
        return Observable.range(1, 50);
    }

    public PublishSubject<Integer> getHotObservable() {
        return PublishSubject.create();
    }
}
