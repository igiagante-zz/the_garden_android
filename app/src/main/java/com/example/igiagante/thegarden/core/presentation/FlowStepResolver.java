package com.example.igiagante.thegarden.core.presentation;

import android.os.Bundle;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
public interface FlowStepResolver {

    void goToNextStep(Bundle bundle, Class clazz);
}
