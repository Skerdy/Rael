package com.rayonit.RaEL.core;

import java.util.HashMap;

/**
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

public interface RaelOperationGenerator<T, V extends HashMap> extends StaticOperationGenerator<T> {

    T generate(V v);

}
