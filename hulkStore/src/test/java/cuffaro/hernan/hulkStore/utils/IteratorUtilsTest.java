package cuffaro.hernan.hulkStore.utils;

import java.util.Iterator;

public class IteratorUtilsTest {

	public static<T> Iterable<T> iteratorToIterable(Iterator<T> iterator) {
		return () -> iterator;
	}
}
