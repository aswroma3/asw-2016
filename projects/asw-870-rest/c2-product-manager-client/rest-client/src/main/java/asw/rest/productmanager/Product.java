package asw.rest.productmanager;

import javax.xml.bind.annotation.*;

/**
 * Un prodotto ha un id, una descrizione e un prezzo.
 * Stesse definizioni che nel servizio, ma senza le annotazioni JPA.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    @XmlAttribute
	private int id;

    @XmlElement
	private String description;

    @XmlElement
	private int price;

	public Product() {
	}

    public Product(int id, String description, int price) {
		this.id = id;
		this.description = description;
		this.price = price;
    }

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public int getPrice() {
		return price;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product[" +
			"id=" + id +
			", description=" + description +
			", price=" + price +
			"]";
	}

}
