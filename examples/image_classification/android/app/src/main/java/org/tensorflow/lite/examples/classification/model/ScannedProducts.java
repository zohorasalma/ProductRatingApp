package org.tensorflow.lite.examples.classification.model;

public class ScannedProducts
{
    private String userEmail;
    private int productId;
    private String productName;
    private int categoryId;
    private int varientNo;
    private String timestamp;

    public ScannedProducts()
    {
    }

    public ScannedProducts(
            String userEmail,
            int productId,
            String productName,
            int categoryId,
            int varientNo,
            String timestamp)
    {
        this.userEmail = userEmail;
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.varientNo = varientNo;
        this.timestamp = timestamp;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
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

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }
}
