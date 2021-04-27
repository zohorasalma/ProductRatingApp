package org.tensorflow.lite.examples.classification.model;

public class Product
{
    private int productId;
    private String productName;
    private String productDescription;
    private int categoryId;
    private int varientNo;
    private float rating;
    private int ratingCount;
    private float sumRating;
    private String photoUrl;


    public Product()
    {
    }

    public Product(
            int productId,
            String productName,
            String productDescription,
            int categoryId,
            int varientNo,
            float rating,
            int ratingCount,
            float sumRating,
            String photoUrl)
    {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.categoryId = categoryId;
        this.varientNo = varientNo;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.sumRating = sumRating;
        this.photoUrl = photoUrl;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductDescription()
    {
        return productDescription;
    }

    public void setProductDescription(String productDescription)
    {
        this.productDescription = productDescription;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getVarientNo()
    {
        return varientNo;
    }

    public void setVarientNo(int varientNo)
    {
        this.varientNo = varientNo;
    }

    public float getRating()
    {
        return rating;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }

    public int getRatingCount()
    {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount)
    {
        this.ratingCount = ratingCount;
    }

    public float getSumRating()
    {
        return sumRating;
    }

    public void setSumRating(float sumRating)
    {
        this.sumRating = sumRating;
    }

    public String getPhotoUrl()
    {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl)
    {
        this.photoUrl = photoUrl;
    }
}
