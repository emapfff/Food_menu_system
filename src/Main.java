//Emil Davlityarov
//e.davlityarov@innopolis.university
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CompositeMenu MainMenu = new CompositeMenu("Main Menu");
        CompositeMenu AppetizerMenu = new CompositeMenu("Appetizer Menu");
        CompositeMenu DessertMenu = new CompositeMenu("Dessert Menu");
        MainMenu.addComponent(AppetizerMenu);
        MainMenu.addComponent(DessertMenu);
        IMenu garlicBread = new VegetarianDecorator(new MenuItem(5.5, "Garlic bread"));
        IMenu chickenWings = new SpiceDecorator(new MenuItem(12.5, "Chicken wings"));
        IMenu tomatoSoup = new SpiceDecorator(new VegetarianDecorator(new MenuItem(10.5, "Tomato soup")));
        AppetizerMenu.addComponent(garlicBread);
        AppetizerMenu.addComponent(chickenWings);
        AppetizerMenu.addComponent(tomatoSoup);
        IMenu pie1 = new MenuItem(4.5, "Pie");
        IMenu pie2 = new SpiceDecorator(new MenuItem(4.5, "Pie"));
        IMenu iceCream = new MenuItem(3.0, "Ice cream");
        DessertMenu.addComponent(pie1);
        DessertMenu.addComponent(pie2);
        DessertMenu.addComponent(iceCream);
        MainMenu.print();


    }
}
interface IMenu {
    void print();
    String getName();
    double getPrice();
}
interface MenuComponent extends IMenu{
    void addComponent(IMenu iMenu);
    void removeComponent(IMenu iMenu);
}
class CompositeMenu implements IMenu, MenuComponent{
    private final String name;
    private final List<IMenu> menuItems;
    CompositeMenu(String name){
        this.name = name;
        menuItems = new ArrayList<>() ;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public double getPrice(){
        double result = 0;
        for (IMenu iMenu: menuItems){
            result += iMenu.getPrice();
        }
        return result;
    }
    @Override
    public void print(){
        System.out.println(this.name + " [ $" + this.getPrice() + " ]");
        for (IMenu iMenu: menuItems){
            iMenu.print();
        }
    }
    @Override
    public void addComponent(IMenu iMenu){
        menuItems.add(iMenu);
    }

    @Override
    public void removeComponent(IMenu iMenu){
        menuItems.remove(iMenu);
    }
}

class MenuItem implements IMenu{
    private final String name;
    private final double price;

    MenuItem(double price, String name){
        this.price = price;
        this.name = name;
    }
    @Override
    public String getName(){
        return this.name;
    }
    @Override
    public double getPrice() {
        return this.price;
    }
    @Override
    public void print(){
        System.out.println(this.name + ", $" + this.price);
    }
}
abstract class MenuItemDecorator implements IMenu{
    protected IMenu menuItem;
    public MenuItemDecorator(IMenu iMenu){
        menuItem = iMenu;
    }
    @Override
    public String getName(){
        return this.menuItem.getName();
    }

    @Override
    public double getPrice(){
        return this.menuItem.getPrice();
    }

    @Override
    public void print(){
        System.out.println(this.menuItem.getName() + ", $" + this.menuItem.getPrice());
    }
}
class SpiceDecorator extends MenuItemDecorator{
    public SpiceDecorator (IMenu iMenu){
        super(iMenu);
    }

    @Override
    public String getName(){
        return this.menuItem.getName();
    }

    @Override
    public double getPrice(){
        return  this.menuItem.getPrice() + 2;
    }

    @Override
    public void print(){
        menuItem.print();
        System.out.println("  -- This item is spicy (+ $2)");
    }
}

class VegetarianDecorator extends MenuItemDecorator{
    public VegetarianDecorator(IMenu iMenu){
        super (iMenu);
    }
    @Override
    public String getName(){
        return this.menuItem.getName();
    }

    @Override
    public double getPrice(){
        return this.menuItem.getPrice() + 4;
    }

    @Override
    public void print(){
        menuItem.print();
        System.out.println("  -- This item is vegetarian (+ $4)");
    }
}
