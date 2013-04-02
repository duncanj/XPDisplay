/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// java annotation

@Retention(RetentionPolicy.RUNTIME)
public @interface Metadata {
    String displayLabel();
}
    
