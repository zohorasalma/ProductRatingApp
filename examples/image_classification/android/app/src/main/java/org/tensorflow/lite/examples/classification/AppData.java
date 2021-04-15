package org.tensorflow.lite.examples.classification;

import org.tensorflow.lite.examples.classification.model.Product;

import javax.inject.Singleton;

public class AppData
{
    @Singleton
    public static Product currentProduct = new Product();
    public static String PRODUCT_NAME = "DOVE";
}
