package com.finbourne.examples.duplicates;

import com.finbourne.features.LusidFeature;

public class DuplicateAnnotations {

    @LusidFeature("Duplicate Test")
    public void methodOne(){}

    @LusidFeature("Duplicate Test")
    public void methodTwo(){}

    @LusidFeature("control annotation")
    public void methodThree(){}

    public void methodFour() {}
}
