# mini CAD
**姓名**: 范钊瑀

**学号**: 3190105838


## 思路与设计
### 布局设计
程序中的整体框架为由三部分组成，最底层的布局为`BorderLayout`
![](https://raw.githubusercontent.com/FanBB2333/picBed/main/img/20211112175746.png)

### 数据结构

#### `Pair`类
 `Pair`类是一个泛型类，用于存储一个坐标点的`x`和`y`值，在本次的工程中，由于获取到的坐标是`Integer`整型，所以在`Shape`等类中大量使用了`Pair<Integer>`的类型，其中，`getX`和`getY`的方法可以分别得到`x`和`y`的值，为了方便也添加了`setNew`方法用于更新坐标。
此外，由于需要计算是否鼠标的位置在椭圆内/直线上，我们也需要一个计算距离的函数，因此在`Pair`类中也存在了`distance`方法，用于计算两点之间的距离。
```java
class Pair<T> implements Serializable{
    private T x;
    private T y;
    Pair(T _x, T _y){
        x = _x;
        y = _y;
    }

    public T getX(){
        return x;
    }

    public T getY() {
        return y;
    }

    public void setNew(T _x, T _y){
        x = _x;
        y = _y;
    }

    double distance(Pair<T> p){
        return Math.sqrt(Math.pow((int)p.getX() - (int)x, 2) + Math.pow((int)p.getY() - (int)y, 2));
    }
}
```

#### 图形类
`Shape`类是一个抽象类，它作为所有图形的父类，其中包含了所有图形共有的属性，包含有确定图形位置的p1和p2两个坐标点，以及确定图形颜色的color属性，和存在有`thickness`变量以用于保存绘制图形的粗细

```java
abstract class Shape implements Serializable{
    public Pair<Integer> p1 = new Pair<>(0, 0);
    public Pair<Integer> p2 = new Pair<>(0, 0);
    public Color color = Color.black;
    public double thickness = 1;

    public abstract void draw(Graphics g, Color c);

    public abstract boolean isInside(Pair<Integer> p);

    public abstract void move(Pair<Integer> p);

    public void resize(double ratio){
        Pair<Integer> center = new Pair<>((int)((p1.getX() + p2.getX()) / 2), (int)((p1.getY() + p2.getY()) / 2));
        Pair<Integer> new_p1 = new Pair<>((int)(center.getX() - (center.getX() - p1.getX()) * ratio), (int)(center.getY() - (center.getY() - p1.getY()) * ratio));
        Pair<Integer> new_p2 = new Pair<>((int)(center.getX() - (center.getX() - p2.getX()) * ratio), (int)(center.getY() - (center.getY() - p2.getY()) * ratio));
        p1 = new_p1;
        p2 = new_p2;
    }

    public void setP1(Pair<Integer> p1) {
        this.p1 = p1;
    }

    public void setP2(Pair<Integer> p2) {
        this.p2 = p2;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public Color getColor() {
        return color;
    }

    public double getThickness(){
        return thickness;
    }

    public Pair<Integer> getTopLeftPoint(){
        return new Pair<>(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));
    }

    public Pair<Integer> getBottomRightPoint(){
        return new Pair<>(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()));
    }

}
```

### 


## 功能实现

### 放置图形

### 尺寸变化
#### 大小改变

#### 粗细改变