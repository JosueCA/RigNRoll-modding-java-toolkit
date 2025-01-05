/*
 * Decompiled with CFR 0.151.
 */
package menu;

public class Item {
    public int id;
    public int price;
    public int auth;
    public int condition;
    public String name;

    public Item(int _price, int _auth, int _condition, String _name, int _id) {
        this.price = _price;
        this.auth = _auth;
        this.condition = _condition;
        this.name = _name;
        this.id = _id;
    }

    public Item(int _price, int _auth, int _condition, String _name) {
        this.price = _price;
        this.auth = _auth;
        this.condition = _condition;
        this.name = _name;
    }
}

