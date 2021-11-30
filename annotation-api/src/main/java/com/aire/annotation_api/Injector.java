package com.aire.annotation_api;

import com.aire.annotation_api.finder.Finder;

public interface Injector<T> {

    void inject(T host, Object source, Finder finder);
}
