package com.solvd.delivery.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @XmlAttribute
    private long id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "price")
    private double price;

    @XmlElement(name = "sku")
    private String sku;

    public Product() {}

    public Product(long id, String name, String description, double price, String sku) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sku = sku;
    }

    @JsonGetter("id")
    public long getId() { return id; }

    @JsonSetter("id")
    public void setId(long id) { this.id = id; }

    @JsonGetter("name")
    public String getName() { return name; }

    @JsonSetter("name")
    public void setName(String name) { this.name = name; }

    @JsonGetter("description")
    public String getDescription() { return description; }

    @JsonSetter("description")
    public void setDescription(String description) { this.description = description; }

    @JsonGetter("price")
    public double getPrice() { return price; }

    @JsonSetter("price")
    public void setPrice(double price) { this.price = price; }

    @JsonGetter("sku")
    public String getSku() { return sku; }

    @JsonSetter("sku")
    public void setSku(String sku) { this.sku = sku; }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", sku='" + sku + '\'' +
                '}';
    }
}
