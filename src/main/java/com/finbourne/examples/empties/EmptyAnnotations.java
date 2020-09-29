package com.finbourne.examples.empties;

import com.finbourne.features.LusidFeature;

public class EmptyAnnotations {
    @LusidFeature("")
    public void methodOne() {
    }

    @LusidFeature("control annotation")
    public void methodTwo() {
    }

    public void methodThree() {

    }
}
