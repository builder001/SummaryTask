package ua.nure.tereshchenko.SummaryTask4.db.entity;
/**
 * Edition entity.
 *
 *
 */
public class Edition extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5610736585433411713L;
	private String name;
    private Integer price;
    private Integer categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Edition [name=" + name + ", price=" + price + ", categoryId="
                + categoryId + ", getId()=" + getId() + "]";
    }

}
