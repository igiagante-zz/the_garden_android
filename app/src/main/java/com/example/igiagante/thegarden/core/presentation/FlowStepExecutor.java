package com.example.igiagante.thegarden.core.presentation;

import android.os.Bundle;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
public class FlowStepExecutor {

    public void goToNextStep(Bundle bundle, Class clazz, FlowStepResolver flowStepResolver) {
        flowStepResolver.goToNextStep(bundle, clazz);
    }
}
